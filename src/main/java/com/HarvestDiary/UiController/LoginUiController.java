package com.HarvestDiary.UiController;

import com.HarvestDiary.Ui.Login;
import com.HarvestDiary.Ui.MainDiary;
import com.HarvestDiary.Ui.Register;
import de.felixroske.jfxsupport.FXMLController;
import javafx.fxml.FXML;
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
        Login.getLoginUiStage().close();
        log.info("关闭登录页面");

        new Register().start(new Stage());
        log.info("打开注册页面");
    }

    @FXML
    void changeMain(MouseEvent event) throws Exception {
        Login.getLoginUiStage().close();
        log.info("关闭登录页面");

        new MainDiary().start(new Stage());
    }

}
