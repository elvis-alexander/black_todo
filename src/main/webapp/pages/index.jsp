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
</head>

<body class="grey lighten-4">
<div class="container valing-wrapper" style="margin-top: 10%">
    <div class="row valign">
        <div class="col s4 offset-s4 grey lighten-5 z-depth-2">
            <div class="col s6 grey lighten-2 text-black center text-logo text-medium">BLACK</div>
            <div class="col s6 grey darken-3 center text-white text-logo text-medium">TODO</div>
            <div class="col s12">Make a todo list today.<div class="divider"></div></div>
            <div class="col s12"><div class="g-signin2" data-onsuccess="onSignIn"></div></div>
        </div>
    </div>
</div>
</body>
</html>