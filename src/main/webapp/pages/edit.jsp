
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Edit a todo list</title>
    <jsp:include page="templates/head.jsp"/>
    <script src="/resources/js/edit.js"></script>
    <script src="/resources/js/browseextended.js"></script>
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
                    <li><a href="/list/public" class="left text-black">Browse Todo's</a></li>
                    <li class="active"><a href="/list" class="left text-black">My Todo's</a></li>
                    <li><a href="/add" class="left text-black">Create Todo</a></li>
                    <li><a href="/" onclick="signOut()" class="text-black"><i class="material-icons left text-black">input</i>Logout</a></li>
                </ul>
            </div>
        </nav>
    </div>
    <div class="padder"></div>
    <div class="container">

        <div class="row">


            <h4>Edit Todo List</h4>
            <div class="padder"></div>
            <div class="row">
                <div class="col s6" id="name_field">
                    <input placeholder="Enter Name Here" type="text" id="name_input" value="<c:out value="${name}"></c:out>">
                    <label for="name_input">TodoList Name</label>
                </div>
                <div class="col s4" id="private_input">
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
                <div class="col s2">
                    <a class="btn waves-effect waves-light light-blue accent-3 text-black" type="submit" onclick="save_todolist('${id}')">Save</a>
                </div>
            </div>
            <div class="padder"></div>
            <div class="row">
                <button type="button" class="waves-effect waves-light btn light-green accent-1 text-black" onclick="on_add_row('new category', 'new description')">Add Row</button>
                <button type="button" class="waves-effect waves-light btn light-green accent-1 text-black" onclick="on_del_row()">Delete Row</button>
                <button type="button" class="waves-effect waves-light btn light-green accent-1 text-black" onclick="move_up()">Move Up</button>
                <button type="button" class="waves-effect waves-light btn light-green accent-1 text-black" onclick="move_down()">Move Down</button>
                <a type="button" class="waves-effect waves-red btn materialize-red accent-1 text-white right-align" href="/remove/${id}">Remove TodoList</a>
            </div>
            <div class="padder"></div>
            <div class="row" id="tbl_container">
                <table class="table">
                    <thead>
                    <tr class="info">
                        <th onclick="sort_table('category')">Category</th>
                        <th onclick="sort_table('description')">Description</th>
                        <th onclick="sort_table('start')">Start Date</th>
                        <th onclick="sort_table('end')">End Date</th>
                        <th onclick="sort_table('completed')">Completed</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${rows}" var="currRow">

                        <tr onclick="on_selected_row(this)">
                            <td align="center" contenteditable='true'>${currRow.category}</td>
                            <td align="center" contenteditable='true'>${currRow.description}</td>

                            <td>
                                <input type="hidden" value="${currRow.start}">
                                <input type="date" value="">
                            </td>
                            <td>
                                <input type="hidden" value="${currRow.end}">
                                <input type="date" value="">
                            </td>
                            <td>

                                <c:choose>
                                    <c:when test="${currRow.completed == true}">

                                        <div class="switch">
                                            <label>
                                                Off
                                                <input checked type="checkbox">
                                                <span class="lever"></span>
                                                On
                                            </label>
                                        </div>

                                    </c:when>
                                    <c:when test="${currRow.completed == false}">

                                        <div class="switch">
                                            <label>
                                                Off
                                                <input type="checkbox">
                                                <span class="lever"></span>
                                                On
                                            </label>
                                        </div>

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
</main>
<script src="https://apis.google.com/js/platform.js?onload=onLoad" async defer></script>
</body>
</html>