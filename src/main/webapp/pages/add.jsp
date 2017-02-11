<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>

<head>
    <title>Create a TodoList</title>
    <jsp:include page="templates/head.jsp"/>
    <link href="/resources/css/add.css" rel="stylesheet">
    <script src="/resources/js/add.js"></script>
    <%--my css/js--%>
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
                <li class="active"><a href="/todolist/add" class="left">Create Todo</a></li>
                <li>
                    <a href="/" onclick="onLoad();signOut();">
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
            <input placeholder="Enter Name Here" type="text" id="name_field">
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
                                <input type="checkbox" id="private" />
                                <label for="private">Mark As Private</label>
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
                    <!-- content added here through jQuery -->
                    <tr >
                        <td align="center" contenteditable='true'>new category</td>
                        <td align="center" contenteditable='true'>new description</td>
                        <td><input type="date"></td>
                        <td><input type="date"></td>

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
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>
<script src="https://apis.google.com/js/platform.js?onload=onLoad" async defer></script>
</body>
</html>