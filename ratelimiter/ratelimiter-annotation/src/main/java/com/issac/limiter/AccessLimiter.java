package com.issac.limiter;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Service;

/**
 * @author: ywy
 * @date: 2021-01-05
 * @desc:
 */
@Service
@Slf4j
@Deprecated
public class AccessLimiter {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RedisScript<Boolean> rateLimitLua;

    public void limitAccess(String key, Integer limit) {
        // step 1: request lua script
        boolean acquired = stringRedisTemplate.execute(
                // lua script 的真身
                rateLimitLua,
                // lua 脚本中的key列表
                Lists.newArrayList(key),
                // lua 脚本参数列表
                limit.toString());
        if (!acquired) {
            log.error("your access is blocked, key={}", key);
            throw new RuntimeException("Your access is blocked!");
        }
    }
}
