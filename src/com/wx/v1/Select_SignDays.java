package com.wx.v1;


import db.DB;
import db.GetConnection;

import tool.utils.DBBridge;
import tool.utils.DataProManager;
import tool.utils.ServletUtils;
import org.json.JSONArray;

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

/**
 * Created by xwy_brh on 2017/8/24.
 */
@WebServlet(description = "select sign days in current month", urlPatterns = { "/Select_SignDays" })
public class Select_SignDays extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        ServletUtils.setResponse(response);
        request.setCharacterEncoding("utf-8");

        PrintWriter out = response.getWriter();
        ResultSet rs = null;
        PreparedStatement past = null;
        String openid = request.getParameter("openid");

        Connection conn = GetConnection.getSuperMarket_Conn();
        try {
            String sql = "select DatePart(dd,dDate)as signDay from Wei_Xin_Vip_Check_In where datediff(month,[dDatetime],getdate())=0 and cWeixinID = ?";
            past = conn.prepareStatement(sql);
            past.setString(1, openid);
            rs = past.executeQuery();
            JSONArray resultArr = DataProManager.resultSetToJsonArray(rs);
            System.out.println("签时间到"+resultArr);
            out.print(resultArr);
        } catch (Exception e) {
            e.printStackTrace();
            out.print("error");
        } finally {
            out.flush();
            out.close();
            DB.closeRs_Con(rs, past, conn);
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
