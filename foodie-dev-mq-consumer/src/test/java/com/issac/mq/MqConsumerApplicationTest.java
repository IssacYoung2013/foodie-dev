package com.issac.mq;

import com.issac.mq.consumer.component.KafkaConsumerService;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * @author: ywy
 * @date: 2020-12-12
 * @desc:
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class MqConsumerApplicationTest {

    @Autowired
    private KafkaConsumerService kafkaConsumerService;


}