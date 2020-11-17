package com.issac.controller.interceptor;

import com.issac.util.JSONResult;
import com.issac.util.JsonUtils;
import com.issac.util.RedisOperator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @author: ywy
 * @date: 2020-10-31
 * @desc:
 */
public class UserTokenInterceptor implements HandlerInterceptor {

    public static final String REDIS_USER_TOKEN = "redis_user_token";

    @Resource
    RedisOperator redisOperator;

    /**
     * 请求之前
     *
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        System.out.println("进入拦截器，被拦截");
        String headerUserId = request.getHeader("headerUserId");
        String headerUserToken = request.getHeader("headerUserToken");

        if (StringUtils.isNotBlank(headerUserId) && StringUtils.isNotBlank(headerUserToken)) {
            String uniqueToken = redisOperator.get(REDIS_USER_TOKEN + ":" + headerUserId);
            if (StringUtils.isBlank(uniqueToken)) {
                error(response,JSONResult.errorMsg("请登录"));
                System.out.println("请登录");
                return false;
            } else {
                if (!uniqueToken.equals(headerUserToken)) {
                    System.out.println("账号在异地登录");
                    error(response,JSONResult.errorMsg("账号在异地登录"));
                    return false;
                }
            }
        } else {
            System.out.println("请登录");
            error(response,JSONResult.errorMsg("请登录"));
            return false;
        }
        // false 代表被拦截驳回
        // true 验证通过
        return true;
    }

    public void error(HttpServletResponse response,
                      JSONResult result) {
        OutputStream out = null;
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/json");
        try {
            out = response.getOutputStream();
            out.write(JsonUtils.objectToJson(result).getBytes("utf-8"));
            out.flush();
        } catch (IOException e) {

        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                }
            }
        }
    }

    /**
     * 请求之后，渲染之前
     *
     * @param request
     * @param response
     * @param handler
     * @param modelAndView
     * @throws Exception
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    /**
     * 请求之后，渲染之后
     *
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
