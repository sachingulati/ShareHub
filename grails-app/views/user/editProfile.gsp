<%--
  Created by IntelliJ IDEA.
  User: intelligrape
  Date: 7/3/15
  Time: 5:57 PM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="afterLogin"/>
    <title>${user.name}</title>
</head>

<body>

<div>
    <div class="row">
        <div class="col-lg-5">
            <!-- User Info -->
            <div class="panel panel-default" style="margin-bottom:7px">
                <div class="panel-body">
                    <g:render template="/user/userInfo" bean="${user}"/>
                </div> <!-- panel-body -->
            </div> <!-- panel -->

        <!-- topics created -->
            <div id="topicsCreated" data-ajax-url="${createLink(controller: "topic", action: "getTopicsCreated")}" data-ajax-params="${[username:user.username] as grails.converters.JSON}">
                <g:render template="/topic/topicList" model="[header: 'Topics Created', hr:true, footer:'Loading...']" bean="${null}" var="topics"/>
            </div>
        </div> <!-- col-lgrailg-5 -->

        <div class="col-lg-7">


        <div class="panel panel-default">
            <div class="panel-heading">
                <h3 class="panel-title">Profile</h3>
            </div> <!-- panel-heading -->
            <div class="panel-body">
                <g:formRemote name="updateProfile" enctype="multipart/form-data" url="[controller: 'user', action: 'updateUser']" method="post" onSuccess="updateProfileStatus(data)">
                %{--<g:form controller="user" action="updateUser">--}%
                    <table width="100%" id="editProfile">
                        <tr>
                            <td>First name*</td>
                            <td><g:textField name="firstName" class="form-control" value="${user.firstName}"/> </td>
                        </tr>
                        <tr>
                            <td>Last name*</td>
                            <td><g:textField name="lastName" class="form-control" value="${user.lastName}"/> </td>
                        </tr>
                        <tr>
                            <td>Username*</td>
                            <td><g:textField name="user-name" readonly="true" class="form-control" value="${user.username}"/> </td>
                        </tr>
                        <tr>
                            <td>Email*</td>
                            <td><g:textField name="email" class="form-control" value="${user.email}"/> </td>
                        </tr>
                        <tr>
                            <td>Photo</td>
                            <td><input type="file" id="photo" name="photo" class="form-control"/>
                                <div style="margin-top: 10px">
                                    <g:checkBox name="removePhoto"/> Remove Photo
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td colspan="2"><g:submitButton name="submit" value="Update" class="btn btn-default" style="width: 100%"/>
                            </td>
                        </tr>
                    </table>
                </g:formRemote>
                %{--</g:form>--}%
                %{--<div id="updateProfileStatus" align="center" style="margin-top: 10px; margin-bottom: 10px;"></div>--}%
            </div>    <!-- panel-body -->
        </div>  <!-- panel -->

        <div class="panel panel-default">
            <div class="panel-heading">
                <h3 class="panel-title">Change password</h3>
            </div> <!-- panel-heading -->
            <div class="panel-body">
                <g:formRemote name="updateProfile" url="[controller: 'user', action: 'changePassword']" method="post" onSuccess="changePasswordStatus(data)">
                %{--<g:form controller="user" action="changePassword">--}%
                    <table width="100%" id="editProfile">
                        <tr>
                            <td>Current Pasword*</td>
                            <td><g:textField name="currentPassword" class="form-control"/> </td>
                        </tr>
                        <tr>
                            <td>New Password*</td>
                            <td><g:textField name="newPassword" class="form-control"/> </td>
                        </tr>
                        <tr>
                            <td>Confirm password*</td>
                            <td><g:textField name="confirmPassword" class="form-control"/> </td>
                        </tr>
                        <tr>
                            <td colspan="2"><g:submitButton name="submit" value="Update" class="btn btn-default" style="width: 100%"/>
                            </td>
                        </tr>
                    </table>
                %{--</g:form>--}%
                </g:formRemote>
                %{--<div id="changePasswordStatus" align="center" style="margin-top: 10px; margin-bottom: 10px;"></div>--}%
            </div>    <!-- panel-body -->
        </div>  <!-- panel -->


        </div> <!-- col-lg-7 -->

    </div><!-- /.row -->
</div>
</body>
</html>