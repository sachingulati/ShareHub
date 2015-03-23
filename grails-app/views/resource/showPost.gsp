<%--
  Created by IntelliJ IDEA.
  User: intelligrape
  Date: 8/3/15
  Time: 2:34 PM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="afterLogin"/>
    <title>${resource?.title}</title>
    <script>
    </script>
</head>

<body>

<div>
    <div class="row">
        <div class="col-lg-7">
            <g:if test="${resource}">
                <div class="panel panel-default" style="margin-bottom:7px">
                <div class="panel-body">
                    <div class="row">
                        <div class="col-lg-6">
                            <div style="float:left; margin-right: 10px">
                                <a href="javascript:void(0)">
                                    <sh:image src="${resource.createdBy.photoUrl}"/>
                                </a>
                            </div>
                            <div class="padding5">
                                <div>
                                    <g:link controller="user" action="profile" params='[id:"${resource.createdBy.username}"]'>${resource.createdBy.name}</g:link>
                                </div>
                                <div>
                                    @${resource.createdBy.username}
                                </div>
                                <div style="margin-top: 10px">
                                    <g:link controller="resource" action="showPost" params='[id:  "${resource.id}"]'>${resource.title}</g:link>
                                </div>
                            </div>
                        </div>
                        <div class="col-lg-6">
                            <div class="row">
                                <span style="float:right; margin-right: 10px">
                                        <g:if test="${resource.topic.visibility==com.sharehub.enums.Visibility.PRIVATE}">
                                            <a href="${g.createLink(controller: "topic", action: "showTopic", params: [id: resource.topic.id])}" style="color:red;">
                                        </g:if>
                                        <g:else>
                                            <a href="${g.createLink(controller: "topic", action: "showTopic", params: [id: resource.topic.id])}">
                                        </g:else>
                                        ${resource.topic.name}</a>
                                </span>
                            </div>
                            <div class="row">
                                <span style="float:right; margin-right: 10px">
                                    <sh:date date="${resource.dateCreated}"/>
                                    %{--${resource.dateCreated}--}%
                                </span>
                            </div>
                            <div class="row">
                                <span class="rate" style="float:right; margin-right: 10px" data-my-rating="${myRating}">
                                    <g:each in="${1..5}" var="id">
                                        <img class="ratingHearts" data-id="${id}" data-resource-id="${resource.id}" width="25" height="25"/>
                                    </g:each>
                                    <div>
                                        Avg. Rating: <span class="avgRating" style="margin: 2px">${rating.avgRating?:0}</span> (<span class="totalCount" style="margin: 2px">${rating.totalCount}</span>)
                                    </div>
                                </span>
                            </div>

                        </div>
                    </div>
                    <div class="row">
                        <div id="recentShareDesc" style="margin: 15px">
                            ${resource.description}
                        </div>
                        <div style="float:right; margin: 10px">
                            <g:if test="${resource.type==com.sharehub.enums.ResourceType.DOCUMENT}">
                                <a href="${createLink(controller: "resource", action: "download", params: [resourceId: resource.id])}" class="inboxLinkStyle" data-ajax-url="${createLink(controller: "resource", action: "download", params: [resourceId: resource.id])}">Download</a>
                            </g:if>
                            <g:else>
                                <a href="${'http://' + resource.url}" class="inboxLinkStyle" target="_blank">View full site</a>
                            </g:else>
                            <sh:markRead resourceId="${resource.id}"/>
                        </div>
                    </div>
                </div> <!-- panel-body -->
            </div> <!-- panel -->
            </g:if>
            <g:else>
                <div class="bg-danger" style="padding: 10px">
                    <h4>Post Not found!</h4>
                </div>
            </g:else>
        </div>
        <div class="col-lg-5">
            %{--Trending Topics--}%
            <div id="trendingTopics" data-ajax-url="${createLink(controller: "topic", action: "getTrendingTopics")}">
                <g:render template="/topic/topicList" model="[header: 'Trending Topics', hr:true, footer:'Loading..']" bean="${null}" var="topics"/>
            </div>
        </div>
    </div>
</div>
<script>
    reLoadContent($("#trendingTopics"));
</script>
</body>
</html>