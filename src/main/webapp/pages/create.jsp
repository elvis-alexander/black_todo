<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>

<head>

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
    <!-- Compiled and minified CSS -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.98.0/css/materialize.min.css">
    <!-- Compiled and minified JavaScript -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.98.0/js/materialize.min.js"></script>
    <link href="https://fonts.googleapis.com/css?family=Roboto" rel="stylesheet">
    <title>Add a list</title>
    <!-- my css/js this -->
    <%--<spring:url value="/resources/css/create.css" var="mainCss" />--%>
    <%--<spring:url value="/resources/js/create.js" var="mainJs" />--%>
    <%--<link href="${mainCss}" rel="stylesheet" />--%>
    <%--<script src="${mainJs}"></script>--%>

    <link href="/resources/css/create.css" rel="stylesheet">
    <script src="/resources/js/create.js"></script>

    <%--my css/js--%>
    <csrf disabled="true"/>

</head>
<body>


<div class="container">

    <div class="row">
        <h1>New Todo List</h1>
        <div class="col s1"></div>

        <div class="col s11" style="margin-top: 50px">


            <div style="height: 10%;width: 100%">

                <div class="row">

                    <div class="col s7">
                        <button type="button" class="waves-effect waves-light btn" onclick="on_add_row('new category', 'new description')">Add Row</button>
                        <button type="button" class="waves-effect waves-light btn" onclick="on_del_row()">Delete Row</button>
                        <button type="button" class="waves-effect waves-light btn" onclick="move_up()">Move Up</button>
                        <button type="button" class="waves-effect waves-light btn" onclick="move_down()">Move Down</button>
                    </div>

                    <div style="font-size: 24px;" class="col s5">

                        <div class="row">
                            <div class="col s2"></div>
                            <div class="col s6" id="private_input">
                                <input type="checkbox" id="private" />
                                <label for="private">Mark As Private</label>
                            </div>

                            <div class="col s4">
                                <button class="btn waves-effect waves-light" type="submit"onclick="save_todolist()">Save</button>
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
                            <!-- <div class="form-check">
                                <label class="form-check-label">
                                    <input class="form-check-input" type="checkbox" value="">
                                    Mark as Completed
                                </label>
                            </div> -->
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
</body>
</html>