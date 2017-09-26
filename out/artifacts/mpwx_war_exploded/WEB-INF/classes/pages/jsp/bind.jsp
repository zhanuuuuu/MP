<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<html>
<head>
    <title>会员绑定</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=no, minimal-ui">
    <meta name="screen-orientation" content="portrait"/>
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="format-detection" content="telephone=no">
    <meta name="full-screen" content="yes">
    <meta name="x5-fullscreen" content="true">
</head>

<script type="application/javascript" src="../common/fastclick.js"></script>
<script type="application/javascript" src="../common/common.js"></script>
<script type="text/javascript" src="../external_js/jquery-1.8.1.min.js"></script>
<script type="text/javascript" src="../common/tool.js"></script>
<link rel="stylesheet" href="../common/common.css"/>
<link rel="stylesheet" href="../css/vipRegister.css"/>
<body>

<div id="wxApp">
    <form id="vipFormID" name="vipFrom" onsubmit="return checkUser();">
        <div class="vipFormWrapper">
            <ul class="vip_list">
                <li class="vip_item">
                    <span class="vip_form_title">绑定会员卡所需信息</span>
                </li>
                <li class="vip_item">
                    <span class="vip_title">
                        <span class="vip_title_content">姓名</span>
					</span>
                    <input title="cVipName" class="vip_text" type="text" name="cVipName" id="name">
                </li>
                <li class="vip_item">
                    <span class="vip_title">
                        <span class="vip_title_content">手机号</span>
					</span>
                    <input title="cTel" class="vip_text" type="text" name="cTel" id="cTel">
                </li>
                <li class="vip_item">
                    <span class="vip_title">
                        <span class="vip_title_content">会员卡号</span>
					</span>
                    <input title="cVipno" class="vip_text" type="text" name="cVipno" id="cVipno">
                </li>
                <li class="vip_item">
                    <span class="vip_title">
                        <span class="vip_title_content">身份证号</span>
					</span>
                    <input class="vip_text" type="text" name="cIdCard" id="idCard">
                </li>
                <input type="hidden" name="openid" id="openid" value="<%=request.getParameter("openid")%>"/>
                <input type="hidden" name="action" value="0"/>
                <input type="hidden" name="cardtype" value="积分卡"/>
                <input type="button" class="btn" value="老会员绑定" id="bind_button"  />
            </ul>
        </div>
    </form>
</div>
</body>
<script type="text/javascript">

    $("#bind_button").click(function() {
        checkUser();
    });

    function checkUser() {
        let [name, tel, cVipno, idCard, openid] = [document.getElementById("name").value, document.getElementById("cTel").value, document.getElementById("cVipno").value, document.getElementById("idCard").value, document.getElementById("openid").value];
        let inputs = [
            {"content": name, "exception": "用户名不能为空", "id": 1},
            {"content": tel, "exception": "手机号不能为空", "id": 2},
            {"content": cVipno, "exception": "会员卡号不能为空", "id": 3},
            {"content": idCard, "exception": "身份证号不能为空", "id": 4},
            {"content": openid, "exception": "微信号获取失败", "id": 5}
        ];
        try {
            inputs.forEach((element) => {
                if (StringTool.isNull(element.content)) {
                    throw new Error(element.exception);
                }
                if (element.id == 2) {
                    let telReg = /^1[3|4|5|7|8][0-9]{9}$/;
                    if (!telReg.test(element.content)) {
                        throw new Error("手机号格式不正确");
                    }
                }
                if (element.id == 4) {
                    let isIDCard_15 = /^[1-9]\d{7}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}$/;
                    let isIDCard_18 = /^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}([0-9]|X)$/;

                    if (!(isIDCard_15.test(element.content) || isIDCard_18.test(element.content))) {
                        throw new Error("身份证号格式不正确");
                    }
                }
            });
            /**
             *  如果以上都字符串不为空且格式都正确
             */
            let params ={"cVipName":name,
                "cTel":tel,
                "cIdCard":idCard,
                "cVipno":cVipno,
                "openid":"<%=request.getParameter("openid")%>",
                "action":"1",
                "cardtype":"积分卡"};
            $.ajax({
                type: "get",
                url: "../../WX_Vip_Card_Action",
                data: params,
                success: function (data) {
                    let o = JSON.parse(data);
                    alert(o.message);
                    if(o.code == 1 || o.code == 0) {
                        window.location.href = "profile.jsp?openid="+"<%=request.getParameter("openid")%>"
                    }
                }
            });
        } catch (e) {
            alert(e.message);
            return
        }

    }
</script>

</html>

