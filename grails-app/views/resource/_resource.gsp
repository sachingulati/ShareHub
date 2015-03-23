<%@ page import="sharehub.Resource" %>

<div>
    <div style="float:left; margin-right: 10px">
        <a href="${g.createLink(controller: "user", action: "profile", params: [id:resource.createdBy.username])}">
            <sh:image src="${resource.createdBy.photoUrl}"/>
        </a>
    </div>
    <div class="padding5">

        %{--    Editing options     --}%
        <sh:isEditableResource resource="${resource}">
            <span class="text-right" style="float: right; padding: 0px; margin: 0px">
                <div class="dropdown">
                    <button class="btn btn-link dropdown-toggle" type="button" id="dropdownMenu1" data-toggle="dropdown" aria-expanded="true" style="margin-left: 0px; padding-left: 0px">
                        <span class="caret"></span>
                    </button>
                    <ul class="dropdown-menu" role="menu" aria-labelledby="dropdownMenu1">
                        <li role="presentation">
                            <a href="javascript:void(0)" role="menuitem" tabindex="-1" onclick=''>Edit</a>
                        </li>
                        <li role="presentation">
                            <g:link role="menuitem" tabindex="-1" controller="topic" action="delTopic" params="[id: resource.id]">Delete</g:link>
                        </li>
                    </ul>
                </div>
            </span>
        </sh:isEditableResource>

        <span style="float:right;">
            <g:if test="${resource.topic.visibility==com.sharehub.enums.Visibility.PRIVATE}">
                <a href="${g.createLink(controller: "topic", action: "showTopic", params: [id: resource.topic.id])}" style="color:red;">
            </g:if>
            <g:else>
                <a href="${g.createLink(controller: "topic", action: "showTopic", params: [id: resource.topic.id])}">
            </g:else>
            ${resource.topic.name}</a>
        </span>
        <div>
            <g:link controller="user" action="profile" params='[id:"${resource.createdBy.username}"]'>${resource.createdBy.name}</g:link>
            @${resource.createdBy.username}
        </div>
        <div>
            <g:link controller="resource" action="showPost" params='[id:  "${resource.id}"]'>${resource.title}</g:link>
        </div>

        <div id="recentShareDesc">
            ${resource.description?.substring(0,resource.description?.size()>180?180:resource.description?.size())}
        </div>
        <div style="float:right">
            <g:if test="${resource.type==com.sharehub.enums.ResourceType.DOCUMENT}">
                <a href="javascript:void(0)" class="inboxLinkStyle">Download</a>
            </g:if>
            <g:else>
                <a href="${'http://' + resource.url}" class="inboxLinkStyle" target="_blank">View full site</a>
            </g:else>
            <sh:markRead resourceId="${resource.id}"/>
            <g:link controller="resource" action="showPost" params='[id:  "${resource.id}"]' class="inboxLinkStyle">View post</g:link>
        </div>
    </div>
    <hr>
</div>