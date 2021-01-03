package com.issac.xademo.db107.dao;

import com.issac.xademo.db107.model.XA107;
import com.issac.xademo.db107.model.XA107Example;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface XA107Mapper {
    long countByExample(XA107Example example);

    int deleteByExample(XA107Example example);

    int deleteByPrimaryKey(Integer id);

    int insert(XA107 record);

    int insertSelective(XA107 record);

    List<XA107> selectByExample(XA107Example example);

    XA107 selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") XA107 record, @Param("example") XA107Example example);

    int updateByExample(@Param("record") XA107 record, @Param("example") XA107Example example);

    int updateByPrimaryKeySelective(XA107 record);

    int updateByPrimaryKey(XA107 record);
}