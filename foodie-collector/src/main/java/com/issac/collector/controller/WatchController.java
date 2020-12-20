package com.issac.collector.controller;

import com.alibaba.fastjson.JSON;
import com.issac.collector.entity.AccurateWatcherMessage;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: ywy
 * @date: 2020-12-13
 * @desc:
 */
@RestController
public class WatchController {
    @RequestMapping(value ="/accurateWatch")
    public String watch(@RequestBody AccurateWatcherMessage accurateWatcherMessage) {
        String ret = JSON.toJSONString(accurateWatcherMessage);
        System.err.println("----告警内容----:" + ret);
        return "is watched" + ret;
    }
}
