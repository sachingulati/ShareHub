
        <div style="float:left; margin-right:10px">
            <a href="#"><asset:image src="user-default.png" class="img-media" alt="User image"/></a>
        </div>

        <div>
            <div>
                <a href="#">${name?name:"name"}</a>
            </div>

            <div>
                @${username?:"username"}
            </div>
            <table>
                <tr>
                    <td class="userTableData">Subscriptions</td>
                    <td class="userTableData">Topics</td>
                </tr>
                <tr>
                    <td class="userTableData"><a href="#">${subCount ? subCount : 0}</a></td>
                    <td class="userTableData"><a href="#">${topicCount ? topicCount : 0}</a></td>
                </tr>
            </table>
        </div>