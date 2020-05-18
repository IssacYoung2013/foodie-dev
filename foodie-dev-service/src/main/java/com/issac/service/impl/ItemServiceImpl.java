package com.issac.service.impl;

import com.issac.enums.CommentLevel;
import com.issac.mapper.*;
import com.issac.pojo.*;
import com.issac.pojo.vo.CommentLevelCountsVO;
import com.issac.service.ItemService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;

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
}
