<%@ page import="tool.utils.ServletUtils" %><%--
  Created by IntelliJ IDEA.
  User: xwy_brh
  Date: 2017/9/18
  Time: 下午11:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    session = request.getSession();
    String sessionID = session.getId();
    String openid = (String) session.getAttribute("openid");
    String tel = (String) session.getAttribute("tel");
    System.out.println(session);
    System.out.println("sessionID=" + sessionID);
    //if (tel == null) {
    System.out.println("9999999999999999999");
    request.setAttribute("path", request.getRequestURL());
    System.out.println(request.getRequestURL());
    String path = ServletUtils.getServerPath(request);
    System.out.println(path);
    request.getRequestDispatcher("/test1").forward(request, response);
    //}
    out.print("openid=" + openid);
%>

<html>
<head>
    <title>Title</title>
</head>
<body>
<h1>TEST2</h1>
</body>
</html>
