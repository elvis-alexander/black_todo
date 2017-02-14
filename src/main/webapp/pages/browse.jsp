<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Browse Lists</title>
    <jsp:include page="templates/head.jsp"/>
</head>
<body class="grey lighten-4">
<script>
    function onLoad() {
        gapi.load('auth2', function() {
            gapi.auth2.init();
        });
    }
</script>
<main>
    <div class="navbar-fixed z-depth-1">
        <nav>
            <div class="nav-wrapper light-blue accent-2">
                <ul class="left">
                    <li>
                        <a class="grey lighten-2 text-large text-logo text-black" href="#">BLACK</a>
                    </li>
                    <li>
                        <a class="grey darken-3 text-large text-logo text-white">TODO</a>
                    </li>
                </ul>
                <ul class="right">
                    <li class="active"><a href="/todolist/browse" class="left">Browse Todo's</a></li>
                    <li><a href="/todolist/mylists" class="left">My Todo's</a></li>
                    <li><a href="/todolist/add" class="left">Create Todo</a></li>
                    <li>
                        <a href="/" onclick="signOut()">
                            <i class="material-icons left">input</i>Logout
                        </a>
                    </li>
                </ul>
            </div>
        </nav>
    </div>

    <div class="container">
        <div class="row">

            <c:forEach items="${todoList}" var="currentTodo">
                <div class="col s4">
                    <div class="card grey darken-2">
                        <div class="card-content white-text">
                            <span class="text-medium">Owner: <c:out value="${currentTodo.ownerName}"></c:out></span><br>
                            <span class="text-medium">Name: <c:out value="${currentTodo.name}"></c:out></span>
                            <div class="divider"></div>
                            <h6>Preview:</h6>
                            <ol>
                                <c:forEach items="${currentTodo.rows}" var="curr_row">
                                    <li><c:out value="${curr_row.category}"></c:out></li>
                                </c:forEach>
                            </ol>
                        </div>
                        <form action="/todolist/browseextended" method="get">
                            <div class="card-action">
                                <input type="hidden" value="${currentTodo.id}" name="todoId">
                                <input type="hidden" value="${currentTodo.ownerName}" name="ownerName">
                                <button class="btn waves-effect waves-light" type="submit" name="action">View (only) todo list
                                    <i class="material-icons right">send</i>
                                </button>
                            </div>
                        </form>
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