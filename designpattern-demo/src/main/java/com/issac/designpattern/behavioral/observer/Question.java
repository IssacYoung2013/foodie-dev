package com.issac.designpattern.behavioral.observer;

/**
 * @author: ywy
 * @date: 2020-12-29
 * @desc:
 */
public class Question {
    private String userName;
    private String questionContent;

    public Question(String userName, String questionContent) {
        this.userName = userName;
        this.questionContent = questionContent;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getQuestionContent() {
        return questionContent;
    }

    public void setQuestionContent(String questionContent) {
        this.questionContent = questionContent;
    }
}
