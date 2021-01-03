package com.issac.designpattern.structual.flyweight;

/**
 * @author: ywy
 * @date: 2020-12-30
 * @desc:
 */
public class Test {
    public static final String departs[] = {"RD","QA","PM"};

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            String depart = departs[(int) (Math.random() * departs.length)];
            Manager m = (Manager) EmployeeFactory.getManager(depart);
        }
    }
}
