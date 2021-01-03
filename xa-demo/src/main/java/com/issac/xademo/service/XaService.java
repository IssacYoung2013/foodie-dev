package com.issac.xademo.service;

import com.issac.xademo.db106.dao.XA106Mapper;
import com.issac.xademo.db106.model.XA106;
import com.issac.xademo.db107.dao.XA107Mapper;
import com.issac.xademo.db107.model.XA107;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author: ywy
 * @date: 2021-01-01
 * @desc:
 */
@Service
public class XaService {

    @Resource
    private XA106Mapper xa106Mapper;

    @Resource
    private XA107Mapper xa107Mapper;

    @Transactional(transactionManager = "xaTransaction",rollbackFor = Exception.class)
    public void testXA() {
        XA106 xa106 = new XA106();
        xa106.setId(1);
        xa106.setName("xa_106");
        xa106Mapper.insert(xa106);

        XA107 xa107 = new XA107();
        xa107.setId(1);
        xa107.setName("xa_107");
        xa107Mapper.insert(xa107);
    }
}
