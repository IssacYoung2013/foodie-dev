package com.issac.designpattern.behavioral.mediator;

/**
 * @author: ywy
 * @date: 2020-12-29
 * @desc:
 */
public class User {
    private String name;
    private String message;

    public User(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void sendMessage(String message) {
        StudyGroup.showMessage(this,message);
    }
}
