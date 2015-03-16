<%--
  Created by IntelliJ IDEA.
  User: intelligrape
  Date: 13/3/15
  Time: 12:30 AM
--%>

<%@ page import="sharehub.User" contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="afterLogin"/>
    <title>Admin Panel</title>
</head>

<body>


<div class="panel panel-default">
    <div class="panel-heading">
        <span style="float:right;">
            <g:select name="userFilter" from="['All Users', 'Active', 'Inactive']"/>
            %{--<g:formRemote name="Search" url="${createLink(controller: "search")}">--}%
                <g:textField name="search" placeholder="Search"/>
            %{--</g:formRemote>--}%
            <a href="javascript:void(0)">View All</a>
        </span>

        <h3 class="panel-title">Users</h3>
    </div> <!-- panel-heading -->
    <div class="panel-body">
<table class="table table-hover table-striped">
    <thead>
    <tr>
        <th>Username</th>
        <th>Email</th>
        <th>First Name</th>
        <th>Last Name</th>
        <th>Manage</th>
    </tr>
    </thead>
    <tbody>
    <g:each in="${users}" var="user">
        <div id="tr${user}">
            <tr>
                <td>
                    <g:link controller="user" action="profile" params="[id: user.username]">${user.username}</g:link>
                </td>
                <td>${user.email}</td>
                <td>${user.firstName}</td>
                <td>${user.lastName}</td>
                <td id="manager_${user.username}">
                    <g:remoteLink update="manager_${user.username}" controller="user" action="${user.active?"Deactivate":"Activate"}" params="[username: user.username]">${user.active?"Deactivate":"Activate"}</g:remoteLink>
                </td>
            </tr>
        </div>
    </g:each>
    </tbody>
</table>
</div>    <!-- panel-body -->
</div>  <!-- panel -->

</body>
</html>