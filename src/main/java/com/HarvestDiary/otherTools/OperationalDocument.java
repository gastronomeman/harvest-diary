package com.HarvestDiary.otherTools;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.file.FileReader;
import cn.hutool.core.io.file.FileWriter;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.symmetric.SymmetricAlgorithm;
import cn.hutool.crypto.symmetric.SymmetricCrypto;
import cn.hutool.json.JSONUtil;
import com.HarvestDiary.pojo.Poetry;
import com.HarvestDiary.pojo.User;
import lombok.extern.slf4j.Slf4j;


import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
public class OperationalDocument {

    // 获取项目根路径
    private static final String rootPath = System.getProperty("user.dir");
    // 构造文件夹路径
    private static final String folderName = "HarvestDiary";
    private static final String folderPath = FileUtil.normalize(Paths.get(rootPath, folderName).toString());



    public static String encryptionString(String content){
        // 创建AES加密工具
        SymmetricCrypto aes = new SymmetricCrypto(SymmetricAlgorithm.AES, Poetry.XinLuNan);
        // 加密
        byte[] encryptedData = aes.encrypt(content);
        content = Base64.encode(encryptedData);

        return content;
    }
    public static void saveFile(String fileName, String content) {
        //加密
        content = encryptionString(content);

        // 创建文件夹（如果不存在）
        FileUtil.mkdir(folderPath);

        // 构造文件路径
        Path filePath = Paths.get(folderPath, fileName);


        // 使用 Hutool 的 FileWriter 写入文本内容
        FileWriter fileWriter = new FileWriter(filePath.toFile(), "UTF-8");
        fileWriter.write(content);

        System.out.println("Text file saved at: " + filePath);

    }
    public static void removeFile(String fileName){
        // 创建文件夹（如果不存在）
        FileUtil.mkdir(folderPath);
        // 构造文件路径
        Path filePath = Paths.get(folderPath, fileName);

        FileUtil.del(filePath);

    }
    public static void continuationFile(String content){

        content = readFile("app.config") + content;
        System.out.println(content);

        content = encryptionString(content);
        // 创建文件夹（如果不存在）
        FileUtil.mkdir(folderPath);
        // 构造文件路径
        Path filePath = Paths.get(folderPath, "app.config");

        // 使用 Hutool 追加内容到文件
        FileWriter writer = new FileWriter(FileUtil.file(filePath.toString()), "UTF-8");
        System.out.println(content);

        writer.write(content);
    }
    public static String readFile(String fileName){
        // 构造文件路径
        Path filePath = Paths.get(folderPath, fileName);

        // 使用 Hutool 的 FileReader 读取加密的 JSON 文件内容
        FileReader fileReader = new FileReader(filePath.toFile(), "UTF-8");
        String encryptedContent = fileReader.readString();

        // Base64 解码
        byte[] encryptedData = Base64.decode(encryptedContent);

        // 创建AES解密工具
        SymmetricCrypto aes = new SymmetricCrypto(SymmetricAlgorithm.AES, Poetry.XinLuNan);

        // 解密
        return aes.decryptStr(encryptedData);
    }
    public static boolean existFile(String fileName){
        return FileUtil.exist(FileUtil.file(folderPath, fileName));
    }

    public static void replace(String oldStr, String newStr, String fileName){
        String s = OperationalDocument.readFile(fileName);
        s = s.replace(oldStr, newStr);
        OperationalDocument.saveFile(fileName, s);
    }
    public static ArrayList<String> readFileToList(String fileName){
        return (ArrayList<String>) StrUtil.split(readFile(fileName), ';');
    }
}