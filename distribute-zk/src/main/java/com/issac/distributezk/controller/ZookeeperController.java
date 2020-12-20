package com.issac.distributezk.controller;

import com.issac.distributezk.zk.ZkLock;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @author: ywy
 * @date: 2020-12-20
 * @desc:
 */
@RestController
@Slf4j
public class ZookeeperController {

    @RequestMapping("/zkLock")
    public String zookeeperLock() {
        log.info("我进入了方法！");
        try (ZkLock zkLock = new ZkLock()) {
            if (zkLock.getLock("order")) {
                log.info("获得了锁");
                Thread.sleep(10000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.info("方法执行完成！");
        return "方法执行完成！";
    }


    @Resource
    CuratorFramework curatorFramework;

    @RequestMapping("/curatorLock")
    public String curatorLock() {
        log.info("我进入了方法！");
        InterProcessMutex lock = new InterProcessMutex(curatorFramework, "/order");
        try {
            if (lock.acquire(30, TimeUnit.SECONDS)) {
                log.info("我获得了锁");
                Thread.sleep(10000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                lock.release();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        log.info("方法执行完成！");
        return "方法执行完成！";
    }
}
