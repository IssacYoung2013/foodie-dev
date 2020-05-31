package com.issac.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.issac.enums.CommentLevel;
import com.issac.enums.YesOrNo;
import com.issac.mapper.*;
import com.issac.pojo.*;
import com.issac.pojo.vo.CommentLevelCountsVO;
import com.issac.pojo.vo.ItemCommentVO;
import com.issac.pojo.vo.SearchItemsVO;
import com.issac.pojo.vo.ShopCartItemVO;
import com.issac.service.ItemService;
import com.issac.util.PagedGridResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author: ywy
 * @date: 2020-05-17
 * @desc:
 */
@Service
public class ItemServiceImpl implements ItemService {

    @Resource
    ItemsMapper itemsMapper;

    @Resource
    ItemsParamMapper itemsParamMapper;

    @Resource
    ItemsSpecMapper itemsSpecMapper;

    @Resource
    ItemsImgMapper itemsImgMapper;

    @Resource
    ItemsCommentsMapper itemsCommentsMapper;

    @Resource
    ItemsCustomMapper itemsCustomMapper;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Items queryItemById(String itemId) {
        return itemsMapper.selectByPrimaryKey(itemId);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<ItemsImg> queryItemImgList(String itemId) {
        Example example = new Example(ItemsImg.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("itemId", itemId);
        return itemsImgMapper.selectByExample(example);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<ItemsSpec> queryItemSpecList(String itemId) {
        Example example = new Example(ItemsImg.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("itemId", itemId);
        return itemsSpecMapper.selectByExample(example);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public ItemsParam queryItemParamList(String itemId) {
        Example example = new Example(ItemsImg.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("itemId", itemId);
        return itemsParamMapper.selectOneByExample(example);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public CommentLevelCountsVO queryCommentCounts(String itemId) {
        CommentLevelCountsVO commentLevelCounts = new CommentLevelCountsVO();
        Integer goodCounts = getCommentCounts(itemId, CommentLevel.good.type);
        Integer normalCounts = getCommentCounts(itemId, CommentLevel.normal.type);
        Integer badCounts = getCommentCounts(itemId, CommentLevel.bad.type);
        Integer totalCounts = goodCounts + normalCounts + badCounts;
        commentLevelCounts.setTotalCounts(totalCounts);
        commentLevelCounts.setGoodCounts(goodCounts);
        commentLevelCounts.setNormalCounts(normalCounts);
        commentLevelCounts.setBadCounts(badCounts);
        return commentLevelCounts;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    Integer getCommentCounts(String itemId, Integer level) {
        ItemsComments condition = new ItemsComments();
        condition.setItemId(itemId);
        if (level != null) {
            condition.setCommentLevel(level);
        }
        return itemsCommentsMapper.selectCount(condition);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public PagedGridResult queryPagedComments(String itemId, Integer level, Integer page, Integer pageSize) {
        Map<String, Object> map = new HashMap<>();
        map.put("itemId", itemId);
        map.put("level", level);

        // mybatis-pagehelper
        PageHelper.startPage(page, pageSize);
        List<ItemCommentVO> itemComments = itemsCustomMapper.queryItemComments(map);
        return setterPagedGrid(itemComments, page);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public PagedGridResult searchItems(String keywords, String sort, Integer page, Integer pageSize) {
        Map<String, Object> map = new HashMap<>();
        map.put("keywords", keywords);
        map.put("sort", sort);

        PageHelper.startPage(page, pageSize);
        List<SearchItemsVO> items = itemsCustomMapper.searchItems(map);
        return setterPagedGrid(items, page);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public PagedGridResult searchItemsByThirdCatId(Integer catId, String sort, Integer page, Integer pageSize) {
        Map<String, Object> map = new HashMap<>();
        map.put("catId", catId);
        map.put("sort", sort);

        PageHelper.startPage(page, pageSize);
        List<SearchItemsVO> items = itemsCustomMapper.searchItemsByThirdCatId(map);
        return setterPagedGrid(items, page);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<ShopCartItemVO> queryItemsBySpecIds(String specIds) {
        String ids[] = specIds.split(",");
        List<String> specIdsList = new ArrayList<>();
        Collections.addAll(specIdsList, ids);
        return itemsCustomMapper.queryItemsBySpecIds(specIdsList);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public ItemsSpec queryItemSpecById(String specId) {
        return itemsSpecMapper.selectByPrimaryKey(specId);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public String queryItemsMainImgById(String itemId) {
        ItemsImg itemsImg = new ItemsImg();
        itemsImg.setItemId(itemId);
        itemsImg.setIsMain(YesOrNo.YES.type);
        ItemsImg result = itemsImgMapper.selectOne(itemsImg);

        return result == null ? "" : result.getUrl();
    }

    /**
     * 防止超卖：不推荐使用 synchronized 集群下无用，性能低下；锁数据库，导致数据库性能低下
     * 分布式锁 zookeeper redis
     *
     * @param specId
     * @param buyCounts
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void decreaseItemSpecStock(String specId, Integer buyCounts) {
        // 1. 查询库存
        int result = itemsCustomMapper.decreaseItemSpecStock(specId, buyCounts);
        if (result != 1) {
            throw new RuntimeException("订单创建失败，原因：库存不足");
        }
        // 2. 判断库存是否够
    }

    private PagedGridResult setterPagedGrid(List<?> list, Integer page) {
        PageInfo<?> pageInfo = new PageInfo<>(list);
        PagedGridResult gridResult = new PagedGridResult();
        gridResult.setPage(page);
        gridResult.setRows(list);
        gridResult.setTotal(pageInfo.getPages());
        gridResult.setRecords(pageInfo.getTotal());
        return gridResult;
    }
}
