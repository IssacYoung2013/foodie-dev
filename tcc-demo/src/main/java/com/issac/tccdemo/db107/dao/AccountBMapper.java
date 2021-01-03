package com.issac.tccdemo.db107.dao;

import com.issac.tccdemo.db107.model.AccountB;
import com.issac.tccdemo.db107.model.AccountBExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AccountBMapper {
    long countByExample(AccountBExample example);

    int deleteByExample(AccountBExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(AccountB record);

    int insertSelective(AccountB record);

    List<AccountB> selectByExample(AccountBExample example);

    AccountB selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") AccountB record, @Param("example") AccountBExample example);

    int updateByExample(@Param("record") AccountB record, @Param("example") AccountBExample example);

    int updateByPrimaryKeySelective(AccountB record);

    int updateByPrimaryKey(AccountB record);
}