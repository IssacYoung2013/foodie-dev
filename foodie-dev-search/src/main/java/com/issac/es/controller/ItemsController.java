package com.issac.es.controller;

import com.issac.es.service.ItemsEsService;
import com.issac.util.JSONResult;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: ywy
 * @date: 2020-11-15
 * @desc:
 */
@RestController
@RequestMapping("items")
public class ItemsController {

    @GetMapping("/hello")
    public Object hello() {
        return "Hello ElasticSearch!";
    }

    @Autowired
    private ItemsEsService itemService;

    @GetMapping("/es/search")
    @ApiOperation(value = "搜索商品列表", notes = "搜索商品列表", httpMethod = "GET")
    public JSONResult search(String keywords,
                             String sort,
                             @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                             @RequestParam(value = "pageSize", required = false, defaultValue = "20") Integer pageSize) {
        return JSONResult.ok(itemService.searchItems(keywords, sort, page, pageSize));
    }

}
