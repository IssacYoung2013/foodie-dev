package com.issac.controller;

import com.issac.pojo.Users;
import com.issac.pojo.vo.UsersVO;
import com.issac.service.UserService;
import com.issac.util.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * @author: ywy
 * @date: 2020-05-08
 * @desc:
 */
@Controller
@ApiIgnore
public class SsoController {

    private static final String REDIS_USER_TICKET = "redis_user_ticket";

    private static final String REDIS_USER_TEMP_TICKET = "redis_user_tmp_ticket";

    public static final String COOKIE_USER_TICKET = "cookie_user_ticket";

    @Resource
    UserService userService;

    @Resource
    RedisOperator redisOperator;

    public static final String REDIS_USER_TOKEN = "redis_user_token";

    public static final Logger log = LoggerFactory.getLogger(SsoController.class);

    @GetMapping("/login")
    public String hello(String returnUrl,
                        Model model,
                        HttpServletRequest request,
                        HttpServletResponse response) throws Exception {
        model.addAttribute("returnUrl", returnUrl);
        // 1. 获取userTicket门票，如果cookie中能够获取到，证明用户登录过
        String userTicket = CookieUtils.getCookieValue(request, COOKIE_USER_TICKET);
        boolean isVerified = verifyUserTicket(userTicket);
        if (isVerified) {
            String tmpTicket = createTmpTicket();
            return "redirect:" + returnUrl + "?tmpTicket=" + tmpTicket;
        }
        return "login";
    }

    /**
     * 校验cas全局用户门票
     *
     * @return
     */
    private boolean verifyUserTicket(String userTicket) {
        if (StringUtils.isBlank(userTicket)) {
            return false;
        }
        String userId = redisOperator.get(REDIS_USER_TICKET + ":" + userTicket);
        if (StringUtils.isBlank(userId)) {
            return false;
        }
        // 2. 验证门票对应会话是否存在
        String userToken = redisOperator.get(REDIS_USER_TOKEN + ":" + userId);
        if (StringUtils.isBlank(userToken)) {
            return false;
        }
        return true;
    }

