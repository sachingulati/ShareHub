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
            <div id="topicPagination" data-ajax-url="${createLink(controller: "topic", action: "getRecentSubscribedTopics")}" data-ajax-params="${[offset: 0, max: 5] as grails.converters.JSON}">
                <g:render template="/topic/topicList" model="[topics: null, header: 'Subscriptions', hr:true, footer:'Loading...', viewAll: true, searchSubscription: true]"/>
            </div>
            %{--Trending Topics--}%
            <div id="trendingTopics" data-ajax-url="${createLink(controller: "topic", action: "getTrendingTopics")}" data-ajax-params="${[offset: 0, max: 5] as grails.converters.JSON}">
                <g:render template="/topic/topicList" model="[header: 'Trending Topics', hr:true, footer:'Loading...']" bean="${null}" var="topics"/>
            </div>
        </div> <!-- col-lg-5 -->

        <div class="col-lg-7">
        <div class="afterReport">
            <div id="resourceList" name="unreadResources" class="applyPaginate" data-ajax-url="${createLink(controller: "resource", action: "renderUnreadResources")}" data-ajax-params="${[offset: 0, max: 10] as grails.converters.JSON}">
                <g:render template="/resource/resourceList" bean="${null}" var="resources" model="[header: 'Inbox', search: true, footer:'Loading...', doPaginate:true, ajaxController: 'resources', ajaxAction: 'renderUnreadResources']"/>
            </div>
        </div>
        </div> <!-- col-lg-7 -->

    </div><!-- /.row -->
</div>
</body>
</html>