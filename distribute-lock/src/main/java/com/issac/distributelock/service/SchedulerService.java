package com.issac.distributelock.service;

import com.issac.distributelock.lock.RedisLock;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author: ywy
 * @date: 2020-12-19
 * @desc:
 */
@Service
@Slf4j
public class SchedulerService {

    @Resource
    private RedisTemplate redisTemplate;

    @Scheduled(cron = "0/5 * * * * ?")
    public void sendSms() {
        try (RedisLock redisLock = new RedisLock(redisTemplate, "autoSms", 30)) {
            if(redisLock.getLock()) {
                log.info("向138XXXXXX发送短信！");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
