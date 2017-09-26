<%--
  Created by IntelliJ IDEA.
  User: xwy_brh
  Date: 2017/9/18
  Time: 下午11:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    session = request.getSession();
    String openid = (String) session.getAttribute("openid");
    System.out.println("opneid=" + openid);
    out.print("openid=" + openid);
%>
<html>
<head>
    <title></title>
</head>
<body>
<h1>Test1</h1>
</body>
</html>
