<div class="topic">
<div id="topicInfo${topic.id}${isHead}" class="topicBox" data-topic-id="${topic.id}">
    <div style="float:left; margin-right:10px">
        <a href="${g.createLink(controller: "user", action: "profile", params: [id:topic.createdBy.username])}">
            <sh:image src="${topic.createdBy.photoUrl}" />
        </a>
    </div>

    <div>
        <div>
            <g:if test="${topic.visibility == com.sharehub.enums.Visibility.PRIVATE}">
                <a href="${g.createLink(controller: "topic", action: "showTopic", params: [id: topic.id])}" style="color:red;">
            </g:if>
            <g:else>
                <a href="${g.createLink(controller: "topic", action: "showTopic", params: [id: topic.id])}">
            </g:else>
            ${topic.name}</a>
            %{--    Editing options     --}%
            <sh:isEditableTopic topic="${topic}">
                <span class="text-right" style="float: right">
                    <div class="dropdown">
                        <button class="btn btn-link dropdown-toggle" type="button" id="dropdownMenu1" data-toggle="dropdown" aria-expanded="true" style="margin-left: 0px; padding-left: 0px">
                            <span class="caret"></span>
                        </button>
                        <ul class="dropdown-menu" role="menu" aria-labelledby="dropdownMenu1">
                            <li role="presentation">
                                <a href="javascript:void(0)" role="menuitem" tabindex="-1" onclick='openEditTopic("${topic.id}", "${topic.name}", "${topic.visibility}")'>Edit</a>
                            </li>
                            <li role="presentation">
                                <g:link role="menuitem" tabindex="-1" controller="topic" action="delTopic" params="[id: topic.id]">Delete</g:link>
                            </li>
                        </ul>
                    </div>
                </span>
            </sh:isEditableTopic>
        </div>
        <table>
            <tr>
                <td class="tableData tableDataWidth">
                    <g:link controller="user" action="profile" params='[id:"${topic.createdBy.username}"]' style="color:#000000;"> @${topic.createdBy.username}</g:link></td>
                <td class="tableData tableDataWidth">Subscriptions</td>
                <td class="tableData">Posts</td>
            </tr>
            <tr>
                <td class="tableData tableDataWidth">
                    <div class="subscriptionStatus${topic.id}" id="subscriptionStatus${topic.id}">
                        <sh:subscribe topic="${topic}"/>
                    </div>
                    %{--<g:remoteLink update='subscriptionStatus${topic.id}' controller='subscription' action='subscribe' params="[topicId: topic.id]" class='btn btn-link'>Subscribe</g:remoteLink>--}%
                    %{--<g:render template="/subscription/subscribeOptions"/>--}%
                </td>
                <td class="tableData tableDataWidth">
                    <g:link controller="topic" action="showTopic" params="[id: topic.id]">${topic.subscriptions.size()}</g:link>
                </td>
                <td class="tableData">
                    <g:link controller="topic" action="showTopic" params="[id: topic.id]">${topic.resources.size()}</g:link>
                </td>
            </tr>
        </table>
    </div>
    <g:if test="${hr}">
        <hr style="margin-top: 7px; margin-bottom: 7px">
    </g:if>
</div>
</div>