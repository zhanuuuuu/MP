package com.wx.v1;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tool.ReadConfig;
import tool.utils.PrintUtils;
import tool.utils.ServletUtils;
import org.json.JSONException;
import org.json.JSONObject;
import tool.HttpTool;
import wxsdk.WXConstant;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(description = "getOpenid", urlPatterns = {"/GetWX_OpenID"})
public class GetWX_OpenID extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ServletUtils.setResponse(response);
        request.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        String code = request.getParameter("code");
        String appID = new ReadConfig().getProp("/conf.properties").getProperty("APP_ID");
        String appSecret = new ReadConfig().getProp("/conf.properties").getProperty("APP_SECRET");
        String action = request.getParameter("action");

        String access_token_Url = "https://api.weixin.qq.com/sns/oauth2/access_token";

        String resultStr = HttpTool.GET(access_token_Url, "appid=" + appID + "&secret=" + appSecret + "&code=" + code + "&grant_type=authorization_code");

        try {
            PrintUtils.print(resultStr);
            JSONObject obj = new JSONObject(resultStr);
            String ACCESS_TOKEN = obj.getString("access_token");
            String OPENID = obj.getString("openid");
            String refresh_token = obj.getString("refresh_token");
            String is_valid = HttpTool.GET("https://api.weixin.qq.com/sns/auth", "access_token=" + ACCESS_TOKEN + "&openid=" + OPENID);
            PrintUtils.print("is_valid_access_token:" + is_valid);
            JSONObject judgeStr = new JSONObject(is_valid);
            String errCode = judgeStr.getString("errcode");
            if (!errCode.equals("0")) {
                resultStr = HttpTool.GET("https://api.weixin.qq.com/sns/oauth2/refresh_token", "appid=" + appID + "&grant_type=refresh_token&refresh_token=" + refresh_token);
            }
            JSONObject o = new JSONObject(resultStr);
            String openid = o.getString("openid");
            System.out.println("openid=" + openid);
            out.print(openid);
            WXConstant.Function f = WXConstant.Function.getFunctionWithCode(Integer.parseInt(action));
            String redirectUrl = "pages/jsp/" + f.uri + "?openid=" + openid;
            response.sendRedirect(redirectUrl);

        } catch (JSONException e) {
            PrintUtils.print("----openid not found---");
            response.sendRedirect("404.jsp");
            e.printStackTrace();
        } finally {
            out.flush();
            out.close();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
        /* String action = request.getParameter("action");
        System.out.println("action=" + action);
        String qyOpenID = "oZJo4w1iR2qaWq-no9vU7M1B03xw";
        WXConstant.Function f = WXConstant.Function.getFunctionWithCode(Integer.parseInt(action));
        String redirectUrl = "pages/jsp/" + f.uri + "?openid=" + qyOpenID;
        response.sendRedirect(redirectUrl);*/
    }
}
