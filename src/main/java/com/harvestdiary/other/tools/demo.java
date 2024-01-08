package com.harvestdiary.other.tools;

import cn.hutool.core.date.ChineseDate;
import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import lombok.extern.slf4j.Slf4j;

import java.text.DateFormatSymbols;
import java.util.Date;
import java.util.Locale;

@Slf4j
public class demo {
    public static void main(String[] args) {
        int month = 1; // 这里以1表示一月，你可以根据实际情况传入当前月份

        // 获取月份的中文表示
        String chineseMonth = getChineseMonth(month);

        System.out.println(chineseMonth);
    }

    private static String getChineseMonth(int month) {
        String[] chineseMonths = new DateFormatSymbols(Locale.CHINA).getMonths();
        return chineseMonths[month - 1];
    }
}
