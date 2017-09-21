<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>

    <title>扫码积分</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=no, minimal-ui">
    <meta name="screen-orientation" content="portrait"/>
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="format-detection" content="telephone=no">
    <meta name="full-screen" content="yes">
    <meta name="x5-fullscreen" content="true">
    <script type="application/javascript" src="../common/fastclick.js"></script>
    <script type="text/javascript" src="../external_js/jquery-3.2.1.min.js"></script>
    <script type="text/javascript" src="../external_js/jweixin-1.0.0.js"></script>
    <%--<link rel="stylesheet" type="text/css" href="juhua.css" />--%>
    <script type="text/javascript" src="../common/common.js"></script>
    <script type="text/javascript">
        $(function () {
            $.ajax({
                type: "get",
                url: "../../GetWX_Get_Config",
                data: {"openid": "<%=request.getParameter("openid")%>"},
                success: function (data) {
                    let o = JSON.parse(data);
                    wx.config({
                        debug: false,
                        appId: o.appId,
                        timestamp: o.timestamp,
                        nonceStr: o.nonceStr,
                        signature: o.signature,
                        jsApiList: ["checkJsApi", "scanQRCode"]
                    });
                    wx.error(function (res) {
                        alert("扫码出错:" + res.errMsg);
                    });
                    wx.ready(function () {
                        wx.checkJsApi({
                            jsApiList: ['scanQRCode'],
                            success: function (res) {
                                console.log(res);
                            }
                        });
                        wx.scanQRCode({
                            needResult: 1,
                            scanType: ["qrCode", "barCode"],
                            success: function (res) {
                                var qrcodeInfo = res.resultStr;
                                var o = JSON.parse(qrcodeInfo);
                                o["openid"] = "<%=request.getParameter("openid")%>";
                                $.ajax({
                                    type: "get",
                                    url: "../../Wei_Xin_Automatic_integration",
                                    data: o,
                                    success: function (scanRes) {
                                        var scanO = JSON.parse(scanRes);
                                        if (scanO.resultStatus == "1") {
                                            if (confirm("扫码积分成功")) {
                                                window.history.back();  //返回上一页
                                            }
                                            else {
                                                window.history.back();  //返回上一页
                                            }
                                        } else if (scanO.resultStatus == "2") {
                                            alert("不能积分,已经积分了");
                                            window.history.back();  //返回上一页
                                        } else if (scanO.resultStatus == "3") {
                                            alert("没有此单号");
                                        } else if (scanO.resultStatus == "4") {
                                            alert("此微信号没有绑定会员卡");
                                        } else {
                                            alert("服务器繁忙");
                                        }
                                    }
                                });
                            },
                        });
                    });
                }
            })
        });
    </script>
</head>
<body>

</body>
</html>
