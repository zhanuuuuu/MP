package com.wx.v1;

public class ConfigBean {

    private String appId;
    private String url;
    private String jsapi_ticket;
    private String nonceStr;
    private long timestamp;
    private String signature;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getJsapi_ticket() {
        return jsapi_ticket;
    }

    public void setJsapi_ticket(String jsapi_ticket) {
        this.jsapi_ticket = jsapi_ticket;
    }

    public String getNonceStr() {
        return nonceStr;
    }

    public void setNonceStr(String nonceStr) {
        this.nonceStr = nonceStr;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }


    public ConfigBean() {
        super();
    }

    public ConfigBean(String appId, String url, String jsapi_ticket, String nonceStr, long timestamp, String signature) {
        this.appId = appId;
        this.url = url;
        this.jsapi_ticket = jsapi_ticket;
        this.nonceStr = nonceStr;
        this.timestamp = timestamp;
        this.signature = signature;
    }


}
