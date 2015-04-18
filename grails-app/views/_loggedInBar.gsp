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
                    <sec:loggedInUserInfo field="name"/>
                    <span class="caret"></span>
                </button>
                <ul class="dropdown-menu" role="menu" aria-labelledby="dropdownMenu1">
                    <li role="presentation"><a role="menuitem" tabindex="-1" href="${g.createLink(controller: "user", action: "myProfile")}">Profile</a></li>
                    <sec:ifAllGranted roles="ROLE_ADMIN">
                        <li role="presentation">
                            <a href='${createLink(controller: "user", action: "adminPanel")}'>Users</a>
                        </li>
                    </sec:ifAllGranted>
                    <li role="presentation"><g:link controller="topic" role="menuitem" tabindex="-1" action="viewAllSubscribedTopics">Topics</g:link></li>
                    <li role="presentation">
                        %{--<g:remoteLink controller="logout" method="POST">Logout</g:remoteLink>--}%

                        <g:link controller="user" action="logout" tabindex="-1">Logout</g:link></li>
                </ul>
            </div>
        </li>
    </ul>
</div><!-- /.navbar-collapse -->
