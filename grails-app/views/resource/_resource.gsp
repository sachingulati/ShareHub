
<div class="resource" data-resource-id="${resource.id}" data-resource-type="${resource.type==com.sharehub.enums.ResourceType.DOCUMENT?"document":"link"}">
    <div style="float:left; margin-right: 10px; margin-top: 10px">
        <a href="${g.createLink(controller: "user", action: "profile", params: [id:resource.createdBy.username])}">
            <sh:image src="${resource.createdBy.photoUrl}"/>
        </a>
    </div>
    <div class="padding5">

        %{--    Editing options     --}%
        <sh:isEditableResource resource="${resource}">
            <g:render template="/resource/resourceOptions" model="[resource: resource]"/>
        </sh:isEditableResource>

        <span style="float:right;">
            <g:if test="${resource.topic.visibility==com.sharehub.enums.Visibility.PRIVATE}">
                <a class="resourceTopicLink" data-topic-id="${resource.topic.id}" href="${g.createLink(controller: "topic", action: "showTopic", params: [id: resource.topic.id])}" style="color:red;">
            </g:if>
            <g:else>
                <a class="resourceTopicLink" data-topic-id="${resource.topic.id}" href="${g.createLink(controller: "topic", action: "showTopic", params: [id: resource.topic.id])}">
            </g:else>
            ${resource.topic.name}</a>
        </span>
        <div>
            <g:link controller="user" action="profile" params='[id:"${resource.createdBy.username}"]'>${resource.createdBy.name}</g:link>
            @${resource.createdBy.username}
        </div>
        <div>
            <g:link class="resourceTitle" controller="resource" action="showPost" params='[id:  "${resource.id}"]'>${resource.title}</g:link>
        </div>

        <div class="resourceDescription">${resource.description}</div>
        <div style="float:right">
            <g:if test="${resource.type==com.sharehub.enums.ResourceType.DOCUMENT}">
                <a href="${createLink(controller: "resource", action: "download", params: [resourceId: resource.id], absolute: true)}" class="inboxLinkStyle resourceLink">Download</a>
            </g:if>
            <g:else>
                <a href="${resource.url}" class="inboxLinkStyle resourceLink" target="_blank">View full site</a>
            </g:else>
            <sh:markRead resourceId="${resource.id}"/>
            <g:link controller="resource" action="showPost" params='[id:  "${resource.id}"]' class="inboxLinkStyle">View post</g:link>
        </div>
        <div style="float: left">
            <div>
                <a href="https://plus.google.com/share?url={${createLink(controller: "resource", action: "showPost", params: [id: resource.id], absolute: true)}}"
                   onclick="javascript:window.open(this.href,'', 'menubar=no,toolbar=no,resizable=yes,scrollbars=yes,height=600,width=600');return false;"><img
                        src="https://www.gstatic.com/images/icons/gplus-16.png" alt="Share on Google+"/></a>
            </div>
            <div class="fb-share-button" style="margin-top: -14px; margin-left: 10px;" data-href="${createLink(controller: "resource", action: "showPost", params: [id: resource.id], absolute: true)}" data-layout="icon"></div>
            %{--<div class="fb-share-button" data-href="${createLink(controller: "resource", action: "showPost", params: [id: resource.id], absolute: true)}" data-layout="button"></div>--}%
            %{--<div class="g-plus" data-action="share" data-href="${createLink(controller: "resource", action: "showPost", params: [id: resource.id], absolute: true)}" ></div>--}%
        </div>
    </div>
    <hr style="margin-bottom: 7px;">
</div>