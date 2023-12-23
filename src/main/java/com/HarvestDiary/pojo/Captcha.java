package com.HarvestDiary.pojo;

import lombok.Getter;

import java.awt.image.BufferedImage;


@Getter
public class Captcha {
    private final String text;
    private final BufferedImage image;

    public Captcha(String text, BufferedImage image) {
        this.text = text;
        this.image = image;
    }

}
