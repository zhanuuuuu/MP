package tool.utils;

import tool.ReadConfig;

/**
 * Created by xwy_brh on 2017/7/15.
 */
public class PrintUtils {

    public static void print(String str) {

        boolean isDebug = ReadConfig.getBoolean(new ReadConfig().getProp("/conf.properties"), "DEBUG", false);

        if (isDebug) {
            System.out.println(str);
        }
    }

    public static void main(String args[]) {
        PrintUtils.print("hello, world");
    }
}
