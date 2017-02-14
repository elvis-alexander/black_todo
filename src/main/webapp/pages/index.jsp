<%--
  Created by Chaeyoung Lee
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <title>Todo List</title>
    <jsp:include page="templates/head.jsp"/>
    <link rel="stylesheet" type="text/css" href="/resources/css/index.css">
</head>

<body>
<div class="section"></div>
<main>
    <center>
        <i class="large material-icons" style="color:white;">toc</i>
        <h1 class="main-message"> Make a to do list today</h1>
        <div class="section"></div>
        <div class="container">
            <div class="row">
                <center>
                    <div class="col l12 text-white center text-logo text-large">BLACK</div>
                    <div class="col l12 text-black text-logo text-large todo-logo">TODO</div>
                </center>
            </div>
            <div class="section"></div>
            <div class="row">
                <div class="col l12"><span class="login-message">Please login with your Google account</span></div>
                <div class="section"></div>
                <div class="col l12"><div class="g-signin2" data-onsuccess="onSignIn"></div></div>
            </div>
        </div>
    </center>
</main>
</body>
</html>