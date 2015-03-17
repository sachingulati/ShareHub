<%@ page import="sharehub.Topic" %>

<div class="panel panel-default">
    <div class="panel-heading">
        <span style="float:right;"><a href="javascript:void(0)">View All</a></span>

        <h3 class="panel-title">${header}</h3>
    </div> <!-- panel-heading -->
    <div class="panel-body">
    %{--<g:render template="/post" collection="${resources}" var="resource"/>--}%
    <g:render template="/topic/topicInfo" collection="${topics}" var="topic"/>
    </div>    <!-- panel-body -->
    <g:if test="${footer}">
        <div class="panel-footer">
            ${footer}
        </div>
    </g:if>
</div>  <!-- panel -->

