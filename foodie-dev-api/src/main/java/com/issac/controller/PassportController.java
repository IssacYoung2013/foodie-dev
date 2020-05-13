package com.issac.controller;

import com.issac.pojo.bo.UserBO;
import com.issac.service.UserService;
import com.issac.util.JSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author: ywy
 * @date: 2020-05-08
 * @desc:
 */
@RestController
@RequestMapping("passport")
@Api(value = "注册登录",tags = {"用于注册登录的相关接口"})
public class PassportController {

    @Resource
    UserService userService;

    @GetMapping("/usernameIsExist")
    @ApiOperation(value = "用户名是否存在",notes = "用户名是否存在",httpMethod = "GET")
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
    @ApiOperation(value = "用户名注册",notes = "用户名注册",httpMethod = "POST")
    public JSONResult regist(@RequestBody UserBO userBO) {
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
        if(password.length() < 6) {
            return JSONResult.errorMsg("密码长度不能小于6");
        }
        // 3. 判断两次密码是否一致
        if(!password.equals(confirmPassword)) {
            return JSONResult.errorMsg("两次密码输入不一致");
        }
        // 4. 实现注册
        userService.createUser(userBO);
        return JSONResult.ok();
    }
}
