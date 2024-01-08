package com.harvestdiary.ui.controller.homepage;

import cn.hutool.core.convert.NumberChineseFormatter;
import cn.hutool.core.date.ChineseDate;
import cn.hutool.core.date.chinese.SolarTerms;
import com.harvestdiary.pojo.Lattice;
import de.felixroske.jfxsupport.FXMLController;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import lombok.extern.slf4j.Slf4j;

import java.text.DateFormatSymbols;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


@Slf4j
@FXMLController
public class CalendarUIController {
    @FXML
    private Label month;
    @FXML
    private GridPane calendar;


    List<Lattice> list = new ArrayList<>();
    @FXML
    public void initialize() {
        setCalendar(LocalDate.now());

    }

    private void setCalendar(LocalDate dateTime) {
        // 添加按钮到每个格子
        // 添加Button到每个格子
        int count = 0;
        for (int row = 1; row < 6; row++) {
            for (int col = 0; col < 7; col++) {
                list.add(new Lattice());
                calendar.add(list.get(count).getStackPane(), col, row);
                count++;
            }
        }
        getTime(dateTime);
    }

    private void getTime(LocalDate currentDate) {
        // 获取当前月份
        month.setText(getChineseMonth(currentDate.getMonthValue()));

        // 获取这个月的第一天日期
        LocalDate firstDayOfMonth = currentDate.withDayOfMonth(1);

        // 获取这个月的最后一天日期
        LocalDate lastDayOfMonth = currentDate.withDayOfMonth(currentDate.lengthOfMonth());

        // 循环从第一天到最后一天
        int count = 1;
        int day = firstDayOfMonth.getDayOfWeek().getValue();
        int lastDayOfMonthPlus =  35 - day;
        while (lastDayOfMonthPlus >= count) {
            setLabelDate(firstDayOfMonth, day);
            firstDayOfMonth = firstDayOfMonth.plusDays(1);
            day++;
            count++;
        }

        firstDayOfMonth = currentDate.withDayOfMonth(1);
        day = firstDayOfMonth.getDayOfWeek().getValue();
        for (int i = day - 1; i >= 0; i--) {
            firstDayOfMonth = firstDayOfMonth.minusDays(1);

            setLabelDate(firstDayOfMonth, i);

        }


    }

    private void setLabelDate(LocalDate firstDayOfMonth, int i) {
        ChineseDate chineseDate = new ChineseDate(firstDayOfMonth);

        Lattice lattice = list.get(i);

        lattice.setDate(firstDayOfMonth.getDayOfMonth() + "");
        if (SolarTerms.getTerm(chineseDate).isEmpty()) {
            lattice.setChineseDate(chineseDate.getChineseDay());
        } else {
            lattice.setChineseDate(SolarTerms.getTerm(chineseDate));
        }
    }

    private static String getChineseMonth(int month) {
        String[] chineseMonths = new DateFormatSymbols(Locale.CHINA).getMonths();
        return chineseMonths[month - 1];
    }

}
