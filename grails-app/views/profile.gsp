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

<div class="contentMargin">
    <div class="row">
        <div class="col-lg-5">
            <!-- User Info -->
            <div class="panel panel-default" style="margin-bottom:7px">
                <div class="panel-body">
                    <g:render template="/userInfo" bean="${user}"/>
                </div> <!-- panel-body -->
            </div> <!-- panel -->

        <!-- subscription -->
        <g:render template="/topicList" model="[header: 'Topics', hr: true]" bean="${user.topicsCreated}" var="topics"/>
        </div> <!-- col-lgrailg-5 -->

        <div class="col-lg-7">
            <g:render template="/posts" bean="${resources}" var="resources" model="[header: 'Posts', search: true]"/>
        </div> <!-- col-lg-7 -->

    </div><!-- /.row -->
</div>

</body>
</html>