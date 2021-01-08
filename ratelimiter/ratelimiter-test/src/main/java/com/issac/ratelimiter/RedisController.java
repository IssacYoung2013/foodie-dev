package com.issac.ratelimiter;

import com.issac.limiter.AccessLimiter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: ywy
 * @date: 2021-01-06
 * @desc:
 */
@RestController
@Slf4j
public class RedisController {

    @Autowired
    private AccessLimiter accessLimiter;

    @GetMapping("test")
    public String test() {
        accessLimiter.limitAccess("ratelimiter",1);
        return "success";
    }

    @GetMapping("test-annotation")
    @com.issac.limiter.annotation.AccessLimiter(limit = 1)
    public String testAnnotation() {
        return "success";
    }
}
