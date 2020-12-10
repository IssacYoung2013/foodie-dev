package com.issac.mq.producer.component;

import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.UUID;

/**
 * @author: ywy
 * @date: 2020-11-20
 * @desc:
 */
@Component
public class RabbitSender {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 确认消息的回调接口，用于确认消息是否被broker锁收到
     */
    final RabbitTemplate.ConfirmCallback confirmCallback = new RabbitTemplate.ConfirmCallback() {
        /**
         *
         * @param correlationData 唯一标识
         * @param b 是否到达broker
         * @param s
         */
        @Override
        public void confirm(CorrelationData correlationData, boolean b, String s) {
            System.out.println("消息ack结果" + b + ",correlationData:" + correlationData.getId());
        }
    };

    /**
     * 对外发送消息
     *
     * @param message
     * @param properties 额外属性
     * @throws Exception
     */
    public void send(Object message, Map<String, Object> properties) throws Exception {
        MessageHeaders messageHeaders = new MessageHeaders(properties);
        Message<Object> msg = MessageBuilder.createMessage(message, messageHeaders);
        rabbitTemplate.setConfirmCallback(confirmCallback);
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
        MessagePostProcessor mpp = new MessagePostProcessor() {
            @Override
            public org.springframework.amqp.core.Message postProcessMessage(org.springframework.amqp.core.Message message) throws AmqpException {
                System.out.println("post to do :" + message);
                return message;
            }
        };
        rabbitTemplate.convertAndSend("exchange-1", "springboot.rabbit", msg, mpp, correlationData);
    }
}
