package com.wx.v2;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by xwy_brh on 2017/9/18.
 */
@WebServlet(description = "获取微信code", urlPatterns = {"/test1"})
public class Test1 extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();

        HttpSession session = request.getSession();
        String path = (String) request.getAttribute("path");
        System.out.println("path=" + path);
        System.out.println(session);
        String sessionID = session.getId();
        System.out.println("sessionID=" + sessionID);
        System.out.println("88888888-servlet in test1");
        session.setAttribute("openid", "12wkewio12dddl1k2osss");
        session.setAttribute("tel", "13253502652");
        response.sendRedirect("/mpwx/test2");
        out.print("/test11111111111111111111");
    }
}
