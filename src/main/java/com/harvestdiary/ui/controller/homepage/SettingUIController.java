package com.harvestdiary.ui.controller.homepage;


import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONUtil;
import com.harvestdiary.other.tools.OperationalDocument;
import com.harvestdiary.other.tools.SettingFontIcon;
import com.harvestdiary.pojo.Diary;
import com.harvestdiary.pojo.Poetry;
import com.harvestdiary.pojo.User;
import com.harvestdiary.pojo.UserStatus;
import com.harvestdiary.ui.HomePage;
import com.harvestdiary.ui.Login;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXToggleButton;
import de.felixroske.jfxsupport.FXMLController;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import org.kordamp.ikonli.antdesignicons.AntDesignIconsOutlined;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@FXMLController
@Slf4j
public class SettingUIController {
    @FXML
    private CheckBox clean1;
    @FXML
    private JFXCheckBox clean2;
    @FXML
    private JFXToggleButton clean3;
    @FXML
    private JFXButton cleanAll;
    @FXML
    private JFXButton downLoad;
    @FXML
    private JFXButton upLoad;
    @FXML
    private DatePicker datePicker;
    @FXML
    private JFXButton delDiary;
    @FXML
    private JFXButton delUser;
    @FXML
    private TextField password;
    @FXML
    private TextField phone;
    @FXML
    private Hyperlink gitee;
    @FXML
    private Hyperlink github;
    @FXML
    private TextField userNumber;
    @FXML
    private TextField username;


    private User user = new User();
    private UserStatus userStatus = new UserStatus();

    @FXML
    public void initialize() {
        setButtonStyle();

        //获取用户数据
        userStatus = JSONUtil.toBean(OperationalDocument.readFile("userStatus.json"), UserStatus.class);
        if (userStatus.getLocalLogin()) {
            user = JSONUtil.toBean(OperationalDocument.readFile("User.json"), User.class);
        } else {
            user = JSONUtil.toBean(OperationalDocument.readFile("sUser.json"), User.class);
        }

        //删除设置


    }

    @FXML
    void delDiaryBtn(MouseEvent event) {
        //设置文件名字
        String fileName = user.getUserId() + datePicker.getValue();
        //判断删除的文件
        if (OperationalDocument.existFile("diary\\" + fileName)) {
            if (!showAlert("您是否要删除" + datePicker.getValue() + "这个时间的日记")) {
                return;
            }
            if (!delDiaryTip()) {
                return;
            }
            delDiary(fileName);
            if (!userStatus.getLocalLogin()) {
                delCouldDiary(fileName);
            }
        } else {
            showAlert("日记不存在，请重新选择");
        }

    }

    private void delDiary(String fileName) {
        if (OperationalDocument.removeFile("diary\\" + fileName)) {
            showAlert("删除成功！");
        } else {
            showAlert("删除失败！");
        }
    }

    private void delCouldDiary(String fileName) {
        String s = "";
        try {
            Diary diary = JSONUtil.toBean(OperationalDocument.readFile("diary\\" + fileName), Diary.class);
            HttpResponse response = HttpRequest.post(Poetry.API + "/diary/del")
                    .header("Content-Type", "application/json")
                    .body(JSONUtil.toJsonStr(diary))
                    .execute();

            s = JSONUtil.parseObj(response.body()).getStr("code");
        } catch (Exception e) {
            log.info("删除云端失败", e);
        } finally {
            if (s.isEmpty()) {
                showAlert("删除云端日记失败，请联网后尝试");
            }
        }
    }

    private Boolean delDiaryTip() {
        // 创建文本输入对话框
        TextInputDialog textInputDialog = new TextInputDialog();
        textInputDialog.setTitle("删除日记");
        textInputDialog.setHeaderText("请输入你的密码以删除" + datePicker.getValue() + "时间的日记");
        textInputDialog.setContentText("请输入密码:");

        // 获取用户的输入
        Optional<String> result = textInputDialog.showAndWait();

        if (result.isPresent()) {
            String enteredPassword = result.get();
            if (user.getPassword().equals(enteredPassword)) {
                // 密码匹配，执行删除日记的操作
                return true;
            } else {
                // 密码不匹配，可以在这里显示错误消息或采取其他适当的措施
                System.out.println("密码不正确，请重新输入或采取其他措施。");
            }
        }

        return false;
    }


    @FXML
    void upLoad(MouseEvent event) {
        if (userStatus.getLocalLogin()) {
            showAlert("本地用户不能使用云端");
            return;
        }

        List<String> list = OperationalDocument.readDiaries(user.getUserId(), false);
        List<Diary> diaryList = new ArrayList<>();

        for (String s : list) {
            Diary d = JSONUtil.toBean(OperationalDocument.readDiary(s), Diary.class);
            diaryList.add(d);
        }


        String temp = JSONUtil.toJsonStr(diaryList);
        try {
            HttpResponse response = HttpRequest.post(Poetry.API + "/diary/setDiary")
                    .header("Content-Type", "application/json")
                    .body(temp)
                    .execute();

            String json = response.body();
            json = JSONUtil.parseObj(json).getStr("code");

            if (json.equals("1")) {
                showAlert("日记上传成功！");
            }

            temp = "";
        } catch (Exception e) {
            log.info("删除云端失败");
        } finally {
            if (!temp.isEmpty()) {
                showAlert("网络不好，请稍后尝试");
            }
        }
    }


