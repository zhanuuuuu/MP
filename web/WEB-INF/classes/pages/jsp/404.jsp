<%--
  Created by IntelliJ IDEA.
  User: xwy_brh
  Date: 2017/9/12
  Time: 下午3:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>404</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=no, minimal-ui">
    <meta name="screen-orientation" content="portrait"/>
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="format-detection" content="telephone=no">
    <meta name="full-screen" content="yes">
    <meta name="x5-fullscreen" content="true">
    <link rel="stylesheet" href="../common/common.css" />
    <script type="text/javascript" src="../common/common.js"></script>
    <style type="text/css">
        .wrapper {
            position: relative;
            width: 100%;
            height:100%;
            background-color: white;
        }
        .container {
            position: absolute;
            text-align: center;
            left: 50%;
            top: 40%;
            transform: translate(-50%, -50%);
        }
        .title {
            font-size: 2rem;
            font-weight: bold;
        }
        .content {
            font-size: 1rem;
        }
        .element {
            margin-bottom: .5rem;
        }
    </style>
</head>
<body>
    <div class="wrapper">
        <div class="container">
            <img class="_404_symbol element" src = "../images/404.png" />
            <h4 class="title element">404</h4>
            <h5 class="content element">页面出错啦</h5>
        </div>
    </div>
</body>
</html>
