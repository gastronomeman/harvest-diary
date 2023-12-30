package com.HarvestDiary.UiController.MainUI;

import com.HarvestDiary.otherTools.SettingFontIcon;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import de.felixroske.jfxsupport.FXMLController;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.util.StringConverter;
import lombok.extern.slf4j.Slf4j;
import org.kordamp.ikonli.antdesignicons.AntDesignIconsOutlined;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;


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
    public void initialize() {
        datePicker.setValue(LocalDate.now());
        datePicker.setConverter(dateFormatter());

        HashMap<String, String> hm = setComboBoxItems();

        title.setText(String.valueOf(datePicker.getValue()));
        setIcon();
        backgroundColor.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            String color = hm.get(newValue);
            diaryText.setStyle("-fx-background-color: " + color);
        });


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
}
