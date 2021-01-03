package com.issac.shardingjdbc.demo.sharding;

import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;

import java.util.Collection;

/**
 * @author: ywy
 * @date: 2020-12-26
 * @desc:
 */
public class MySharding implements PreciseShardingAlgorithm<Long> {
    @Override
    public String doSharding(Collection<String> tableNames, PreciseShardingValue<Long> preciseShardingValue) {
        Long value = preciseShardingValue.getValue();
        long mode = Math.abs(value.hashCode() % tableNames.size());
        String[] strings = tableNames.toArray(new String[0]);
        System.out.println(strings[0] + "------" + strings[1]);
        System.out.println("mode=" + mode);
        return strings[(int) mode];
    }
}
