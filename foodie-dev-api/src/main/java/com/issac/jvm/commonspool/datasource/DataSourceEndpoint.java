package com.issac.jvm.commonspool.datasource;

import com.google.common.collect.Maps;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: ywy
 * @date: 2021-02-04
 * @desc: 可以用 /actuator/datasource
 */
@Endpoint(id = "datasource")
public class DataSourceEndpoint {

    private MyDataSource dataSource;

    public DataSourceEndpoint(MyDataSource dataSource) {
        this.dataSource = dataSource;
    }

    @ReadOperation
    public Map<String, Object> pool() {
        GenericObjectPool<MyConnection> pool = dataSource.getPool();
        HashMap<String, Object> map = Maps.newHashMap();
        map.put("numActive", pool.getNumActive());
        map.put("numIdle", pool.getNumIdle());
        map.put("createdAccount", pool.getCreatedCount());
        return map;
    }
}
