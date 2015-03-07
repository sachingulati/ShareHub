<%@ page import="sharehub.Resource" %>

<div>
    <div style="float:left; margin-right: 10px">
        <a href="#">
            <sh:image src="${resource.createdBy.photoUrl}"/>
        </a>
    </div>
    <div class="padding5">
        <span style="float:right;">
            <g:if test="${resource.topic.visibility==com.sharehub.enums.Visibility.PRIVATE}">
                <a href="#" style="color:red;">
            </g:if>
            <g:else>
                <a href="#">
            </g:else>
            ${resource.topic.name}</a></span>

        <div>
            <g:link controller="user" action="profile" params='[id:"${resource.createdBy.username}"]'>${resource.createdBy.name}</g:link>
            @${resource.createdBy.username}
        </div>
        <div>
            <a href="#">${resource.title}</a>
        </div>

        <div id="recentShareDesc">
            ${resource.description?.substring(0,resource.description?.size()>180?180:resource.description?.size())}
        </div>
        <div style="float:right">
            <g:if test="${resource.type==com.sharehub.enums.ResourceType.DOCUMENT}">
                <a href="#" class="inboxLinkStyle">Download</a>
            </g:if>
            <g:else>
                <a href="${'http://' + resource.url}" class="inboxLinkStyle" target="_blank">View full site</a>
            </g:else>
            <sh:markRead resourceId="${resource.id}"/>
            <a href="#" class="inboxLinkStyle">View post</a>
        </div>
    </div>
    <hr>
</div>