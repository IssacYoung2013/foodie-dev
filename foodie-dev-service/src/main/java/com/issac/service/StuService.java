package com.issac.service;

import com.issac.pojo.Stu;

/**
 * @author: ywy
 * @date: 2020-05-10
 * @desc:
 */
public interface StuService {

    Stu getStuInfo(int id);

    void saveStu();

    void updateStu(int id);

    void deleteStu(int id);

    void saveParent();

    void saveChildren();
}
