package com.issac.controller;

import com.issac.pojo.bo.ShopCartItemBO;
import com.issac.util.JSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author: ywy
 * @date: 2020-05-08
 * @desc: 购物车
 */
@RestController
@Api(value = "购物车接口", tags = "购物车接口")
@RequestMapping("shopcart")
public class ShopCartController {

    @PostMapping("/add")
    @ApiOperation(value = "添加商品到购物车", notes = "添加商品到购物车", httpMethod = "POST")
    public JSONResult add(@RequestParam String userId,
                          @RequestBody ShopCartItemBO shopCartBO,
                          HttpServletRequest request,
                          HttpServletResponse response) {
        if (StringUtils.isBlank(userId)) {
            return JSONResult.errorMsg("用户未登录");
        }
        System.out.println(shopCartBO);
        // TODO 同步购物车到redis
        return JSONResult.ok();
    }
}
