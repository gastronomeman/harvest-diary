package com.HarvestDiary.Ui.Main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.Getter;

import java.util.Objects;

public class MainDiary extends Application {

    @Getter
    private static Stage mainDiaryUistage;
    @Override
    public void start(Stage stage) throws Exception {
        Parent root= FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("fxml/MainUI/MainDiaryUI.fxml")));
        stage.setTitle("写日记");
        Scene scene=new Scene(root);
        scene.getRoot().requestFocus();
        stage.setScene(scene);
        stage.setResizable(false);
        mainDiaryUistage =stage;
        stage.show();
    }

}
