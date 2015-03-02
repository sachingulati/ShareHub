<%--
  Created by IntelliJ IDEA.
  User: intelligrape
  Date: 25/2/15
  Time: 1:13 PM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
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
    <g:layoutHead/>

</head>

<body>

<nav class="navbar navbar-default">
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
            <a class="navbar-brand" href="#">Share Hub</a>
        </div>

        <!-- Collect the nav links, forms, and other content for toggling -->
        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
            <ul class="nav navbar-nav navbar-right">
                <li>
                    <g:form controller="login" action="loginHandler" class="navbar-form navbar-left" role="search">
                        <div class="form-group">
                            ${error}
                            <g:textField name="username" class="form-control" placeholder="Username"/>
                            <g:passwordField name="password" class="form-control" placeholder="Password"/>
                        </div>
                        <g:submitButton name="Login" class="btn btn-default">Login</g:submitButton>
                    </g:form>

                </li>
            </ul>
        </div><!-- /.navbar-collapse -->
    </div><!-- /.container-fluid -->
</nav>

<g:layoutBody/>
</body>
</html>