<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
<body>
<h2>Hello World!</h2>
<sec:authorize var="loggedIn" access="isAuthenticated()" />
<c:choose>
    <c:when test="${loggedIn}">
        Welcome, <sec:authentication property="name"/><br>
        <c:if test="${isAdmin}">
            <a href="/secured">Dark side</a><br>
        </c:if>
        <a href="/logout">UnWelcome</a><br>
    </c:when>
    <c:otherwise>
        <a href="/login">xxx is waiting for you</a><br>
    </c:otherwise>
</c:choose>
</body>
</html>
