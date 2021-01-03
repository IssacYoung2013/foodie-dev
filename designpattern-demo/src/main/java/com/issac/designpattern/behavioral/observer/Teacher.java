package com.issac.designpattern.behavioral.observer;

import java.util.Observable;
import java.util.Observer;

/**
 * @author: ywy
 * @date: 2020-12-29
 * @desc:
 */
public class Teacher implements Observer {
    private String teacherName;

    public Teacher(String teacherName) {
        this.teacherName = teacherName;
    }

    /**
     * @param o   被观察者对象
     * @param arg 参数
     */
    @Override
    public void update(Observable o, Object arg) {
        Course course = (Course) o;
        Question question = (Question) arg;
        System.out.println(teacherName + "老师的课程" + course.getCourseName() +
                "接收到一个" + question.getUserName() + "提的问题:" + question.getQuestionContent());
    }
}
