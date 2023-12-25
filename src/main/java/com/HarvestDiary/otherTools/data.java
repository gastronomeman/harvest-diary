package com.HarvestDiary.otherTools;

import cn.hutool.core.date.ChineseDate;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.chinese.SolarTerms;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class data {
    public static void main(String[] args) {
        // 构建农历对象
        ChineseDate chineseDate = new ChineseDate(2022, 12, 10);
        // 构建公立对象
        ChineseDate chineseDate1 = new ChineseDate(DateUtil.parse("2023-01-01"));
        // 年
        int chineseYear = chineseDate.getChineseYear();
        int chineseYear1 = chineseDate1.getChineseYear();
        String cyclical = chineseDate.getCyclical();
        String cyclical1 = chineseDate1.getCyclical();
        // 获取天干地支
        String cyclicalYMD = chineseDate.getCyclicalYMD();
        String cyclicalYMD1 = chineseDate1.getCyclicalYMD();
        log.info("农历构建：" + chineseYear + "年");
        log.info("公历构建：" + chineseYear1 + "年");
        log.info("农历构建：" + cyclical);
        log.info("农历构建：" + cyclicalYMD);
        log.info("公历构建：" + cyclical1);
        log.info("公历构建：" + cyclicalYMD1);
        // 月
        String chineseMonth = chineseDate.getChineseMonth();
        String chineseMonth1 = chineseDate1.getChineseMonth();
        log.info("农历构建：" + chineseMonth);
        log.info("公历构建：" + chineseMonth1);
        String chineseMonthName = chineseDate.getChineseMonthName();
        String chineseMonthName1 = chineseDate1.getChineseMonthName();
        log.info("农历构建：" + chineseMonthName);
        log.info("公历构建：" + chineseMonthName1);
        // 农历构建的，获取农历day
        String chineseDay = chineseDate.getChineseDay();
        log.info(chineseDay);
        // 农历构建的， 获取公历day
        int gregorianDay = chineseDate.getGregorianDay();
        log.info(gregorianDay + "号");
        // 公历构建的，获取农历的day
        String chineseDay1 = chineseDate1.getChineseDay();
        log.info(chineseDay1);
        // 公历构建，自己的day就是公历的day
        int gregorianDay1 = chineseDate1.getGregorianDay();
        log.info(gregorianDay1 + "号");

        // 生肖
        String zodiac = chineseDate.getChineseZodiac();
        String zodiac1 = chineseDate1.getChineseZodiac();
        log.info("农历构建：" + zodiac);
        log.info("公历构建：" + zodiac1);

        // 节日
        String festivals = chineseDate.getFestivals();
        String festivals1 = chineseDate1.getFestivals();
        // 空字符串，因为1月1号是元旦节，不是中国的传统节日
        log.info(festivals);
        log.info(festivals1);

        // 2023年1月21日是除夕
        DateTime dateTime = DateUtil.parse("2023年1月21日");
        ChineseDate chineseDate2 = new ChineseDate(dateTime);
        String festivals2 = chineseDate2.getFestivals();
        // 除夕
        log.info(festivals2);

        // 2023年1月22日是春节
        String festivals3 = new ChineseDate(DateUtil.parse("2023年1月22日")).getFestivals();
        log.info(festivals3);
        // 2023年1月23日是正月初二，犬日
        String festivals4 = new ChineseDate(DateUtil.parse("2023年1月23日")).getFestivals();
        log.info(festivals4);
        // 2023年1月24日是正月初三，猪日
        String festivals5 = new ChineseDate(DateUtil.parse("2023年1月24日")).getFestivals();
        log.info(festivals5);

        // 壬寅虎年 腊月初十
        log.info(chineseDate.toString());
        // 壬寅虎年 腊月初十
        log.info(chineseDate1.toString());
        // 癸卯兔年 正月初二
        log.info(chineseDate2.toString());

    }
}
