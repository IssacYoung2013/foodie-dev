package com.issac.designpattern.behavioral.command;

/**
 * @author: ywy
 * @date: 2020-12-29
 * @desc:
 */
public class Test {
    public static void main(String[] args) {
        CourseVideo courseVideo = new CourseVideo("设计模式");
        Command openCommand = new OpenCourseCommand(courseVideo);
        Command closeCommand = new CloseCourseCommand(courseVideo);

        Stuff stuff = new Stuff();
        stuff.addCommand(openCommand);
        stuff.addCommand(closeCommand);

        stuff.executeCommands();
    }
}
