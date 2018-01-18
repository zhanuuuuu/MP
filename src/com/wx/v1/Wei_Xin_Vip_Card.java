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
import tool.String_Tool;

/**
 * 加积分
 */
@WebServlet(description = "register new vip", urlPatterns = { "/Wei_Xin_Vip_Card" })
public class Wei_Xin_Vip_Card extends HttpServlet {
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

        String sql = "";
        try {
            if (action != null && action.equals("0")) {

                PreparedStatement pasts = conn.prepareStatement("select * from t_vip where cVipno=? or cWeixinId=? ");
                pasts.setString(1, cTel);
                pasts.setString(2, openid);
                ResultSet rs = pasts.executeQuery();
                if (rs.next()) {
                    DB.closeResultSet(rs);
                    DB.closePreparedStatement(pasts);
                    response.sendRedirect("yizhuce.jsp");//
                    return;
                }
                PreparedStatement pasts1 = conn.prepareStatement("select * from t_vip where cWeixinId=? ");
                pasts1.setString(1, openid);
                ResultSet rs1 = pasts1.executeQuery();
                if (rs.next()) {
                    DB.closeResultSet(rs1);
                    DB.closePreparedStatement(pasts1);
                    response.sendRedirect("yizhuce.jsp");//
                    return;
                } else {
                    sql = "insert into t_vip (cVipno,cVipName,cTel,cWorkUnit,cardtype,cWeixinId,dValidStart,dValidEnd) values(?,?,?,?,?,?,?,?)";
                    past = conn.prepareStatement(sql);
                    past.setString(1, cVipno);
                    past.setString(2, cVipName);
                    past.setString(3, cTel);
                    past.setString(4, cIdCard);
                    past.setString(5, cardtype);
                    past.setString(6, openid);
                    past.setString(7, String_Tool.DataBaseTime());

                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(new Date());
                    calendar.add(Calendar.YEAR, 2);
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                    String dateString = formatter.format(calendar.getTime());
                    past.setString(8, dateString);
                    DB.closeResultSet(rs);
                    DB.closePreparedStatement(pasts);

                }
            } else if (action != null && action.equals("1")) {// �ϻ�Ա�󶨻�Ա��
                cVipno = request.getParameter("cVipno");
                sql = " update t_vip set cVipName=? ,cWorkUnit=? ,cWeixinId=?,cVipno=?  where cVipno=? or cTel=? ";
                past = conn.prepareStatement(sql);
                past.setString(1, cVipName);
                past.setString(2, cIdCard);
                past.setString(3, openid);
                past.setString(4, cVipno);
                past.setString(5, cVipno);
                past.setString(6, cTel);
            }
            int a = past.executeUpdate();
            if (a >= 1) {
                if (action.equals("0")) {
                    String sql1 = "update t_vip set fcurvalue=fcurvalue+?,fCurValue_Pos=fCurValue_Pos+? where cWeixinId=?";// 50
                    PreparedStatement past_integral = conn.prepareStatement(sql1);
                    past_integral.setBigDecimal(1, new BigDecimal(50));
                    past_integral.setBigDecimal(2, new BigDecimal(50));
                    past_integral.setString(3, openid);
                    int b = past_integral.executeUpdate();
                    System.out.println("affect_row_count" + b);
                    DB.closePreparedStatement(past_integral);
                    System.out.println("cVipno" + cVipno);
                    System.out.println("openid" + openid);
                    response.sendRedirect("RegSuccess.jsp");
                } else {
                    response.sendRedirect("BindSuccess.jsp");
                }
            } else {
                if (action.equals("0")) {
                    response.sendRedirect("Regfail.jsp");
                } else {
                    response.sendRedirect("Bindfail.jsp");
                }
                out.print("{\"resultStatus\":\"0\"}");
            }
        } catch (SQLException e) {
            response.sendRedirect("Error.jsp");
            e.printStackTrace();
        } finally {
            DB.closePreparedStatement(past);
            DB.closeConn(conn);
        }
        out.flush();
        out.close();
    }
}
