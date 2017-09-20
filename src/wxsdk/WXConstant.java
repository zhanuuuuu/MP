package wxsdk;

/**
 * Created by xwy_brh on 2017/9/12.
 */
public class WXConstant {

    /**
     * REGISTER --- 注册
     * BIND -- 绑定
     * SIGN -- 签到
     * PROFILE -- 个人中心
     * SCAN -- 扫码积分
     */
    public enum Function {
        REGISTER("register.jsp", "注册", 1),
        BIND("bind.jsp", "绑定", 2),
        SIGN("sign.jsp", "签到", 3),
        SCAN("scan.jsp", "扫码积分", 4),
        PROFILE("profile.jsp", "个人中心", 5),
        UNKNOWN("404.jsp", "404页面", 404);

        public String uri;
        public String about;
        public int code;

        Function(String uri, String about, int code) {
            this.uri = uri;
            this.about = about;
            this.code = code;
        }

        public static Function getFunctionWithCode(int code) {
            for (Function f :
                    Function.values()) {
                if (f.code == code) {
                    return f;
                }
            }
            return UNKNOWN;
        }

        public static void main(String[] args) {
            Function f = Function.getFunctionWithCode(5);
            System.out.println(f);
        }

    }

    public static final String CODE_DOMAIN_URL = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=REDIRECT_URI&response_type=code&scope=SCOPE&state=STATE#wechat_redirect";

    /**
     * scope:应用授权作用域
     * 　snsapi_base:不弹出授权页面，直接跳转，只能获取用户openid
     * 　snsapi_userinfo:弹出授权页面，可通过openid拿到昵称、性别、所在地
     */

    public static final String SNS_AUTH_TYPE = "snsapi_base";


}