    @FXML
    void downLoad(MouseEvent event) {
        if (userStatus.getLocalLogin()) {
            showAlert("本地用户不能使用云端");
            return;
        }
        String userId = user.getUserId();
        try {
            HttpResponse response = HttpRequest.post(Poetry.API + "/diary/getDiary")
                    .header("Content-Type", "application/json")
                    .body(userId)
                    .execute();

            String json = response.body();

            if (JSONUtil.parseObj(json).getStr("code").equals("1")) {
                showAlert("日记下载成功！");
            }

            String data = JSONUtil.parseObj(json).getStr("data");
            List<Diary> list = JSONUtil.toList(JSONUtil.parseArray(data), Diary.class);

            for (Diary diary : list) {
                OperationalDocument.writeDiary(diary.getUserId() + diary.getTime(), JSONUtil.toJsonStr(diary));
            }

            userId = "";
        } catch (Exception e) {
            log.info("删除云端失败");
        } finally {
            if (!userId.isEmpty()) {
                showAlert("网络不好，请稍后尝试");
            }
        }

    }

    @FXML
    void delUser(MouseEvent event) {
        if (!showAlert("确定注销用户！")) return;

        User u = new User();
        u.setUserId(userNumber.getText());
        u.setUsername(username.getText());
        u.setPassword(password.getText());
        u.setPhone(phone.getText());

        if (checkIsEmpty()) {
            showAlert("输入框四项不能为空！");
            return;
        }

        if (userStatus.getLocalLogin()) {
            Platform.runLater(() -> {
                try {
                    delLocalUser(u);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
        }else {
            Platform.runLater(() -> {
                try {
                    delServerUser(u);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
        }


    }



    private Boolean checkIsEmpty() {
        return userNumber.getText().isEmpty() || username.getText().isEmpty()
                || password.getText().isEmpty() || phone.getText().isEmpty();

    }

    private void delLocalUser(User u) throws Exception {
        if (!u.equals(user)) {
            showAlert("填写信息错误");
            return;
        }

        List<String> list = OperationalDocument.readDiaries(user.getUserId(), true);

        for (String s : list) {
            OperationalDocument.removeFile("diary\\" + s);
        }


        OperationalDocument.removeFile("weatherAS.json");
        OperationalDocument.removeFile("userStatus.json");
        OperationalDocument.removeFile("user.json");

        showAlert("注销用户成功！");

        HomePage.getMainDiaryUiStage().close();
        new Login().start(new Stage());
    }

    private void delServerUser(User u) throws Exception {
        if (!u.getUserId().equals(user.getUserId()) && u.getPassword().equals(user.getPassword())) {
            showAlert("填写信息错误");
            return;
        }

        String temp = JSONUtil.toJsonStr(u);
        try {
            HttpResponse response = HttpRequest.post(Poetry.API + "/user/delUser")
                    .header("Content-Type", "application/json")
                    .body(temp)
                    .execute();

            String json = response.body();
            json = JSONUtil.parseObj(json).getStr("code");

            if (json.equals("1")) {
                showAlert("用户注销成功！");
            }

            temp = "";
        } catch (Exception e) {
            log.info("用户注销失败");
        } finally {
            if (!temp.isEmpty()) {
                showAlert("网络不好，请稍后尝试");
            }
        }

        List<String> list = OperationalDocument.readDiaries(user.getUserId(), false);

        for (String s : list) {
            OperationalDocument.removeFile("diary\\" + s);
        }

        OperationalDocument.removeFile("weatherAS.json");
        OperationalDocument.removeFile("userStatus.json");
        OperationalDocument.removeFile("sUser.json");

        HomePage.getMainDiaryUiStage().close();
        new Login().start(new Stage());
    }

    @FXML
    void copyGitee(MouseEvent event) {
        copyUrl("https://gitee.com/gastronome-0_0/harvest-diary");
    }

    @FXML
    void copyGithub(MouseEvent event) {
        copyUrl("https://github.com/gastronomeman/harvest-diary.git");
    }

    private void copyUrl(String s) {
        Thread thread = new Thread(() -> {
            Platform.runLater(() -> {
                // 获取系统剪贴板
                Clipboard clipboard = Clipboard.getSystemClipboard();

                // 创建 ClipboardContent 对象，用于保存要复制的字符串
                ClipboardContent content = new ClipboardContent();
                content.putString(s);

                // 将 ClipboardContent 对象设置到剪贴板
                clipboard.setContent(content);
            });
        });
        thread.start();
    }

    private void setButtonStyle() {
        datePicker.setValue(LocalDate.now());
        delDiary.setGraphic(SettingFontIcon.setSizeAndColor(AntDesignIconsOutlined.DELETE, 15, Color.web("#617172")));
        upLoad.setGraphic(SettingFontIcon.setSizeAndColor(AntDesignIconsOutlined.CLOUD_UPLOAD, 22, Color.web("#617172")));
        downLoad.setGraphic(SettingFontIcon.setSizeAndColor(AntDesignIconsOutlined.CLOUD_DOWNLOAD, 22, Color.web("#617172")));
        delUser.setGraphic(SettingFontIcon.setSizeAndColor(AntDesignIconsOutlined.LOGOUT, 20, Color.web("#617172")));
        cleanAll.setGraphic(SettingFontIcon.setSizeAndColor(AntDesignIconsOutlined.CLEAR, 20, Color.web("#617172")));
        github.setGraphic(SettingFontIcon.setSizeAndColor(AntDesignIconsOutlined.GITHUB, 20, Color.web("#617172")));
    }

    private boolean showAlert(String s) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("提示");
        alert.setHeaderText("请查看警告：注意！注意！注意！");
        alert.setContentText(s);

        // 显示提示框并等待用户响应
        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;
    }
}