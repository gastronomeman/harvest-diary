package com.HarvestDiary.UiController;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONUtil;
import com.HarvestDiary.Ui.ForgotPassword;
import com.HarvestDiary.Ui.Login;
import com.HarvestDiary.Ui.MainDiary;
import com.HarvestDiary.Ui.Register;
import com.HarvestDiary.otherTools.OperationalDocument;
import com.HarvestDiary.pojo.Poetry;
import com.HarvestDiary.pojo.User;
import com.HarvestDiary.pojo.UserStatus;
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

    private UserStatus userStatus;

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

        userStatus = JSONUtil.toBean(OperationalDocument.readFile("userStatus.json"), UserStatus.class);

        if (userStatus.getRememberPw()) {
            User user = JSONUtil.toBean(OperationalDocument.readFile("user.json"), User.class);
            userNumber.setText(user.getUserId());
            password.setText(user.getPassword());
            rememberPw.setSelected(true);
        }
        if (userStatus.getAutoLogin()) {
            autoLogin.setSelected(true);
            Thread thread = new Thread(() -> {
                Platform.runLater(() -> {
                    Login.getLoginUiStage().close();
                    try {
                        new MainDiary().start(new Stage());
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                });
            });
            thread.start();
        }
    }

    @FXML
    void changeUi(MouseEvent event) throws Exception {
        Login.getLoginUiStage().close();
        log.info("关闭登录页面");

        new Register().start(new Stage());
        log.info("打开注册页面");
    }


    @FXML
    void changFUi(MouseEvent event) throws Exception {
        Login.getLoginUiStage().close();
        new ForgotPassword().start(new Stage());
    }

    @FXML
    void changeMain(MouseEvent event) throws Exception {
        //本地登录
        if (localhost.isSelected()) {
            if (localLogin()) {
                setLocalLogin();
                Login.getLoginUiStage().close();
                new MainDiary().start(new Stage());
                return;
            }
        }
        //线上登录
        Thread thread = new Thread(() -> {
            Platform.runLater(() -> {
                try {
                    if (serverLogin()) {
                        setLocalLogin();

                        Login.getLoginUiStage().close();

                        new MainDiary().start(new Stage());

                    }
                } catch (Exception e) {
                    showAlert("网络连接异常");
                }
            });
        });
        thread.start();
    }

    //本地登录
    private boolean localLogin() {
        if (userNumber.getText().isEmpty() || password.getText().isEmpty()) {
            showAlert("账号密码不能为空");
            return false;
        }
        if (!OperationalDocument.existFile("user.json")) {
            showAlert("暂无本地用户");
            return false;
        }
        User user = JSONUtil.toBean(OperationalDocument.readFile("user.json"), User.class);
        System.out.println(user);

        if (!user.getUserId().equals(userNumber.getText()) &&
                !user.getPassword().equals(password.getText())) {
            showAlert("账号密码错误");
            return false;
        }

        return true;
    }

    //线上登录
    private boolean serverLogin() throws Exception {
        if (userNumber.getText().isEmpty() || password.getText().isEmpty()) {
            showAlert("账号密码不能为空");
            return false;
        }
        User user = new User();
        user.setUserId(userNumber.getText());
        user.setPassword(password.getText());
        HttpResponse response = HttpRequest.post(Poetry.API + "/user/login")
                .header("Content-Type", "application/json")
                .body(JSONUtil.toJsonStr(user))
                .execute();
        //提取出JSON数据
        String json = "{" + StrUtil.subBetween(response.body(), "{", "}") + "}";

        if (JSONUtil.parseObj(json).getStr("code").equals("0") && JSONUtil.parseObj(json).getStr("msg").equals("error3")) {
            showAlert("账号密码错误，请重新输入！");
            return false;
        }

        return JSONUtil.parseObj(json).get("code").equals("1");
    }

    //点击登录设置一系列消息到json数组里，如果是服务器登陆，把sUser也到json数组
    private void setLocalLogin() {
        userStatus.setUserId(userNumber.getText());
        userStatus.setRememberPw(rememberPw.isSelected());
        userStatus.setAutoLogin(autoLogin.isSelected());
        userStatus.setLocalLogin(localhost.isSelected());

        String json = JSONUtil.toJsonStr(userStatus);

        OperationalDocument.saveFile("userStatus.json", json);


        if (!localhost.isSelected()) {
            User user = new User();
            user.setUserId(userNumber.getText());
            user.setPassword(password.getText());
            OperationalDocument.saveFile("sUser.json", JSONUtil.toJsonStr(user));
        }
    }

    private void showAlert(String s) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("提示");
        alert.setHeaderText(s);
        alert.setContentText("\t要在页面选上本地登录的按钮方可以不需要联网进行操作,\n软件只能有一个本地用户。");

        // 显示提示框并等待用户响应
        alert.showAndWait();
    }
}
