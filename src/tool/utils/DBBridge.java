package tool.utils;


import db.DB;
import org.json.JSONArray;

import javax.xml.crypto.Data;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;

/**
 * Created by xwy_brh on 2017/7/15.
 * 主要用来和数据库进行交互的类, 各个类方法的主要返回值
 * <1>JSONArray</1>
 * <2>JSON</2>
 * <3>JSON String</3>
 */
public class DBBridge {



    public static JSONArray Select_Goods_Info(Connection conn, String cGoodsNo) {

        ResultSet rs = null;
        PreparedStatement past = null;
        ResultSet rs2 = null;
        PreparedStatement past2 = null;
        try {
            String sql = "SELECT cGoodsName, cGoodsNo FROM T_GOODS WHERE cGoodsNo = ?";
            past = conn.prepareStatement(sql);
            past.setString(1, cGoodsNo);
            rs = past.executeQuery();

            String sql2 = "SELECT  * FROM t_Price WHERE cGoodsNo = ?";
            past2 = conn.prepareStatement(sql2);
            past2.setString(1, cGoodsNo);
            rs2 = past2.executeQuery();

            PrintUtils.print(sql);
            return DataProManager.Insert(rs2, "pf_Prices", rs);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            DB.closeResultSet(rs);
            DB.closePreparedStatement(past);
            DB.closeResultSet(rs2);
            DB.closePreparedStatement(past2);
            DB.closeConn(conn);
        }
    }

}
