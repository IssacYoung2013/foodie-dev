package com.issac.util;

/**
 * @author: ywy
 * @date: 2020-05-30
 * @desc:
 */
public class CurrencyUtils {
    public static Integer getYuan2Fen(String totalCount) {
        return Integer.parseInt(totalCount.replace("元", "").replace("¥", "")) * 100;
    }

    public static String getFen2YuanWithPoint(Integer totalCount) {
        double result = ((double) totalCount) / 100;
        return result + "";
    }
}
