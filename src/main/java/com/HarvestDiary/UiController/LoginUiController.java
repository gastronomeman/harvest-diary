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
    private JFXCheckBox rememberPw;
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
        Login.getLoginUiStage().close();
        new MainDiary().start(new Stage());
    }

    @FXML
    void changFUi(MouseEvent event) throws Exception {
        Login.getLoginUiStage().close();
        new ForgotPassword().start(new Stage());
    }



    private void showAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("提示");
        alert.setHeaderText("账号或密码不正确，请重新输入");
        alert.setContentText("\t要在页面选上本地登录的按钮方可以不需要联网进行操作,\n软件只能有一个本地用户");

        // 显示提示框并等待用户响应
        alert.showAndWait();
    }
}
