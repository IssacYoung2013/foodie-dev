package com.issac.controller;

import com.issac.pojo.bo.ShopCartItemBO;
import com.issac.util.JSONResult;
import com.issac.util.JsonUtils;
import com.issac.util.RedisOperator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: ywy
 * @date: 2020-05-08
 * @desc: 购物车
 */
@RestController
@Api(value = "购物车接口", tags = "购物车接口")
@RequestMapping("shopcart")
public class ShopCartController extends BaseController {

    @Autowired
    RedisOperator redisOperator;

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
        // 判断当前购物车包含已存在的商品，累加
        boolean isHaving = false;
        String shopcartJson = redisOperator.get(FOODIE_SHOPCART + ":" + userId);
        List<ShopCartItemBO> shopCartList = null;
        if (StringUtils.isNotBlank(shopcartJson)) {
            // redis 已经有购物车
            shopCartList = JsonUtils.jsonToList(shopcartJson, ShopCartItemBO.class);
            for (ShopCartItemBO sc : shopCartList) {
                String specId = sc.getSpecId();
                if (specId.equals(shopCartBO.getSpecId())) {
                    sc.setBuyCounts(sc.getBuyCounts() + shopCartBO.getBuyCounts());
                    isHaving = true;
                }
            }
            if (!isHaving) {
                shopCartList.add(shopCartBO);
            }
        } else {
            shopCartList = new ArrayList<>();
            shopCartList.add(shopCartBO);
        }
        redisOperator.set(FOODIE_SHOPCART + ":" + userId, JsonUtils.objectToJson(shopCartList));
        return JSONResult.ok();
    }

    @PostMapping("/del")
    @ApiOperation(value = "删除购物车商品", notes = "删除购物车商品", httpMethod = "POST")
    public JSONResult del(@RequestParam String userId,
                          @RequestParam String itemSpecId,
                          HttpServletRequest request,
                          HttpServletResponse response) {
        if (StringUtils.isBlank(userId) || StringUtils.isBlank(itemSpecId)) {
            return JSONResult.errorMsg("");
        }
        String shopcartJson = redisOperator.get(FOODIE_SHOPCART + ":" + userId);
        if (StringUtils.isNotBlank(shopcartJson)) {
            // redis 已经有购物车
            List<ShopCartItemBO> shopCartList = JsonUtils.jsonToList(shopcartJson, ShopCartItemBO.class);
            for (ShopCartItemBO sc : shopCartList) {
                String specId = sc.getSpecId();
                if (specId.equals(itemSpecId)) {
                    shopCartList.remove(sc);
                    break;
                }
            }
            // 覆盖
            redisOperator.set(FOODIE_SHOPCART + ":" + userId, JsonUtils.objectToJson(shopCartList));
        }
        return JSONResult.ok();
    }
}
