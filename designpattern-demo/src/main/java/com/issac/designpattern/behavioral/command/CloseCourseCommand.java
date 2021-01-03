package com.issac.designpattern.behavioral.command;

/**
 * @author: ywy
 * @date: 2020-12-29
 * @desc:
 */
public class CloseCourseCommand implements Command {
    private CourseVideo courseVideo;

    public CloseCourseCommand(CourseVideo courseVideo) {
        this.courseVideo = courseVideo;
    }

    @Override
    public void execute() {
        courseVideo.close();
    }
}
