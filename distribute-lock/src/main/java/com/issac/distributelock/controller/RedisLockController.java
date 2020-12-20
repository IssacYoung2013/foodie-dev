package com.issac.distributelock.controller;

import com.issac.distributelock.lock.RedisLock;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author: ywy
 * @date: 2020-12-19
 * @desc:
 */
@RestController
@Slf4j
public class RedisLockController {

    @Resource
    private RedisTemplate redisTemplate;

    @RequestMapping("redisLock")
    public String redisLock() {
        log.info("我进入了方法！");
        String key = "redisKey";
        try (RedisLock redisLock = new RedisLock(redisTemplate, key, 30);) {
            if (redisLock.getLock()) {
                log.info("我进入了锁");
                try {
                    Thread.sleep(15000);
                } catch (InterruptedException e) {
                } finally {
                    log.info("释放锁的结果：" + redisLock.unLock());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.info("方法执行完成");
        return "方法执行完成";
    }
}
