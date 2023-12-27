package com.HarvestDiary.UiController.MainUI;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javax.swing.text.html.ImageView;

public class DiaryUiController extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

    }

    @FXML
    VBox vContainer;

    @FXML
    VBox titleBar;

    @FXML
    Button PreviousMouth;

    @FXML
    Button NextMouth;
}
