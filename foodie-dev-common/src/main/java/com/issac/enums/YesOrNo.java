package com.issac.enums;

/**
 * @author: ywy
 * @date: 2020-05-13
 * @desc: 是否
 */
public enum YesOrNo {
    NO(0,"否"),
    YES(1,"是");

    public final Integer type;

    public final String value;

    YesOrNo(Integer type, String value) {
        this.type = type;
        this.value = value;
    }
}
