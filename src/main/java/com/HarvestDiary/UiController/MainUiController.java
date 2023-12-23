package com.HarvestDiary.UiController;

import com.HarvestDiary.otherTools.SettingFontIcon;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTreeView;
import de.felixroske.jfxsupport.FXMLController;
import javafx.fxml.FXML;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import org.kordamp.ikonli.Ikon;
import org.kordamp.ikonli.antdesignicons.AntDesignIconsOutlined;
import org.kordamp.ikonli.bootstrapicons.BootstrapIcons;
import org.kordamp.ikonli.javafx.FontIcon;

import java.io.IOException;

@FXMLController
public class MainUiController {
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
        home.setGraphic(SettingFontIcon.setSizeAndColor(AntDesignIconsOutlined.HOME, 30, Color.web("#617172")));
        today.setGraphic(SettingFontIcon.setSizeAndColor(AntDesignIconsOutlined.CALENDAR, 30, Color.web("#617172")));
        diary.setGraphic(SettingFontIcon.setSizeAndColor(AntDesignIconsOutlined.BOOK, 30, Color.web("#617172")));
        setting.setGraphic(SettingFontIcon.setSizeAndColor(AntDesignIconsOutlined.SETTING, 30, Color.web("#617172")));
        exit.setGraphic(SettingFontIcon.setSizeAndColor(AntDesignIconsOutlined.EXPORT, 30, Color.web("#617172")));
    }


}
