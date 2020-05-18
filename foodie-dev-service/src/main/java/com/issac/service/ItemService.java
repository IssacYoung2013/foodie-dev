package com.issac.service;

import com.issac.pojo.Items;
import com.issac.pojo.ItemsImg;
import com.issac.pojo.ItemsParam;
import com.issac.pojo.ItemsSpec;
import com.issac.pojo.vo.CommentLevelCountsVO;

import java.util.List;

/**
 * @author: ywy
 * @date: 2020-05-17
 * @desc: 商品相关
 */
public interface ItemService {

    /**
     * 查询商品详情
     * @param itemId
     * @return
     */
    Items queryItemById(String itemId);

    /**
     * 查询商品图片
     * @param itemId
     * @return
     */
    List<ItemsImg> queryItemImgList(String itemId);

    /**
     * 查询商品规格
     * @param itemId
     * @return
     */
    List<ItemsSpec> queryItemSpecList(String itemId);

    /**
     * 查询商品属性
     * @param itemId
     * @return
     */
    ItemsParam queryItemParamList(String itemId);

    /**
     * 查询商品评价
     * @param itemId
     * @return
     */
    CommentLevelCountsVO queryCommentCounts(String itemId);
}
