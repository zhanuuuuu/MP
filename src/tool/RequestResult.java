package tool;

import com.google.gson.Gson;
import com.sun.org.apache.regexp.internal.RE;

/**
 * Created by xwy_brh on 2017/9/12.
 */
public class RequestResult {

    private boolean success;

    private int code;

    private String message;

    private String redirectURL;

    public RequestResult(boolean success, int code, String message, String redirectURL) {
        this.success = success;
        this.code = code;
        this.message = message;
        this.redirectURL = redirectURL;
    }

    public String toJSON() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public static void main(String[] args) {
        RequestResult requestResult = new RequestResult(true, 1, "注册成功", "success.jsp");
        System.out.println(requestResult.toJSON());
    }


    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getRedirectURL() {
        return redirectURL;
    }

    public void setRedirectURL(String redirectURL) {
        this.redirectURL = redirectURL;
    }
}
