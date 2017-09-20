package db;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.dbcp2.BasicDataSourceFactory;

import tool.ReadConfig;

public class DB {

    private static Map<String, DataSource> map = new HashMap<String, DataSource>();

    public static void init(String IP, String DataSourceName) {

        Properties p = new Properties();
        p.setProperty("driverClassName", "com.microsoft.sqlserver.jdbc.SQLServerDriver");
        p.setProperty("url", "jdbc:sqlserver://" + IP + ";databaseName=" + DataSourceName);
        String username = new ReadConfig().getProp("/db.properties").getProperty("USERNAME");
        String password = new ReadConfig().getProp("/db.properties").getProperty("PASSWORD");
        p.setProperty("username", username);
        p.setProperty("password", password);
        p.setProperty("maxActive", "30");
        p.setProperty("maxIdle", "100");
        p.setProperty("maxWait", "1000");
        p.setProperty("removeAbandoned", "true");
        p.setProperty("removeAbandonedTimeout", "300");
        p.setProperty("testOnBorrow", "true");
        p.setProperty("logAbandoner", "true");
        try {
            BasicDataSource dataSource = BasicDataSourceFactory.createDataSource(p);
            map.put(DataSourceName, dataSource);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void init(String IP, String DataSourceName, String PassWord) {
        Properties p = new Properties();
        p.setProperty("driverClassName", "com.microsoft.sqlserver.jdbc.SQLServerDriver");
        p.setProperty("url", "jdbc:sqlserver://" + IP + ";databaseName=" + DataSourceName);
        p.setProperty("username", "sa");
        p.setProperty("password", PassWord);
        p.setProperty("maxActive", "30");
        p.setProperty("maxIdle", "100");
        p.setProperty("maxWait", "1000");
        p.setProperty("removeAbandoned", "true");
        p.setProperty("removeAbandonedTimeout", "300");
        p.setProperty("testOnBorrow", "true");
        p.setProperty("logAbandoner", "true");
        try {
            BasicDataSource dataSource = BasicDataSourceFactory.createDataSource(p);
            map.put(DataSourceName, dataSource);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection(String IP, String DataSourceName) throws SQLException {
        BasicDataSource dataSource = (BasicDataSource) map.get(DataSourceName);
        if (dataSource == null) {
            init(IP, DataSourceName);
            System.out.println("-----new connection-----" + DataSourceName);
            dataSource = (BasicDataSource) map.get(DataSourceName);
        }
        return dataSource.getConnection();
    }

    public static Connection getConnection(String IP, String DataSourceName, String PassWord) throws SQLException {
        BasicDataSource dataSource = (BasicDataSource) map.get(DataSourceName);
        if (dataSource == null) {
            init(IP, DataSourceName, PassWord);
            System.out.println("-----new connection-----" + DataSourceName);
            dataSource = (BasicDataSource) map.get(DataSourceName);
        }
        return dataSource.getConnection();
    }

    public static void closeResultSet(ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void closeCallState(CallableStatement c) {
        try {
            if (c != null) {
                c.close();
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void closeConn(Connection conn) {
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void closePreparedStatement(PreparedStatement pstmt) {
        if (pstmt != null) {
            try {
                pstmt.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    public static void closeRs_Con(CallableStatement c, Connection conn) {
        closeCallState(c);
        closeConn(conn);
    }

    public static void closeRs_Con(ResultSet rs, CallableStatement c,
                                   Connection conn) {
        closeResultSet(rs);
        closeRs_Con(c, conn);
    }

    public static void closeRs_Con(ResultSet rs, PreparedStatement past,
                                   Connection conn) {
        closeResultSet(rs);
        closePreparedStatement(past);
        closeConn(conn);

    }

    public static void closeAll(ResultSet rs, PreparedStatement pstmt, CallableStatement c, Connection conn) {
        closeResultSet(rs);
        closePreparedStatement(pstmt);
        closeRs_Con(c, conn);
    }

    public static void main(String args[]) {
        GetConnection.getConn();
    }
}
