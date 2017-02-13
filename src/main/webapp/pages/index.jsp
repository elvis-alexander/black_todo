<%--
  Created by IntelliJ IDEA.
  User: jeonghoon-kim
  Date: 2/11/17
  Time: 3:22 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <title>Todo List</title>
    <jsp:include page="templates/head.jsp"/>
    <link rel="stylesheet" href="/resources/css/index.css">
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
            </div>
        </nav>
    </div>

    <div class="center-div">

        <div class="card grey darken-2" style="height: 340px">
            <div class="card-content white-text">
                <span class="text-medium">Create A TodoList</span>
                <div class="divider"></div>
                <h6>Sign In:</h6>
                <ol>
                    <li><div class="g-signin2" data-onsuccess="onSignIn"></div></li>
                </ol>
            </div>
            <div class="card-action">
                <p class="light-blue-text text-accent-2">Copyright BlackTodo</p>
            </div>
        </div>
    </div>
</main>
</body>