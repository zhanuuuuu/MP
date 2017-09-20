package com.wx.v2;

import tool.ReadConfig;
import tool.utils.PrintUtils;
import tool.utils.ServletUtils;
import wxsdk.WXConstant;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;

/**
 * Created by xwy_brh on 2017/9/18.
 */
@WebServlet(description = "获取微信code", urlPatterns = {"/wx/v2/get/code"})
public class GetCode extends HttpServlet {


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServletUtils.setResponse(response);
        request.setCharacterEncoding("UTF-8");

        String url = WXConstant.CODE_DOMAIN_URL;
        try {
            String appID = new ReadConfig().getProp("/conf.properties").getProperty("APP_ID");
            String redirect_url = ServletUtils.getServerPath(request) + "GetWX_OpenID";
            String redirect_uri = URLEncoder.encode(redirect_url, "utf-8");
            PrintUtils.print(redirect_url);
            PrintUtils.print(redirect_uri);
            String scope = WXConstant.SNS_AUTH_TYPE;
            url = url.replace("APPID", appID);
            url = url.replace("SCOPE", scope);
            url = url.replace("REDIRECT_URI", redirect_uri);
            response.sendRedirect(url);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
