<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: kingfernandez
  Date: 2/9/17
  Time: 2:30 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>My Lists</title>
</head>
<body>

            <c:forEach items="${todoList}" var="current">
                <tr>
                    <td><c:out value="${current.privateTodo}" /><td>
                </tr>
            </c:forEach>


</body>
</html>
