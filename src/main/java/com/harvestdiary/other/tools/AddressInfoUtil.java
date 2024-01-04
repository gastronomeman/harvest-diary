package com.harvestdiary.other.tools;

import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.harvestdiary.pojo.WeatherAS;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;

@Slf4j
public class AddressInfoUtil {
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
    }

    //获取用户所在地天气
    public static void getAddressWeather() {
        WeatherAS weatherAS = new WeatherAS();
        try {
            String url = "https://www.mxnzp.com/api/weather/current/" + getAddressCity() + "?app_id=yd8hjgswgpjhmnfv&app_secret=1v0kRyednMC1WqzVo85Wwxb5ZsFXUMEm";

            HttpResponse response = HttpUtil.createGet(url).execute();
            JSONObject json = JSONUtil.parseObj(response.body());
            System.out.println(json);
            if (!json.getStr("code").equals("101")) {
                JSONObject data = JSONUtil.parseObj(json.getStr("data"));
                weatherAS.setTime(String.valueOf(LocalDate.now()));
                weatherAS.setAddress(data.getStr("address").replaceAll(".*\\s", "").trim());
                weatherAS.setWeather(data.getStr("weather"));
                weatherAS.setWind(data.getStr("windDirection") + "风 " + data.getStr("windPower"));
                OperationalDocument.saveFile("weatherAS.json", JSONUtil.toJsonStr(weatherAS));
            }
        } catch (Exception e) {
            log.info(String.valueOf(e));
        } finally {
            if (!OperationalDocument.existFile("weatherAS.json")) {
                weatherAS = new WeatherAS(String.valueOf(LocalDate.now()), "...", "...", "...");
                OperationalDocument.saveFile("weatherAS.json", JSONUtil.toJsonStr(weatherAS));
            }
        }

    }

}
