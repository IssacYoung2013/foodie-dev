package com.issac.mapper;

import com.issac.pojo.vo.ItemCommentVO;
import com.issac.pojo.vo.SearchItemsVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ItemsCustomMapper {
    List<ItemCommentVO> queryItemComments(@Param("map") Map<String, Object> map);

    List<SearchItemsVO> searchItems(@Param("map") Map<String, Object> map);

    List<SearchItemsVO> searchItemsByThirdCatId(@Param("map") Map<String, Object> map);
}