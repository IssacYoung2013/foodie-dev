package com.issac.designpattern.structual.flyweight;

/**
 * @author: ywy
 * @date: 2020-12-30
 * @desc:
 */
public class Manager implements Employee {
    private String depart;
    private String reportContent;

    public Manager(String depart) {
        this.depart = depart;
    }

    public String getReportContent() {
        return reportContent;
    }

    public void setReportContent(String reportContent) {
        this.reportContent = reportContent;
    }

    @Override
    public void report() {
        System.out.println(reportContent);
    }
}
