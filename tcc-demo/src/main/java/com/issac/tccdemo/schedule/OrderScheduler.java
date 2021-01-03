package com.issac.tccdemo.schedule;

import com.issac.tccdemo.db106.dao.PaymentMsgMapper;
import com.issac.tccdemo.db106.model.PaymentMsg;
import com.issac.tccdemo.db106.model.PaymentMsgExample;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author: ywy
 * @date: 2021-01-02
 * @desc:
 */
@Service
public class OrderScheduler {

    @Resource
    PaymentMsgMapper paymentMsgMapper;

    @Scheduled(cron = "0/10 * * * * ?")
    public void orderNotify() throws IOException {

        PaymentMsgExample paymentMsgExample = new PaymentMsgExample();
        // 未发送
        paymentMsgExample.createCriteria().andStatusEqualTo(0);
        List<PaymentMsg> paymentMsgs =
                paymentMsgMapper.selectByExample(paymentMsgExample);
        if (paymentMsgs == null || paymentMsgs.size() == 0) {
            return;
        }

        for (PaymentMsg paymentMsg : paymentMsgs) {
            Integer orderId = paymentMsg.getOrderId();
            CloseableHttpClient httpClient = HttpClientBuilder.create().build();
            HttpPost httpUriRequest = new HttpPost("http://localhost:8080/handleOrder");
            NameValuePair orderIdPair = new BasicNameValuePair("orderId", orderId.toString());
            List<NameValuePair> list = new ArrayList<>();
            list.add(orderIdPair);
            HttpEntity httpEntity = new UrlEncodedFormEntity(list);
            httpUriRequest.setEntity(httpEntity);
            CloseableHttpResponse response = httpClient.execute(httpUriRequest);
            String s = EntityUtils.toString(response.getEntity());
            if ("success".equals(s)) {
                paymentMsg.setStatus(1);
            } else {
                Integer failureCnt = paymentMsg.getFailureCnt();
                failureCnt++;
                paymentMsg.setFailureCnt(failureCnt);
                if (failureCnt > 5) {
                    paymentMsg.setStatus(2);
                }
            }
            paymentMsg.setUpdateTime(new Date());
            paymentMsg.setUpdateUser(0);
            paymentMsgMapper.updateByPrimaryKey(paymentMsg);
        }

    }
}
