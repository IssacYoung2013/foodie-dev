package com.issac.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author: ywy
 * @date: 2020-05-16
 * @desc:
 */
@Component
@Aspect
public class ServiceLogAspect {

    public static final Logger log = LoggerFactory.getLogger(ServiceLogAspect.class);

    /**
     * AOP通知：
     * 1. 前置
     * 2. 后置 正常调用
     * 3. 环绕 前后
     * 4. 异常
     * 5. 最终 最终调用 finally
     * 切面表达式
     * 第一处 返回类型 *代表所有类型
     * 第二处 包名代表监控所在的包
     * 第三处 .. 代表该包及子包下的所有类方法
     * 第四处 . 代表所有类名 * 代表所有类
     * 第五处 *(..) * 代表类中的方法名，(..) 方法参数
     */

    @Around("execution(* com.issac.service.impl..*.*(..))")
    public Object recordTimeLog(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("====== 执行开始 {}.{}=======", joinPoint.getClass(),
                joinPoint.getSignature().getName());
        long startTime = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long endTime = System.currentTimeMillis();
        long takeTime = endTime - startTime;
        if (takeTime > 3000) {
            log.error("====== 执行结束，耗时{}毫秒", takeTime);
        } else if (takeTime > 2000) {
            log.warn("====== 执行结束，耗时{}毫秒", takeTime);
        } else {
            log.info("====== 执行结束，耗时{}毫秒", takeTime);
        }
        return result;
    }
}
