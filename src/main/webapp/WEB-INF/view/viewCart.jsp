<%--
  Created by IntelliJ IDEA.
  User: chanwook
  Date: 2014. 3. 6.
  Time: 오전 8:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>상품보기</title>
</head>
<body>

    <div>
        <h1>장바구니!</h1>
    </div>

    <div>
        <c:if test="${_userId != null}">
            <h3>${_userName}님 반가워요!</h3>
            <a href="/logout">로그아웃</a>
        </c:if>
        <c:if test="${_userId == null}">
            <a href="/login?_returnUrl=/cart">로그인</a>
        </c:if>
    </div>

</body>
</html>
