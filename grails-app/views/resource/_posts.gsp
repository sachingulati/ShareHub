
<div class="panel panel-default">
    <div class="panel-heading">
        <g:if test="${search}">
            <span style="float:right;">
                <g:formRemote name="search" url="[controller:'search', action: 'inboxSearch']" method="get" update="resourceList">
                    <g:textField name="searchString" placeholder="Search"/>
                    <g:submitButton name="Search" value="Search"/>
                </g:formRemote>
            </span>
        </g:if>
        <h3 class="panel-title">${header}</h3>
    </div>  <!-- panel-heading -->
    <div class="panel-body">
            <g:render template="/resource/post" collection="${resources}" var="resource"/>
    </div>  <!-- panel-body -->
    <g:if test="${footer}">
        <div class="panel-footer">
            ${footer}
        </div>
    </g:if>
</div> <!-- panel -->
