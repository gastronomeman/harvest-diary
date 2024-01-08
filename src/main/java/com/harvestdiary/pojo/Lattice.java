package com.harvestdiary.pojo;

import com.jfoenix.controls.JFXButton;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
public class Lattice {
    @Setter
    private StackPane stackPane;
    private final Label date;
    private final Label chineseDate;
    @Setter
    private JFXButton jfxButton;

    public Lattice(){
        stackPane = new StackPane();

        VBox vBox = new VBox();
        vBox.setAlignment(javafx.geometry.Pos.CENTER);
        vBox.setMaxHeight(50.0);
        vBox.setMaxWidth(50.0);
        vBox.setPrefHeight(50.0);
        vBox.setPrefWidth(50.0);

        date = new Label("Label");
        date.setFont(Font.font(14));
        VBox.setMargin(date, new Insets(0, 0, 5.0, 0)); // 设置下边距

        chineseDate = new Label("Label");
        chineseDate.setFont(Font.font(14));

        jfxButton = new JFXButton(" ");
        jfxButton.setPrefHeight(80.0);
        jfxButton.setPrefWidth(80.0);
        jfxButton.setStyle
                ("-fx-background-radius: 20;" +
                        "-fx-border-radius: 20");


        vBox.getChildren().addAll(date, chineseDate);
        stackPane.getChildren().addAll(vBox, jfxButton);
    }

    public void setDate(String date) {
        this.date.setText(date);
    }

    public void setChineseDate(String chineseDate) {
        this.chineseDate.setText(chineseDate);
    }

}
