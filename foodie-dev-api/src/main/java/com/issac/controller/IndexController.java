package com.issac.controller;

import com.issac.enums.YesOrNo;
import com.issac.pojo.Carousel;
import com.issac.pojo.Category;
import com.issac.pojo.vo.CategoryVO;
import com.issac.service.CarouselService;
import com.issac.service.CategoryService;
import com.issac.util.JSONResult;
import com.issac.util.JsonUtils;
import com.issac.util.RedisOperator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

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

    @Autowired
    private RedisOperator redisOperator;

    /**
     * 1. 后台运营系统，一旦广告发送更改，就可以删除缓存，然后重置
     * 2. 定时删除
     * 3. 设置过期时间
     *
     * @return
     */
    @GetMapping("/carousel")
    @ApiOperation(value = "获取轮播图", notes = "获取轮播图", httpMethod = "GET")
    public JSONResult carousel() {
        List<Carousel> list = new ArrayList<>();
        String carouselStr = redisOperator.get("carousel");
        if (StringUtils.isBlank(carouselStr)) {
            list = carouselService.queryAll(YesOrNo.YES.type);
            redisOperator.set("carousel", JsonUtils.objectToJson(list));
        } else {
            list = JsonUtils.jsonToList(carouselStr, Carousel.class);
        }
        return JSONResult.ok(list);
    }

    /**
     * 首页分类展示需求：
     * 1. 第一次刷新主页查询大分类，渲染展示到首页
     * 2. 如果鼠标移到大分类，加载子分类
     */
    @GetMapping("/cats")
    @ApiOperation(value = "获取一级分类", notes = "获取一级分类", httpMethod = "GET")
    public JSONResult cats() {
        List<Category> cats = new ArrayList<>();
        String catsStr = redisOperator.get("cats");
        if (StringUtils.isBlank(catsStr)) {
            cats = categoryService.queryAllRootLevelCat();
            redisOperator.set("cats", JsonUtils.objectToJson(cats));
        } else {
            cats = JsonUtils.jsonToList(catsStr, Category.class);
        }
        return JSONResult.ok(cats);
    }

    @GetMapping("/subCat/{rootCatId}")
    @ApiOperation(value = "获取一级分类子分类", notes = "获取一级分类子分类", httpMethod = "GET")
    @ApiParam(name = "rootCatId", value = "一级分类id", required = true)
    public JSONResult subCats(@PathVariable Integer rootCatId) {
        if (rootCatId == null) {
            return JSONResult.errorMsg("分类不存在");
        }
        List<CategoryVO> catVos = new ArrayList<>();
        String catsStr = redisOperator.get("cats:" + rootCatId);
        if (StringUtils.isBlank(catsStr)) {
            catVos = categoryService.getSubCatList(rootCatId);
            if (!CollectionUtils.isEmpty(catVos)) {
                redisOperator.set("cats:" + rootCatId, JsonUtils.objectToJson(catVos));
            } else {
                redisOperator.set("cats:" + rootCatId, JsonUtils.objectToJson(catVos), 5 * 60);

            }
        } else {
            catVos = JsonUtils.jsonToList(catsStr, CategoryVO.class);
        }
        return JSONResult.ok(catVos);
    }

    @GetMapping("/sixNewItems/{rootCatId}")
    @ApiOperation(value = "查询一级分类下的最新6条商品数据", notes = "查询一级分类下的最新6条商品数据", httpMethod = "GET")
    @ApiParam(name = "rootCatId", value = "一级分类id", required = true)
    public JSONResult sixNewItems(@PathVariable Integer rootCatId) {
        if (rootCatId == null) {
            return JSONResult.errorMsg("分类不存在");
        }
        return JSONResult.ok(categoryService.getSixNewItemsLazy(rootCatId));
    }
}
