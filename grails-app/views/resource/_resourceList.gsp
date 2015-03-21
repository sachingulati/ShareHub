
<div class="panel panel-default" xmlns="http://www.w3.org/1999/html">
    <div class="panel-heading" id="resourceList">
        <g:if test="${search}">
            <span style="float:right;">
                <g:formRemote name="search" url="[controller:'search', action: 'inboxSearch']" method="get" update="resourceList"  style="margin: -5px">
                    <div class="input-group">
                        <g:textField name="searchString" placeholder="Search" aria-describedby="basic-addon2"/>
                        <button type="button" name="search" class="btn btn-default" aria-label="Left Align" id="basic-addon2">
                            <span class="glyphicon glyphicon-search" aria-hidden="true"></span>
                        </button>
                    </div>
                </g:formRemote>
            </span>
        </g:if>
        <h3 class="panel-title">${header}</h3>
    </div>  <!-- panel-heading -->
    <div class="panel-body" name="resourceListPage">
            <g:render template="/resource/resource" collection="${resources}" var="resource"/>
    </div>  <!-- panel-body -->

    <util:remotePaginate update="unreadResources" max="10" controller="resource" action="unreadResourceList" total="${resources?resources.getTotalCount():0}"/>

    <g:if test="${footer}">
        <div class="panel-footer">
            ${footer}
        </div>
    </g:if>
</div> <!-- panel -->
