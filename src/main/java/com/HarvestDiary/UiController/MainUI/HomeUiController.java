package com.HarvestDiary.UiController.MainUI;

import cn.hutool.core.date.ChineseDate;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.chinese.SolarTerms;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;

import com.HarvestDiary.otherTools.SettingFontIcon;
import com.HarvestDiary.otherTools.AddressInfoUtil;

import com.jfoenix.controls.JFXButton;
import de.felixroske.jfxsupport.FXMLController;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.StringConverter;
import lombok.extern.slf4j.Slf4j;
import org.kordamp.ikonli.antdesignicons.AntDesignIconsOutlined;
import org.kordamp.ikonli.fluentui.FluentUiRegularMZ;


import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;


@Slf4j
@FXMLController
public class HomeUiController {

    @FXML
    private VBox HomeUI;
    @FXML
    private Label weather;
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
    private ImageView pic;
    @FXML
    private JFXButton copy;
    @FXML
    private JFXButton backToToday;
    @FXML
    private Label address;
    @FXML
    private Label wind;


    @FXML
    public void initialize() throws IOException {
        backToToday.setGraphic(SettingFontIcon.setSizeAndColor(AntDesignIconsOutlined.RELOAD, 14, Color.web("#617172")));

        // 创建圆角边框
        // 创建 Rectangle 作为剪裁区域
        Rectangle clip = new Rectangle(480, 270); // 替换为你希望的剪裁区域大小
        clip.setArcWidth(20); // 圆角宽度
        clip.setArcHeight(20); // 圆角高度

        //顶部日历设置
        setDataTime();

        imageBackground.setClip(clip);

        pic.setImage(new Image(randomPic(pic.getImage())));

        change.setGraphic(SettingFontIcon.setSizeAndColor(AntDesignIconsOutlined.SYNC, 22, Color.web("#617172")));
        copy.setGraphic(SettingFontIcon.setSizeAndColor(AntDesignIconsOutlined.COPY, 22, Color.web("#617172")));

        //设置地址天气
        setWeather();

    }

    @FXML
    void backToToday(MouseEvent event) {
        setDataTime();
    }

    @FXML
    void changePic(MouseEvent event) {
        Thread thread = new Thread(() -> {
            Platform.runLater(() -> {
                pic.setImage(new Image(randomPic(pic.getImage())));
            });
        });
        thread.start();
    }

    @FXML
    void copyInfo(MouseEvent event) {
        String s = getString();


        Thread thread = new Thread(() -> {
            Platform.runLater(() -> {
                // 获取系统剪贴板
                Clipboard clipboard = Clipboard.getSystemClipboard();

                // 创建 ClipboardContent 对象，用于保存要复制的字符串
                ClipboardContent content = new ClipboardContent();
                content.putString(s);

                // 将 ClipboardContent 对象设置到剪贴板
                clipboard.setContent(content);
            });
        });
        thread.start();
    }

    private String getString() {
        String s = " ";
        if (Objects.equals(datePicker.getValue(), LocalDate.now())) {
            if (!address.getText().equals("地址：...")) {
                s = DateUtil.now() + "\n" +
                        chineseYear.getText() + " " + chineseDate.getText() + " \n" +
                        solarTerms24.getText() + " " + festival.getText() + " \n" +
                        address.getText() + " ，" + weather.getText() + " ，" +
                        wind.getText();
            } else {
                s = DateUtil.now() + "\n" +
                        chineseYear.getText() + " " + chineseDate.getText() + " \n" +
                        solarTerms24.getText() + " " + festival.getText() + " \n";
            }
        } else {
            s = datePicker.getValue() + "\n" +
                    chineseYear.getText() + " " + chineseDate.getText() + " \n" +
                    solarTerms24.getText() + " " + festival.getText();
        }

        return s;
    }

    //随机更换图片
    private String randomPic(Image image) {
        return "image/MainUi/home/1000" + RandomUtil.randomInt(1, 9) + ".jpg";
    }

    //设置上方的日期
    private void setDataTime() {
        //设置日期为现在时间
        LocalDate nowDate = LocalDate.now();
        //让DatePicker显示格式为yyyy-MM-dd
        datePicker.setConverter(dateFormatter());
        //把值设置进日历
        datePicker.setValue(nowDate);

        SwitchDate(nowDate);

        //改变日期标题也变
        datePicker.valueProperty().addListener(((observable, oldValue, newValue) -> {
            SwitchDate(newValue);
        }));

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
    private String typeDay(ChineseDate cDay, LocalDate day) {
        String festival = cDay.getFestivals();
        if (!festival.isEmpty()) {
            return festival;
        } else {
            DayOfWeek dayOfWeek = day.getDayOfWeek();
            if (dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY) {
                return "休息日";
            } else {
                return "工作日";
            }
        }
    }

    //切换日期
    private void SwitchDate(LocalDate date) {
        //设置农历日月
        ChineseDate chineseCalendar = new ChineseDate(date);
        //大字
        chineseDate.setText(chineseCalendar.getChineseMonthName() + chineseCalendar.getChineseDay());
        //天干地支+生肖
        chineseYear.setText(chineseCalendar.getCyclical() + chineseCalendar.getChineseZodiac() + "年");
        //24节气判断
        solarTerms24.setText(findSolarTerms(date));
        //获取中国传统节日或者工作日或者休息日
        festival.setText(typeDay(chineseCalendar, date));
    }

    //设置天气图标和获取天气消息
    private void setWeather() {
        address.setGraphic(SettingFontIcon.setSizeAndColor(FluentUiRegularMZ.MY_LOCATION_24, 20, Color.web("#617172")));
        weather.setGraphic(SettingFontIcon.setSizeAndColor(FluentUiRegularMZ.WEATHER_HAIL_DAY_20, 20, Color.web("#617172")));
        wind.setGraphic(SettingFontIcon.setSizeAndColor(FluentUiRegularMZ.WEATHER_BLOWING_SNOW_20, 20, Color.web("#617172")));
        Thread thread = new Thread(() -> {
            Platform.runLater(() -> {
                String s = "101";
                try {
                    s = AddressInfoUtil.getAddressWeather();
                }catch (Exception e){
                    log.info("网络连接异常", e);
                }

                System.out.println(s);
                if (!s.equals("101")){
                    JSONObject json = JSONUtil.parseObj(s);
                    address.setText("地址：" + json.getStr("address").replaceAll(".*\\s", "").trim());
                    weather.setText("天气：" + json.getStr("weather"));
                    wind.setText("风力：" + json.getStr("windDirection") + "风 " +
                            json.getStr("windPower"));
                }

            });
        });
        thread.start();

    }
}
