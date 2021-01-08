package com.issac.limiter.annotation;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * @author: ywy
 * @date: 2021-01-08
 * @desc:
 */
@Aspect
@Component
@Slf4j
public class AccessLimiterAspect {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RedisScript<Boolean> rateLimitLua;

    @Pointcut("@annotation(com.issac.limiter.annotation.AccessLimiter)")
    public void cut() {
        log.info("cut");
    }

    @Before("cut()")
    public void before(JoinPoint joinPoint) {
        // 1. 获得方法签名
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        AccessLimiter annotation = method.getAnnotation(AccessLimiter.class);
        if (annotation == null) {
            return;
        }
        Integer limit = annotation.limit();
        String key = annotation.methodKey();
        if (StringUtils.isEmpty(key)) {
            Class<?>[] types = method.getParameterTypes();
            key = method.getName();
            if (types != null) {
                String paramType = Arrays.stream(types)
                        .map(Class::getName)
                        .collect(Collectors.joining(","));
                log.info("param types:{}", paramType);
                key += paramType;
            }
        }
        // 2. 调用redis
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
