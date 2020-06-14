package com.issac.mapper;

import com.issac.my.mapper.MyMapper;
import com.issac.pojo.ItemsComments;
import com.issac.pojo.vo.MyCommentVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ItemsCommentsCustomMapper extends MyMapper<ItemsComments> {
    /**
     * 循环保存
     *
     * @param map
     */
    void saveComments(Map<String, Object> map);

    /**
     * 查询我的评价列表
     * @param map
     * @return
     */
    List<MyCommentVO> queryMyComments(@Param("paramsMap") Map<String, Object> map);
}