<%@ page import="tool.ReadConfig" %><%--
  Created by IntelliJ IDEA.
  User: xwy_brh
  Date: 2017/8/22
  Time: 上午10:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html PUBLIC "-//WAPFORUM//DTD XHTML Mobile 1.0//EN" "http://www.wapforum.org/DTD/xhtml-mobile10.dtd">
<html>
<head>
    <title>每日签到</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=no, minimal-ui">
    <meta name="screen-orientation" content="portrait"/>
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="format-detection" content="telephone=no">
    <meta name="full-screen" content="yes">
    <meta name="x5-fullscreen" content="true">
    <!-- css style start -->
    <link rel="stylesheet" type="text/css" href="../css/sign2.css"/>
    <link rel='stylesheet' type='text/css' href='../flavr/fonts/lato.css'/>
    <link rel="stylesheet" type="text/css" href="../flavr/fonts/fontawesome.css"/>
    <link rel="stylesheet" type="text/css" href="../css/vip_sign.css"/>
    <!-- css style end -->
    <script type="text/javascript" src="../external_js/jquery-1.8.1.min.js"></script>
    <script type="text/javascript" src="../external_js/calendar2.js" charset="utf-8"></script>
    <script type="text/javascript">
        $(function () {
            reSetCal();
        });
    </script>

</head>
<body class="sign_body">
<div class="calendar_head_wrapper">
    <img src="../images/sign_head_bg@3x.png" alt="" class="new_mess_c"/>
    <div class="sign_button_wrapper">
        <button id="sign_button"></button>
    </div>
</div>
<div id="calendar"></div>

<div id="sign_note">
    <span style="color:#333333;">说明：点击每日签到即可获取<%=new ReadConfig().getProp("/conf.properties").getProperty("SIGN_GIFT_POINTS")%>个积分;</span>
</div>
</body>
<script type="text/javascript" src="../js/fitImage.js"></script>
<script type="text/javascript">
    $(function () {
        $.ajax({
            type: "GET",
            url: "../../GetWX_VIP_Message",
            data: {"openid": "<%=request.getParameter("openid")%>"},
            success: function (data) {
                var o = JSON.parse(data);
                if (o.hasOwnProperty("error")) {
                    alert("您还不是会员, 去注册!");
                    window.location.href = "../../GetWX_CODE?action=1";
                    return;
                }
            }
        });
    });


    let calStyle = function () {
        var _w = parseInt($(window).width());
        $(".th_cell").each(function (i) {
            let th_cell = $(this);
            let height = _w / 7;
            $(th_cell).css("display", "table-cell");
            $(th_cell).css("height", height + "px");
            $(th_cell).css("width", height + "px");
        });

        $(".table_cell_text").each(function (i) {
            let table_cell_text = $(this);
            let height = _w / 7 * 0.8;
            $(table_cell_text).css("height", height + "px");
            $(table_cell_text).css("width", height + "px");
            $(table_cell_text).css("line-height", height + "px");
        });

    };
    window.onload = calStyle();
    let SIGNING = false;
    $("#sign_button").click(function (e) {
        if (!SIGNING) {
            SIGNING = true;
            $.ajax({
                type: "POST",
                url: "../../Wei_Xin_Vip_Check_In",
                data: {"openid": "<%=request.getParameter("openid")%>"},
                success: function (data) {
                    var o = JSON.parse(data);
                    if (o.resultStatus == "1") {
                        alert("恭喜您,签到成功!获得<%=new ReadConfig().getProp("/conf.properties").getProperty("SIGN_GIFT_POINTS")%>积分");
                        reSetCal();
                    } else if (o.resultStatus == "2") {
                        alert("今天已经签到");
                        reSetCal();
                    } else if (o.resultStatus == "-1") {
                        alert("服务器出现异常");
                    } else {
                        //失败页面
                    }
                    SIGNING = false;
                },
                fail: function () {
                    SIGNING = false;
                }
            });
            e.preventDefault();
        } else {
            return;
        }
    });

    let reSetCal;
    reSetCal = function () {
        $.ajax({
            type: "POST",
            url: "../../Select_SignDays",
            data: {"openid": "<%=request.getParameter("openid")%>"},
            success: function (data) {
                console.log(data);
                if (data == "error") {
                    alert("系统错误, 请联系广场人员.");
                } else {
                    let signList = JSON.parse(data);
                    console.log(signList);
                    calUtil.init(signList);
                    calStyle();
                }
            }
        });
    };

</script>
</html>
