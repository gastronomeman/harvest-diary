package com.HarvestDiary.otherTools;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.file.FileReader;
import cn.hutool.core.io.file.FileWriter;
import cn.hutool.crypto.symmetric.SymmetricAlgorithm;
import cn.hutool.crypto.symmetric.SymmetricCrypto;
import cn.hutool.json.JSONUtil;
import com.HarvestDiary.pojo.User;
import lombok.extern.slf4j.Slf4j;

import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
public class OperationalDocument {

    // 获取项目根路径
    private static final String rootPath = System.getProperty("user.dir");
    // 构造文件夹路径
    private static final String folderName = "HarvestDiary";  // 替换为你想要的文件夹名字
    private static final String folderPath = FileUtil.normalize(Paths.get(rootPath, folderName).toString());

    public static void saveJSON(String content) {
        String fileName = "user.json";  // 文件名

        // 创建AES加密工具
        SymmetricCrypto aes = new SymmetricCrypto(SymmetricAlgorithm.AES, Key.key);

        // 加密
        byte[] encryptedData = aes.encrypt(content);
        content = Base64.encode(encryptedData);


        // 创建文件夹（如果不存在）
        FileUtil.mkdir(folderPath);

        // 构造文件路径
        Path filePath = Paths.get(folderPath, fileName);


        // 使用 Hutool 的 FileWriter 写入文本内容
        FileWriter fileWriter = new FileWriter(filePath.toFile(), "UTF-8");
        fileWriter.write(content);

        System.out.println("Text file saved at: " + filePath);


    }

    public static User readUser() {

        // 构造文件路径
        Path filePath = Paths.get(folderPath, "user.json");

        // 使用 Hutool 的 FileReader 读取加密的 JSON 文件内容
        FileReader fileReader = new FileReader(filePath.toFile(), "UTF-8");
        String encryptedContent = fileReader.readString();

        // Base64 解码
        byte[] encryptedData = Base64.decode(encryptedContent);

        // 创建AES解密工具
        SymmetricCrypto aes = new SymmetricCrypto(SymmetricAlgorithm.AES, Key.key);

        // 解密
        String decryptedJson = aes.decryptStr(encryptedData);

        return JSONUtil.toBean(decryptedJson, User.class);
    }

    public static void saveFile(String fileName, String content){

        // 创建文件夹（如果不存在）
        FileUtil.mkdir(folderPath);

        // 构造文件路径
        Path filePath = Paths.get(folderPath, fileName);


        // 使用 Hutool 的 FileWriter 写入文本内容
        FileWriter fileWriter = new FileWriter(filePath.toFile(), "UTF-8");
        fileWriter.write(content);

        System.out.println("Text file saved at: " + filePath);
    }
    public static boolean existFile(String fileName){
        return FileUtil.exist(FileUtil.file(folderPath, fileName));
    }
}
