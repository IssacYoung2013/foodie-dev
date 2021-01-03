package com.issac.tccdemo.service;

import com.issac.tccdemo.db106.dao.AccountAMapper;
import com.issac.tccdemo.db106.dao.PaymentMsgMapper;
import com.issac.tccdemo.db106.model.AccountA;
import com.issac.tccdemo.db106.model.PaymentMsg;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author: ywy
 * @date: 2021-01-02
 * @desc:
 */
@Service
public class PaymentService {
    @Resource
    AccountAMapper accountAMapper;
    @Resource
    PaymentMsgMapper paymentMsgMapper;
    @Autowired
    DefaultMQProducer producer;

    @Transactional(transactionManager = "tm106", rollbackFor = Exception.class)
    public int payment(int userId, int orderId, BigDecimal amount) {
        // 支付操作
        AccountA accountA = accountAMapper.selectByPrimaryKey(userId);
        if (accountA == null) {
            return 1;
        }
        if (accountA.getBalance().compareTo(amount) < 0) {
            return 2;
        }
        accountA.setBalance(accountA.getBalance().subtract(amount));
        accountAMapper.updateByPrimaryKey(accountA);

        PaymentMsg paymentMsg = new PaymentMsg();
        paymentMsg.setOrderId(orderId);
        paymentMsg.setStatus(0);
        paymentMsg.setCreateTime(new Date());
        paymentMsg.setCreateUser(userId);
        paymentMsg.setUpdateUser(userId);
        paymentMsg.setUpdateTime(new Date());

        paymentMsgMapper.insertSelective(paymentMsg);

        return 0;
    }

    @Transactional(transactionManager = "tm106", rollbackFor = Exception.class)
    public int paymentMQ(int userId, int orderId, BigDecimal amount) throws Exception {
        // 支付操作
        AccountA accountA = accountAMapper.selectByPrimaryKey(userId);
        if (accountA == null) {
            return 1;
        }
        if (accountA.getBalance().compareTo(amount) < 0) {
            return 2;
        }
        accountA.setBalance(accountA.getBalance().subtract(amount));
        accountAMapper.updateByPrimaryKey(accountA);

        Message message = new Message();
        message.setTopic("payment");
        message.setKeys(orderId + "");
        message.setBody("订单已支付".getBytes());
        try {
            SendResult sendResult = producer.send(message);
            if (sendResult.getSendStatus() == SendStatus.SEND_OK) {
                return 0;
            } else {
                throw new Exception("消息发送失败！");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
}
