package com.HarvestDiary.UiController;

import cn.hutool.json.JSONUtil;
import com.HarvestDiary.Ui.ForgotPassword;
import com.HarvestDiary.Ui.Login;
import com.HarvestDiary.Ui.MainDiary;
import com.HarvestDiary.Ui.Register;
import com.HarvestDiary.otherTools.OperationalDocument;
import com.HarvestDiary.pojo.User;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXToggleButton;
import de.felixroske.jfxsupport.FXMLController;
import com.jfoenix.controls.JFXCheckBox;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;

@FXMLController
@Slf4j
public class LoginUiController {

    @FXML
    private ImageView avatar;

    @FXML
    private PasswordField password;

    @FXML
    private JFXButton registerUI;
    @FXML
    private JFXToggleButton localhost;
    @FXML
    private TextField userNumber;

    @FXML
    private JFXCheckBox autoLogin;
    @FXML
    private JFXCheckBox rememberPW;
    @FXML
    private JFXButton forgotPw;

    /**
     * 初始化方法，用于设置 JavaFX 控制器的初始状态。
     */
    @FXML
    public void initialize() throws Exception {

        // 创建圆形裁剪区域
        Circle clip = new Circle();

        // 设置圆形裁剪区域的半径，使用图像宽度的一半
        clip.setRadius((double) 70 / 2);

        // 设置圆形裁剪区域的中心位置，使其居中
        clip.setCenterX((double) 70 / 2);
        clip.setCenterY((double) 70 / 2);

        // 将圆形裁剪区域应用于图像视图
        avatar.setClip(clip);




        //保存密码，输入进文本框
        if (OperationalDocument.readFile("app.config").contains("rememberPW:true;")) {
            rememberPW.setSelected(true);
            User user = JSONUtil.toBean(OperationalDocument.readFile("user.json"), User.class);
            userNumber.setText(user.getUserNumber());
            password.setText(user.getPassword());
        }
        //取消保存密码，去除属性
        rememberPW.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (!rememberPW.isSelected()){
                OperationalDocument.replace("rememberPW:true;", "rememberPW:false;", "app.config");
            }
        });


        if (OperationalDocument.readFile("app.config").contains("autoLogin:true;")) {
            autoLogin.setSelected(true);

            Thread thread = new Thread(() -> {
                Platform.runLater(() -> {
                    try {
                        new MainDiary().start(new Stage());
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                    Login.getLoginUiStage().close();
                });
            });

          thread.start();
        }
        autoLogin.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (!autoLogin.isSelected()){
                OperationalDocument.replace("autoLogin:true;", "autoLogin:false;", "app.config");
            }
        });

        if (OperationalDocument.readFile("app.config").contains("localhostLogin:true;")){
            localhost.setSelected(true);
        }
        localhost.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (localhost.isSelected()){
                OperationalDocument.replace("localhostLogin:false;", "localhostLogin:true;", "app.config");
            }else {
                OperationalDocument.replace("localhostLogin:true;", "localhostLogin:false;", "app.config");
            }
        });

    }

    @FXML
    void changeUi(MouseEvent event) throws Exception {

        Login.getLoginUiStage().close();
        log.info("关闭登录页面");

        new Register().start(new Stage());
        log.info("打开注册页面");
    }


    @FXML
    void changeMain(MouseEvent event) throws Exception {
        if (checkUser() && OperationalDocument.readFile("app.config").contains("localhostLogin:true;")) {
            Login.getLoginUiStage().close();
            log.info("关闭登录页面");
            if (autoLogin.isSelected()) {
                OperationalDocument.continuationFile("autoLogin:true;");
            }
            if (rememberPW.isSelected()) {
                OperationalDocument.continuationFile("rememberPW:true;");
            }else {
                OperationalDocument.replace("rememberPW:true;", "rememberPW:false;", "app.config");
            }
            new MainDiary().start(new Stage());
            log.info("打开注册页面");
        } else {
            showAlert();
        }
    }

    @FXML
    void changFUi(MouseEvent event) throws Exception {
        Login.getLoginUiStage().close();
        new ForgotPassword().start(new Stage());
    }


    private boolean checkUser() {
        if (userNumber.getText().isEmpty() || password.getText().isEmpty()) {
            return false;
        }
        if (!OperationalDocument.existFile("user.json")) {
            return false;
        }
        User user = JSONUtil.toBean(OperationalDocument.readFile("user.json"), User.class);
        System.out.println(user);
        return user.getUserNumber().equals(userNumber.getText()) &&
                user.getPassword().equals(password.getText());
    }

    private void showAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("提示");
        alert.setHeaderText("账号或密码不正确，请重新输入");
        alert.setContentText("要在页面选上本地登录的按钮方可以不需要联网进行操作,\n软件只能有一个本地用户");

        // 显示提示框并等待用户响应
        alert.showAndWait();
    }
}
