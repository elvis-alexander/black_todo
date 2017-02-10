<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>My Lists</title>
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="/resources/materialize/css/materialize.min.css">
    <link rel="stylesheet" type="text/css" href="/resources/css/font.css">
    <script type="text/javascript" src="https://code.jquery.com/jquery-2.1.1.min.js"></script>
    <script type="text/javascript" src="/resources/materialize/js/materialize.min.js"></script>

</head>


<body class="grey lighten-4">
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
                    <li><a href="/todolist/browse" class="left">Browse Todo's</a></li>
                    <li class="active"><a href="/todolist/mylists" class="left">My Todo's</a></li>
                    <li><a href="/todolist/add" class="left">Create Todo</a></li>
                    <li>
                        <a href="#">
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
                            <span class="text-medium"><c:out value="${currentTodo.name}"></c:out></span>
                            <%--<span class="text-medium">no name</span>--%>
                            <div class="divider"></div>
                            <h6>Preview:</h6>
                            <ol>
                                <c:forEach items="${currentTodo.rows}" var="curr_row">
                                    <li><c:out value="${curr_row.description}"></c:out></li>
                                </c:forEach>
                                <li><c:out value="${currentTodo.id}"></c:out></li>
                            </ol>
                        </div>
                        <form action="/todolist/edit" method="get">
                            <div class="card-action">

                                <button class="btn waves-effect waves-light" type="submit" name="action">View/Edit full todo list
                                    <i class="material-icons right">send</i>
                                </button>
                                <input name="todoId" value="<c:out value="${currentTodo.id}"></c:out>">

                            </div>
                        </form>
                    </div>
                </div>
            </c:forEach>
        </div>
    </div>

    </div>
</main>
</body>
</html>


<%--<input type="submit" value="View/Edit full todo list" class="btn waves-effect waves-light">--%>
<%--<i class="material-icons right">send</i>--%>