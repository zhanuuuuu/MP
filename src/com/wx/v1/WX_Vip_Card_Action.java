package com.wx.v1;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import db.DB;
import db.GetConnection;
import tool.ReadConfig;
import tool.RequestResult;
import tool.String_Tool;

@WebServlet(description = "绑定送积分", urlPatterns = {"/WX_Vip_Card_Action"})
public class WX_Vip_Card_Action extends HttpServlet {
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

        Connection conn = GetConnection.getPosmanagement_mainStoreConn();
        PreparedStatement past = null;
        String cVipno = "";
        String cVipName = request.getParameter("cVipName");
        String cTel = request.getParameter("cTel");
        cVipno = cTel;

        String cIdCard = request.getParameter("cIdCard");
        String openid = request.getParameter("openid"); // openid //cWeixinId
        String action = request.getParameter("action");
        String cardtype = request.getParameter("cardtype");

        System.out.println(cVipno);
        System.out.println(cVipName);
        System.out.println(cTel);
        System.out.println(cIdCard);
        System.out.println("openid=" + openid);
        System.out.println("action=" + action);
        System.out.println(cardtype);
        PrintWriter out = response.getWriter();
        /**
         * 说明: 0 -- 已经注册/绑定过
         *      1 -- 成功注册/绑定
         *      2 -- 失败
         *      3 -- 数据库异常
         */
        String sql = "";
        try {
            if (action != null && action.equals("0")) {

                PreparedStatement pasts = conn.prepareStatement("select * from t_vip where cVipno=? or cWeixinId=?");
                pasts.setString(1, cTel);
                pasts.setString(2, openid);
                ResultSet rs = pasts.executeQuery();
                if (rs.next()) {
                    DB.closeResultSet(rs);
                    DB.closePreparedStatement(pasts);
                    //response.sendRedirect("yizhuce.jsp");
                    RequestResult requestResult = new RequestResult(false, 0, "用户已注册", "alreadyRegistered.jsp");
                    out.print(requestResult.toJSON());
                    return;
                }

                Calendar calendar = Calendar.getInstance();
                calendar.setTime(new Date());
                calendar.add(Calendar.YEAR, 2);
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                String dateString = formatter.format(calendar.getTime());
                sql = "insert into t_vip (cVipno,cVipName,cTel,cWorkUnit,cardtype,cWeixinId,dValidStart,dValidEnd,card_code) values(?,?,?,?,?,?,?,?,?)";
                past = conn.prepareStatement(sql);
                past.setString(1, cVipno);
                past.setString(2, cVipName);
                past.setString(3, cTel);
                past.setString(4, cIdCard);
                past.setString(5, cardtype);
                past.setString(6, openid);
                past.setString(7, String_Tool.DataBaseTime());
                past.setString(8, dateString);
                past.setString(9, "" + 1001);
                /**
                 * card_code没有值的话, 代表该卡为线下卡
                 * 1001 代表 该卡为微信电子卡
                 * 1001 -- 积分卡,
                 * 1002 -- 金卡,
                 * 1003 --白金卡
                 */
                DB.closeResultSet(rs);
                DB.closePreparedStatement(pasts);

            } else if (action != null && action.equals("1")) {
                cVipno = request.getParameter("cVipno");
                System.out.println("cVipno=" + cVipno);
                sql = " update t_vip set cVipName=? ,cWorkUnit=? ,cWeixinId=? ,fcurvalue=fcurvalue+?,fCurValue_Pos=fCurValue_Pos+? where cVipno=?";
                String scoreStr = new ReadConfig().getProp("/conf.properties").getProperty("BIND_GIFT_POINTS");
                int score = Integer.parseInt(scoreStr);

                past = conn.prepareStatement(sql);
                past.setString(1, cVipName);
                past.setString(2, cIdCard);
                past.setString(3, openid);
                past.setBigDecimal(4, new BigDecimal(score));
                past.setBigDecimal(5, new BigDecimal(score));
                past.setString(6, cVipno);
            }
            int a = past.executeUpdate();
            /**
             *  对结果的处理
             */
            if (a >= 1) {
                if (action.equals("0")) {
                    String sql1 = "update t_vip set fcurvalue=fcurvalue+?,fCurValue_Pos=fCurValue_Pos+? where cWeixinId=?";

                    String scoreStr = new ReadConfig().getProp("/conf.properties").getProperty("REGISTER_GIFT_POINTS");
                    int score = Integer.parseInt(scoreStr);

                    PreparedStatement past_integral = conn.prepareStatement(sql1);
                    past_integral.setBigDecimal(1, new BigDecimal(score));
                    past_integral.setBigDecimal(2, new BigDecimal(score));
                    past_integral.setString(3, openid);
                    int b = past_integral.executeUpdate();
                    System.out.println("affect_row_count" + b);
                    DB.closePreparedStatement(past_integral);
                    System.out.println("cVipno" + cVipno);
                    System.out.println("openid" + openid);
                    RequestResult requestResult = new RequestResult(true, 1, "恭喜您, 注册成功,获得"+scoreStr+"积分", "registerSuccess.jsp");
                    out.print(requestResult.toJSON());
                    return;
                } else {
                    String scoreStr = new ReadConfig().getProp("/conf.properties").getProperty("BIND_GIFT_POINTS");
                    RequestResult requestResult = new RequestResult(true, 1, "恭喜您, 绑定成功, 获得"+scoreStr+"积分", "bindSuccess.jsp");
                    out.print(requestResult.toJSON());
                    return;
                }
            } else {
                if (action.equals("0")) {
                    RequestResult requestResult = new RequestResult(false, 2, "很抱歉, 注册失败", "registerFail.jsp");
                    out.print(requestResult.toJSON());
                    return;
                } else {
                    RequestResult requestResult = new RequestResult(false, 2, "很抱歉, 绑定失败", "bindFail.jsp");
                    out.print(requestResult.toJSON());
                    return;
                }
            }
        } catch (SQLException e) {
            RequestResult requestResult = new RequestResult(false, 3, "数据库更新异常", "sqlException.jsp");
            out.print(requestResult.toJSON());
            e.printStackTrace();
        } finally {
            DB.closePreparedStatement(past);
            DB.closeConn(conn);
            out.flush();
            out.close();
        }
    }
}
