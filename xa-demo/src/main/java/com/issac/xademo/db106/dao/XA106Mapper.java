package com.issac.xademo.db106.dao;

import com.issac.xademo.db106.model.XA106;
import com.issac.xademo.db106.model.XA106Example;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface XA106Mapper {
    long countByExample(XA106Example example);

    int deleteByExample(XA106Example example);

    int deleteByPrimaryKey(Integer id);

    int insert(XA106 record);

    int insertSelective(XA106 record);

    List<XA106> selectByExample(XA106Example example);

    XA106 selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") XA106 record, @Param("example") XA106Example example);

    int updateByExample(@Param("record") XA106 record, @Param("example") XA106Example example);

    int updateByPrimaryKeySelective(XA106 record);

    int updateByPrimaryKey(XA106 record);
}