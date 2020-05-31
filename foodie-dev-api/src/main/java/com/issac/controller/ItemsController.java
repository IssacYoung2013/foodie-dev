package com.issac.controller;

import com.issac.pojo.Items;
import com.issac.pojo.ItemsImg;
import com.issac.pojo.ItemsParam;
import com.issac.pojo.ItemsSpec;
import com.issac.pojo.vo.ItemInfoVO;
import com.issac.service.ItemService;
import com.issac.util.JSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author: ywy
 * @date: 2020-05-08
 * @desc:
 */
@RestController
@RequestMapping("items")
@Api(value = "商品详情", tags = {"商品详情相关接口"})
public class ItemsController extends BaseController {

    public static final Logger log = LoggerFactory.getLogger(ItemsController.class);

    @Resource
    ItemService itemService;

    @GetMapping("/info/{itemId}")
    @ApiOperation(value = "获取商品详情", notes = "获取商品详情", httpMethod = "GET")
    public JSONResult info(@PathVariable String itemId) {
        if (StringUtils.isBlank(itemId)) {
            return JSONResult.errorMsg("null");
        }
        Items item = itemService.queryItemById(itemId);
        if (item == null) {
            return JSONResult.errorMsg("商品不存在");
        }
        List<ItemsImg> itemImgList = itemService.queryItemImgList(itemId);
        List<ItemsSpec> itemSpecList = itemService.queryItemSpecList(itemId);
        ItemsParam itemParam = itemService.queryItemParamList(itemId);
        ItemInfoVO itemInfoVO = new ItemInfoVO();
        itemInfoVO.setItem(item);
        itemInfoVO.setItemImgList(itemImgList);
        itemInfoVO.setItemSpecList(itemSpecList);
        itemInfoVO.setItemParams(itemParam);
        return JSONResult.ok(itemInfoVO);
    }

    @GetMapping("/commentLevel")
    @ApiOperation(value = "查询商品评价等级", notes = "查询商品评价等级", httpMethod = "GET")
    public JSONResult commentLevel(@RequestParam String itemId) {
        if (StringUtils.isBlank(itemId)) {
            return JSONResult.errorMsg("null");
        }
        return JSONResult.ok(itemService.queryCommentCounts(itemId));
    }

    @GetMapping("/comments")
    @ApiOperation(value = "查询商品评论", notes = "查询商品评论", httpMethod = "GET")
    public JSONResult comments(@RequestParam String itemId,
                               @RequestParam(value = "level", required = false) Integer level,
                               @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                               @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize) {
        if (StringUtils.isBlank(itemId)) {
            return JSONResult.errorMsg("null");
        }
        return JSONResult.ok(itemService.queryPagedComments(itemId, level, page, pageSize));
    }

    @GetMapping("/search")
    @ApiOperation(value = "搜索商品列表", notes = "搜索商品列表", httpMethod = "GET")
    public JSONResult search(@RequestParam String keywords,
                             @RequestParam(value = "sort", required = false) String sort,
                             @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                             @RequestParam(value = "pageSize", required = false, defaultValue = "20") Integer pageSize) {
        return JSONResult.ok(itemService.searchItems(keywords, sort, page, pageSize));
    }

    @GetMapping("/catItems")
    @ApiOperation(value = "搜索商品分类列表", notes = "搜索商品分类列表", httpMethod = "GET")
    public JSONResult catItems(@RequestParam Integer catId,
                               @RequestParam(value = "sort", required = false) String sort,
                               @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                               @RequestParam(value = "pageSize", required = false, defaultValue = "20") Integer pageSize) {
        return JSONResult.ok(itemService.searchItemsByThirdCatId(catId, sort, page, pageSize));
    }

    @GetMapping("/refresh")
    @ApiOperation(value = "刷新购物车的数据", notes = "刷新购物车的数据", httpMethod = "GET")
    public JSONResult refresh(@ApiParam(name = "itemSpecIds", value = "拼接的规格ids", required = true)
                                  @RequestParam("itemSpecIds") String itemSpecIds) {
        if (StringUtils.isBlank(itemSpecIds)) {
            return JSONResult.ok();
        }
        return JSONResult.ok(itemService.queryItemsBySpecIds(itemSpecIds));
    }
}
