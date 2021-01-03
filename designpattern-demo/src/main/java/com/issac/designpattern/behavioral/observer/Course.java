package com.issac.designpattern.behavioral.observer;

import java.util.Observable;

/**
 * @author: ywy
 * @date: 2020-12-29
 * @desc: 被观察对象
 */
public class Course extends Observable {
    private String courseName;

    public Course(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseName() {
        return courseName;
    }

    public void produceQuestion(Course course,Question question) {
        System.out.println(question.getUserName() + "在" +course.getCourseName() + "提了一个问题");
        setChanged();
        notifyObservers(question);
    }
}
