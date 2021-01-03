package com.issac.designpattern.behavioral.command;

/**
 * @author: ywy
 * @date: 2020-12-29
 * @desc:
 */
public class OpenCourseCommand implements Command {
    private CourseVideo courseVideo;

    public OpenCourseCommand(CourseVideo courseVideo) {
        this.courseVideo = courseVideo;
    }

    @Override
    public void execute() {
        courseVideo.open();
    }
}
