package com.HarvestDiary.otherTools;

//密钥

import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.symmetric.SymmetricAlgorithm;

public class Key {

    //{-1, -2, -3, 52, 0, 13, 13, 0, -14, -14, 0, -52, 3, 62, 2, 1}
    //SecureUtil.generateKey(SymmetricAlgorithm.AES.getValue()).getEncoded()
    static final byte[] key = {-1, -2, -3, 52, 0, 13, 13, 0, -14, -14, 0, -52, 3, 62, 2, 1};
}
