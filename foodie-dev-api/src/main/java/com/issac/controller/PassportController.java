package com.issac.controller;

import com.issac.pojo.Users;
import com.issac.pojo.bo.UserBO;
import com.issac.service.UserService;
import com.issac.util.CookieUtils;
import com.issac.util.JSONResult;
import com.issac.util.JsonUtils;
import com.issac.util.MD5Utils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author: ywy
 * @date: 2020-05-08
 * @desc:
 */
@RestController
@RequestMapping("passport")
@Api(value = "注册登录", tags = {"用于注册登录的相关接口"})
public class PassportController {

    @Resource
    UserService userService;

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
        users = setNullProperty(users);
        CookieUtils.setCookie(request, response, "user", JsonUtils.objectToJson(users), true);
        return JSONResult.ok();
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
        users = setNullProperty(users);
        CookieUtils.setCookie(request, response, "user", JsonUtils.objectToJson(users), true);
        return JSONResult.ok(users);
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

        // TODO 用户推出登录，需要清空购物车，清除用户回话信息
        return JSONResult.ok();
    }
}
