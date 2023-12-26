package com.HarvestDiary.UiController;

import cn.hutool.json.JSONUtil;
import com.HarvestDiary.Ui.Login;
import com.HarvestDiary.Ui.Register;
import com.HarvestDiary.otherTools.Captcha;
import com.HarvestDiary.otherTools.OperationalDocument;
import com.HarvestDiary.otherTools.SettingFontIcon;
import com.HarvestDiary.pojo.User;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import de.felixroske.jfxsupport.FXMLController;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;

import org.kordamp.ikonli.antdesignicons.AntDesignIconsOutlined;


import java.io.IOException;
import java.util.regex.Pattern;

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
    @FXML
    private Label tip;

    @FXML
    private JFXCheckBox localhost;

    @FXML
    private TextField password;
    @FXML
    private TextField phone;
    @FXML
    private TextField userNumber;
    @FXML
    private TextField username;
    @FXML
    private TextField password1;

    @FXML
    private TextField captchaText;
    //设置验证码
    Captcha c = new Captcha();

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


        c.generateImages();//生成验证码图片
        captcha.setImage(c.getImage());//设置图片到页面
        log.info(c.getCode());

        Thread thread = new Thread(() -> {
            Platform.runLater(() -> {
                if (OperationalDocument.existFile("localhost.login")){
                    localhost.setSelected(true);
                }
            });
        });
        thread.start();

    }


    @FXML
    void changeCaptcha(MouseEvent mouseEvent) throws IOException {
        c.generateImages();
        this.captcha.setImage(c.getImage());
        log.info(c.getCode());
    }

    @FXML
    void changeUi(MouseEvent event) throws Exception {
        if (localhost.isSelected()){
            if (filterUser()){
                Thread thread = new Thread(() -> {
                    Platform.runLater(this::addUserJson);
                });
                thread.start();
                Register.getRegisterUiStage().close();
                log.info("关闭注册页面");

                new Login().start(new Stage());
                log.info("打开登录页面");
            }
        }



    }

    @FXML
    void exitUi(MouseEvent event) throws Exception {
        Register.getRegisterUiStage().close();
        log.info("关闭注册页面");

        new Login().start(new Stage());
        log.info("打开登录页面");
    }


    //判断注册页面的文本框的内容
    private boolean filterUser(){

        if (userNumber.getText().trim().isEmpty()){
            tip.setText("账号不能为空");
            return false;
        }
        if (username.getText().trim().isEmpty()){
            tip.setText("昵称不能为空");
            return false;
        }
        if (password.getText().trim().isEmpty()){
            tip.setText("密码不能为空");
            return false;
        }


        if (phone.getText().trim().isEmpty()){
            tip.setText("电话不能为空");
            return false;
        }
        String regex = "^(?:(?:\\+|00)86)?1(?:(?:3[0-9])|(?:4[5-79])|(?:5[0-35-9])|(?:6[5-7])|(?:7[0-8])|(?:8[0-9])|(?:9[189]))\\d{8}$";
        if (!Pattern.matches(regex, phone.getText())){
            tip.setText("电话不正确");
            return false;
        }
        System.out.println(Pattern.matches(regex, phone.getText()));

        if (!password.getText().equals(password1.getText())){
            tip.setText("两次输入密码不一致");
            return false;
        }
        if (!captchaText.getText().equalsIgnoreCase(c.getCode())){
            tip.setText("验证码不正确");
            return false;
        }
        tip.setText("");
        return true;
    }

    private void addUserJson(){
        User user = new User();
        user.setUserNumber(userNumber.getText());
        user.setUsername(username.getText());
        user.setPassword(password.getText());
        user.setPhone(phone.getText());
        // 使用Hutool将JavaBean转换为JSON字符串
        String jsonString = JSONUtil.toJsonStr(user);

        OperationalDocument.saveJSON(jsonString);
    }

}
