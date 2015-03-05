
<div>
    <div style="float:left; margin-right:10px">
        <a href="#"><asset:image src="user-default.png" class="img-media" alt="User image"/></a>
    </div>

    <div>
        <div>
            <a href="#">${topic.name ? topic.name : "null"}</a>
        </div>
        <table>
            <tr>
                <td class="tableData tableDataWidth">@${topic.createdBy.username}</td>
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