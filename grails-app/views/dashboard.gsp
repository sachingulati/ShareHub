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

<div>
    <div class="row">
        <div class="col-lg-5">
            %{--<!-- User Info -->--}%
            <div class="panel panel-default" style="margin-bottom:7px">
                <div class="panel-body">
                    <g:render template="/user/userInfo" bean="${user}"/>
                </div> <!-- panel-body -->
            </div> <!-- panel -->

            <!-- subscription -->
            <div id="subscriptionList" data-ajax-url="${createLink(controller: "topic", action: "getRecentSubscribedTopics")}">
                <g:render template="/topic/topicList" model="[header: 'Subscriptions', hr:true, footer:'Loading..']" bean="${null}" var="topics"/>
            </div>
            %{--Trending Topics--}%
            <div id="trendingTopics" data-ajax-url="${createLink(controller: "topic", action: "getTrendingTopics")}">
                <g:render template="/topic/topicList" model="[header: 'Trending Topics', hr:true, footer:'Loading..']" bean="${null}" var="topics"/>

            </div>
        </div> <!-- col-lg-5 -->

        <div class="col-lg-7" id="resourceList">
            <div id="unreadResources" data-ajax-url="${createLink(controller: "resource", action: "unreadResourceList")}">
                <g:render template="/resource/posts" bean="${null}" var="resources" model="[header: 'Inbox', search: true, footer:'Loading..']"/>
            </div>
        </div> <!-- col-lg-7 -->

    </div><!-- /.row -->
</div>
<script>/*
    $('#unreadResources').on('load',function(){
        reLoadContent($(this));
    })
    $('#subscriptionList').on('load', function(){
        reLoadContent($(this));
    })
    $('#trendingTopics').on('load', function(){
        reLoadContent($(this), "");
    })*//*
    $('#unreadResources').load();
    $('#subscriptionList').load();
    $('#trendingTopics').load();
*/

    reLoadContent($("#unreadResources"));
    reLoadContent($("#subscriptionList"));
    reLoadContent($("#trendingTopics"));

</script>
</body>
</html>