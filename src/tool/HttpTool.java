package tool;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpTool {

    public static String GET(String url1, String data) {
        try {
            URL url = new URL(url1);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("GET");
            OutputStream out = conn.getOutputStream();
            out.write(data.getBytes("UTF-8"));
            InputStream in = conn.getInputStream();
            ByteArrayOutputStream byyut = new ByteArrayOutputStream();
            int len = 0;
            byte b[] = new byte[1024];
            while ((len = in.read(b)) != -1) {
                byyut.write(b, 0, len);
            }
            String strName = new String(byyut.toByteArray(), "UTF-8");
            return strName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String GET(String url1) {
        try {
            URL url = new URL(url1);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("GET");
            InputStream in = conn.getInputStream();
            ByteArrayOutputStream byyut = new ByteArrayOutputStream();
            int len = 0;
            byte b[] = new byte[1024];
            while ((len = in.read(b)) != -1) {
                byyut.write(b, 0, len);
            }
            String strName = new String(byyut.toByteArray(), "UTF-8");
            return strName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String POST(String url1, String data) {
        try {
            URL url = new URL(url1);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("GET");
            OutputStream out = conn.getOutputStream();
            out.write(data.getBytes("UTF-8"));
            InputStream in = conn.getInputStream();
            ByteArrayOutputStream byyut = new ByteArrayOutputStream();
            int len = 0;
            byte b[] = new byte[1024];
            while ((len = in.read(b)) != -1) {
                byyut.write(b, 0, len);
            }
            String strName = new String(byyut.toByteArray(), "UTF-8");
            return strName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
