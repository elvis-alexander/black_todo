<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>

<head>
    <title>Edit TodoList</title>
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="/resources/materialize/css/materialize.min.css">
    <script type="text/javascript" src="https://code.jquery.com/jquery-2.1.1.min.js"></script>
    <script type="text/javascript" src="/resources/materialize/js/materialize.min.js"></script>
    <link rel="stylesheet" type="text/css" href="/resources/css/font.css">
    <link href="/resources/css/edit.css" rel="stylesheet">
    <script src="/resources/js/edit.js"></script>

    <%--my css/js--%>
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


        <h4>New Todo List</h4>
        <div class="input-field col s6" id="name_field">
            <input placeholder="Enter Name Here" type="text" id="name_field" value='<c:out value="${name}"></c:out>'>
            <label for="name_field">TodoList Name</label>
        </div>

        <div class="col s1"></div>
        <div class="col s11" style="margin-top: 50px">


            <div style="height: 10%;width: 100%">

                <div class="row">

                    <div class="col s7">
                        <button type="button" class="waves-effect waves-light btn light-blue accent-2" onclick="on_add_row('new category', 'new description')">Add Row</button>
                        <button type="button" class="waves-effect waves-light btn light-blue accent-2" onclick="on_del_row()">Delete Row</button>
                        <button type="button" class="waves-effect waves-light btn light-blue accent-2" onclick="move_up()">Move Up</button>
                        <button type="button" class="waves-effect waves-light btn light-blue accent-2" onclick="move_down()">Move Down</button>
                    </div>

                    <div style="font-size: 24px;" class="col s5">

                        <div class="row">
                            <div class="col s2"></div>
                            <div class="col s6" id="private_input">
                                <c:choose>
                                    <c:when test="${privateTodo == false}">
                                        <input type="checkbox" id="private" />
                                        <label for="private">Mark As Private</label>
                                    </c:when>
                                    <c:otherwise>
                                        <input type="checkbox" id="private" checked/>
                                        <label for="private">Mark As Private</label>
                                    </c:otherwise>
                                </c:choose>
                            </div>

                            <div class="col s4">
                                <button class="btn waves-effect waves-light light-blue accent-2" type="submit"onclick="save_todolist()">Save</button>
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
                                <td align="center" contenteditable='true'><c:out value="${currRow.category}"></c:out></td>
                                <td align="center" contenteditable='true'><c:out value="${currRow.description}"></c:out></td>

                                <td>
                                    <input type="hidden" value="${currRow.start}">
                                    <input type="date" value="">
                                </td>
                                <td>
                                    <input type="hidden" value="${currRow.end}">
                                    <input type="date" value="">
                                </td>
                                <td>
                                    <div class="switch">
                                        <label>
                                            Off
                                            <input type="checkbox">
                                            <span class="lever"></span>
                                            On
                                        </label>
                                    </div>
                                </td>
                            </tr>

                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>
</body>
</html>