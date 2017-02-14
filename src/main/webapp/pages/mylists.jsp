<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>My Lists</title>
    <jsp:include page="templates/head.jsp"/>
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
                    <li><a href="/list/public" class="left text-black">Browse Todo's</a></li>
                    <li class="active"><a href="/list" class="left text-black">My Todo's</a></li>
                    <li><a href="/add" class="left text-black">Create Todo</a></li>
                    <li><a href="/" onclick="signOut()" class="text-black"><i class="material-icons left text-black">input</i>Logout</a></li>
                </ul>
            </div>
        </nav>
    </div>
    <div class="divider"></div>
    <div class="container">
        <div class="row">
            <c:forEach items="${todoList}" var="currentTodo">
            <div class="col s4">
                <c:choose>
                <c:when test="${not currentTodo.privateTodo}">
                <div class="card white">
                    <div class="card-content black-text">
                        </c:when>
                        <c:otherwise>
                        <div class="card grey darken-2">
                            <div class="card-content white-text">
                                </c:otherwise>
                                </c:choose>
                                <span class="text-medium"><c:out value="${currentTodo.name}"></c:out></span>
                                <c:choose>
                                    <c:when test="${not currentTodo.privateTodo}">
                                        <i class="material-icons right small">supervisor_account</i>
                                    </c:when>
                                    <c:otherwise>
                                        <i class="material-icons right small">lock</i>
                                    </c:otherwise>
                                </c:choose>
                                    <%--<span class="text-medium">no name</span>--%>
                                <div class="divider"></div>
                                <h6>Preview:</h6>
                                <ol>
                                    <c:forEach items="${currentTodo.rows}" var="curr_row">
                                        <li><c:out value="${curr_row.category}"></c:out></li>
                                    </c:forEach>
                                </ol>
                            </div>
                            <div class="card-action">
                                <a class="btn waves-effect waves-light" href="edit/${currentTodo.id}">View/Edit full todo list
                                    <i class="material-icons right">send</i>
                                </a>
                            </div>
                        </div>
                    </div>
                    </c:forEach>
                </div>
            </div>
</main>
<script src="https://apis.google.com/js/platform.js?onload=onLoad" async defer></script>
</body>
</html>