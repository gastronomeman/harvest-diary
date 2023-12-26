package com.HarvestDiary.otherTools;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.io.file.FileWriter;
import cn.hutool.crypto.symmetric.SymmetricAlgorithm;
import cn.hutool.crypto.symmetric.SymmetricCrypto;
import lombok.extern.slf4j.Slf4j;

import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
public class transformJSON {
    public static void save(String content) {
        String fileName = "user.json";  // 文件名

        // 创建AES加密工具
        SymmetricCrypto aes = new SymmetricCrypto(SymmetricAlgorithm.AES, Key.key);


        // 加密
        byte[] encryptedData = aes.encrypt(content);
        content = Base64.encode(encryptedData);

        try {
            // 获取项目根路径
            String rootPath = System.getProperty("user.dir");

            // 构造文件路径
            Path filePath = Paths.get(rootPath, fileName);


            // 使用 Hutool 的 FileWriter 写入文本内容
            FileWriter fileWriter = new FileWriter(filePath.toFile(), "UTF-8");
            fileWriter.write(content);

            System.out.println("Text file saved at: " + filePath);

        } catch (Exception e) {
            log.info(String.valueOf(e));
        }

    }

}
