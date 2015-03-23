<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Login</title>
    <meta name="layout" content="beforeLogin">
    <asset:javascript src="registration.js"/>
</head>

<body>

<div class="contentMargin">

    <div class="panel panel-default center-block" style="max-width: 500px; margin-top: 120px">
        <div class="panel-heading">
            <h3 class="panel-title">Forgot Password</h3>
        </div>  <!-- panel-heading -->
        <div class="panel-body">
            <g:form controller="login" action="updatePassword">
                <label style="margin-left: 2px">New password:</label> <br>
                <g:hiddenField name="token" value="${token}"/>
                <g:passwordField name="password" class="form-control" placeholder="New password" required="required" title="Password cannot be empty!"/>
                <label style="margin-left: 2px">Confirm password:</label> <br>
                <g:passwordField name="confirmPassword" class="form-control" placeholder="Confirm password" required="required" title="Confirm password cannot be empty!"/>
                <div class="text-danger" style="margin-top: 20px; display: inline-block">${flash.userNotFound}</div>
                <span style="float: right; margin: 0px; padding-top: 10px">
                    <g:submitButton name="submit" value="Change password" class="btn btn-primary"/>
                    <g:link controller="login" class="btn-default btn">Cancel</g:link>
                </span>
            </g:form>
        </div>  <!-- panel-body -->
    </div>
</body>
</html>
