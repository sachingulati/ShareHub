<%--
  Created by IntelliJ IDEA.
  User: intelligrape
  Date: 27/2/15
  Time: 2:02 AM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta content="afterLogin" name="layout">
    <title>Topic: ${topic.name}</title>
</head>

<body>
<div class="row contentMargin">
    <div class="col-lg-5">
        <div class="panel panel-default">
            <div class="panel-body">
        <g:render template="/topicInfo" bean="${topic}" var="topic"/>
</div>
            </div>
        <g:render template="/userList" model='[topicName: "${topic.name}"]' bean="${topic.subscriptions.user}" var="users"/>
    </div>
    <div class="col-lg-7">
        <g:render template="/posts" model='[header:"Posts: ${topic.name}"]' bean="${topic.resources}" var="resources"/>
    </div>
</div>
</body>
</html>