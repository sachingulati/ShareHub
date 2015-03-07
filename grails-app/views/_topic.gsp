
<div>
    <div style="float:left; margin-right:10px">
        <a href="#">
            <sh:image src="${topic.createdBy.photoUrl}" />
        </a>
    </div>

    <div>
        <div>
            <g:if test="${topic.visibility == com.sharehub.enums.Visibility.PRIVATE}">
                <a href="#" style="color:red;">
            </g:if>
            <g:else>
                <a href="#">
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
                    <g:if test="$subscribed">
                        <a href=""
                    </g:if>

                </td>
                <td class="tableData tableDataWidth"><a href="#">${topic.subscriptions.size()}</a></td>
                <td class="tableData"><a href="#">${topic.resources.size()}</a></td>
            </tr>
        </table>
    </div>
    <hr>
</div>