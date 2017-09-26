<%@ page import="test.User" %>
<!doctype html>
<html lang="zh_CN">

<%@ page import="java.io.*,java.util.*" %>
<%
    // 获取session创建时间
    Date createTime = new Date(session.getCreationTime());
    // 获取最后访问页面的时间
    Date lastAccessTime = new Date(session.getLastAccessedTime());

    String title = "Welcome Back to my website";
    Integer visitCount = 0;
    String visitCountKey = "visitCount";
    String userIDKey = "userID";
    String userID = "ABCD";

    Cookie cookie = new Cookie("isNew", "true");
    Cookie cookie1 = new Cookie("name", "jack");
    response.addCookie(cookie);
    response.addCookie(cookie1);
    // 检测网页是否由新的访问用户
    if (session.isNew()){
        title = "Welcome to my website";
        session.setAttribute(userIDKey, userID);
        session.setAttribute(visitCountKey,  visitCount);
    }
    visitCount = (Integer)session.getAttribute(visitCountKey);
    visitCount = visitCount + 1;
    userID = (String)session.getAttribute(userIDKey);
    session.setAttribute(visitCountKey,  visitCount);
%>
<head>
    <title>Session Tracking</title>
</head>
<body>

    <h1>Session Tracking</h1>
    <h2><% out.print(title);%></h2>
<table border="1" align="center">
    <tr bgcolor="#949494">
        <th>Session info</th>
        <th>Value</th>
    </tr>
    <tr>
        <td>id</td>
        <td><% out.print( session.getId()); %></td>
    </tr>
    <tr>
        <td>Creation Time</td>
        <td><% out.print(createTime); %></td>
    </tr>
    <tr>
        <td>Time of Last Access</td>
        <td><% out.print(lastAccessTime); %></td>
    </tr>
    <tr>
        <td>User ID</td>
        <td><% out.print(userID); %></td>
    </tr>
    <tr>
        <td>Number of visits</td>
        <td><% out.print(visitCount); %></td>
    </tr>
</table>
</body>
</html>