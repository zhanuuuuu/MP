package tool;


import java.util.Formatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import tool.utils.PrintUtils;
import com.wx.v1.ConfigBean;


public class Sign {

    public static ConfigBean sign(String jsapi_ticket, String url) {
        String appID = new ReadConfig().getProp("/conf.properties").getProperty("APP_ID");
        Map<String, Object> ret = new HashMap<String, Object>();
        String nonce_str = create_nonce_str();
        long timestamp = create_timestamp();
        String string1;
        String signature = "";
        PrintUtils.print(url);
        string1 = "jsapi_ticket=" + jsapi_ticket +
                "&noncestr=" + nonce_str +
                "&timestamp=" + timestamp +
                "&url=" + url;
        PrintUtils.print(string1);
        try {
            signature = SHA1Tool.getSha1(string1);
            PrintUtils.print("signature:" + signature);
        } catch (Exception e) {
            e.printStackTrace();
        }
        ConfigBean config = new ConfigBean(appID, url, jsapi_ticket, nonce_str, timestamp, signature);

        ret.put("appId", appID);
        ret.put("url", url);
        ret.put("jsapi_ticket", jsapi_ticket);
        ret.put("nonceStr", nonce_str);
        ret.put("timestamp", timestamp);
        ret.put("signature", signature);

        return config;
    }

    private static String byteToHex(final byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash) {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }

    private static String create_nonce_str() {
        return UUID.randomUUID().toString();
    }

    private static long create_timestamp() {
        return (System.currentTimeMillis() / 1000);
    }

}
