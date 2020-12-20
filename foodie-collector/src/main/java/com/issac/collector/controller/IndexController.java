package com.issac.collector.controller;

import com.issac.collector.util.InputMDC;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: ywy
 * @date: 2020-12-12
 * @desc:
 */
@RestController
@Slf4j
public class IndexController {

    /**
     * [%d{yyyy-MM-dd'T'HH:mm:ss.SSSZZ}]
     * [%level{length=5}]
     * [%thread-%tid]
     * [%logger]
     * [%X{hostName}]
     * [%X{ip}]
     * [%X{applicationName}]
     * [%F,%L,%C,%M]
     * [%m] ## '%ex'%n
     * <p>
     * <p>
     * [2020-12-12T22:49:03.608+08:00]
     * [INFO]
     * [main-1]
     * [org.springframework.boot.web.embedded.tomcat.TomcatWebServer]
     * [] [] []
     * [TomcatWebServer.java,90,org.springframework.boot.web.embedded.tomcat.TomcatWebServer,initialize]
     * [Tomcat initialized with port(s): 8001 (http)] ## ''
     *
     * @return
     */
    @RequestMapping(value = "/index")
    public String index() {
        // MDC 当前线程所绑定的局部变量
        InputMDC.putMDC();
        log.info("我是一条info日志");
        log.warn("我是一条warn日志");
        log.error("我是一条error日志");
        return "index";
    }

    @RequestMapping("/err")
    public String err() {
        try {
            int a = 1 / 0;
        } catch (Exception e) {
            log.error("算法异常", e);
        }
        return "err";
    }
}
