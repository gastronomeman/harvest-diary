package com.HarvestDiary.UiController;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONUtil;
import com.HarvestDiary.Ui.ForgotPassword;
import com.HarvestDiary.Ui.Login;
import com.HarvestDiary.otherTools.Captcha;
import com.HarvestDiary.otherTools.OperationalDocument;
import com.HarvestDiary.otherTools.SettingFontIcon;
import com.HarvestDiary.pojo.Poetry;
import com.HarvestDiary.pojo.User;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXToggleButton;
import de.felixroske.jfxsupport.FXMLController;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import org.kordamp.ikonli.antdesignicons.AntDesignIconsOutlined;

import java.io.IOException;

@Slf4j
@FXMLController
public class ForgotPasswordUiController {
    @FXML
    private StackPane avatarBackground;

    @FXML
    private ImageView captcha;

    @FXML
    private TextField captchaText;

    @FXML
    private JFXButton changeCaptcha;

    @FXML
    private JFXButton exit;

    @FXML
    private TextField phone;

    @FXML
    private JFXButton register;

    @FXML
    private Label tip;

    @FXML
    private TextField username;

    @FXML
    private JFXToggleButton localhost;

    //设置验证码
    Captcha c = new Captcha();

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

    }

    @FXML
    void changeCaptcha(MouseEvent event) throws IOException {
        c.generateImages();
        this.captcha.setImage(c.getImage());
        log.info(c.getCode());
    }

    @FXML
    void changeUi(MouseEvent event) throws Exception {
        if (localhost.isSelected() && checkUser()){
            ForgotPassword.getForgotPasswordUiStage().close();

            new Login().start(new Stage());
        }
        Thread thread = new Thread(() -> {
            Platform.runLater(() -> {
                if (!localhost.isSelected() && checksUser()){
                    ForgotPassword.getForgotPasswordUiStage().close();

                    try {
                        new Login().start(new Stage());
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            });
        });
        thread.start();
    }

    @FXML
    void exitUi(MouseEvent event) throws Exception {
        ForgotPassword.getForgotPasswordUiStage().close();

        new Login().start(new Stage());
    }

    private boolean checkUser() {
        if (username.getText().isEmpty() ||
                phone.getText().isEmpty() ||
                captchaText.getText().isEmpty()) {
            return false;
        }
        if (!OperationalDocument.existFile("user.json")) {
            return false;
        }
        User user = JSONUtil.toBean(OperationalDocument.readFile("user.json"), User.class);
        System.out.println(user);

        if (!user.getUsername().equals(username.getText())){
            tip.setText("昵称错误！");
            return false;
        }else if (!user.getPhone().equals(phone.getText())){
            tip.setText("电话错误！");
            return false;
        }else if (!c.getCode().equals(captchaText.getText())){
            tip.setText("验证码错误！");
            return false;
        }else {
            showAlert("你的账号是：" + user.getUserId() + "\n" +
                        "密码是：" + user.getPassword() );
            return true;
        }

    }
    private boolean checksUser(){
        if (username.getText().isEmpty() ||
                phone.getText().isEmpty() ||
                captchaText.getText().isEmpty()) {
            return false;
        }
        User user = new User();
        user.setUsername(username.getText());
        user.setPhone(phone.getText());
        HttpResponse response = HttpRequest.post(Poetry.API + "/user/findPassword")
                .header("Content-Type", "application/json")
                .body(JSONUtil.toJsonStr(user))
                .execute();

        String json = response.body();

        if (JSONUtil.parseObj(json).getStr("code").equals("0")){
            showAlert("昵称或电话填写错误，请重新尝试");
            return false;
        }
        String data = JSONUtil.parseObj(json).getStr("data");
        System.out.println(data);
        showAlert("你的账号是：" + JSONUtil.parseObj(data).getStr("userId")
                + "\n" +
                "密码是：" + JSONUtil.parseObj(data).getStr("password") );

        return JSONUtil.parseObj(json).getStr("code").equals("1");
    }
    private void showAlert(String s) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("提示");
        alert.setHeaderText("验证成功");
        alert.setContentText(s);

        // 显示提示框并等待用户响应
        alert.showAndWait();
    }
}
