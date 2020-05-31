package com.issac.mapper;

import com.issac.pojo.vo.ItemCommentVO;
import com.issac.pojo.vo.SearchItemsVO;
import com.issac.pojo.vo.ShopCartItemVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ItemsCustomMapper {
    List<ItemCommentVO> queryItemComments(@Param("map") Map<String, Object> map);

    List<SearchItemsVO> searchItems(@Param("map") Map<String, Object> map);

    List<SearchItemsVO> searchItemsByThirdCatId(@Param("map") Map<String, Object> map);

    List<ShopCartItemVO> queryItemsBySpecIds(@Param("paramsList") List<String> specIds);

    int decreaseItemSpecStock(@Param("specId") String specId,
                              @Param("pendingCounts") Integer pendingCounts);
}