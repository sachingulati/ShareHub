<%--
  Created by IntelliJ IDEA.
  User: intelligrape
  Date: 25/2/15
  Time: 1:26 PM
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

    <!-- bootstrap modal -->
    <asset:javascript src="modal.js"/>

    <!-- my css -->
    <asset:stylesheet src="mycss.css"/>

    <script>
        function openShareLink() {
            var options = {
                "backdrop": "true",
                "keyboard": "true"
            }
            $('#shareLink').modal(options);
        }

        function openShareDocument() {
            var options = {
                "backdrop": "true",
                "keyboard": "true"
            }
            $('#shareDocument').modal(options);
        }

        function openCreateTopic() {
            var options = {
                "backdrop": "true",
                "keyboard": "true"
            }
            $('#createTopic').modal(options);
        }
        function openSendInvite() {
            var options = {
                "backdrop": "true",
                "keyboard": "true"
            }
            $('#sendInvite').modal(options);
        }
        function showMessage(){
            if("${message}".length>0){
                alert("${message}");
            }
        }
    </script>
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
                    <form class="navbar-form navbar-left" role="search">
                        <div class="form-group">
                            <input type="text" class="form-control" placeholder="Search">
                        </div>
                        <!-- <button type="submit" class="btn btn-default">Search</button> -->
                    </form>
                </li>
                <li>
                    <button type="button" class="btn btn-default" aria-label="Left Align" style="margin-top:10px">
                        <span class="glyphicon glyphicon-comment" aria-hidden="true" onclick="openCreateTopic()"></span>
                    </button>
                </li>
                <li>
                    <button type="button" class="btn btn-default" aria-label="Left Align" style="margin-top:10px" onclick="openSendInvite()">
                        <span class="glyphicon glyphicon-envelope" aria-hidden="true"></span>
                    </button>
                </li>
                <li>
                    <button type="button" class="btn btn-default" aria-label="Left Align" style="margin-top:10px" onclick="openShareLink()">
                        <span class="glyphicon glyphicon-link" aria-hidden="true"></span>
                    </button>
                </li>
                <li>
                    <button type="button" class="btn btn-default" aria-label="Left Align" style="margin-top:10px" onclick="openShareDocument()">
                        <span class="glyphicon glyphicon-paperclip" aria-hidden="true"></span>
                    </button>
                </li>
                <li>
                    <div class="dropdown">
                        <button class="btn btn-default dropdown-toggle" type="button" id="dropdownMenu1"
                                data-toggle="dropdown" style="margin:7px" aria-expanded="true">
                            <sh:username/>
                            <span class="caret"></span>
                        </button>
                        <ul class="dropdown-menu" role="menu" aria-labelledby="dropdownMenu1">
                            <li role="presentation"><a role="menuitem" tabindex="-1" href="#">Profile</a></li>
                            <li role="presentation"><a role="menuitem" tabindex="-1" href="#">Users</a></li>
                            <li role="presentation"><a role="menuitem" tabindex="-1" href="#">Topics</a></li>
                            <li role="presentation"><a role="menuitem" tabindex="-1" href="#">Posts</a></li>
                            <li role="presentation">
                                <g:link controller="user" action="logout" tabindex="-1">Logout</g:link></li>
                        </ul>
                    </div>
                </li>
            </ul>
        </div><!-- /.navbar-collapse -->
    </div><!-- /.container-fluid -->
</nav>
<g:render template="/shareLink"/>
<g:render template="/shareDocument"/>
<g:render template="/createTopic"/>
<g:render template="/sendInvite"/>
<g:layoutBody/>
<script>
    showErrors();
</script>
</body>
</html>