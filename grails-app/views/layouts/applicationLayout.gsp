<%--
  Created by IntelliJ IDEA.
  User: intelligrape
  Date: 25/2/15
  Time: 1:26 PM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html xmlns="http://www.w3.org/1999/html">
<head>
    <title><g:layoutTitle default="Share Hub"/></title>

    %{--<g:javascript library="jquery"/>--}%
    <g:setProvider library="jquery"/>

    <!-- jquery -->
    <asset:javascript src="jquery-2.1.3.min.js"/>

    <!-- Latest compiled and minified CSS -->
    <asset:stylesheet src="bootstrap.min.css"/>

    <!-- Optional theme -->
    <asset:stylesheet src="bootstrap-theme.min.css"/>

    <!-- Latest compiled and minified JavaScript -->
    <asset:javascript src="bootstrap.min.js"/>

    <!-- bootstrap modal -->
    <asset:javascript src="modal.js"/>

    <!-- my css -->
    <asset:stylesheet src="application.css"/>



    %{--jquery validation--}%
    <asset:javascript src="jquery.validate.min.js"/>
    <asset:javascript src="tooltip"/>
    <g:render template="/vars" model="[loggedIn: true]"/>
    <asset:javascript src="application.js"/>
    <asset:javascript src="reports.js"/>
    <script src="https://apis.google.com/js/platform.js" async defer></script>
    <g:layoutHead/>
</head>

<body>
<div id="fb-root"></div>
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
            <g:link class="navbar-brand" controller="login">Share Hub</g:link>
        </div>
        <!-- Collect the nav links, forms, and other content for toggling -->
        <g:if test="${session["username"]}">
            <g:render template="/loggedInBar"/>
        </g:if>
        <g:else>
            <g:render template="/notLoggedInBar"/>
        </g:else>
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
<g:render template="/resource/shareLink"/>
<g:render template="/resource/shareDocument"/>
<g:render template="/topic/createTopic"/>
<g:render template="/topic/editTopic"/>
<g:render template="/invites/sendInvite"/>

<div class="${session["username"]?"afterLogin":"beforeLogin"}" id="contentBody">
    <g:layoutBody/>
</div>
</body>
</html>