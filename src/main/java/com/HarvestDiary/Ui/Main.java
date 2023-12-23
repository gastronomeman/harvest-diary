package com.HarvestDiary.Ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.Getter;

import java.util.Objects;

public class Main extends Application {
    @Getter
    private static Stage mainUiStage;

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(
                Objects.requireNonNull(getClass().getClassLoader().getResource("fxml/MainUI.fxml"))
        );

        stage.setTitle("日记");
        Scene scene = new Scene(root);
        scene.getRoot().requestFocus();
        stage.setScene(scene);
        mainUiStage = stage;
        stage.show();
    }
}
