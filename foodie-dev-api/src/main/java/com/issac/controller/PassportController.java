package com.issac.controller;

import com.issac.pojo.Users;
import com.issac.pojo.bo.ShopCartItemBO;
import com.issac.pojo.bo.UserBO;
import com.issac.pojo.vo.UsersVO;
import com.issac.service.UserService;
import com.issac.util.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author: ywy
 * @date: 2020-05-08
 * @desc:
 */
@RestController
@RequestMapping("passport")
@Api(value = "注册登录", tags = {"用于注册登录的相关接口"})
public class PassportController extends BaseController {

    @Resource
    UserService userService;
    @Resource
    RedisOperator redisOperator;

    @GetMapping("/usernameIsExist")
    @ApiOperation(value = "用户名是否存在", notes = "用户名是否存在", httpMethod = "GET")
    public JSONResult usernameIsExist(@RequestParam String username) {
        // 1. 判断用户名不能为空
        if (StringUtils.isBlank(username)) {
            return JSONResult.errorMsg("用户名不能为空");
        }
        boolean isExist = userService.queryUsernameIsExist(username);
        if (isExist) {
            return JSONResult.errorMsg("用户名已经存在");
        }
        return JSONResult.ok();
    }

    @PostMapping("/regist")
    @ApiOperation(value = "用户名注册", notes = "用户名注册", httpMethod = "POST")
    public JSONResult regist(@RequestBody UserBO userBO,
                             HttpServletRequest request,
                             HttpServletResponse response) {
        // 0. 判断用户名和比吗不为空
        String username = userBO.getUsername();
        String password = userBO.getPassword();
        String confirmPassword = userBO.getConfirmPassword();
        if (StringUtils.isBlank(username) ||
                StringUtils.isBlank(password) ||
                StringUtils.isBlank(confirmPassword)) {
            return JSONResult.errorMsg("用户名或密码不能为空");
        }
        // 1. 判断用户名是否存在
        boolean isExist = userService.queryUsernameIsExist(username);
        if (isExist) {
            return JSONResult.errorMsg("用户名已经存在");
        }
        // 2. 密码长度不能少于6位
        if (password.length() < 6) {
            return JSONResult.errorMsg("密码长度不能小于6");
        }
        // 3. 判断两次密码是否一致
        if (!password.equals(confirmPassword)) {
            return JSONResult.errorMsg("两次密码输入不一致");
        }
        // 4. 实现注册
        Users users = userService.createUser(userBO);
//        users = setNullProperty(users);
        CookieUtils.setCookie(request, response, "user",
                JsonUtils.objectToJson(convertUsersVO(users)), true);
        synchShopcartData(users.getId(), request, response);
        return JSONResult.ok();
    }

    private UsersVO convertUsersVO(Users users) {
        // 实现用户的redis会话
        String uniqueToken = UUID.randomUUID().toString().trim();
        redisOperator.set(REDIS_USER_TOKEN + ":" + users.getId(), uniqueToken);
        UsersVO usersVO = new UsersVO();
        BeanUtils.copyProperties(users, usersVO);
        usersVO.setUserUniqueToken(uniqueToken);
        return usersVO;
    }

    @PostMapping("/login")
    @ApiOperation(value = "用户登录", notes = "用户登录", httpMethod = "POST")
    public JSONResult login(@RequestBody UserBO userBO,
                            HttpServletRequest request,
                            HttpServletResponse response) throws Exception {
        // 0. 判断用户名和比吗不为空
        String username = userBO.getUsername();
        String password = userBO.getPassword();
        if (StringUtils.isBlank(username) ||
                StringUtils.isBlank(password)) {
            return JSONResult.errorMsg("用户名或密码不能为空");
        }
        Users users = userService.queryUserForLogin(username, MD5Utils.getMD5Str(password));
        if (users == null) {
            return JSONResult.errorMsg("用户名或密码不正确");
        }
//        users = setNullProperty(users);
        CookieUtils.setCookie(request, response, "user",
                JsonUtils.objectToJson(convertUsersVO(users)), true);
        synchShopcartData(users.getId(), request, response);
        return JSONResult.ok(users);
    }

    /**
     * 注册登录成功后，同步cookie和redis数据
     */
    private void synchShopcartData(String userId,
                                   HttpServletRequest request,
                                   HttpServletResponse response) {
        // 1. redis 数据无，cookie为空，不处理
        //                cookie不为空，同步直接放入redis
        // 2. redis 有数据，cookie 为空，redis数据覆盖本地cookie
        //               cookie 不为空 以cookie中为准 覆盖删除redis中重复商品
        // 3. 同步到redis后，覆盖本地cookie购物车数据，保证本地购物车数据是同步最新的。

        // 从 redis 获取购物车
        String shopcartJsonRedis = redisOperator.get(FOODIE_SHOPCART + ":" + userId);
        // cookie 获取购物车
        String shopcartJsonCookie = CookieUtils.getCookieValue(request, FOODIE_SHOPCART, true);
        if (StringUtils.isBlank(shopcartJsonRedis)) {
            if (StringUtils.isNotBlank(shopcartJsonCookie)) {
                redisOperator.set(FOODIE_SHOPCART + ":" + userId, shopcartJsonCookie);
            }
        } else {
            if (StringUtils.isNotBlank(shopcartJsonCookie)) {
                List<ShopCartItemBO> shopcartListRedis =
                        JsonUtils.jsonToList(shopcartJsonRedis, ShopCartItemBO.class);
                List<ShopCartItemBO> shopcartListCookie =
                        JsonUtils.jsonToList(shopcartJsonCookie, ShopCartItemBO.class);
                List<ShopCartItemBO> pendingDelete = new ArrayList<>();
                for (ShopCartItemBO redisSc : shopcartListRedis) {
                    String specId = redisSc.getSpecId();
                    for (ShopCartItemBO cookieSc : shopcartListCookie) {
                        if (cookieSc.getSpecId().equals(specId)) {
                            redisSc.setBuyCounts(cookieSc.getBuyCounts());
                            pendingDelete.add(cookieSc);
                        }
                    }
                }
                shopcartListCookie.removeAll(pendingDelete);
                shopcartListRedis.addAll(shopcartListCookie);
                redisOperator.set(FOODIE_SHOPCART + ":" + userId, JsonUtils.objectToJson(shopcartListRedis));
                CookieUtils.setCookie(request, response, FOODIE_SHOPCART, JsonUtils.objectToJson(shopcartListRedis), true);
            } else {
                CookieUtils.setCookie(request, response, FOODIE_SHOPCART, shopcartJsonRedis, true);
            }
        }
    }

    private Users setNullProperty(Users users) {
        users.setPassword(null);
        users.setRealname(null);
        users.setMobile(null);
        users.setEmail(null);
        users.setCreatedTime(null);
        users.setUpdatedTime(null);
        users.setBirthday(null);
        return users;
    }

    @PostMapping("/logout")
    @ApiOperation(value = "用户登出", notes = "用户登出", httpMethod = "POST")
    public JSONResult logout(@RequestParam String userId,
                             HttpServletRequest request,
                             HttpServletResponse response) throws Exception {
        // 清除登录cookie
        CookieUtils.deleteCookie(request, response, "user");

        // 用户推出登录，需要清空购物车，清除用户回话信息
        redisOperator.del(REDIS_USER_TOKEN + ":" + userId);
        CookieUtils.deleteCookie(request, response, FOODIE_SHOPCART);
        return JSONResult.ok();
    }
}
