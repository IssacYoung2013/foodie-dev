package com.issac.service.impl;

import com.issac.service.StuService;
import com.issac.service.TestTransService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author: ywy
 * @date: 2020-05-10
 * @desc:
 */
@Service
public class TestTransServiceImpl implements TestTransService {

    @Resource
    private StuService stuService;

    /**
     * 事务传播级别:
     * REQUIRED: 使用当前的事务，如果当前没有事务，则自己新建一个事务，子方法必须运行在一个事务中；
     * 如果当前存在事务，则加入事务；作用于增删改
     * SUPPORTS: 如果当前有事务，则使用；否则，不使用
     * MANDATORY: 必须要有事务，类似EJB
     * REQUIRES_NEW: 创建新事务，如果当前存在，则挂起当前事务，创建新事务
     * NOT_SUPPORTED: 没有事务执行语句
     * NEVER: 没有事务执行语句，有事务则抛出异常
     * NESTED: 父子事务，嵌套事务是独立提交或者回滚，如果主事务提交，则会携带子事务一起提交；如果主事务回滚，则子事务会一起回滚；
     *        子事务异常，父事务可以回滚或者不回滚
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void testPropagationTrans() {
        stuService.saveParent();
        try {
            // save point
            stuService.saveChildren();
        } catch (Exception e) {
            e.printStackTrace();
        }
//        int a = 1/ 0;
    }
}
