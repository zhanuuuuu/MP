package com.wx.v1;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by xwy_brh on 2017/7/26.
 */
@WebServlet(description = "设置标志, 配置微信公众号", urlPatterns = { "/Auth_WX_Token" })

public class Auth_WX_Token extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final String token = "warelucent";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String signature = request.getParameter("signature");
        String timestamp = request.getParameter("timestamp");
        String nonce = request.getParameter("nonce");
        String echostr = request.getParameter("echostr");
        System.out.println("signature:" + signature);
        System.out.println("timestamp:" + timestamp);
        System.out.println("nonce:" + nonce);
        System.out.println("echostr:" + echostr);
        PrintWriter pw = response.getWriter();
        pw.append(echostr);
        pw.flush();
        pw.close();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
