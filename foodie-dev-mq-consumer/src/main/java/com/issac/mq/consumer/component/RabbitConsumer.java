package com.issac.mq.consumer.component;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

/**
 * @author: ywy
 * @date: 2020-11-21
 * @desc:
 */
@Component
public class RabbitConsumer {

    /**
     * 组合使用监听
     *
     * @param message
     * @param channel
     * @throws Exception
     */
    @RabbitListener(
            bindings = @QueueBinding(
                    value = @Queue(value = "queue-1", durable = "true")
                    , exchange = @Exchange(name = "${spring.rabbitmq.listener.order.exchange.name}", durable = "true",
                    type = "topic", ignoreDeclarationExceptions = "true"),
                    key = "springboot.*"
            )
    )
    @RabbitHandler
    public void onMessage(Message message, Channel channel) throws Exception {
        // 1. 收到消息进行业务端消费处理
        System.out.println("------------------");
        System.out.println("消费消息：" + message.getPayload());
        // 2. 处理成功获取deliverTag，手工ack
        Long deliverTag = (Long) message.getHeaders().get(AmqpHeaders.DELIVERY_TAG);

        channel.basicAck(deliverTag, false);
    }
}
