
<div id="topicInfo${topic.id}${isHead}">
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
            ${topic.name ? topic.name : "Topic"}</a>
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
                    <div id="subscriptionStatus${topic.id}">
                        <sh:subscribe topic="${topic}"/>
                    </div>
                    %{--<g:remoteLink update='subscriptionStatus${topic.id}' controller='subscription' action='subscribe' params="[topicId: topic.id]" class='btn btn-link'>Subscribe</g:remoteLink>--}%
                    %{--<g:render template="/subscription/subscribeOptions"/>--}%
                </td>
                <td class="tableData tableDataWidth"><a href="#">${topic.subscriptions.size()}</a></td>
                <td class="tableData"><a href="#">${topic.resources.size()}</a></td>
            </tr>
        </table>
    </div>
    <g:if test="${hr}">
        <hr>
    </g:if>
</div>