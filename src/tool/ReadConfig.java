package tool;

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by xwy_brh on 2017/7/14.
 */

public class ReadConfig {
    //构造方法
    public ReadConfig() {

    }


    /**
     * 获取布尔型属性（可指定默认值）
     */
    public static boolean getBoolean(Properties props, String key, boolean defaultValue) {
        boolean value = defaultValue;
        if (props.containsKey(key)) {
            return Boolean.parseBoolean(props.getProperty(key));
        }
        return value;
    }


    public Properties getProp(String name) {
        Properties prop = new Properties();
        InputStream in = this.getClass().getResourceAsStream(name);
        try {
            prop.load(in);
            in.close();
            return prop;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static void main(String arg[]) {

        try {
            Properties pro = new Properties();
            FileWriter fw = new FileWriter("/version.properties");
            pro.store(fw, "key-value"); //key-value：对属性文件的描述
            fw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
