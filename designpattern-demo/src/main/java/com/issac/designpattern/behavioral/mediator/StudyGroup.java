package com.issac.designpattern.behavioral.mediator;

import java.util.Date;

/**
 * @author: ywy
 * @date: 2020-12-29
 * @desc:
 */
public class StudyGroup {
    public static void showMessage(User user, String message) {
        System.out.println(new Date().toString() + "[" + user.getName() + "]:" + user.getMessage());
    }
}
