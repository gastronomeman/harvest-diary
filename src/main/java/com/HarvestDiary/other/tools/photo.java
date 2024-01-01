package com.HarvestDiary.other.tools;

import cn.hutool.core.io.FileUtil;
import cn.hutool.http.HttpUtil;

public class photo {
    public static void main(String[] args) {
        // 定义要下载图片的URL
        String imageUrl = "https://source.unsplash.com/user/erondu/1600x900";

        // 发送HTTP GET请求并获取图片的二进制内容
        byte[] imageBytes = HttpUtil.downloadBytes(imageUrl);

        // 保存图片到本地文件
        String filePath = "D:/image.jpg"; // 指定保存的本地文件路径
        FileUtil.writeBytes(imageBytes, filePath);

        System.out.println("图片已保存到：" + filePath);
    }
}
