package com.issac.controller;

import com.issac.enums.YesOrNo;
import com.issac.service.CarouselService;
import com.issac.service.CategoryService;
import com.issac.util.JSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author: ywy
 * @date: 2020-05-08
 * @desc:
 */
@RestController
@RequestMapping("index")
@Api(value = "首页", tags = {"首页展示的相关接口"})
public class IndexController {

    public static final Logger log = LoggerFactory.getLogger(IndexController.class);

    @Resource
    CarouselService carouselService;

    @Resource
    CategoryService categoryService;

    @GetMapping("/carousel")
    @ApiOperation(value = "获取轮播图", notes = "获取轮播图", httpMethod = "GET")
    public JSONResult carousel() {
        return JSONResult.ok(carouselService.queryAll(YesOrNo.YES.type));
    }

    /**
     * 首页分类展示需求：
     * 1. 第一次刷新主页查询大分类，渲染展示到首页
     * 2. 如果鼠标移到大分类，加载子分类
     */
    @GetMapping("/cats")
    @ApiOperation(value = "获取一级分类", notes = "获取一级分类", httpMethod = "GET")
    public JSONResult cats() {
        return JSONResult.ok(categoryService.queryAllRootLevelCat());
    }

    @GetMapping("/subCat/{rootCatId}")
    @ApiOperation(value = "获取一级分类子分类", notes = "获取一级分类子分类", httpMethod = "GET")
    @ApiParam(name = "rootCatId", value = "一级分类id", required = true)
    public JSONResult subCats(@PathVariable Integer rootCatId) {
        if (rootCatId == null) {
            return JSONResult.errorMsg("分类不存在");
        }

        return JSONResult.ok(categoryService.getSubCatList(rootCatId));
    }
}
