package com.wx.v1;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tool.ReadConfig;
import tool.utils.PrintUtils;
import tool.utils.ServletUtils;
import org.json.JSONObject;

import com.google.gson.Gson;

import tool.Sign;
import tool.HttpTool;

@WebServlet(description = "配置wxSDK", urlPatterns = {"/GetWX_Get_Config"})
public class GetWX_Get_Config extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        response.setContentType("text/html;charset=UTF-8");
        response.setHeader("content-type", "text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        String appID = new ReadConfig().getProp("/conf.properties").getProperty("APP_ID");
        String appSecret = new ReadConfig().getProp("/conf.properties").getProperty("APP_SECRET");
        PrintWriter out = response.getWriter();
        String requestUrl = request.getRequestURL().toString();
        String openid = request.getParameter("openid");
        PrintUtils.print("Config openid---" + openid);
        PrintUtils.print(requestUrl);
        try {
            String url = "https://api.weixin.qq.com/cgi-bin/token";

            String str = HttpTool.GET(url, "grant_type=client_credential&appid=" + appID + "&secret=" + appSecret);
            System.out.println(str);
            JSONObject obj = new JSONObject(str);
            String access_token = obj.getString("access_token");
            String str1 = HttpTool.GET("https://api.weixin.qq.com/cgi-bin/ticket/getticket", "access_token=" + access_token + "&type=jsapi");
            System.out.println(str1);
            JSONObject obj1 = new JSONObject(str1);
            String jsapi_ticket = obj1.getString("ticket");
            System.out.println(jsapi_ticket);
            Gson gson = new Gson();
            String signedURL = ServletUtils.getServerPath(request) + "pages/jsp/scan.jsp?openid=" + openid;
            System.out.println("signed url ==" + signedURL);
            ConfigBean bean = Sign.sign(jsapi_ticket, signedURL);
            String resultStr = gson.toJson(bean);
            out.print(resultStr);
            PrintUtils.print(ServletUtils.getServerPath(request) + "scanQR.jsp?openid=" + openid);
            PrintUtils.print(resultStr);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
