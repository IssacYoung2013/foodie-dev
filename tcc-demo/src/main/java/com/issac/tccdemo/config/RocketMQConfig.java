package com.issac.tccdemo.config;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListener;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @author: ywy
 * @date: 2021-01-02
 * @desc:
 */
@Configuration
public class RocketMQConfig {

    @Bean(initMethod = "start", destroyMethod = "shutdown")
    public DefaultMQProducer producer() {
        DefaultMQProducer producer = new DefaultMQProducer("paymentGroup");
        producer.setNamesrvAddr("localhost:9876");
        return producer;
    }

    @Bean(initMethod = "start", destroyMethod = "shutdown")
    public DefaultMQPushConsumer consumer(@Qualifier("messageListener") MessageListenerConcurrently messageListener) throws MQClientException {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("paymentConsumerGroup");
        consumer.setNamesrvAddr("localhost:9876");
        consumer.subscribe("payment", "*");
        consumer.registerMessageListener(messageListener);
        return consumer;
    }

}
