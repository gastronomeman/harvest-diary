package com.HarvestDiary.Ui.Main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.Getter;

import java.util.Objects;

public class MainSetting extends Application {

    @Getter
    private static Stage mainSettingUistage;
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(
                Objects.requireNonNull(getClass().getClassLoader().getResource("fxml/MainUI/MainSettingUI.fxml"))
        );
        stage.setTitle("日记");
        Scene scene = new Scene(root);
        scene.getRoot().requestFocus();
        stage.setScene(scene);
        mainSettingUistage = stage;
        stage.setResizable(false);
        stage.show();
    }
}
