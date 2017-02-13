<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Create a todo list</title>
    <jsp:include page="templates/head.jsp"/>
    <script src="/resources/js/add.js"></script>
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
            <h5>by <c:out value="${owner}"></c:out> <i class="material-icons">supervisor_account</i></h5>
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
                        <td align="center" contenteditable='true'><c:out value="${currRow.category}"></c:out></td>
                        <td align="center" contenteditable='true'><c:out value="${currRow.description}"></c:out></td>
                        <td><input type="date" value="${currRow.start}"></td>
                        <td><input type="date" value="${currRow.end}"></td>
                        <td>
                            <div class="switch">
                                <label>
                                    Off
                                    <input type="checkbox">
                                    <span class="lever"><c:choose>
                                        <c:when test="${not currRow.completed}">
                                            :after
                                        </c:when>
                                        <c:otherwise>
                                            :before
                                        </c:otherwise>
                                    </c:choose></span>
                                    On
                                </label>
                            </div>
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
