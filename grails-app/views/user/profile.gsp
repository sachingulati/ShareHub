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

<div>
    <div class="row">
        <div class="col-lg-5">
            <!-- User Info -->
            <div class="panel panel-default" style="margin-bottom:7px">
                <div class="panel-body">
                    <g:render template="/user/userInfo" bean="${user}" model="[profileHeader: true]"/>
                </div> <!-- panel-body -->
            </div> <!-- panel -->

        <!-- topics created -->
            <div id="topicsCreated" data-ajax-url="${createLink(controller: "topic", action: "getTopicsCreated")}" data-ajax-params="${[username:user.username] as grails.converters.JSON}">
                <g:render template="/topic/topicList" model="[header: 'Topics Created', hr:true, footer:'Loading...']" bean="${null}" var="topics"/>
            </div>
        </div> <!-- col-lgrailg-5 -->

        <div class="col-lg-7">
            <g:if test="${myProfile}">
                <div style="margin: 10px 0px; width: 100%">
                    <g:link controller="user" action="editProfile" class="btn btn-default" style="width: 100%">Edit Profile</g:link>
                </div>
            </g:if>

            <div id="resourcesCreated" data-ajax-url="${createLink(controller: "resource", action: "renderResourcesCreated")}" data-ajax-params="${[username:user.username] as grails.converters.JSON}">
                <g:render template="/resource/resourceList" bean="${null}" var="resources" model="[header: 'Posts', search: true, footer:'Loading...']"/>
            </div>
            %{--<g:render template="/resource/posts" bean="${resources}" var="resources" model="[header: 'Posts', search: true]"/>--}%
        </div> <!-- col-lg-7 -->

    </div><!-- /.row -->
</div>
</body>
</html>