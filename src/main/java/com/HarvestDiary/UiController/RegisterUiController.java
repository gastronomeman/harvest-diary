package com.HarvestDiary.UiController;

import com.HarvestDiary.Ui.Login;
import com.HarvestDiary.Ui.Register;
import com.HarvestDiary.otherTools.CaptchaGenerator;
import com.HarvestDiary.otherTools.SettingFontIcon;
import com.jfoenix.controls.JFXButton;
import de.felixroske.jfxsupport.FXMLController;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;

import org.kordamp.ikonli.antdesignicons.AntDesignIconsOutlined;
import org.kordamp.ikonli.bootstrapicons.BootstrapIcons;
import org.kordamp.ikonli.javafx.FontIcon;


import java.io.IOException;

@FXMLController
@Slf4j
public class RegisterUiController {
    @FXML
    private StackPane avatarBackground;

    @FXML
    private JFXButton exit;

    @FXML
    private ImageView captcha;

    @FXML
    private JFXButton changeCaptcha;





    /**
     * 初始化方法，用于设置 JavaFX 控制器的初始状态。
     */
    @FXML
    public void initialize() throws IOException {
        // 创建圆形裁剪区域
        Circle clip = new Circle();

        // 设置圆形裁剪区域的半径，使用图像宽度的一半
        clip.setRadius((double) 70 / 2);

        // 设置圆形裁剪区域的中心位置，使其居中
        clip.setCenterX((double) 70 / 2);
        clip.setCenterY((double) 70 / 2);

        // 将圆形裁剪区域应用于图像视图
        avatarBackground.setClip(clip);

        //设置按钮图标
        changeCaptcha.setGraphic(SettingFontIcon.setColor(AntDesignIconsOutlined.SYNC, Color.web("#617172")));

        //退出设置图标
        exit.setGraphic(SettingFontIcon.setColor(AntDesignIconsOutlined.EXPORT, Color.web("#617172")));

        //设置验证码
        captcha.setImage(CaptchaGenerator.generateImages());


    }


    @FXML
    void changeCaptcha(MouseEvent mouseEvent) throws IOException {
        captcha.setImage(CaptchaGenerator.generateImages());
    }

    @FXML
    void changeUi(MouseEvent event) throws Exception {
        Register.getRegisterUiStage().close();
        log.info("关闭注册页面");

        new Login().start(new Stage());
        log.info("打开登录页面");
    }

    @FXML
    void exitUi(MouseEvent event) throws Exception {
        Register.getRegisterUiStage().close();
        log.info("关闭注册页面");

        new Login().start(new Stage());
        log.info("打开登录页面");
    }
}
