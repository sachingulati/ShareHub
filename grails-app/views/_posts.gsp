<%@ page import="sharehub.Resource" %>

<div class="panel panel-default">
    <div class="panel-heading">
        <span style="float:right;">
            <g:form controller="search" action="inboxSearch" method="get">
                <g:textField name="searchString" placeholder="Search"/>
                <g:submitButton name="Search" value="Search"/>
            </g:form>
        </span>

        <h3 class="panel-title">${header}</h3>
    </div>  <!-- panel-heading -->
    <div class="panel-body">
    <% resources.each {sharehub.Resource resource -> %>
        <div>
            <div style="float:left">
                <a href="#"><asset:image src="user-default.png" class="img-media" alt="User image"/></a>
            </div>
            <div class="padding5">
                <span style="float:right;"><a href="#">${resource.topic.name}</a></span>

                <div>
                    <a href="#">${resource.topic.createdBy.name}</a>
                    @${resource.topic.createdBy.username}
                </div>
                <div>
                    <a href="#">${resource.title}</a>
                </div>

                <div id="recentShareDesc">
                    ${resource.description}
                </div>

                <div style="float:right">
                    <g:if test="${resource.type}==${com.sharehub.enums.ResourceType.DOCUMENT}">
                       <a href="#" class="inboxLinkStyle">Download</a>
                    </g:if>
                    <g:else>
                        <a href="#" class="inboxLinkStyle">View full site</a>
                    </g:else>
                    <a href="#" class="inboxLinkStyle">Mark as read</a>
                    <a href="#" class="inboxLinkStyle">View post</a>
                </div>
            </div>
            <hr>
        </div>
<% } %>
    </div>  <!-- panel-body -->
</div> <!-- panel -->