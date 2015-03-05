
<div class="panel panel-default">
    <div class="panel-heading">
        <g:if test="${search}">
            <span style="float:right;">
                <g:form controller="search" action="inboxSearch" method="get">
                    <g:textField name="searchString" placeholder="Search"/>
                    <g:submitButton name="Search" value="Search"/>
                </g:form>
            </span>
        </g:if>
        <h3 class="panel-title">${header}</h3>
    </div>  <!-- panel-heading -->
    <div class="panel-body">
    <g:render template="/post" collection="${resources}" var="resource"/>
    </div>  <!-- panel-body -->
</div> <!-- panel -->