package com.wx.v1;

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
 * Created by xwy_brh on 2017/7/13.
 */
@WebServlet(description = "获取微信code", urlPatterns = {"/GetWX_CODE"})

public class GetWX_CODE extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ServletUtils.setResponse(response);
        request.setCharacterEncoding("UTF-8");

        String action = request.getParameter("action");
        String url = WXConstant.CODE_DOMAIN_URL;
        try {
            String appID = new ReadConfig().getProp("/conf.properties").getProperty("APP_ID");
            String redirect_url = ServletUtils.getServerPath(request) + "GetWX_OpenID?action=" + action;
            String redirect_uri = URLEncoder.encode(redirect_url, "utf-8");
            PrintUtils.print(redirect_url);
            PrintUtils.print(redirect_uri);
            String scope = "snsapi_base";
            url = url.replace("APPID", appID);
            url = url.replace("SCOPE", scope);
            url = url.replace("REDIRECT_URI", redirect_uri);
            response.sendRedirect(url);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
