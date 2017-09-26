package com.wx.v1;

import db.DB;
import db.GetConnection;
import tool.ReadConfig;
import tool.String_Tool;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


@WebServlet(description = "vip check in", urlPatterns = { "/Wei_Xin_Vip_Check_In" })
public class Wei_Xin_Vip_Check_In extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.getWriter().append("Served at: ").append(request.getContextPath());
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        response.setContentType("text/html;charset=UTF-8");
        response.setHeader("content-type", "text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        Connection conn = GetConnection.getSuperMarket_Conn();
        Connection conn2 = GetConnection.getPosmanagement_mainStoreConn();
        String openid = request.getParameter("openid");
        String sql = "select cWeixinID,dDate,dDatetime from Wei_Xin_Vip_Check_In where cWeixinID=? and dDate=? ";
        PreparedStatement past = null;
        ResultSet rs = null;
        try {
            past = conn.prepareStatement(sql);
            past.setString(1, openid);
            past.setString(2, String_Tool.DataBaseYear_Month_Day());
            rs = past.executeQuery();
            if (rs.next()) {
                out.print("{\"resultStatus\":\"" + 2 + "\"," + "\"data\":\"" + 2 + "\"}");
            } else {
                PreparedStatement past1 = conn.prepareStatement("INSERT INTO Wei_Xin_Vip_Check_In (cWeixinID,dDate,dDatetime)values(?,?,?)");
                past1.setString(1, openid);
                past1.setString(2, String_Tool.DataBaseYear_Month_Day());
                past1.setString(3, String_Tool.DataBaseTime());
                int a = past1.executeUpdate();
                DB.closePreparedStatement(past1);

                String cVipno="";
                PreparedStatement past_s=conn2.prepareStatement("select cVipno from t_Vip  where cWeixinID=? ");
                past_s.setString(1, openid);
                ResultSet rs1=past_s.executeQuery();
                if(rs1.next()){
                    cVipno=rs1.getString("cVipno");
                }
                DB.closeResultSet(rs1);
                DB.closePreparedStatement(past_s);

                String sql1 = "update t_vip set fcurvalue=fcurvalue+?,fCurValue_Pos=fCurValue_Pos+? where cVipno=?";
                String scoreStr = new ReadConfig().getProp("/conf.properties").getProperty("SIGN_GIFT_POINTS");
                int score = Integer.parseInt(scoreStr);

                PreparedStatement past_integral=conn2.prepareStatement(sql1);
                past_integral.setBigDecimal(1, new BigDecimal(score));
                past_integral.setBigDecimal(2, new BigDecimal(score));
                past_integral.setString(3, cVipno);
                past_integral.executeUpdate();
                DB.closePreparedStatement(past_integral);
                out.print("{\"resultStatus\":\"" + a + "\"," + "\"data\":\"" + a + "\"}");
            }
        } catch (SQLException e) {
            out.print("{\"resultStatus\":\"" + -1 + "\"," + "\"data\":\"" + -1 + "\"}");
            e.printStackTrace();
        } finally {
            DB.closeRs_Con(rs, past, conn);
            DB.closeConn(conn2);
        }
        out.flush();
        out.close();
    }

}
