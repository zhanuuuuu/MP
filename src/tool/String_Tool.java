package tool;

/**
 * Created by xwy_brh on 2017/7/15.
 */
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class String_Tool {

    public static void main(String args[]) {

    }

        public static double add(double value1,double value2){
            BigDecimal b1 = new BigDecimal(Double.valueOf(value1));
            BigDecimal b2 = new BigDecimal(Double.valueOf(value2));
            return b1.add(b2).doubleValue();
        }


        public static double sub(double value1,double value2){
            BigDecimal b1 = new BigDecimal(Double.valueOf(value1));
            BigDecimal b2 = new BigDecimal(Double.valueOf(value2));
            return b1.subtract(b2).doubleValue();
        }


        public static double mul(double value1,double value2){
            BigDecimal b1 = new BigDecimal(Double.valueOf(value1));
            BigDecimal b2 = new BigDecimal(Double.valueOf(value2));
            return b1.multiply(b2).doubleValue();
        }


        public static double div(double value1,double value2,int scale) throws IllegalAccessException{

            if(scale<0){
                throw new IllegalAccessException("not illegal");
            }
            BigDecimal b1 = new BigDecimal(Double.valueOf(value1));
            BigDecimal b2 = new BigDecimal(Double.valueOf(value2));
            return b1.divide(b2, scale).doubleValue();
        }


        public static String String_IS_Four(double d) {
            DecimalFormat df = new DecimalFormat("0.0000");
            return df.format(d);
        }

        public static String String_IS_Four(int d) {
            DecimalFormat df = new DecimalFormat("0000");
            return df.format(d);
        }

        public static String DataBaseTime() {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
                    "yyyy-MM-dd HH:mm:ss");
            String time = simpleDateFormat.format(new Date());
            return time;
        }
        public static String DataBaseTime_MM() {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
                    "yyyyMMddHHmm");
            String time = simpleDateFormat.format(new Date());
            return time;
        }

        public static String DataBaseYear() {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy");
            String time = simpleDateFormat.format(new Date());
            return time;
        }

        public static String DataBaseYear_Month_Day() {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String time = simpleDateFormat.format(new Date());
            return time;
        }

        public static String DataBaseH_M_S() {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
            String time = simpleDateFormat.format(new Date());
            return time;
        }

        public static String DataBaseH_M_S_String() {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HHmmss");
            String time = simpleDateFormat.format(new Date());
            return time;
        }

        public static String DataBaseM() {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM");
            String time = simpleDateFormat.format(new Date());
            return time;
        }

        public static String reformat() {
            Date today = new Date();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddhhmmss");
            try {
                String dateString = formatter.format(today);
                return dateString;

            } catch (IllegalArgumentException iae) {

            }
            return null;
        }
        public static String qu(String b){
            String a[]={"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};
            ArrayList<String> list=new ArrayList<String>();
            for(int i=0;i<a.length;i++){
                for(int j=0;j<a.length;j++){
                    //System.out.println(a[i]+a[j]);
                    list.add(a[i]+a[j]);
                }
            }
            return list.get(list.indexOf(b)+1);
        }

        public static boolean isEmpty(String str) {
            if (str == null) {
                return true;
            } else if (str.equals("null")) {
                return true;
            } else if (str.equals("NULL")) {
                return true;
            } else if (str.equals("")) {
                return true;
            } else if (str.equals("''")) {
                return true;
            } else {
                return false;
            }
        }


    public static String getRidOfSpace(String str) {
        if (String_Tool.isEmpty(str)) {
            return null;
        } else {
            return str.replace(" ", "");
        }
    }
}
