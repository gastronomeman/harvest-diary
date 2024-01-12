package com.harvestdiary.ui;

import com.harvestdiary.other.tools.SettingFontIcon;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import lombok.Getter;

import java.util.Objects;

public class ForgotPassword extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Getter//创建get方法
    private static Stage forgotPasswordUiStage;

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(
                Objects.requireNonNull(getClass().getClassLoader().getResource("fxml/ForgotPasswordUI.fxml"))
        );

        stage.setTitle("忘记密码");
        SettingFontIcon.setStageIcon(stage);
        Scene scene = new Scene(root);
        scene.getRoot().requestFocus();
        stage.setScene(scene);
        stage.setResizable(false);//锁定窗口，禁止缩放
        forgotPasswordUiStage = stage;
        stage.show();
    }
}
