<%--
  Created by Chaeyoung Lee
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title><c:out value="${name}"></c:out></title>
    <jsp:include page="templates/head.jsp"/>
    <script src="/resources/js/view.js"></script>
    <script src="/resources/js/browseextended.js"></script>
    <script>
        function onLoad() {
            gapi.load('auth2', function() {
                gapi.auth2.init();
            });
        }
    </script>
</head>
<body>

<main>
    <div class="navbar-fixed z-depth-1">
        <nav class="top-nav-bar">
            <div class="nav-wrapper">
                <ul class="left">
                    <li><a href="/" class="text-large text-logo text-black">BLACK TODO</a></li>
                </ul>
                <ul class="right">
                    <li class="active"><a href="/list/public" class="left text-black">Browse Todo's</a></li>
                    <li><a href="/list" class="left text-black">My Todo's</a></li>
                    <li><a href="/add" class="left text-black">Create Todo</a></li>
                    <li><a href="/" onclick="signOut()" class="text-black"><i class="material-icons left text-black">input</i>Logout</a></li>
                </ul>
            </div>
        </nav>
    </div>
    <div class="padder"></div>
    <div class="container">

        <div class="row">
            <h4><c:out value="${name}"></c:out></h4>
            <h5>by <c:out value="${ownerName}"></c:out>
                <c:choose>
                    <c:when test="${not privateTodo}">
                        <i class="material-icons right small">supervisor_account</i>
                    </c:when>
                    <c:otherwise>
                        <i class="material-icons right small">lock</i>
                    </c:otherwise>
                </c:choose>
            </h5>
        </div>
        <div class="padder"></div>
        <div id="tbl_container" class="row">
            <table class="table">
                <thead>
                <tr class="info">
                    <th>Category</th>
                    <th>Description</th>
                    <th>Start Date</th>
                    <th>End Date</th>
                    <th>Completed</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${rows}" var="currRow">
                    <tr >
                        <td align="center" contenteditable='false'><c:out value="${currRow.category}"></c:out></td>
                        <td align="center" contenteditable='false'><c:out value="${currRow.description}"></c:out></td>
                        <td><input type="hidden" value="${currRow.start}">
                            <input type="date" value="" readonly></td>
                        <td><input type="hidden" value="${currRow.end}">
                            <input type="date" value="" readonly></td>
                        <td>
                            <c:choose>
                                <c:when test="${currRow.completed == true}">
                                    <i class="material-icons small">check</i>
                                </c:when>
                            </c:choose>
                        </td>
                    </tr>

                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</main>
<script src="https://apis.google.com/js/platform.js?onload=onLoad" async defer></script>
</body>
</html>
