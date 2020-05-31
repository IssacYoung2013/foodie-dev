package com.issac.config;

import com.issac.service.OrderService;
import com.issac.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import static com.issac.util.DateUtil.DATETIME_PATTERN;

/**
 * @author: ywy
 * @date: 2020-05-31
 * @desc:
 */
@Component
public class OrderJob {

    @Autowired
    OrderService orderService;

    /**
     * 问题：
     * 1. 会有时间差，程序不严谨
     * 10:39 下单,11点检查不足一小时，12点检查，超过1小时多余39分钟
     * 2. 不支持集群
     * 解决方案：只使用一台计算机节点，单独用来运行所有的定时任务
     * 3. 会对数据库全表搜索，及其影响数据库性能
     *
     * 定时任务，仅仅只适用于小型轻量级项目，传统项目
     *
     * 后续课程会涉及到消息队列：MQ -》RabbitMQ，RocketMQ，Kafka，Redis,ZeroMQ...
     * 延时任务（队列）
     * 10:12下单，状态是10，11点12分检查，如果当前状态还是10，则直接关闭订单即可
     */
    @Scheduled(cron = "0 0 0/1 * * ?")
    public void autoCloseOrder() {
        orderService.closeOrder();
        System.out.println("执行定时任务，当前时间是：" + DateUtil.getCurrentDateString(DATETIME_PATTERN));
    }
}
