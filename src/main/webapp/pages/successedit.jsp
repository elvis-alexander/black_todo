<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>

<head>
    <title>Successfully Added TodoList</title>
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="/resources/materialize/css/materialize.min.css">
    <script type="text/javascript" src="https://code.jquery.com/jquery-2.1.1.min.js"></script>
    <script type="text/javascript" src="/resources/materialize/js/materialize.min.js"></script>
    <link rel="stylesheet" type="text/css" href="/resources/css/font.css">
    <csrf disabled="true"/>

</head>
<body>
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
                <li><a href="/todolist/mylists" class="left">My Todo's</a></li>
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
        <h1>Successfully Edited</h1>
    </div>
</div>
</body>
</html>
