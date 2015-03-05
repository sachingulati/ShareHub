<%@ page import="sharehub.Resource" %>

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
            <g:if test="${resource.type==com.sharehub.enums.ResourceType.DOCUMENT}">
                <a href="#" class="inboxLinkStyle">Download</a>
            </g:if>
            <g:else>
                <a href="#" class="inboxLinkStyle">View full site</a>
            </g:else>
            <g:if test="${markRead}">
                <g:link controller="resource" class="inboxLinkStyle" action="readResource" params="[resource:resource.id]">Mark as read</g:link>
            </g:if>
            <a href="#" class="inboxLinkStyle">View post</a>
        </div>
    </div>
    <hr>
</div>