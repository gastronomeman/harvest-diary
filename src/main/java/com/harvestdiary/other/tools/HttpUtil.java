package com.harvestdiary.other.tools;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONUtil;
import com.harvestdiary.pojo.Poetry;

public class HttpUtil {
    /**
     * 发送post请求
     * @param url
     * @param content
     * @return
     */
    public static String httpResponse(String url, String content){
        HttpResponse response = HttpRequest.post(Poetry.API + url)
                .header("Content-Type", "application/json")
                .body(content)
                .execute();
        //提取出JSON数据
        return response.body();
    }
}
