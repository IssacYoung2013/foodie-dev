package com.issac.designpattern.behavioral.command;

/**
 * @author: ywy
 * @date: 2020-12-29
 * @desc:
 */
public class CourseVideo {
    private String name;

    public CourseVideo(String name) {
        this.name = name;
    }

    public void open() {
        System.out.println(this.name + "开放");
    }

    public void close() {
        System.out.println(this.name + "下架");
    }
}
