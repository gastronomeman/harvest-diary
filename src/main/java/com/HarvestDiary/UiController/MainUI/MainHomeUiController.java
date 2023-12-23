package com.HarvestDiary.UiController.MainUI;

import com.HarvestDiary.Ui.Login;
import com.HarvestDiary.Ui.Main.MainHome;
import com.HarvestDiary.Ui.Register;
import com.HarvestDiary.otherTools.SettingFontIcon;
import com.jfoenix.controls.JFXButton;
import de.felixroske.jfxsupport.FXMLController;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import org.kordamp.ikonli.antdesignicons.AntDesignIconsOutlined;

import java.io.IOException;

@FXMLController
@Slf4j
public class MainHomeUiController {
    @FXML
    private StackPane avatarBackground;

    @FXML
    private ImageView avatar;

    @FXML
    private JFXButton home;

    @FXML
    private JFXButton today;

    @FXML
    private JFXButton diary;

    @FXML
    private JFXButton setting;

    @FXML
    private JFXButton exit;


    /**
     * 初始化方法，用于设置 JavaFX 控制器的初始状态。
     */
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


        //设置按钮图标
        home.setGraphic(SettingFontIcon.setSizeAndColor(AntDesignIconsOutlined.HOME, 25, Color.web("#f7e8aa")));
        today.setGraphic(SettingFontIcon.setSizeAndColor(AntDesignIconsOutlined.CALENDAR, 25, Color.web("#f9e9cd")));
        diary.setGraphic(SettingFontIcon.setSizeAndColor(AntDesignIconsOutlined.BOOK, 25, Color.web("#f9e9cd")));
        setting.setGraphic(SettingFontIcon.setSizeAndColor(AntDesignIconsOutlined.SETTING, 25, Color.web("#f9e9cd")));
        exit.setGraphic(SettingFontIcon.setSizeAndColor(AntDesignIconsOutlined.IMPORT, 25, Color.web("#f9e9cd")));
    }

    @FXML
    void changeUi(MouseEvent event) throws Exception {
        MainHome.getMainHomeUiStage().close();
        log.info("关闭主页");

        new Login().start(new Stage());
        log.info("回到登录页面");
    }


}
