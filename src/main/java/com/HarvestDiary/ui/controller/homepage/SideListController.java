package com.HarvestDiary.ui.controller.homepage;

import cn.hutool.json.JSONUtil;
import com.HarvestDiary.ui.Login;
import com.HarvestDiary.ui.HomePage;
import com.HarvestDiary.other.tools.OperationalDocument;
import com.HarvestDiary.other.tools.SettingFontIcon;
import com.HarvestDiary.pojo.UserStatus;
import com.jfoenix.controls.JFXButton;
import de.felixroske.jfxsupport.FXMLController;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import org.kordamp.ikonli.antdesignicons.AntDesignIconsOutlined;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Slf4j
@FXMLController
public class SideListController {
    @FXML
    private ImageView avatar;
    @FXML
    private StackPane avatarBackground;
    @FXML
    private JFXButton home;
    @FXML
    private JFXButton calendar;
    @FXML
    private JFXButton diary;
    @FXML
    private JFXButton setting;
    @FXML
    private JFXButton exit;

    ArrayList<JFXButton> buttons = new ArrayList<>();

    @FXML
    public void initialize() throws IOException {
        // 创建圆形裁剪区域
        Circle clip = new Circle();

        // 设置圆形裁剪区域的半径，使用图像宽度的一半
        clip.setRadius((double) 60 / 2);

        // 设置圆形裁剪区域的中心位置，使其居中
        clip.setCenterX((double) 80 / 2);
        clip.setCenterY((double) 90 / 2);

        // 将圆形裁剪区域应用于图像视图
        avatarBackground.setClip(clip);

        //添加按钮进集合
        buttons.addAll(Arrays.asList(home, calendar, diary, setting, exit));

        //设置按钮图标
        resettingColor();
        //设置home的颜色
        home.setGraphic(SettingFontIcon.setSizeAndColor(AntDesignIconsOutlined.HOME, 25, Color.web("#f7e8aa")));

    }

    @FXML
    void switchUi(ActionEvent event) throws Exception {
        //获取事件
        Thread thread = new Thread(() -> {
            Platform.runLater(() -> {
                try {
                    String buttonId = ((Button) event.getSource()).getId();
                    log.info(buttonId);

                    HBox root = (HBox) HomePage.getMainDiaryUiStage().getScene().getRoot();

                    List<Parent> mainUI = null;

                    mainUI = getFxmlLoader();
                    //判断事件按钮切换场景
                    switch (buttonId) {
                        case "exit" -> {
                            UserStatus u = JSONUtil.toBean(OperationalDocument.readFile("userStatus.json"), UserStatus.class);
                            u.setAutoLogin(false);
                            OperationalDocument.saveFile("userStatus.json", JSONUtil.toJsonStr(u));

                            HomePage.getMainDiaryUiStage().close();
                            new Login().start(new Stage());
                        }
                        case "home" -> {

                            root.getChildren().remove(1);
                            root.getChildren().add(mainUI.get(0));
                            choseButton(home);
                        }
                        case "calendar" -> {

                            root.getChildren().remove(1);
                            root.getChildren().add(mainUI.get(1));
                            choseButton(calendar);
                        }
                        case "diary" -> {
                            root.getChildren().remove(1);
                            root.getChildren().add(mainUI.get(2));
                            choseButton(diary);
                        }
                        case "setting" -> {
                            root.getChildren().remove(1);
                            root.getChildren().add(mainUI.get(3));
                            choseButton(setting);
                        }
                    }

                } catch (Exception e) {
                    throw new RuntimeException(e);
                }

            });
        });

        thread.start();
    }

    /*
        右侧的fxml文件
        homeUI      ： 0
        calendarUI  ： 1
        diaryUI     ： 2
        settingUI   ： 3
     */
    private List<Parent> getFxmlLoader() throws IOException {
        Parent homeUI = FXMLLoader.load(
                Objects.requireNonNull(HomePage.class.getClassLoader().getResource("fxml/homepage/HomeUI.fxml"))
        );
        Parent calendarUI = FXMLLoader.load(
                Objects.requireNonNull(HomePage.class.getClassLoader().getResource("fxml/homepage/CalendarUI.fxml"))
        );
        Parent diaryUI = FXMLLoader.load(
                Objects.requireNonNull(HomePage.class.getClassLoader().getResource("fxml/homepage/DiaryUi.fxml"))
        );

        Parent settingUI = FXMLLoader.load(
                Objects.requireNonNull(HomePage.class.getClassLoader().getResource("fxml/homepage/SettingUI.fxml"))
        );
        return new ArrayList<>(Arrays.asList(homeUI, calendarUI, diaryUI, settingUI));
    }
    //触发按钮选择
    private void choseButton(JFXButton chose) {
        //把图标背景色全设置为未选中的样式
        for (JFXButton jfxButton : buttons) {
            jfxButton.setStyle(
                    "-fx-background-radius: 15;" + "-fx-border-radius: 15;"
            );
        }
        resettingColor();
        //
        chose.setStyle(
                "-fx-background-radius: 15;" +
                "-fx-border-radius: 15;" +
                "-fx-border-color:  linear-gradient(to bottom right, #FAD9C2, #F3AC9E);" +
                "-fx-border-width: 2; " +
                "-fx-effect: dropshadow(three-pass-box,  #f7e8aa, 10, 0, 0, 0);"
        );

    }
    //初始化颜色
    private void resettingColor() {
        //统一颜色
        home.setGraphic(SettingFontIcon.setSizeAndColor(AntDesignIconsOutlined.HOME, 25, Color.WHITE));
        calendar.setGraphic(SettingFontIcon.setSizeAndColor(AntDesignIconsOutlined.CALENDAR, 25, Color.WHITE));
        diary.setGraphic(SettingFontIcon.setSizeAndColor(AntDesignIconsOutlined.BOOK, 25, Color.WHITE));
        setting.setGraphic(SettingFontIcon.setSizeAndColor(AntDesignIconsOutlined.SETTING, 25, Color.WHITE));
        exit.setGraphic(SettingFontIcon.setSizeAndColor(AntDesignIconsOutlined.IMPORT, 25, Color.WHITE));
    }
}
