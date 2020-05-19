package com.issac.service;

import com.issac.pojo.Items;
import com.issac.pojo.ItemsImg;
import com.issac.pojo.ItemsParam;
import com.issac.pojo.ItemsSpec;
import com.issac.pojo.vo.CommentLevelCountsVO;
import com.issac.pojo.vo.ItemCommentVO;
import com.issac.util.PagedGridResult;

import java.util.List;

/**
 * @author: ywy
 * @date: 2020-05-17
 * @desc: 商品相关
 */
public interface ItemService {

    /**
     * 查询商品详情
     *
     * @param itemId
     * @return
     */
    Items queryItemById(String itemId);

    /**
     * 查询商品图片
     *
     * @param itemId
     * @return
     */
    List<ItemsImg> queryItemImgList(String itemId);

    /**
     * 查询商品规格
     *
     * @param itemId
     * @return
     */
    List<ItemsSpec> queryItemSpecList(String itemId);

    /**
     * 查询商品属性
     *
     * @param itemId
     * @return
     */
    ItemsParam queryItemParamList(String itemId);

    /**
     * 查询商品评价tong j
     *
     * @param itemId
     * @return
     */
    CommentLevelCountsVO queryCommentCounts(String itemId);

    /**
     * 根据商品id查询商品评价
     *
     * @param itemId
     * @param level
     * @return
     */
    PagedGridResult queryPagedComments(String itemId, Integer level, Integer page, Integer pageSize);

    /**
     * 搜索商品
     * @param keywords
     * @param sort
     * @param page
     * @param pageSize
     * @return
     */
    PagedGridResult searchItems(String keywords, String sort, Integer page, Integer pageSize);

    /**
     * 搜索分类商品
     * @param catId
     * @param sort
     * @param page
     * @param pageSize
     * @return
     */
    PagedGridResult searchItemsByThirdCatId(Integer catId, String sort, Integer page, Integer pageSize);

}
