<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Browse Lists</title>
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="/resources/materialize/css/materialize.min.css">
    <link rel="stylesheet" type="text/css" href="/resources/css/font.css">
    <script type="text/javascript" src="https://code.jquery.com/jquery-2.1.1.min.js"></script>
    <script type="text/javascript" src="/resources/materialize/js/materialize.min.js"></script>
    <script src="/resources/js/browseextended.js"></script>
</head>
<body class="grey lighten-4">
<main>
    <div class="navbar-fixed z-depth-1">
        <nav>
            <div class="nav-wrapper light-blue accent-2">
                <ul class="left">
                    <li><a class="grey lighten-2 text-large text-logo text-black" href="#">BLACK</a>
                        <a class="grey darken-3 text-large text-logo text-white">TODO</a></li>
                </ul>
                <ul class="right">
                    <li class="active"><a href="/todolist/browse" class="left">Browse Todo's</a></li>
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


            <h4>Owner Name: <c:out value="${ownerName}"></c:out></h4>
            <div class="input-field col s6" id="name_field">
                <input id="id_field" type="hidden" value="${id}">
                <input placeholder="Enter Name Here" type="text" id="name_field" value='<c:out value="${name}"></c:out>' readonly>
                <label for="name_field">TodoList Name</label>
            </div>

            <div class="col s1"></div>
            <div class="col s11" style="margin-top: 50px">


                <div style="height: 10%;width: 100%">

                    <div class="row">

                        <div class="col s7">
                        </div>

                        <div style="font-size: 24px;" class="col s5">

                            <div class="row">
                                <div class="col s2"></div>
                                <div class="col s6"></div>

                                <div class="col s4">
                                </div>
                            </div>

                        </div>
                        <br>

                    </div>
                </div>

                <div id="tbl_container">
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
                                <td align="center" contenteditable='false'><c:out value="${currRow.category}"></c:out></td>
                                <td align="center" contenteditable='false'><c:out value="${currRow.description}"></c:out></td>

                                <td>
                                    <input type="hidden" value="${currRow.start}">
                                    <input type="date" value="" readonly>
                                </td>
                                <td>
                                    <input type="hidden" value="${currRow.end}">
                                    <input type="date" value="" readonly>
                                </td>
                                <td>

                                    <c:choose>
                                        <c:when test="${currRow.completed == true}">
                                            <h6>Completed</h6>
                                        </c:when>
                                        <c:when test="${currRow.completed == false}">
                                            <h6>Not Completed</h6>
                                        </c:when>
                                    </c:choose>


                                </td>
                            </tr>

                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</main>
</body>
</html>