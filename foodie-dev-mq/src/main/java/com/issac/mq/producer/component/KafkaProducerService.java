package com.issac.mq.producer.component;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import javax.annotation.Resource;

/**
 * @author: ywy
 * @date: 2020-12-12
 * @desc:
 */
@Slf4j
@Component
public class KafkaProducerService {

    @Resource
    KafkaTemplate<String, Object> kafkaTemplate;

    public void sendMessage(String topic, Object object) {
        ListenableFuture<SendResult<String, Object>> future = kafkaTemplate.send(topic, object);
        future.addCallback(new ListenableFutureCallback<SendResult<String, Object>>() {
            @Override
            public void onFailure(Throwable throwable) {
                log.info("发送消息失败：" + throwable.getMessage());
            }

            @Override
            public void onSuccess(SendResult<String, Object> stringObjectSendResult) {
                log.info("发送消息成功：" + stringObjectSendResult.toString());
            }
        });
    }
}
