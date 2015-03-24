
<div class="panel panel-default" xmlns="http://www.w3.org/1999/html">
    <div class="panel-heading" id="resourceList">
        <g:if test="${search}">
            <span style="float:right;">
                <g:formRemote name="search" url="[controller:'search', action: 'inboxSearch']" method="get" update="resourceList"  style="margin: -5px">
                    <div class="input-group">
                        <g:textField name="searchString" placeholder="Search" aria-describedby="basic-addon2"/>
                    </div>
                </g:formRemote>
            </span>
        </g:if>
        <h3 class="panel-title">${header}</h3>
    </div>  <!-- panel-heading -->
    <div class="panel-body" name="resourceListPage">
        <g:if test="${!resources?.isEmpty()}">
            <g:render template="/resource/resource" collection="${resources}" var="resource"/>
        </g:if>
        <g:elseif test="${!footer}">
            No posts to show!
        </g:elseif>
    </div>  <!-- panel-body -->

    <g:if test="${doPaginate}">
        <div class="pagination center-block" style="text-align: center">
            <util:remotePaginate name="paginate" update="resourceList" max="10" controller="${ajaxController}" action="${ajaxAction}" params='${ajaxParams}' total="${resources?resources.getTotalCount():0}"/>
        </div>
    </g:if>
    <g:if test="${footer}">
        <div class="panel-footer">
            ${footer}
        </div>
    </g:if>
</div> <!-- panel -->
