package com.HarvestDiary.UiController;

import com.jfoenix.controls.JFXButton;
import de.felixroske.jfxsupport.FXMLController;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;

@FXMLController
public class ForgotPasswordUiController {
    @FXML
    private StackPane avatarBackground;

    @FXML
    private ImageView captcha;

    @FXML
    private TextField captchaText;

    @FXML
    private JFXButton changeCaptcha;

    @FXML
    private JFXButton exit;

    @FXML
    private TextField phone;

    @FXML
    private JFXButton register;

    @FXML
    private Label tip;

    @FXML
    private TextField username;

    @FXML
    void changeCaptcha(MouseEvent event) {

    }

    @FXML
    void changeUi(MouseEvent event) {

    }

    @FXML
    void exitUi(MouseEvent event) {

    }
}
