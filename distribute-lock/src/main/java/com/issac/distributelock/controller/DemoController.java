package com.issac.distributelock.controller;

import com.issac.distributelock.dao.DistributeLockMapper;
import com.issac.distributelock.model.DistributeLock;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author: ywy
 * @date: 2020-12-18
 * @desc:
 */
@RestController
@Slf4j
public class DemoController {
    private Lock lock = new ReentrantLock();

    @Resource
    DistributeLockMapper distributeLockMapper;

    @RequestMapping("singleLock")
    @Transactional(rollbackFor = Exception.class)
    public String singleLock() throws Exception {
        log.info("我进入了方法");
        DistributeLock distributeLock = distributeLockMapper.selectDistributeLock("demo");
        if (distributeLock == null) {
            throw new Exception("分布式锁找不到");
        }
//        lock.lock();
        log.info("我进入了锁");
        try {
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        lock.unlock();
        return "我已经进行完成";
    }
}
