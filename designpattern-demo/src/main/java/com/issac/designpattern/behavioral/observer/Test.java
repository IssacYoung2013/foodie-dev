package com.issac.designpattern.behavioral.observer;

/**
 * @author: ywy
 * @date: 2020-12-29
 * @desc:
 */
public class Test {
    public static void main(String[] args) {
        Course course = new Course("设计模式精讲");
        Teacher teacher = new Teacher("Alpha");
        Teacher teacher2 = new Teacher("Beta");

        course.addObserver(teacher);
        course.addObserver(teacher2);

        // 业务逻辑
        Question question = new Question("Issac","Java的主函数如何编写");
        course.produceQuestion(course,question);
    }
}
