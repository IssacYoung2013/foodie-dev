package com.issac.tccdemo.consumer;

import com.issac.tccdemo.db107.dao.OrderMapper;
import com.issac.tccdemo.db107.model.Order;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author: ywy
 * @date: 2021-01-02
 * @desc:
 */
@Service("messageListener")
public class ChangeOrderStatus implements MessageListenerConcurrently {

    @Resource
    OrderMapper orderMapper;

    @Override
    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
        if (CollectionUtils.isEmpty(msgs)) {
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        }
        for (MessageExt msg : msgs) {
            String orderId = msg.getKeys();
            String tMsg = new String(msg.getBody());
            System.out.println("msg=" + tMsg);
            Order order = orderMapper.selectByPrimaryKey(Integer.valueOf(orderId));
            if (order == null) {
                return ConsumeConcurrentlyStatus.RECONSUME_LATER;
            }
            try {
                // 已支付
                order.setOrderStatus(1);
                order.setUpdateTime(new Date());
                order.setUpdateUser(0);
                orderMapper.updateByPrimaryKey(order);
            } catch (Exception e) {
                e.printStackTrace();
                return ConsumeConcurrentlyStatus.RECONSUME_LATER;
            }
        }

        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
    }
}
