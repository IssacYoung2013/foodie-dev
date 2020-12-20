package com.issac.distributelock.dao;

import com.issac.distributelock.model.DistributeLock;
import com.issac.distributelock.model.DistributeLockExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface DistributeLockMapper {
    long countByExample(DistributeLockExample example);

    int deleteByExample(DistributeLockExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(DistributeLock record);

    int insertSelective(DistributeLock record);

    List<DistributeLock> selectByExample(DistributeLockExample example);

    DistributeLock selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") DistributeLock record, @Param("example") DistributeLockExample example);

    int updateByExample(@Param("record") DistributeLock record, @Param("example") DistributeLockExample example);

    int updateByPrimaryKeySelective(DistributeLock record);

    int updateByPrimaryKey(DistributeLock record);

    DistributeLock selectDistributeLock(@Param("businessCode") String businessCode);
}