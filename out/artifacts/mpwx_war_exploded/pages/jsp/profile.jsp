<%--
  Created by IntelliJ IDEA.
  User: xwy_brh
  Date: 2017/8/21
  Time: 下午8:54
  To change this template use File | Settings | File Templates.
--%>
<%--<% String openid = "oZJo4wxdzJJxGAYRMZ_zq4inoM7w"; %>--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>我的会员</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=no, minimal-ui">
    <meta name="screen-orientation" content="portrait"/>
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="format-detection" content="telephone=no">
    <meta name="full-screen" content="yes">
    <meta name="x5-fullscreen" content="true">
    <link rel="stylesheet" type="text/css" href="../common/common.css"/>
    <link rel="stylesheet" type="text/css" href="../css/myvip.css">
    <link rel="stylesheet" type="text/css" href="../css/my_vip_func.css">
    <script type="application/javascript" src="../common/fastclick.js"></script>
    <script type="text/javascript" src="../external_js/jquery-1.6.0.min.js"></script>
    <script type="text/javascript" src="../common/common.js"></script>
    <script type="text/javascript" src="../external_js/vue.min.js"></script>
    <script type="text/javascript" src="../common/tool.js"></script>
</head>
<body>
<div class="myvip_wrapper">
    <div class="vip_content">
        <div class="user_info_wrapper">
				<span class="headImageUrl inline_block"> <img class="headImage" id="headImage" src="../images/avatar.png"/>
				</span> <span class="nickname_wrapper inline_block"> <span
                class="nickname" id="nickname">香江百货VIP</span>
				</span> <span class="vip_icon inline_block"> <img
                src="../images/vip.png" class="vip_icon_image"/>
				</span>
        </div>
        <div id="card_wrapper_app" class="card_wrapper_app">
            <ul id="card_wrapper_list">
                <%--<li>
                    <div class="card_wrapper">
                        <div class="cardImage_box">
                            <img class="card_image_source" id="card_image_source"
                                 src="../images/jifen.png"/>
                        </div>
                        <div class="card_NO_box">
                            <span class="card_NO_text">13223393303</span>
                        </div>
                    </div>
                    <div class="card_info">
                        <ul class="card_list">
                            <li class="card_item">
                                <div class="card_title">
                                    <span class="card_title_text">积分</span>
                                </div>
                                <div class="card_text_content">
                                    <span class="card_text" id="card_jifen">0.00</span>
                                </div>
                            </li>
                            <li class="card_line"></li>
                            <li class="card_item">
                                <div class="card_title">
                                    <span class="card_title_text">类型</span>
                                </div>
                                <div class="card_text_content">
                                    <span class="card_text" id="card_type_text">积分卡</span>
                                </div>
                            </li>
                        </ul>
                    </div>
                </li>--%>
            </ul>
        </div>
        <div class="remark_wrapper">
            <ul class="remark_list">
                <!--  <li class="remark_item">
                <span class="remark_title">会员卡详情</span>
                <span class="remark_icon">
                        <img class="remark_icon_source" src="images/arrow.png" />
                    </span>
            </li> -->
                <!--
                <li class="remark_item" id="signBtn"><span
                        class="remark_title">香粉签到</span> <span class="remark_icon">
							<img class="remark_icon_source" src="images/arrow.png" />
					</span></li>
                -->
            </ul>
        </div>
        <div id="func_wrapper_app" class="func_wrapper">
            <ul class="func_list">
                <li v-for="(func_item,index) in func_list" class="func_item" v-on:click="didSelect(index, openid='<%=request.getParameter("openid")%>')">
                    <div class="func_item_wrapper">
                        <div class="func_item_icon_wrapper">
                            <img class="func_item_icon" :src="func_item.icon"/>
                        </div>
                        <div class="func_item_title">
                            {{func_item.title}}
                        </div>
                    </div>
                </li>
            </ul>
        </div>
    </div>
</div>
</body>
<script type="text/javascript" src="../js/myvip.js" charset="utf-8"></script>
<script type="text/javascript">

    function isJson(obj) {
        var isJson = typeof(obj) == "object" && Object.prototype.toString.call(obj).toLowerCase() == "[object object]" && !obj.length;
        return isJson;
    }

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
                } else {
                    configureData(o);
                }
            }
        });
    });

    function configureData(o) {
        let cardImageName = (card_code, card_type) => {
            if (StringTool.isNull(card_code) && StringTool.isNull(card_type)) {
                return "eCard.png";
            } else {
                if (card_type == "金卡") {
                    return "jinka.png";
                } else if (card_type == "白金卡") {
                    return "baijin.png";
                } else {
                    return "jifen.png";
                }
            }
        };

        if (!isJson(o)) {
            alert("返回信息格式错误");
        } else {
            document.getElementById("nickname").innerText = o.nickname;
            document.getElementById("headImage").src = o.headimgurl;
            let cardInfo = o.cardInfo;
            console.log(cardInfo);
            let r = document.getElementById("card_wrapper_list");
            let htmls = new Array();
            cardInfo.forEach((element) => {
                let card_type = (StringTool.isNull(element.card_code) && StringTool.isNull(element.cardtype)) ? "电子卡" : element.cardtype;
                let card_img_name = cardImageName(element.card_code, element.cardtype);
                htmls.push("<li class='card_wrapper_item'>");
                htmls.push("<div class='card_wrapper'><div class='cardImage_box'>");
                htmls.push("<img class='card_image_source' id='card_image_source' src='../images/" + card_img_name + "'" + "/>");
                htmls.push("</div>");
                htmls.push("<div class='card_NO_box'><span class='card_NO_text'>");
                htmls.push(element.cVipno);
                htmls.push("</span></div>");
                htmls.push("</div>");
                htmls.push("<div class='card_info'>");
                htmls.push("<ul class='card_list'><li class='card_item'>");
                htmls.push("<div class='card_title'><span class='card_title_text'>");
                htmls.push("积分");
                htmls.push("</span></div>");
                htmls.push("<div class='card_text_content'>");
                htmls.push("<span class='card_text' id='card_jifen'>");
                htmls.push(parseFloat(element.fCurValue).toFixed(2));
                htmls.push("</span></div></li>");
                htmls.push("<li class='card_line'></li>");
                htmls.push("<li class='card_item'>");
                htmls.push("<div class='card_title'><span class='card_title_text'>");
                htmls.push("类型");
                htmls.push("</span></div>");
                htmls.push("<div class='card_text_content'><span class='card_text' ='card_type_text'>");
                htmls.push(card_type);
                htmls.push("</span></div>");
                htmls.push("</li></ul>");
                htmls.push("</div>");
                htmls.push("</li>")
            });
            let htmls_string = htmls.join('');
            r.innerHTML = htmls_string;
        }
    }


</script>
</html>
