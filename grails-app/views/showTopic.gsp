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
    <title>Topic: TopicName</title>
</head>

<body>
<div class="row contentMargin">
    <div class="col-lg-5">
        <g:render template="/topicInfo"/>
        <g:render template="/userList"/>
    </div>
    <div class="col-lg-7">
        <g:render template="/posts" model="[header:'Posts: TopicName']"/>
    </div>
</div>
</body>
</html>