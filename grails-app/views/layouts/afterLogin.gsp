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
    <asset:stylesheet src="mycss.css"/>


    %{--jquery validation--}%
    <asset:javascript src="jquery.validate.min.js"/>

    <g:render template="/vars"/>
    <asset:javascript src="afterLogin.js"/>
    <asset:javascript src="reports.js"/>
    <g:layoutHead/>
</head>

<body>

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
        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
            <ul class="nav navbar-nav navbar-right">
                <li>
                    <g:form class="navbar-form navbar-left" method="get" role="search" controller="search">
                        <div class="form-group">
                            <sh:topBarSearch/>
                        </div>
                    </g:form>
                </li>
                <li>
                    <button type="button" class="btn btn-default" aria-label="Left Align" style="margin-top:10px" onclick="openCreateTopic()" data-toggle="tooltip" data-placement="bottom" title="Create Topic">
                        <span class="glyphicon glyphicon-comment" aria-hidden="true"></span>
                    </button>
                </li>
                <li>
                    <button type="button" class="btn btn-default" aria-label="Left Align" style="margin-top:10px" onclick="openSendInvite()" data-toggle="tooltip" data-placement="bottom" title="Send Invite">
                        <span class="glyphicon glyphicon-envelope" aria-hidden="true"></span>
                    </button>
                </li>
                <li>
                    <button type="button" class="btn btn-default" aria-label="Left Align" style="margin-top:10px" onclick="openShareLink()" data-toggle="tooltip" data-placement="bottom" title="Share Link">
                        <span class="glyphicon glyphicon-link" aria-hidden="true"></span>
                    </button>
                </li>
                <li>
                    <button type="button" class="btn btn-default" aria-label="Left Align" style="margin-top:10px" onclick="openShareDocument()" data-toggle="tooltip" data-placement="bottom" title="">
                        <span class="glyphicon glyphicon-paperclip" aria-hidden="true"></span>
                    </button>
                </li>
                <li>
                    <div class="dropdown">
                        <button class="btn btn-default dropdown-toggle" type="button" id="dropdownMenu1"
                                data-toggle="dropdown" style="margin:7px" aria-expanded="true">
                            <sh:userName/>
                            <span class="caret"></span>
                        </button>
                        <ul class="dropdown-menu" role="menu" aria-labelledby="dropdownMenu1">
                            <li role="presentation"><a role="menuitem" tabindex="-1" href="${g.createLink(controller: "user", action: "myProfile")}">Profile</a></li>
                            <li role="presentation"><sh:admin/></li>
                            <li role="presentation"><a role="menuitem" tabindex="-1" href="javascript:void(0)">Topics</a></li>
                            <li role="presentation"><a role="menuitem" tabindex="-1" href="javascript:void(0)">Posts</a></li>
                            <li role="presentation">
                                <g:link controller="user" action="logout" tabindex="-1">Logout</g:link></li>
                        </ul>
                    </div>
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
<g:render template="/resource/shareLink"/>
<g:render template="/resource/shareDocument"/>
<g:render template="/topic/createTopic"/>
<g:render template="/topic/editTopic"/>
<g:render template="/invites/sendInvite"/>

<div class="contentMargin" id="contentBody">
    <g:layoutBody/>
</div>
<script>

</script>
</body>
</html>