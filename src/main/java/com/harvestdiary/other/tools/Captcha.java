package com.harvestdiary.other.tools;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import javafx.scene.image.Image;
import lombok.Getter;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * 随机生成五位数的验证码
 */
@Getter
public class Captcha {
    private String code;
    private Image image;

    public void generateImages() throws IOException {
        LineCaptcha captcha = CaptchaUtil.createLineCaptcha(110, 60, 5, 50);

        code = captcha.getCode();

        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(saveImageToByteArrayOutputStream(captcha.getImage()));

        image = new Image(byteArrayInputStream);
    }

    private byte[] saveImageToByteArrayOutputStream(BufferedImage image) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ImageIO.write(image, "png", byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

}
