package com.issac.ratelimit.controller;

import com.google.common.util.concurrent.RateLimiter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * @author: ywy
 * @date: 2021-01-03
 * @desc:
 */
@RestController
@Slf4j
public class Controller {
    /**
     * 每秒两个通行证
     */
    RateLimiter limiter = RateLimiter.create(2.0);

    /**
     * 非阻塞限流
     *
     * @param count
     * @return
     */
    @GetMapping("/tryAcquire")
    public String tryAcquire(Integer count) {
        if (limiter.tryAcquire(count)) {
            log.info("success, rate is {}", limiter.getRate());
            return "success";
        } else {
            log.info("fail, rate is {}", limiter.getRate());
            return "fail";
        }
    }

    /**
     * 限定时间非阻塞限流
     *
     * @param count
     * @param timeout
     * @return
     */
    @GetMapping("/tryAcquireWithTimeout")
    public String tryAcquireWithTimeout(Integer count, Integer timeout) {
        if (limiter.tryAcquire(count, timeout, TimeUnit.SECONDS)) {
            log.info("success, rate is {}", limiter.getRate());
            return "success";
        } else {
            log.info("fail, rate is {}", limiter.getRate());
            return "fail";
        }
    }

    /**
     * 同步阻塞
     *
     * @param count
     * @return
     */
    @GetMapping("/acquire")
    public String acquire(Integer count) {
        limiter.acquire(count);
        log.info("success, rate is {}", limiter.getRate());
        return "success";
    }


    /**
     * Nginx
     * 1. 修改host文件 - www.issac-training.com = localhost 127.0.0.1
     * 2. 修改 nginx -> 步骤1的域名，添加到路由规则中
     * 3. 添加配置项
     */
    @GetMapping("/nginx")
    public String nginx() {
        log.info("Nginx success");
        return "success";
    }

    @GetMapping("/nginx-conn")
    public String nginxConn(@RequestParam(defaultValue = "0") int secs) {
        try {
            TimeUnit.SECONDS.sleep(secs);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "success";
    }
}
