package com.HarvestDiary.pojo;

//密钥

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

public class Poetry {
    //{-1, -2, -3, 52, 0, 13, 13, 0, -14, -14, 0, -52, 3, 62, 2, 1}
    //SecureUtil.generateKey(SymmetricAlgorithm.AES.getValue()).getEncoded()
    //文件加密密钥
    final static String ancientPoetry = "----长风破浪会有时，----";
    public static final byte[] XinLuNan = ancientPoetry.getBytes(StandardCharsets.UTF_8);


    //API接口地址http://50b179dc.r16.vip.cpolar.cn
    public final static String API = "http://localhost:8080";

}
