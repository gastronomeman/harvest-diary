package com.harvestdiary.ui.controller.homepage;


import com.harvestdiary.other.tools.SettingFontIcon;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXToggleButton;
import de.felixroske.jfxsupport.FXMLController;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import lombok.extern.slf4j.Slf4j;
import org.kordamp.ikonli.antdesignicons.AntDesignIconsOutlined;

import java.io.IOException;

@FXMLController
@Slf4j
public class SettingUIController {
    @FXML
    private CheckBox clean1;
    @FXML
    private JFXCheckBox clean2;
    @FXML
    private JFXToggleButton clean3;
    @FXML
    private JFXButton cleanAll;
    @FXML
    private JFXButton cloud;
    @FXML
    private DatePicker datePicker;
    @FXML
    private JFXButton delDiary;
    @FXML
    private JFXButton delUser;
    @FXML
    private TextField password;
    @FXML
    private TextField phone;
    @FXML
    private Label gitee;
    @FXML
    private Label github;
    @FXML
    private TextField userNumber;
    @FXML
    private TextField username;

    @FXML
    public void initialize() {
        setButtonStyle();

    }
    @FXML
    void giteeRed(MouseEvent event) {
        gitee.setTextFill(Color.RED);

    }

    @FXML
    void githubRed(MouseEvent event) {
        github.setTextFill(Color.RED);
    }
    @FXML
    void toBackColor(MouseEvent event) {
        github.setTextFill(Color.web("#617172"));
        gitee.setTextFill(Color.web("#617172"));
    }

    private void setButtonStyle(){
        delDiary.setGraphic(SettingFontIcon.setSizeAndColor(AntDesignIconsOutlined.DELETE, 15, Color.web("#617172")));
        cloud.setGraphic(SettingFontIcon.setSizeAndColor(AntDesignIconsOutlined.CLOUD_SYNC, 22, Color.web("#617172")));
        delUser.setGraphic(SettingFontIcon.setSizeAndColor(AntDesignIconsOutlined.LOGOUT, 20, Color.web("#617172")));
        cleanAll.setGraphic(SettingFontIcon.setSizeAndColor(AntDesignIconsOutlined.CLEAR, 20, Color.web("#617172")));
        github.setGraphic(SettingFontIcon.setSizeAndColor(AntDesignIconsOutlined.GITHUB, 20, Color.web("#617172")));
    }
}