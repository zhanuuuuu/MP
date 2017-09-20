package tool.utils;

import org.apache.log4j.Logger;

import java.io.*;

/**
 * Created by xwy_brh on 2017/9/14.
 */
public class FileUtil {

    static Logger logger = Logger.getLogger(FileUtil.class);

    public static void main(String[] args) {
        String str = readJSONFromResource("create_vip_card.json");
        System.out.println(str);
    }

    public static String getFilePathByName(String fileName) {
        try {
            String filePath = FileUtil.class.getClassLoader().getResource(fileName).getPath();
            return filePath;
        } catch (Exception e) {
            logger.error(fileName + "is not exist in the resources folder");
        }
        return null;
    }

    public static String readJSONFromResource(String fileName) {
        return readFileFromResource(fileName).replace(" ", "");
    }


    public static String readFileFromResource(String fileName) {
        String resultStr = "";
        String path = getFilePathByName(fileName);
        if (path == null) {
            return null;
        }
        File file = new File(path);
        BufferedReader reader = null;
        try {
            FileInputStream in = new FileInputStream(file);
            reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
            String tempString = null;
            while ((tempString = reader.readLine()) != null) {
                resultStr = resultStr + tempString;
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException el) {
                    el.printStackTrace();
                }
            }
        }
        return resultStr;
    }





}
