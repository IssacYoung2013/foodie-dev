package com.issac.distributelock;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

/**
 * @author: ywy
 * @date: 2020-12-20
 * @desc:
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class DistributeLockApplicationTest {


    @Resource
    private RedissonClient redissonClient;

    @Test
    public void testRedissonLock() {
        Config config = new Config();
        config.useSingleServer().setAddress("redis://localhost:6379");
        RedissonClient redissonClient = Redisson.create(config);
        RLock rLock = redissonClient.getLock("order");
        rLock.lock(30, TimeUnit.SECONDS);
        log.info("我获得了锁");
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            rLock.unlock();
            log.info("我释放了锁");
        }
    }

    @Test
    public void testClient() {
        RLock rLock = redissonClient.getLock("order");
        rLock.lock(30, TimeUnit.SECONDS);
        log.info("我获得了锁");
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            rLock.unlock();
            log.info("我释放了锁");
        }
    }
}