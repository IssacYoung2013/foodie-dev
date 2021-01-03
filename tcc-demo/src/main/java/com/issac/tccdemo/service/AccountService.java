package com.issac.tccdemo.service;

import com.issac.tccdemo.db106.dao.AccountAMapper;
import com.issac.tccdemo.db106.model.AccountA;
import com.issac.tccdemo.db107.dao.AccountBMapper;
import com.issac.tccdemo.db107.model.AccountB;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;

/**
 * @author: ywy
 * @date: 2021-01-02
 * @desc:
 */
@Service
public class AccountService {

    @Resource
    AccountAMapper accountAMapper;

    @Resource
    AccountBMapper accountBMapper;

    @Transactional(transactionManager = "tm106", rollbackFor = Exception.class)
    public void transferAccount() {
        AccountA accountA = accountAMapper.selectByPrimaryKey(1);
        accountA.setBalance(accountA.getBalance().subtract(new BigDecimal(200)));
        accountAMapper.updateByPrimaryKey(accountA);
        AccountB accountB = accountBMapper.selectByPrimaryKey(2);
        accountB.setBalance(accountB.getBalance().add(new BigDecimal(200)));
        accountBMapper.updateByPrimaryKey(accountB);
        try {

            int i = 1 / 0;
        } catch (Exception e) {
            AccountB accountb = accountBMapper.selectByPrimaryKey(2);
            accountb.setBalance(accountb.getBalance().subtract(new BigDecimal(200)));
            accountBMapper.updateByPrimaryKey(accountb);
        }
    }
}
