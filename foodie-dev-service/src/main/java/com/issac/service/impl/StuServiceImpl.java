package com.issac.service.impl;

import com.issac.mapper.StuMapper;
import com.issac.pojo.Stu;
import com.issac.service.StuService;
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
public class StuServiceImpl implements StuService {

    @Resource
    StuMapper stuMapper;

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Stu getStuInfo(int id) {
        Stu stu = stuMapper.selectByPrimaryKey(id);
        return stu;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void saveStu() {
        Stu stu = new Stu();
        stu.setName("issac");
        stu.setAge(30);
        stuMapper.insert(stu);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void updateStu(int id) {
        Stu stu = new Stu();
        stu.setName("young");
        stu.setAge(20);
        stu.setId(id);
        stuMapper.updateByPrimaryKey(stu);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteStu(int id) {
        stuMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void saveParent() {
        Stu stu = new Stu();
        stu.setName("parent");
        stu.setAge(19);
        stuMapper.insert(stu);
    }

    @Override
    @Transactional(propagation = Propagation.NEVER)
    public void saveChildren() {
        saveChild1();
        int a = 1/ 0;
        saveChild2();
    }

    public void saveChild1() {
        Stu stu = new Stu();
        stu.setName("child-1");
        stu.setAge(11);
        stuMapper.insert(stu);
    }

    public void saveChild2() {
        Stu stu = new Stu();
        stu.setName("child-2");
        stu.setAge(22);
        stuMapper.insert(stu);
    }


}
