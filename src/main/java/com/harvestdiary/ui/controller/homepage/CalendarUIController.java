package com.harvestdiary.ui.controller.homepage;

import cn.hutool.core.date.ChineseDate;
import cn.hutool.core.date.chinese.SolarTerms;
import com.harvestdiary.other.tools.SettingFontIcon;
import com.harvestdiary.pojo.Lattice;
import com.jfoenix.controls.JFXButton;
import de.felixroske.jfxsupport.FXMLController;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import lombok.extern.slf4j.Slf4j;
import org.kordamp.ikonli.antdesignicons.AntDesignIconsOutlined;

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
    private JFXButton backToToday;
    @FXML
    private JFXButton next;
    @FXML
    private JFXButton prev;
    @FXML
    private GridPane calendar;
    @FXML
    private Label date;


    List<Lattice> list = new ArrayList<>();
    LocalDate checkDate = LocalDate.now();
    @FXML
    public void initialize() {
        setIcon();

        setCalendar(checkDate);



    }

    @FXML
    void backToToday(MouseEvent event) {
        resettingCalendar();
        checkDate = LocalDate.now();
        setCalendar(checkDate);
    }

    private void setCalendar(LocalDate today) {
        clearCalender();

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
        getTime(today);

        int day = today.withDayOfMonth(1).getDayOfWeek().getValue() - 1 + today.getDayOfMonth();
        if (day < 35){
            toRedLattice(list.get(day));
        }


        setCalendarEvent();
    }
    private void setCalendarEvent() {
        for (Lattice lattice : list) {
            lattice.getJfxButton().setOnAction(event -> {
                toRedLattice(lattice);
                if (checkMonth(lattice)){
                    log.info("这个月：{}", lattice.getLocalDate());
                    resettingCalendar();
                    lattice.getDate().setTextFill(Color.RED);
                    lattice.getChineseDate().setTextFill(Color.RED);
                }else {
                    log.info("{}", lattice.getLocalDate());
                    clearCalender();
                    setCalendar(lattice.getLocalDate());
                }
            });
        }
    }
    private Boolean checkMonth(Lattice lattice){
        LocalDate day = lattice.getLocalDate();
        if (day.getMonthValue() == checkDate.getMonthValue()){
            checkDate = day;
            return true;
        }
        checkDate = day;
        return false;
    }
    private void resettingCalendar() {
        for (Lattice lattice : list) {
            lattice.getDate().setTextFill(Color.BLACK);
            lattice.getChineseDate().setTextFill(Color.BLACK);
        }
    }
    private void clearCalender(){
        for (Lattice lattice : list) {
            calendar.getChildren().remove(lattice.getStackPane());
        }
        list.clear();
    }
    private void toRedLattice(Lattice lattice) {
        lattice.getDate().setTextFill(Color.RED);
        lattice.getChineseDate().setTextFill(Color.RED);
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
            ChineseDate chineseDate = new ChineseDate(firstDayOfMonth);
            Lattice lattice = list.get(day);


            lattice.setDate(firstDayOfMonth.getDayOfMonth() + "");
            if (SolarTerms.getTerm(chineseDate).isEmpty()) {
                lattice.setChineseDate(chineseDate.getChineseDay());
            } else {
                lattice.setChineseDate(SolarTerms.getTerm(chineseDate));
            }

            if (count > lastDayOfMonth.getDayOfMonth()){
                lattice.getStackPane().setOpacity(0.3);
            }
            lattice.setLocalDate(firstDayOfMonth);
            firstDayOfMonth = firstDayOfMonth.plusDays(1);
            day++;
            count++;
        }

        firstDayOfMonth = currentDate.withDayOfMonth(1);
        day = firstDayOfMonth.getDayOfWeek().getValue();
        for (int i = day - 1; i >= 0; i--) {
            firstDayOfMonth = firstDayOfMonth.minusDays(1);


            ChineseDate chineseDate = new ChineseDate(firstDayOfMonth);

            Lattice lattice = list.get(i);

            lattice.getStackPane().setOpacity(0.3);

            lattice.setDate(firstDayOfMonth.getDayOfMonth() + "");
            if (SolarTerms.getTerm(chineseDate).isEmpty()) {
                lattice.setChineseDate(chineseDate.getChineseDay());
            } else {
                lattice.setChineseDate(SolarTerms.getTerm(chineseDate));
            }

            lattice.setLocalDate(firstDayOfMonth);
        }


    }
    private String getChineseMonth(int month) {
        String[] chineseMonths = new DateFormatSymbols(Locale.CHINA).getMonths();
        return chineseMonths[month - 1];
    }
    private void setIcon(){
        backToToday.setGraphic(SettingFontIcon.setSizeAndColor(AntDesignIconsOutlined.RELOAD, 14, Color.web("#617172")));
        prev.setGraphic(SettingFontIcon.setSizeAndColor(AntDesignIconsOutlined.CARET_LEFT, 18, Color.web("#617172")));
        next.setGraphic(SettingFontIcon.setSizeAndColor(AntDesignIconsOutlined.CARET_RIGHT, 18, Color.web("#617172")));
    }
}
