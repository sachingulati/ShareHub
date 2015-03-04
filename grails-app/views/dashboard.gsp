<%--
  Created by IntelliJ IDEA.
  User: intelligrape
  Date: 25/2/15
  Time: 1:28 PM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="afterLogin"/>
    <title>Dashboard</title>
</head>

<body>

<div class="contentMargin">
    <div class="row">
        <div class="col-lg-5">
            <!-- User Info -->
            <div class="panel panel-default" style="margin-bottom:7px">
                <div class="panel-body">
                    <g:render template="/userInfo"/>
                </div> <!-- panel-body -->
            </div> <!-- panel -->

        <!-- subscription -->
        <g:render template="/subscriptions"/>
            <div class="panel panel-default">
                <div class="panel-heading">
                    <span style="float:right;"><a href="#">View All</a></span>

                    <h3 class="panel-title">Trending Topics</h3>
                </div> <!-- panel-heading  -->

                <div class="panel-body">
                    <div>
                        <div style="float:left; margin-right:10px">
                            <a href="#"><asset:image src="user-default.png" class="img-media" alt="User image"/></a>
                        </div>

                        <div>
                            <div>
                                <a href="#">${topicName ? topicName : "null"}</a>
                            </div>
                            <table>
                                <tr>
                                    <td class="tableData tableDataWidth">@${username}</td>
                                    <td class="tableData tableDataWidth">Subscriptions</td>
                                    <td class="tableData">Posts</td>
                                </tr>
                                <tr>
                                    <td class="tableData tableDataWidth"><a href="#">Subscribe</a></td>
                                    <td class="tableData tableDataWidth"><a href="#">${subCount ?: 0}</a></td>
                                    <td class="tableData"><a href="#">${postCount ?: 0}</a></td>
                                </tr>
                            </table>
                        </div>
                        <hr>

                        <!--  </div>    <!-- panel-body -->
                    </div>

                </div> <!-- panel-body -->
            </div> <!-- panel -->

        </div> <!-- col-lg-5 -->

        <div class="col-lg-7">
            <g:render template="/posts" model="[header: 'Inbox', resources: unreadResources]"/>
        </div> <!-- col-lg-7 -->

    </div><!-- /.row -->
</div>

</body>
</html>