package com.harvestdiary.other.tools;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.file.FileReader;
import cn.hutool.core.io.file.FileWriter;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.symmetric.SymmetricAlgorithm;
import cn.hutool.crypto.symmetric.SymmetricCrypto;
import com.harvestdiary.pojo.Poetry;
import lombok.extern.slf4j.Slf4j;


import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class OperationalDocument {

    // 获取项目根路径
    private static final String rootPath = System.getProperty("user.dir");
    // 构造文件夹路径
    private static final String folderName = "HarvestDiary";
    private static final String folderPath = FileUtil.normalize(Paths.get(rootPath, folderName).toString());


    /**
     * 加密工具
     *
     * @param content
     * @return
     */
    public static String encryptionString(String content) {
        // 创建AES加密工具
        SymmetricCrypto aes = new SymmetricCrypto(SymmetricAlgorithm.AES, Poetry.XinLuNan);
        // 加密
        byte[] encryptedData = aes.encrypt(content);

        content = Base64.encode(encryptedData);

        return content;
    }


    public static void saveDir(String fileName) {
        // 创建文件夹（如果不存在）
        FileUtil.mkdir(folderPath);
        //储存日记的文件夹
        FileUtil.mkdir(folderPath + "\\" + fileName);
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
    }

    public static void writeDiary(String fileName, String content) {
        //加密
        content = encryptionString(content);

        // 创建文件夹（如果不存在）
        FileUtil.mkdir(folderPath + "\\diary");

        // 构造文件路径
        Path filePath = Paths.get(folderPath + "\\diary", fileName);


        // 使用 Hutool 的 FileWriter 写入文本内容
        FileWriter fileWriter = new FileWriter(filePath.toFile(), "UTF-8");
        fileWriter.write(content);
    }

    public static Boolean removeFile(String fileName) {
        // 构造文件路径
        Path filePath = Paths.get(folderPath, fileName);

        System.out.println(filePath);

        return FileUtil.del(filePath);
    }

    public static void replace(String oldStr, String newStr, String fileName) {
        String s = OperationalDocument.readFile(fileName);
        s = s.replace(oldStr, newStr);
        OperationalDocument.saveFile(fileName, s);
    }

    public static String readFile(String fileName) {

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

    public static boolean existFile(String fileName) {
        return FileUtil.exist(FileUtil.file(folderPath, fileName));
    }

    public static String readDiary(String fileName) {
        if (existFile("\\diary\\" + fileName)) {
            return readFile("\\diary\\" + fileName);
        }
        return null;
    }

    public static List<String> readDiaries(String userId, Boolean local) {
        //List<>
        List<String> list = FileUtil.listFileNames(folderPath + "\\diary");

        if (local) {
            list.removeIf(s -> !s.startsWith(userId) && !s.endsWith("Local"));
        } else {
            list.removeIf(s -> !s.startsWith(userId) && s.endsWith("Local"));
        }


        return list;
    }
}
