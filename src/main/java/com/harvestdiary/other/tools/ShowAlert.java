package com.harvestdiary.other.tools;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public class ShowAlert {

    /**
     * 确定弹窗
     * @param s
     * @return
     */
    public static Boolean confirmationAlert(String s){
        //Alert.AlertType.CONFIRMATION
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("提示");
        alert.setHeaderText("请查看警告：注意！注意！注意！");
        alert.setContentText(s);
        // 显示提示框并等待用户响应
        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;
    }

    public static void informationAlert(String headerText, String contentText){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("提示");
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);

        // 显示提示框并等待用户响应
        alert.showAndWait();
    }
}
