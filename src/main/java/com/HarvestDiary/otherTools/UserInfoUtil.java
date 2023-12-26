package com.HarvestDiary.otherTools;

import cn.hutool.core.net.NetUtil;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;

public class UserInfoUtil {
    /*
      手机号:13268120482
      APP_ID:yd8hjgswgpjhmnfv
      APP_SECRET:1v0kRyednMC1WqzVo85Wwxb5ZsFXUMEm
       */
    //获取用户IP地址
    public static String getAddressCity() {

        // 使用一个提供公网 IP 查询服务的接口
        String url = "https://www.mxnzp.com/api/ip/self?app_id=lpkspetfes0fjqxy&app_secret=wmb1HNJlPExNsUIsT6NHM1yFt9qaBywZ";


        String ipAddress = HttpUtil.get(url);
        JSONObject jsonObjectIp = JSONUtil.parseObj(ipAddress);
        JSONObject data = jsonObjectIp.getJSONObject("data");

        return data.getStr("city");

        // 输出 data 字段的数据
      /*  System.out.println("IP: " + data.getStr("ip"));
        System.out.println("Province: " + data.getStr("province"));
        System.out.println("ProvinceId: " + data.getInt("provinceId"));
        System.out.println("City: " + data.getStr("city"));
        System.out.println("CityId: " + data.getInt("cityId"));
        System.out.println("ISP: " + data.getStr("isp"));
        System.out.println("Desc: " + data.getStr("desc"));*/
    }

    //获取用户所在地天气
    public static String getAddressWeather() {
        String url = "https://www.mxnzp.com/api/weather/current/" + getAddressCity() + "?app_id=yd8hjgswgpjhmnfv&app_secret=1v0kRyednMC1WqzVo85Wwxb5ZsFXUMEm";
        //System.out.println(url);
        HttpResponse response = HttpUtil.createGet(url).execute();
        // 获取响应结果
        String result = response.body();
        // 将字符串数据转换为 JSON 对象
        JSONObject jsonObject = JSONUtil.parseObj(result);

        // 获取 data 字段的数据
        JSONObject data = jsonObject.getJSONObject("data");

        if (data.getStr("weather") == null){
            return "网络不好...";
        }else {
            return data.getStr("weather");
        }
    }

}
