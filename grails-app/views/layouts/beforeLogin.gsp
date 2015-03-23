<%--
  Created by IntelliJ IDEA.
  User: intelligrape
  Date: 25/2/15
  Time: 1:13 PM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html xmlns="http://www.w3.org/1999/html">
<head>
    <title><g:layoutTitle default="Share Hub"/></title>

    <!-- jquery -->
    <asset:javascript src="jquery-2.1.3.min.js"/>
    <!-- Latest compiled and minified CSS -->
    <asset:stylesheet src="bootstrap.min.css"/>

    <!-- Optional theme -->
    <asset:stylesheet src="bootstrap-theme.min.css"/>

    <!-- Latest compiled and minified JavaScript -->
    <asset:javascript src="bootstrap.min.js"/>

    <!-- my css -->
    <asset:stylesheet src="mycss.css"/>

    %{--jquery validation--}%
    <asset:javascript src="jquery.validate.min.js"/>
    <asset:javascript src="reports.js"/>
    <g:layoutHead/>

</head>

<body>
<g:render template="/vars"/>
<nav class="navbar navbar-default topBar">
    <div class="container-fluid">
        <!-- Brand and toggle get grouped for better mobile display -->
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse"
                    data-target="#bs-example-navbar-collapse-1">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <g:link class="navbar-brand" controller="Login">Share Hub</g:link>
        </div>

        <!-- Collect the nav links, forms, and other content for toggling -->
        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
            <ul class="nav navbar-nav navbar-right">
                <li style="padding-top: 20px; color: #ff0000">
                    ${flash.message}
                </li>
                <li>
                    <g:form controller="login" action="loginHandler" class="navbar-form navbar-left" role="search">
                        <div class="form-group">
                            <g:textField name="username" class="form-control" placeholder="Username"/>
                            <g:passwordField name="password" class="form-control" placeholder="Password"/>
                        </div>
                        <g:submitButton name="Login" class="btn btn-default">Login</g:submitButton>
                        <div class="row">
                            <div class="col-lg-5">
                                    <g:checkBox name="keepMeLogin"/> Keep me Login
                            </div>
                            <div class="col-lg-7">
                                <g:link controller="login" action="forgotPassword">Forgot Password?</g:link>
                            </div>
                        </div>
                    </g:form>
                </li>
            </ul>
        </div><!-- /.navbar-collapse -->
    </div><!-- /.container-fluid -->
    <div class="bg-info report" id="info">
        <h4 class="reportText"></h4>
    </div>
    <div class="bg-success report" id="successReport">
        <h4 class="reportText"></h4>
    </div>
    <div class="bg-danger report" id="warningReport">
        <h4 class="reportText"></h4>
    </div>

</nav>
<div style="margin-top: 85px">
<g:layoutBody/>
</div>%{--
    <script>
        alert("success: " + ${flash.successMessage});
        alert("error: " + ${flash.successMessage});
        <g:if test="${flash.successMessage}">
            successReport(${flash.successMessage});
        </g:if>
        <g:if test="${flash.errorMessage}">
            warningReport(${flash.errorMessage});
        </g:if>
    </script>--}%
</body>
</html>