package com.HarvestDiary.UiController;

import com.HarvestDiary.Ui.Login;
import com.HarvestDiary.Ui.MainDiary;
import com.HarvestDiary.Ui.Register;
import com.HarvestDiary.otherTools.OperationalDocument;
import com.HarvestDiary.pojo.User;
import com.jfoenix.controls.JFXButton;
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
    private TextField userNumber;

    @FXML
    private JFXCheckBox autoLogin;
    @FXML
    private JFXCheckBox rememberPW;

    /**
     * 初始化方法，用于设置 JavaFX 控制器的初始状态。
     */
    @FXML
    public void initialize() {

        // 创建圆形裁剪区域
        Circle clip = new Circle();

        // 设置圆形裁剪区域的半径，使用图像宽度的一半
        clip.setRadius((double) 70 / 2);

        // 设置圆形裁剪区域的中心位置，使其居中
        clip.setCenterX((double) 70 / 2);
        clip.setCenterY((double) 70 / 2);

        // 将圆形裁剪区域应用于图像视图
        avatar.setClip(clip);
    }

    @FXML
    void changeUi(MouseEvent event) throws Exception {
        OperationalDocument.removeFile("localhost.login");

        Login.getLoginUiStage().close();
        log.info("关闭登录页面");

        new Register().start(new Stage());
        log.info("打开注册页面");
    }

    @FXML
    void changeUiAlert(MouseEvent event) throws Exception {
        if (OperationalDocument.existFile("user.json")){
            showAlert("你选择了本地已有一个账户");
            return;
        }
        Login.getLoginUiStage().close();
        log.info("关闭登录页面");
        Thread thread = new Thread(() -> {
            Platform.runLater(() -> {
                OperationalDocument.saveFile("localhost.login", "验证是否是本地登录");
            });
        });
        thread.start();
        showAlert("你选择了本地登录");
        new Register().start(new Stage());
        log.info("打开注册页面");
    }

    @FXML
    void changeMain(MouseEvent event) throws Exception {
        if (checkUser()){
            Login.getLoginUiStage().close();
            log.info("关闭登录页面");

            new MainDiary().start(new Stage());
            log.info("打开注册页面");
        }else {
            showAlert("账号或密码不正确，请重新输入");
        }
    }


    private boolean checkUser(){
        User user = OperationalDocument.readUser();
        return user.getUserNumber().equals(userNumber.getText()) &&
                user.getPassword().equals(password.getText());
    }
    private void showAlert(String s) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("提示");
        alert.setHeaderText(s);
        alert.setContentText("要在页面选上本地登录的按钮方可以不需要联网进行操作,\n软件只能有一个本地用户");

        // 显示提示框并等待用户响应
        alert.showAndWait();
    }
}
