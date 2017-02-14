<%--
  Created by Chaeyoung Lee
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Public Todo Lists</title>
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
                    <li class="active"><a href="/list/public" class="left text-black">Browse Todo's</a></li>
                    <li><a href="/list" class="left text-black">My Todo's</a></li>
                    <li><a href="/add" class="left text-black">Create Todo</a></li>
                    <li><a href="/" onclick="signOut()" class="text-black light-green"><i class="material-icons left text-black">input</i>Logout</a></li>
                </ul>
            </div>
        </nav>
    </div>
    <div class="divider"></div>
    <div class="container">
        <div class="row">
            <c:forEach items="${todoList}" var="currentTodo">
                <div class="col s4">
                    <div class="card white">
                        <div class="card-content black-text">
                            <span class="text-medium"><i class="material-icons right small">supervisor_account</i> <c:out value="${currentTodo.name}"></c:out></span>
                            <span class="text-small">by <c:out value="${currentTodo.ownerName}"></c:out></span>
                            <div class="divider"></div>
                            <h6>Preview:</h6>
                            <ol>
                                <c:forEach items="${currentTodo.rows}" var="curr_row">
                                    <li><c:out value="${curr_row.category}"></c:out></li>
                                </c:forEach>
                            </ol>
                        </div>
                        <div class="card-action">
                            <a class="btn waves-effect waves-light light-blue" href="/view/${currentTodo.id}">View full todo list<i class="material-icons right">send</i></a>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>
    </div>

    </div>
    <script src="https://apis.google.com/js/platform.js?onload=onLoad" async defer></script>
</main>
</body>
</html>
