package com.HarvestDiary.otherTools;

import com.HarvestDiary.pojo.Captcha;
import javafx.scene.image.Image;
import lombok.extern.slf4j.Slf4j;

import javax.imageio.ImageIO;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

@Slf4j
public class CaptchaGenerator {
    public static Image generateImages() throws IOException {
        //"src/main/resources/fxml/LoginUI.fxml";
        // 生成验证码
        Captcha captcha = generateCaptcha();

        //验证码以字节流的形式输入
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(saveImageToByteArrayOutputStream(captcha.getImage()));

        return new Image(byteArrayInputStream);
    }

    private static Captcha generateCaptcha() {
        int width = 100;
        int height = 60;

        // 创建一个 BufferedImage 对象
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = image.createGraphics();

        // 设置背景色
        g2d.setColor(new Color(247, 232, 170));
        g2d.fillRect(0, 0, width, height);

        // 生成随机验证码文本
        String captchaText = generateRandomText();

        // 将验证码文本绘制到图片上
        // 随机生成 R、G、B 值
        Random random = new Random();
        int red = random.nextInt(255);
        int green = random.nextInt(255);
        int blue = random.nextInt(255);

        g2d.setColor(new Color(red, green, blue));
        g2d.setFont(new Font("Arial", Font.BOLD, 30));
        g2d.drawString(captchaText, 2, 40);

        // 添加干扰元素，这里添加了15条随机线条
        for (int i = 0; i < 15; i++) {
            int x1 = random.nextInt(width);
            int y1 = random.nextInt(height);
            int x2 = random.nextInt(width);
            int y2 = random.nextInt(height);
            g2d.drawLine(x1, y1, x2, y2);
        }

        // 释放图形上下文资源
        g2d.dispose();

        return new Captcha(captchaText, image);
    }

    private static String generateRandomText() {
        int length = 5;
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder captchaText = new StringBuilder();

        Random random = new Random();
        for (int i = 0; i < length; i++) {
            char randomChar = characters.charAt(random.nextInt(characters.length()));
            captchaText.append(randomChar);
        }

        return captchaText.toString();
    }

    private static byte[] saveImageToByteArrayOutputStream(BufferedImage image) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ImageIO.write(image, "png", byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }


}
