package com.HarvestDiary.ui.controller.pages;

import cn.hutool.json.JSONUtil;
import com.HarvestDiary.other.tools.OperationalDocument;
import com.HarvestDiary.other.tools.SettingFontIcon;
import com.HarvestDiary.pojo.Diary;
import com.HarvestDiary.pojo.UserStatus;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import de.felixroske.jfxsupport.FXMLController;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.StringConverter;
import lombok.extern.slf4j.Slf4j;
import org.kordamp.ikonli.antdesignicons.AntDesignIconsOutlined;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Optional;


@Slf4j
@FXMLController
public class DiaryUIController {
    @FXML
    private JFXComboBox<String> backgroundColor;
    @FXML
    private JFXButton amplify;
    @FXML
    private DatePicker datePicker;
    @FXML
    private JFXButton backToToday;
    @FXML
    private JFXTextArea diaryText;
    @FXML
    private JFXButton clear;
    @FXML
    private JFXButton reduce;
    @FXML
    private JFXButton save;
    @FXML
    private TextField title;
    @FXML
    private Label ziShu;
    private int fontSize = 15;
    UserStatus userStatus = JSONUtil.toBean(OperationalDocument.readFile("userStatus.json"), UserStatus.class);
    private final Diary diary = new Diary();

    @FXML
    public void initialize() {

        diary.setUserId(userStatus.getUserId());

        //设置日记类的值
        datePicker.setValue(LocalDate.now());
        //给日记类设置格式为yyyy-MM-dd
        datePicker.setConverter(dateFormatter());

        backToToday.setGraphic(SettingFontIcon.setSizeAndColor(AntDesignIconsOutlined.RELOAD, 14, Color.web("#617172")));

        //获取选项卡里面的键值
        HashMap<String, String> hm = setComboBoxItems();

        //设置标题头为日期
        title.setText(String.valueOf(datePicker.getValue()));
        //设置图标字体
        setIcon();
        //给diary设置值
        diary.setColor(backgroundColor.getPromptText());

        readDiary(hm);
        // 更新字符数量


        //设置键值的方法
        backgroundColor.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            //获取颜色键对应的键
            String color = hm.get(newValue);
            //给diary设置值
            diary.setColor(newValue);
            //设置背景色
            diaryText.setStyle("-fx-background-color: " + color);
        });
        // 添加值变化监听器
        datePicker.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null){
                showAlert("日期不能为空");
                datePicker.setValue(LocalDate.now());
                return;
            }
            //设置标题头为日期
            title.setText(String.valueOf(newValue));
            readDiary(hm);
        });
        //数字数
        characterCount(diaryText.getText());
        diaryText.textProperty().addListener((observable, oldValue, newValue) -> {
            characterCount(diaryText.getText());
        });

    }

    @FXML
    void getToMax(MouseEvent event) {
        if (fontSize >= 50) return;
        fontSize += 5;
        diary.setFontSize(fontSize + "");
        diaryText.setFont(Font.font(fontSize));
    }
    @FXML
    void getToMin(MouseEvent event) {
        if (fontSize <= 10) return;
        fontSize -= 5;
        diary.setFontSize(fontSize + "");
        diaryText.setFont(Font.font(fontSize));
    }

    @FXML//清除文本按钮
    void clearTextArea(MouseEvent event) {
        if (showAlert("是否确定清除文本内容！！！")){
            title.setText("");
            diaryText.setText("");
        }
    }
    @FXML//保存日记
    void saveDiary(MouseEvent event) {
        diary.setTime(datePicker.getValue());//设置时间
        diary.setTitle(title.getText());//设置文章标题
        diary.setContent(diaryText.getText());//设置文章内容
        //存文章进本地
        if (userStatus.getLocalLogin() && showAlert("是否确认保存")){
            OperationalDocument.writeDiary(diary.getUserId() + diary.getTime() + "Local", JSONUtil.toJsonStr(diary));
        }else if (showAlert("是否确认保存")){
            OperationalDocument.writeDiary(diary.getUserId() + diary.getTime(), JSONUtil.toJsonStr(diary));
        }

    }
    @FXML
    void backToToday(MouseEvent event) {
        datePicker.setValue(LocalDate.now());
    }
    private HashMap<String, String> setComboBoxItems() {
        HashMap<String, String> hm = new HashMap<>();
        backgroundColor.getItems().add(" 芡食白");//#e2e1e4
        hm.put(" 芡食白", "#e2e1e4");
        backgroundColor.getItems().add(" 凤信紫");//#c8adc4
        hm.put(" 凤信紫", "#c8adc4");
        backgroundColor.getItems().add(" 水红");  //#f1c4cd
        hm.put(" 水红", "#f1c4cd");
        backgroundColor.getItems().add(" 鲸鱼灰");//#f1c4cd
        hm.put(" 鲸鱼灰", "#f1c4cd");
        backgroundColor.getItems().add(" 星蓝");  //#93b5cf
        hm.put(" 星蓝", "#93b5cf");
        backgroundColor.getItems().add(" 湖水蓝");//#b0d5df
        hm.put(" 湖水蓝", "#b0d5df");
        backgroundColor.getItems().add(" 青矾绿");//#2c9678
        hm.put(" 青矾绿", "#2c9678");
        backgroundColor.getItems().add(" 毛绿");  //#66c18c
        hm.put(" 毛绿", "#66c18c");
        backgroundColor.getItems().add(" 杏仁黄");//#f7e8aa
        hm.put(" 杏仁黄", "#f7e8aa");
        backgroundColor.getItems().add(" 篾黄");  //#f7de98
        hm.put(" 篾黄", "#f7de98");
        backgroundColor.getItems().add(" 瓜瓤粉");//#f9cb8b
        hm.put(" 瓜瓤粉", "#f9cb8b");
        backgroundColor.getItems().add(" 淡绯");  //#f2cac9
        hm.put(" 淡绯", "#f2cac9");
        backgroundColor.getItems().add(" 银鼠灰");//#b5aa90
        hm.put(" 银鼠灰", "#b5aa90");
        backgroundColor.getItems().add(" 藕荷");  //#edc3ae
        hm.put(" 藕荷", "#edc3ae");
        backgroundColor.getItems().add(" 远山紫"); //#ccccd6
        hm.put(" 远山紫", "#ccccd6");
        return hm;
    }
    //设置按钮的图标
    private void setIcon() {
        amplify.setGraphic(SettingFontIcon.setSizeAndColor(AntDesignIconsOutlined.ZOOM_IN, 20, Color.web("#617172")));
        reduce.setGraphic(SettingFontIcon.setSizeAndColor(AntDesignIconsOutlined.ZOOM_OUT, 20, Color.web("#617172")));
        clear.setGraphic(SettingFontIcon.setSizeAndColor(AntDesignIconsOutlined.CLEAR, 25, Color.web("#617172")));
        save.setGraphic(SettingFontIcon.setSizeAndColor(AntDesignIconsOutlined.SAVE, 25, Color.web("#617172")));
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
    Boolean tip = false;//方法只提示一次
    //数 字数
    private void characterCount(String s){
        // 更新字符数量
        int characterCount = s.length();
        if (characterCount > 520 && tip){
            showAlert("\t字数超过520，若选择了同步上传云端500后的字数将不会上传\n进行同步");
            tip = true;
        }
        ziShu.setText(characterCount + "/520字");
    }
    private void readDiary(HashMap<String, String> hm){
        String diaryPath;

        if (userStatus.getLocalLogin()){
            diaryPath = OperationalDocument.readDiary(userStatus.getUserId() + datePicker.getValue() + "Local");
        }else {
            diaryPath = OperationalDocument.readDiary(userStatus.getUserId() + datePicker.getValue());
        }
        if (!(diaryPath == null)){
            Diary d = JSONUtil.toBean(diaryPath, Diary.class);
            datePicker.setValue(d.getTime());

            String s = d.getColor();

            //更改选项选中的选项
            backgroundColor.getSelectionModel().select(s);

            //设置背景色
            diaryText.setStyle("-fx-background-color: " + hm.get(s));

            title.setText(d.getTitle());

            diaryText.setText(d.getContent());
        }else {
            diaryText.setText("");
        }
    }
    private boolean showAlert(String s) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("提示");
        alert.setHeaderText("请查看警告：注意！注意！注意！");
        alert.setContentText(s);

        // 显示提示框并等待用户响应
        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;
    }
}
