package com.issac.designpattern.behavioral.chainofresponsibility;

/**
 * @author: ywy
 * @date: 2020-12-29
 * @desc:
 */
public abstract class Approver {
    protected Approver approver;
    public void setNextApprover(Approver approver) {
        this.approver = approver;
    }
    public abstract void delpoy(Course course);
}
