package com.issac.mq.test;

import com.issac.mq.producer.component.KafkaProducerService;
import com.issac.mq.producer.component.RabbitSender;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: ywy
 * @date: 2020-11-21
 * @desc:
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class MqApplicationTest {

    @Autowired
    private RabbitSender rabbitSender;

    @Autowired
    private KafkaProducerService kafkaProducerService;

    @Test
    public void testSender() throws Exception {
        Map<String, Object> properties = new HashMap<>();
        properties.put("attr1", "12345");
        properties.put("attr2", "abcde");

        rabbitSender.send("hello rabbitmq", properties);

        Thread.sleep(10000);
    }


    @Test
    public void send() throws InterruptedException {
        String topic = "topic02";
        for (int i = 0; i < 1000; i++) {
            kafkaProducerService.sendMessage(topic,"hello kafka "+ i);
            Thread.sleep(5);
        }
        Thread.sleep(Integer.MAX_VALUE);
    }
}
