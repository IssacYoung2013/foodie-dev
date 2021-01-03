package com.issac.designpattern.behavioral.chainofresponsibility;

/**
 * @author: ywy
 * @date: 2020-12-30
 * @desc:
 */
public class ArticleApprover extends Approver {

    @Override
    public void delpoy(Course course) {
        if(course.getArticle() != null && course.getArticle() != "") {

        }
    }
}
