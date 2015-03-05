
        <div style="float:left; margin-right:10px">
            <a href="#">
                %{--<img src="${photoUrl}" class="img-media" alt="User image" />--}%
                %{--<asset:image src="${photoUrl?:'user-default.png'}" class="img-media" alt="User image"/>--}%
                <sh:image src="${photoUrl}"/>
            </a>
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