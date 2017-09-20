package tool;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;

public class ResultSet_To_JSON {

    static Logger logger = Logger.getLogger(ResultSet_To_JSON.class);

    public static String resultSetToJson(ResultSet rs) {
        JSONArray array = new JSONArray();
        try {
            ResultSetMetaData metaData;
            metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();


            while (rs.next()) {
                JSONObject jsonObj = new JSONObject();

                for (int i = 1; i <= columnCount; i++) { // ����
                    String columnName = metaData.getColumnLabel(i);
                    String value = rs.getString(columnName);
                    if (String_Tool.isEmpty(value)) {
                        value = "";
                    }
                    try {
                        jsonObj.put(columnName, value);
                    } catch (JSONException e) {
                        logger.error("JSON is not correct format");
                        e.printStackTrace();
                    }
                }
                array.put(jsonObj);
            }
        } catch (SQLException e) {
            logger.error("exception:" + e.getLocalizedMessage());
            e.printStackTrace();
        }
        return array.toString().replace(" ", "");
    }


    public static JSONArray resultSetToJsonArray(ResultSet rs) {
        JSONArray array = new JSONArray();
        try {

            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();
            while (rs.next()) {
                JSONObject jsonObj = new JSONObject();

                for (int k = 1; k <= columnCount; k++) {
                    String column_name = metaData.getColumnLabel(k);
                    String value = rs.getString(column_name);
                    if (String_Tool.isEmpty(value)) {
                        value = "";
                    }
                    jsonObj.put(column_name, value);
                }
                array.put(jsonObj);
            }
        } catch (Exception e) {
            logger.error(e.getLocalizedMessage());
        }
        return array;
    }


    public String Map_To_JSON(List<Map<String, String>> list) {
        Gson gson = new Gson();
        String str = gson.toJson(list);
        return str;
    }

}
