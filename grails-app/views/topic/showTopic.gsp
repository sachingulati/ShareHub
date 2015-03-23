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
    <title>Topic: ${topic?.name}</title>
</head>

<body>
<div class="row">
    <g:if test="${topic}">
    <div class="col-lg-5">
        <div class="panel panel-default">
            <div class="panel-body">
                <g:render template="/topic/topicInfo" bean="${topic}" var="topic" model="[isHead: 'header']"/>
            </div>
        </div>
        <g:render template="/user/userList" model='[topicName: "${topic.name}"]' bean="${topic.subscriptions.user}" var="users"/>
    </div>
    <div class="col-lg-7">
        <g:render template="/resource/resourceList" model='[header:"Posts: ${topic.name}"]' bean="${topic.resources}" var="resources"/>
    </div>
    </g:if>
    <g:else>
        <div class="bg-danger" style="padding: 10px">
            <h4>Topic Not found!</h4>
        </div>
    </g:else>
</div>
</body>
</html>