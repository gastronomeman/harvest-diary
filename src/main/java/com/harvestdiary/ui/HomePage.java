package com.harvestdiary.ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import lombok.Getter;

import java.util.Objects;

public class HomePage extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Getter//创建get方法
    private static Stage mainDiaryUiStage;

    @Override
    public void start(Stage stage) throws Exception {
        Parent sideList = FXMLLoader.load(
                Objects.requireNonNull(getClass().getClassLoader().getResource("fxml/homepage/SideList.fxml"))
        );

        Parent homeUI = FXMLLoader.load(
                Objects.requireNonNull(getClass().getClassLoader().getResource("fxml/homepage/HomeUI.fxml"))
        );


        HBox hbox = new HBox();
        hbox.setPrefWidth(800);
        hbox.setPrefHeight(500);
        hbox.getChildren().addAll(sideList, homeUI);
        stage.getIcons().add(new Image("/image/kls.png"));
        Scene scene = new Scene(hbox);
        scene.getRoot().requestFocus();
        stage.setScene(scene);
        stage.setResizable(false);
        mainDiaryUiStage = stage;
        stage.show();
    }


}
