package com.wx.v1;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import db.DB;
import db.GetConnection;
import tool.String_Tool;

/**
 *  加积分
 */
@WebServlet(description = "微信扫码积分", urlPatterns = {"/Wei_Xin_Automatic_integration"})
public class Wei_Xin_Automatic_integration extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        response.setContentType("text/html;charset=UTF-8");
        response.setHeader("content-type", "text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        Connection conn = null;
        Connection connp = null;
        PreparedStatement past = null;
        String DATE = request.getParameter("DATE");
        String NO = request.getParameter("NO");
        String VIP = request.getParameter("VIP");
        String SCORE = request.getParameter("SCORE");
        String cWeixinId = request.getParameter("openid");
        System.out.println("日期" + DATE);
        System.out.println("单号" + NO);
        System.out.println("日期" + DATE);
        System.out.println("积分" + SCORE);
        System.out.println("扫码积分会员卡id" + cWeixinId);
        System.out.println("扫码积分" + VIP);
        String sql = "";
        try {
            connp = GetConnection.getSuperMarket_Conn();
            conn = GetConnection.getPosmanagement_mainStoreConn();
            conn.setAutoCommit(false);
            connp.setAutoCommit(false);
            past = conn.prepareStatement("select cVipno from  t_Vip where cWeixinID=? ");
            past.setString(1, cWeixinId);
            ResultSet rs = past.executeQuery();
            if (rs.next()) {
                String ccccVipno = rs.getString("cVipno");
                sql = "select * from lsd where substring(lsdNo,0,charindex('-',lsdNo)) = ?  and Zdriqi=? ";
                PreparedStatement pasts = connp.prepareStatement(sql);
                pasts.setString(1, NO);
                pasts.setString(2, DATE);
                ResultSet rss = pasts.executeQuery();
                if (rss.next()) {
                    String vipno = rss.getString("vipno");
                    if (String_Tool.isEmpty(vipno)) {

                        String sql1 = "update t_vip set fcurvalue=fcurvalue+?,fCurValue_Pos=fCurValue_Pos+? where cVipNo=?";
                        PreparedStatement pastp = conn.prepareStatement(sql1);
                        pastp.setBigDecimal(1, new BigDecimal(SCORE));
                        pastp.setBigDecimal(2, new BigDecimal(SCORE));
                        pastp.setString(3, ccccVipno);
                        pastp.executeUpdate();
                        DB.closePreparedStatement(pastp);

                        PreparedStatement past1 = connp.prepareStatement("UPDATE lsd set vipno=?  where substring(lsdNo,0,charindex('-',lsdNo)) = ? and Zdriqi=? ");
                        past1.setString(1, ccccVipno);
                        past1.setString(2, NO);
                        past1.setString(3, DATE);
                        int a = past1.executeUpdate();
                        if (a >= 1) {
                            conn.commit();
                            conn.setAutoCommit(true);
                            connp.commit();
                            connp.setAutoCommit(true);
                        } else {
                            conn.rollback();
                            connp.rollback();
                        }
                        DB.closePreparedStatement(past1);
                        DB.closeConn(connp);
                        out.print("{\"resultStatus\":\"" + 1 + "\"," + "\"data\":\"" + "1" + "\"}");
                        System.out.println("{\"resultStatus\":\"" + 1 + "\"," + "\"data\":\"" + "1" + "\"}");
                    } else {
                        System.out.println("{\"resultStatus\":\"" + 2 + "\"," + "\"data\":\"" + "不能积分,已经积分了" + "\"}");
                        out.print("{\"resultStatus\":\"" + 2 + "\"," + "\"data\":\"" + "不能积分,已经积分了" + "\"}");
                    }
                } else {
                    out.print("{\"resultStatus\":\"" + 3 + "\"," + "\"data\":\"" + "没有此单号" + "\"}");
                }
                DB.closeResultSet(rss);
                DB.closePreparedStatement(pasts);
                DB.closeConn(conn);
            } else {
                conn.commit();
                conn.setAutoCommit(true);
                connp.commit();
                connp.setAutoCommit(true);
                out.print("{\"resultStatus\":\"" + 4 + "\"," + "\"data\":\"" + "此微信号没有绑定会员卡" + "\"}");
            }
        } catch (SQLException e) {
            out.print("{\"resultStatus\":\"" + -1 + "\"," + "\"data\":" + -1 + "}");
            e.printStackTrace();
        } finally {
            DB.closePreparedStatement(past);
            DB.closeConn(conn);
        }
        out.flush();
        out.close();
    }
}
