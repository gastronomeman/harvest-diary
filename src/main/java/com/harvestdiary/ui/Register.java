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

public class Register extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Getter
    private static Stage RegisterUiStage;

    @Override
    public void start(Stage stage) throws Exception {

        Parent root = FXMLLoader.load(
                Objects.requireNonNull(getClass().getClassLoader().getResource("fxml/registerUI.fxml"))
        );

        SettingFontIcon.setStageIcon(stage);
        stage.setTitle("注册");
        Scene scene = new Scene(root);
        scene.getRoot().requestFocus();
        stage.setScene(scene);
        stage.setResizable(false);
        RegisterUiStage = stage;
        stage.show();
    }

}
