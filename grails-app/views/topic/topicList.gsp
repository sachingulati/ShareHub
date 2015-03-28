<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="applicationLayout"/>
    <title>Topics</title>
    ${doPaginate=false}
</head>
<body>
<div>
    <div class="row">

        <div class="col-lg-5">
            <!-- Topic List -->
            <div id="topicList" data-ajax-url="${ajaxUrl}" data-ajax-params="${ajaxParams}">
                <g:render template="/topic/topicList" model="[header: header, hr:true, footer:'Loading...']" bean="${null}" var="topics"/>
            </div>
        </div> <!-- col-lg-5 -->

        <div class="col-lg-7">
            <div class="afterReport">
                <div id="resourceList" class="applyPaginate" data-ajax-params="${[offset: 0, max: 10] as grails.converters.JSON}">
                    <g:render template="/resource/resourceList" model="[resources: null, header: 'Posts',
                                doPaginate:false, footer: 'Click on a topic to show its posts...']"/>
                </div>
            </div>
        </div> <!-- col-lg-7 -->
    </div><!-- /.row -->
</div>
<asset:javascript src="topicList.js"/>
</body>
</html>