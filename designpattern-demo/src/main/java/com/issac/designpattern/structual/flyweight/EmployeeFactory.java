package com.issac.designpattern.structual.flyweight;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: ywy
 * @date: 2020-12-30
 * @desc:
 */
public class EmployeeFactory {
    private static final Map<String, Employee> EMPLOYEE_MAP = new HashMap<>();

    public static Employee getManager(String depart) {
        Manager manager = (Manager) EMPLOYEE_MAP.get(depart);
        if (manager == null) {
            manager = new Manager(depart);
            manager.setReportContent(depart + "回报内容");
            EMPLOYEE_MAP.put(depart, manager);
            System.out.println("创建部门经理：" + depart);
            System.out.println("创建报告：" + manager.getReportContent());
        }
        return manager;
    }


}
