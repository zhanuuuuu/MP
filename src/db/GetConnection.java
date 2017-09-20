package db;


/**
 * Created by xwy_brh on 2017/7/14.
 */


import java.sql.Connection;
import org.apache.log4j.Logger;
import tool.ReadConfig;


public class GetConnection {

    static  Logger logger = Logger.getLogger(GetConnection.class);

    public  static Connection getConn() {
        String IP = new ReadConfig().getProp("/db.properties").getProperty("DataBase_IP_Port");
        String DataBase_Name = new ReadConfig().getProp("/db.properties").getProperty("DataBaseName");
        try {
            Connection conn = DB.getConnection(IP, DataBase_Name);
            return conn;
        } catch (Exception e) {
            logger.error("-----------数据库连接异常----------");
            e.printStackTrace();
        }
        return null;
    }

    public static Connection getPosmanagement_mainStoreConn() {
        try {
            Connection conn = DB.getConnection(new ReadConfig().getProp("/db.properties").getProperty("Posmanagement_main_IP"),new ReadConfig().getProp("/db.properties").getProperty("Posmanagement_main_DataSource"));
            return conn;
        } catch (Exception e) {
            logger.error("-----------数据库连接异常----------");
            e.printStackTrace();
        }
        return null;
    }

    public static Connection getSuperMarket_Conn() {
        try {
            Connection conn = DB.getConnection(new ReadConfig().getProp("/db.properties").getProperty("SuperMarket_IP"),new ReadConfig().getProp("/db.properties").getProperty("SuperMarket_DataSource"));
            return conn;
        } catch (Exception e) {
            logger.error("-----------数据库连接异常----------");
            e.printStackTrace();
        }
        return null;
    }


}
