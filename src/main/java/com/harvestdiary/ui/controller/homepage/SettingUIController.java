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
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXToggleButton;
import de.felixroske.jfxsupport.FXMLController;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import lombok.extern.slf4j.Slf4j;
import org.kordamp.ikonli.antdesignicons.AntDesignIconsOutlined;

import java.time.LocalDate;
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
    private JFXButton cloud;
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
    private Label gitee;
    @FXML
    private Label github;
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
    void pullDiaryToLocal(MouseEvent event) {
      /*  try {

            HttpResponse response = HttpRequest.post(Poetry.API + "/diary/del")
                    .header("Content-Type", "application/json")
                    .body(JSONUtil.toJsonStr())
                    .execute();

            s = JSONUtil.parseObj(response.body()).getStr("code");
        } catch (Exception e) {
            log.info("删除云端失败", e);
        } finally {
            if (s.isEmpty()) {
                showAlert("删除云端日记失败，请联网后尝试");
            }
        }*/
    }

    @FXML
    void toBackColor(MouseEvent event) {
        github.setTextFill(Color.web("#617172"));
        gitee.setTextFill(Color.web("#617172"));
    }

    @FXML
    void githubRed(MouseEvent event) {
        github.setTextFill(Color.RED);
    }

    @FXML
    void giteeRed(MouseEvent event) {
        gitee.setTextFill(Color.RED);

    }

    private void setButtonStyle() {
        datePicker.setValue(LocalDate.now());
        delDiary.setGraphic(SettingFontIcon.setSizeAndColor(AntDesignIconsOutlined.DELETE, 15, Color.web("#617172")));
        cloud.setGraphic(SettingFontIcon.setSizeAndColor(AntDesignIconsOutlined.CLOUD_SYNC, 22, Color.web("#617172")));
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