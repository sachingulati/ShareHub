<div class="panel panel-default" style="margin-bottom:7px">
    <div class="panel-body">
        <div style="float:left; margin-right:10px">
            <a href="#"><asset:image src="user-default.png" class="img-media" alt="User image"/></a>
        </div>

        <div>
            <div>
                <a href="#">${name ? name : "TopicName"}</a> (public)
            </div>

            <table>
                <tr>
                    <td class="userTableData">@${username ? username : "username"}</td>
                    <td class="userTableData">Subscriptions</td>
                    <td class="userTableData">Posts</td>
                </tr>
                <tr>
                    <td class="userTableData"><a href="#">Subscribe</a></td>
                    <td class="userTableData"><a href="#">${subCount ? subCount : 0}</a></td>
                    <td class="userTableData"><a href="#">${postCount ? postCount : 0}</a></td>
                </tr>
            </table>

            <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                <ul class="nav navbar-nav navbar-right">
                    <li>
                        <form class="navbar-form navbar-left" role="search">
                            <div class="form-group">
                                <g:select name="shareLinkTopic" from="${['Serious', 'Very Serious']}"
                                          class="form-control"/>
                            </div>
                            <!-- <button type="submit" class="btn btn-default">Search</button> -->
                        </form>
                    </li>
                    <li>
                        <button type="button" class="btn btn-default" aria-label="Left Align"
                                style="margin-top:10px;margin-right: 40px" onclick="openSendInvite()">
                            <span class="glyphicon glyphicon-envelope" aria-hidden="true"></span>
                        </button>
                    </li>
                </ul>
            </div>
        </div>
    </div> <!-- panel-body -->
</div> <!-- panel -->
