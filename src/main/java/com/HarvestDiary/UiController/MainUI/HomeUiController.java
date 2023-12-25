package com.HarvestDiary.UiController.MainUI;

import cn.hutool.core.date.ChineseDate;
import cn.hutool.core.date.chinese.SolarTerms;
import com.HarvestDiary.otherTools.SettingFontIcon;
import com.jfoenix.controls.JFXButton;
import de.felixroske.jfxsupport.FXMLController;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.util.StringConverter;
import lombok.extern.slf4j.Slf4j;
import org.kordamp.ikonli.antdesignicons.AntDesignIconsOutlined;

import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Date;

@Slf4j
@FXMLController
public class HomeUiController {
    @FXML
    private VBox HomeUI;
    @FXML
    private Label chineseDate;
    @FXML
    private DatePicker datePicker;
    @FXML
    private Label chineseYear;
    @FXML
    private Label solarTerms24;
    @FXML
    private Label festival;
    @FXML
    private StackPane imageBackground;
    @FXML
    private JFXButton change;

    @FXML
    public void initialize() throws IOException {
        // 创建圆角边框
        // 创建 Rectangle 作为剪裁区域
        Rectangle clip = new Rectangle(480, 270); // 替换为你希望的剪裁区域大小
        clip.setArcWidth(20); // 圆角宽度
        clip.setArcHeight(20); // 圆角高度

        //顶部日历天气设置
        setDataTime();


        imageBackground.setClip(clip);

        change.setGraphic(SettingFontIcon.setColor(AntDesignIconsOutlined.SYNC, Color.web("#617172")));


    }

    private void setDataTime() {
        //让DatePicker显示格式为yyyy-MM-dd
        datePicker.setConverter(dateFormatter());
        //设置日期为现在时间
        LocalDate nowDate = LocalDate.now();
        datePicker.setValue(nowDate);

        datePicker.valueProperty().addListener(((observable, oldValue, newValue) -> {
            log.info(String.valueOf(newValue));
        }));

        //设置农历日月
        ChineseDate chineseCalendar = new ChineseDate(nowDate);

        //大字
        chineseDate.setText(chineseCalendar.getChineseMonthName() + chineseCalendar.getChineseDay());
        //天干地支+生肖
        chineseYear.setText(chineseCalendar.getCyclical() + chineseCalendar.getChineseZodiac() + "年");
        //24节气判断
        solarTerms24.setText(findSolarTerms(nowDate));
        //获取中国传统节日或者工作日或者休息日
        festival.setText(typeDay(chineseCalendar, nowDate));

    }

    //格式化DataPicker
    private StringConverter<LocalDate> dateFormatter() {
        // 创建日期格式化器
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        // 创建StringConverter并设置给DatePicker让DatePicker显示格式为yyyy-MM-dd
        return new StringConverter<>() {
            @Override
            public String toString(LocalDate object) {
                if (object != null) {
                    return dateFormatter.format(object);
                } else {
                    return "";
                }
            }

            @Override
            public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return LocalDate.parse(string, dateFormatter);
                } else {
                    return null;
                }
            }
        };
    }

    //查找节气，如果是节气当天显示节气，如果不是当前这显示**（节气）后，**（节气）前
    private String findSolarTerms(LocalDate day) {
        if (!SolarTerms.getTerm(day).isEmpty()) {
            return SolarTerms.getTerm(day);
        } else {
            String futureSolarTerms = futureSolarTerms(day);
            String formerlySolarTerms = formerlySolarTerms(day);

            return formerlySolarTerms + "后，" + futureSolarTerms + "前";
        }
    }
    //查找下一个节气
    private String futureSolarTerms(LocalDate day) {
        do {
            day = day.plusDays(1);
        } while (SolarTerms.getTerm(day).isEmpty());
        return SolarTerms.getTerm(day);
    }
    //查找上一个节气
    private String formerlySolarTerms(LocalDate day) {
        do {
            day = day.minusDays(1);
        } while (SolarTerms.getTerm(day).isEmpty());
        return SolarTerms.getTerm(day);
    }

    //判断是否是中国传统节日或者工作日或者休息日
    private String typeDay(ChineseDate cDay,  LocalDate day){
        String festival = cDay.getFestivals();
        if (!festival.isEmpty()){
            return festival;
        }else {
            DayOfWeek dayOfWeek = day.getDayOfWeek();
            if (dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY){
                return "休息日";
            }else {
                return "工作日";
            }
        }
    }
}
