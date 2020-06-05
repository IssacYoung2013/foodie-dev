package com.issac.controller.center;

import com.issac.pojo.Users;
import com.issac.service.center.CenterUserService;
import com.issac.util.JSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author: ywy
 * @date: 2020-05-31
 * @desc:
 */
@RestController
@RequestMapping("/center")
@Api(value = "center - 用户中心", tags = "用户中心展示的相关接口")
public class CenterController {

    @Resource
    CenterUserService centerUserService;

    @ApiOperation(value = "获取用户信息", notes = "获取用户信息", httpMethod = "GET")
    @GetMapping("userInfo")
    public JSONResult userInfo(@RequestParam @ApiParam(name = "userId", value = "用户id", required = true) String userId) {
        Users users = centerUserService.queryUserInfo(userId);
        return JSONResult.ok(users);
    }

}
