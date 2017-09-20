package tool.utils;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


import com.google.gson.Gson;
import org.apache.log4j.Logger;
import org.json.JSONArray;

import org.json.JSONException;
import org.json.JSONObject;
import tool.String_Tool;

/**
 * Created by xwy_brh on 2017/7/15.
 */
public class DataProManager {

    static Logger logger = Logger.getLogger(DataProManager.class);

    public static String resultSetToJson(ResultSet rs) {
        JSONArray array = new JSONArray();
        try {
            // 获取列数
            ResultSetMetaData metaData;
            metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();

            // 遍历ResultSet中的每条数据
            while (rs.next()) {
                JSONObject jsonObj = new JSONObject();
                // 遍历每一列
                for (int i = 1; i <= columnCount; i++) { // 列数
                    String columnName = metaData.getColumnLabel(i);
                    String value = rs.getString(columnName);
                    if(String_Tool.isEmpty(value)){
                        value="";
                    }
                    try {
                        jsonObj.put(columnName, value);
                    } catch (JSONException e) {
                        logger.error("把结果集放到json中出现异常");
                        e.printStackTrace();
                    }
                }
                array.put(jsonObj);
            }
        } catch (SQLException e) {
            logger.error("把结果集异常"+e.getLocalizedMessage());
            e.printStackTrace();
        }
        return array.toString().replace(" ", "");
    }

    public static JSONArray resultSetToJsonArray(ResultSet rs) {
        JSONArray array = new JSONArray();
        try {
            // 获取列数
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();

            // 遍历ResultSet中的每条数据
            while (rs.next()) {
                JSONObject jsonObj = new JSONObject();
                // 遍历每一列
                for (int i = 1; i <= columnCount; i++) { // 列数
                    String columnName = metaData.getColumnLabel(i);
                    String value = rs.getString(columnName);
                    if(String_Tool.isEmpty(value)){
                        value="0";
                    }
                    jsonObj.put(columnName, value);
                }
                array.put(jsonObj);
            }
        } catch (Exception e) {
            logger.error(e.getLocalizedMessage());
        }
        return array;
    }
    public String Map_To_JSON(List<Map<String,String>> list){
        Gson gson=new Gson();
        String str=gson.toJson(list);
        return str;
    }

    /*
    * originRS  要插入的JSONArray
    * key       字段(key)
    * desRs     最终要返回的结果
    * */
    public static JSONArray Insert(ResultSet originRs, String key, ResultSet desRs) {

        JSONArray array = new JSONArray();
        try {
            // 获取列数
            ResultSetMetaData metaData = desRs.getMetaData();
            int columnCount = metaData.getColumnCount();

            while (desRs.next()) {
                JSONObject o = new JSONObject();
                for (int i = 1; i <= columnCount; i++) {
                    String columnName = metaData.getColumnLabel(i);
                    String value = desRs.getString(columnName);
                    if (String_Tool.isEmpty(value)) {
                        value = "";
                    }
                    o.put(columnName, value);
                }
                o.put(key, resultSetToJsonArray(originRs));
                array.put(o);
            }
        } catch (Exception e) {
            logger.error(e.getLocalizedMessage());
            System.out.println("将一个结果集添加到另一个结果集错误");
        }
        return  array;
    }

    public static  JSONArray InsertWithMaps(HashMap<String, ResultSet> map, ResultSet desRs) {
        JSONArray desArr = new JSONArray();
        try {
            // 获取列数
            ResultSetMetaData metaData = desRs.getMetaData();
            int columnCount = metaData.getColumnCount();

            while (desRs.next()) {
                JSONObject o = new JSONObject();
                for (int i = 1; i <= columnCount; i++) {
                    String columnName;
                    columnName = metaData.getColumnLabel(i);
                    String value = desRs.getString(columnName);
                    if (String_Tool.isEmpty(value)) {
                        value = "";
                    }
                    o.put(columnName, value);
                }
                Iterator iterator = map.entrySet().iterator();
                while (iterator.hasNext()) {
                    Map.Entry entry = (Map.Entry) iterator.next();
                    String key = (String) entry.getKey();
                    ResultSet originRs = (ResultSet) entry.getValue();
                    o.put(key, resultSetToJsonArray(originRs));
                }
                desArr.put(o);
            }
        } catch (Exception e) {
            logger.error(e.getLocalizedMessage());
            System.out.println("使用字典的方式将一个结果集添加到另一个结果集错误");
        }
        return desArr;
    }

}