    /**
     * CAS的统一登录接口
     * 1. 创建全局会话
     * 2. 创建用户全局门票
     * 3. 创建临时票据，用于回跳
     *
     * @param username
     * @param password
     * @param returnUrl
     * @param model
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @PostMapping("/doLogin")
    public String doLogin(String username,
                          String password,
                          String returnUrl,
                          Model model,
                          HttpServletRequest request,
                          HttpServletResponse response) throws Exception {
        model.addAttribute("returnUrl", returnUrl);
        if (StringUtils.isBlank(username) ||
                StringUtils.isBlank(password)) {
            model.addAttribute("errmsg", "用户名或密码不能为空");
            return "login";
        }
        Users users = userService.queryUserForLogin(username, MD5Utils.getMD5Str(password));
        if (users == null) {
            model.addAttribute("errmsg", "用户名或密码不正确");
            return "login";
        }
        // 2. 实现用户的redis会话
        String uniqueToken = UUID.randomUUID().toString().trim();
        UsersVO usersVO = new UsersVO();
        BeanUtils.copyProperties(users, usersVO);
        usersVO.setUserUniqueToken(uniqueToken);
        redisOperator.set(REDIS_USER_TOKEN + ":" + users.getId(), JsonUtils.objectToJson(usersVO));

        // 3. 生成门票
        String userTicket = UUID.randomUUID().toString().trim();

        // 3.1 用户全局门票需要放入cas cookie中
        CookieUtils.setCookie(request, response, COOKIE_USER_TICKET, userTicket, true);
//        setCookie(COOKIE_USER_TICKET, userTicket, response);

        // 4. 关联用户id
        redisOperator.set(REDIS_USER_TICKET + ":" + userTicket, users.getId());

        // 5. 生成临时票据，回跳到调用端网站
        String tmpTicket = createTmpTicket();

        /**
         * userTicket 用于用于在cas端的一个登录状态
         * tmpTicket 用于颁发给用于进行一次性的验证票据，有时效
         *  动物园门票 - 全局门票
         *      海洋馆门票 凭全局门票领取
         */
        return "redirect:" + returnUrl + "?tmpTicket=" + tmpTicket;
    }

    @GetMapping("/get/cookie")
    @ResponseBody
    public JSONResult testCookie(
            HttpServletRequest request,
            HttpServletResponse response) {
        String temp = CookieUtils.getCookieValue(request, COOKIE_USER_TICKET);
        return JSONResult.ok(temp);
    }

    @PostMapping("/verifyTmpTicket")
    @ResponseBody
    public JSONResult verifyTmpTicket(String tmpTicket,
                                      HttpServletRequest request,
                                      HttpServletResponse response) throws Exception {
        // 使用一次性临时票据来验证用户是否登录，如果登录过，把用户会话信息返回给站点
        String tmpTicketVal = redisOperator.get(REDIS_USER_TEMP_TICKET + ":" + tmpTicket);
        if (StringUtils.isBlank(tmpTicketVal)) {
            return JSONResult.errorUserTicket("用户票据异常");
        }

        // 如果临时票据ok，则需要销毁，拿全局ticket
        if (!tmpTicketVal.equals(MD5Utils.getMD5Str(tmpTicket))) {
            return JSONResult.errorUserTicket("用户票据异常");
        } else {
            // 销毁临时票据
            redisOperator.del(REDIS_USER_TEMP_TICKET + ":" + tmpTicket);
        }

        // 1. 验证并获取用户的userTicket
        String temp = CookieUtils.getCookieValue(request, COOKIE_USER_TICKET);
        CookieUtils.getCookieValue(request, COOKIE_USER_TICKET);
        String userTicket = getCookie(COOKIE_USER_TICKET, request);
        String userId = redisOperator.get(REDIS_USER_TICKET + ":" + userTicket);
        if (StringUtils.isBlank(userId)) {
            return JSONResult.errorUserTicket("用户票据异常");
        }
        // 2. 验证门票对应会话是否存在
        String userToken = redisOperator.get(REDIS_USER_TOKEN + ":" + userId);
        if (StringUtils.isBlank(userToken)) {
            return JSONResult.errorUserTicket("用户票据异常");
        }
        return JSONResult.ok(JsonUtils.jsonToPojo(userToken, UsersVO.class));
    }

    private String getCookie(String key, HttpServletRequest request) {
        String cookie = request.getHeader("cookie");
        Cookie[] cookies = request.getCookies();
        if (cookies == null || StringUtils.isBlank(key)) {
            return null;
        }
        for (int i = 0; i < cookies.length; i++) {
            if (cookies[i].getName().equals(key)) {
                return cookies[i].getValue();
            }
        }
        return null;
    }

    /**
     * 创建临时票据
     *
     * @return
     */
    private String createTmpTicket() throws Exception {
        String tmpTicket = UUID.randomUUID().toString().trim();
        redisOperator.set(REDIS_USER_TEMP_TICKET + ":" + tmpTicket,
                MD5Utils.getMD5Str(tmpTicket), 600);
        return tmpTicket;
    }

    private void setCookie(String key, String val,
                           HttpServletResponse response) {
        Cookie cookie = new Cookie(key, val);
        cookie.setDomain("sso.com");
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    @PostMapping("/logout")
    @ResponseBody
    public JSONResult logout(String userId,
                             HttpServletRequest request,
                             HttpServletResponse response) throws Exception {
        // 1. 获取cas的用户门票
        String userTicket = CookieUtils.getCookieValue(request, COOKIE_USER_TICKET);
        // 2. 清除cookie
        CookieUtils.deleteCookie(request, response, COOKIE_USER_TICKET);
        redisOperator.del(REDIS_USER_TICKET + ":" + userTicket);

        // 3. 清除用户全局会话
        redisOperator.del(REDIS_USER_TOKEN + ":" + userId);
        return JSONResult.ok();
    }
}
