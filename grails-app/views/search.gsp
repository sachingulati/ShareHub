<%--
  Created by IntelliJ IDEA.
  User: intelligrape
  Date: 25/2/15
  Time: 1:28 PM
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="applicationLayout"/>
    <title>Search: ${search}</title>
</head>

<body>

<div>

    <div class="row">
        <div class="col-lg-5">

            <!-- subscription -->
            <div id="topicPagination" data-ajax-url="${createLink(controller: "search", action: "searchTopic")}" data-ajax-params="${[offset: 0, max: 5, search: search] as grails.converters.JSON}">
                <g:render template="/topic/topicList" model="[topics: null, header: 'Topic search: ' + search, hr:true, footer:'Loading...', viewAll: true, searchSubscription: true]"/>
            </div>
%{--
            <!-- subscription -->
            <div id="trendingTopics" data-ajax-url="${createLink(controller: "topic", action: "getTrendingTopics", params: [offset: 0, max: 5])}">
                <g:render template="/topic/topicList" model="[header: 'Trending Topics', hr:true, footer:'Loading...']" bean="${null}" var="topics"/>
            </div>
            --}%%{--Top Post--}%%{--
            <div id="topPost" data-ajax-url="${createLink(controller: "resource", action: "renderTopPost", params: [offset: 0, max: 5])}">
                <g:render template="/resource/resourceList" bean="${null}" var="resources" model="[header:'Top Posts', search:false]"/>
            </div>--}%
        </div> <!-- col-lg-5 -->

        <div class="col-lg-7" id="resourceList">
        <div class="afterReport">
            <div id="resourceList" class="applyPaginate" data-ajax-url="${createLink(controller: "search", action: "searchResources")}" data-ajax-params="${[offset: 0, max: 10, search: search] as grails.converters.JSON}">
                <g:render template="/resource/resourceList" bean="${resources}" var="resources"
                          model="[header: 'Resource search: '+search, search: false, doPaginate:true, ajaxController: 'search', ajaxAction: 'searchResources']"/>
            </div>
        </div>
        </div> <!-- col-lg-7 -->

    </div><!-- /.row -->
</div>
</body>
</html>