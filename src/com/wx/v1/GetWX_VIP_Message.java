package com.wx.v1;

import db.DB;
import db.GetConnection;
import tool.HttpTool;
import tool.ReadConfig;
import tool.ResultSet_To_JSON;
import tool.utils.PrintUtils;
import tool.utils.ServletUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Iterator;

/**
 * Created by xwy_brh on 2017/8/23.
 */
@WebServlet(description = "get user message", urlPatterns = {"/GetWX_VIP_Message"})
public class GetWX_VIP_Message extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServletUtils.setResponse(response);
        request.setCharacterEncoding("UTF-8");

        PrintWriter out = response.getWriter();
        String openid = request.getParameter("openid");
        String appID = new ReadConfig().getProp("/conf.properties").getProperty("APP_ID");
        String appSecret = new ReadConfig().getProp("/conf.properties").getProperty("APP_SECRET");
        String url = "https://api.weixin.qq.com/cgi-bin/token";
        Connection conn = GetConnection.getPosmanagement_mainStoreConn();
        PreparedStatement past = null;
        ResultSet rs = null;
        String str1 = "";
        try {
            past = conn.prepareStatement("select cVipno,cTel,fCurValue,cStore,cIdCard,cardtype,card_code from t_vip where cWeixinID= ? ");
            past.setString(1, openid);
            rs = past.executeQuery();
            JSONArray array = ResultSet_To_JSON.resultSetToJsonArray(rs);
            if (array != null && array.length() > 0) {
                String str = HttpTool.GET(url, "grant_type=client_credential&appid=" + appID + "&secret=" + appSecret);
                System.out.println(str);
                JSONObject obj = new JSONObject(str);
                String access_token = obj.getString("access_token");
                str1 = HttpTool.GET("https://api.weixin.qq.com/cgi-bin/user/info", "access_token=" + access_token + "&openid=" + openid + "&lang=zh_CN");
                //JSONObject jsonObject = array.getJSONObject(0);
                JSONObject jsonObject1 = new JSONObject(str1);

                /*Iterator<?> iterator = jsonObject.keys();
                while (iterator.hasNext()) {
                    String key = (String) iterator.next();
                    String value = jsonObject.getString(key);
                    jsonObject1.put(key, value);
                }*/
                jsonObject1.put("cardInfo", array);
                String resultStr = jsonObject1.toString().replace(" ", "");
                PrintUtils.print("resultStr=" + resultStr);
                out.print(resultStr);
            } else {
                out.print("{\"error\":\"" + "用户信息查找失败" + "\"" + "}");
                PrintUtils.print("{\"error\":\"" + "用户信息查找失败" + "\"" + "}");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DB.closeRs_Con(rs, past, conn);
        }
        out.flush();
        out.close();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
