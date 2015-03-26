%{--
variables required:
search: boolean, for enabling or disabling search bar
viewAll: boolean, for enabling disabling viewAll option in panel header (only implemented for subscribed topic as of now)
searchSubscription: boolean, if true search within the subscribed topics of users else from public topics
header: string, panel header
topics: List<Resource>, resource list, can be empty.
totalCount: integer, for pagination
doPaginate: boolean, for enabling or disabling pagination
      ->  ajaxController: String, controller to hit for pagination
      ->  ajaxAction: String, action to hit for pagination
      ->  ajaxParams: HashMap, params for pagination
--}%


<div class="panel panel-default">
    <div class="panel-heading">
        <g:if test="${viewAll==true}">
            <span style="float:right;"><g:link controller="topic" action="viewAllSubscribedTopics">View All</g:link> </span>
        </g:if>
    <span style="float:right;">
        <g:if test="${search==true}">
            <g:formRemote name="search" url="[controller:'search', action: 'searchTopic']" method="get" update="topicPagination"  style="margin: -5px">
                <div class="input-group">
                    <g:textField name="search" placeholder="Search" aria-describedby="basic-addon2" required="required" title="Please enter some text to search"/>
                    <g:hiddenField name="searchSubscription" value="${searchSubscription}"/>
                </div>
            </g:formRemote>
        </g:if>
    </span>
        <h3 class="panel-title">${header?:"Topics"}</h3>
    </div> <!-- panel-heading -->
    <div class="panel-body">
        <g:if test="${!topics?.isEmpty()}">
            <g:render template="/topic/topicInfo" collection="${topics}" var="topic"/>
        </g:if>
        <g:elseif test="${!footer}">
            No topics to show!
        </g:elseif>
    </div>    <!-- panel-body -->

    <g:if test="${doPaginate}">
        <div class="pagination center-block" style="text-align: center">
            <util:remotePaginate name="paginate" update="topicPagination" max="5" controller="${ajaxController}"
                                 action="${ajaxAction}" params='${ajaxParams}' total="${totalCount?:0}"/>
        </div>
    </g:if>
    <g:if test="${footer}">
        <div class="panel-footer">
            ${footer}
        </div>
    </g:if>
</div>  <!-- panel -->

