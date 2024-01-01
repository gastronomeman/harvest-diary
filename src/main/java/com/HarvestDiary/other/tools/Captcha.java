package com.HarvestDiary.other.tools;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import javafx.scene.image.Image;
import lombok.Getter;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

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