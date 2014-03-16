<%--
  Created by IntelliJ IDEA.
  User: chanwook
  Date: 2014. 3. 6.
  Time: 오전 8:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<html>
<head>
    <title>로그인 화면</title>
</head>
<body>

    <h3>로그인하세요!</h3>
    <form action="/login" method="post">
        ID: <input type="text" name="id" title="id" />
        PASSWORD: <input type="password" name="password" title="password" />
        <input type="hidden" name="_returnUrl" id="_returnUrl" title="_returnUrl" value="${_returnUrl}" />
        <input type="submit" />
    </form>

</body>
</html>
