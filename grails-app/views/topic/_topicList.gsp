<div class="panel panel-default">
    <div class="panel-heading">
        <g:if test="${viewAll==true}">
            <span style="float:right;"><g:link controller="topic" action="viewAllSubscribedTopics">View All</g:link> </span>
        </g:if>
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

