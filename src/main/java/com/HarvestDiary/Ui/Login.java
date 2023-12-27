package com.HarvestDiary.Ui;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import lombok.Getter;


import java.util.Objects;

public class Login extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Getter//创建get方法
    private static Stage loginUiStage;

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(
                Objects.requireNonNull(getClass().getClassLoader().getResource("fxml/LoginUI.fxml"))
        );

        stage.setTitle("登录");


        stage.getIcons().add(new Image("/image/kls.png"));

        Scene scene = new Scene(root);
        scene.getRoot().requestFocus();
        stage.setScene(scene);
        stage.setResizable(false);//锁定窗口，禁止缩放
        loginUiStage = stage;
        stage.show();
    }
}
