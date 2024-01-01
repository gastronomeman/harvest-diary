package com.HarvestDiary.ui.controller.homepage;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;

import java.io.IOException;

public class SettingUIController {

//    整个Tabpane的id
    @FXML
    TabPane settingPane;

//    搜索部分的文本输入框的id
    @FXML
    TextField search_input;

//    搜索部分的确认搜索按钮
    @FXML
    Button search;
    //    修改图片部分确定跳转按钮事件
    @FXML
    private void removeImageConfig() throws IOException {
//        Tab newTab=new Tab("修改图片");
//       FXMLLoader fxmlLoader=new FXMLLoader(getClass().getResource("/fxml/TabUi/modifytab.fxml"));
//        Parent newContent=fxmlLoader.load();
//        newTab.setContent(newContent);
//        settingPane.getTabs().add(newTab);

        createTab("修改图片","/fxml/TabUi/modifytab.fxml");

    }

//    赞助我们部分按钮跳转事件
    @FXML
    private void sponsorConfig() throws IOException {
       createTab("赞助我们","/fxml/TabUi/sponsortab.fxml");

    }

    @FXML
    private void clearDirConfig() throws IOException{
        createTab("清空日记","/fxml/TabUi/clear_dir.fxml");
    }

    @FXML
    private void clearAcConfig() throws IOException{
        createTab("注销账户","/fxml/TabUi/clear_ac.fxml");

    }

private void createTab(String name,String path) throws IOException {
        Tab newTab=new Tab(name);
        FXMLLoader fxmlLoader=new FXMLLoader(getClass().getResource(path));
        Parent newContent=fxmlLoader.load();
        newTab.setContent(newContent);
        settingPane.getTabs().add(newTab);
}
}