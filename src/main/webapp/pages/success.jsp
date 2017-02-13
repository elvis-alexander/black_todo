<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>

<head>
    <title>Successfully Added TodoList</title>
    <jsp:include page="templates/head.jsp"/>
    <csrf disabled="true"/>
</head>
<body>
<script>
    function onLoad() {
        gapi.load('auth2', function() {
            gapi.auth2.init();
        });
    }
</script>
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
        <h1>Successfully Added/Edited TodoList</h1>
    </div>
</div>
<script src="https://apis.google.com/js/platform.js?onload=onLoad" async defer></script>
</body>
</html>
