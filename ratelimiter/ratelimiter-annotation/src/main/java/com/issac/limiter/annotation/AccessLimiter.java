package com.issac.limiter.annotation;

import java.lang.annotation.*;

/**
 * @author: ywy
 * @date: 2021-01-08
 * @desc:
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface AccessLimiter {
    int limit();
    String methodKey() default "";
}
