
        <div style="float:left; margin-right:10px">
            <a href="${g.createLink(controller: "user", action: "profile", params: [id:user.username])}">
                %{--<sh:image src="${user.photoUrl}"/>--}%
                ${sh.image(src: user.photoUrl)}
            </a>
        </div>

        <div style="margin-top: 6px">
            <div>
                <g:link controller="user" action="profile" params='[id: "${user.username}"]'>${user.name}</g:link>
            </div>

            <div>
                @${user.username}
            </div>
            <table>
                <tr>
                    <td class="userTableData">
                        <g:if test="${profileHeader}">My subscription</g:if>
                        <g:else>Subscriptions</g:else>
                        : %{--<g:link controller="topic" action="viewAllSubscribedTopics">--}%${user.subscriptions.size()}%{--</g:link>--}%
                    </td>
                    <td class="userTableData">
                        <g:if test="${profileHeader}">My topics</g:if>
                        <g:else>Topics</g:else>
                        : <g:link controller="user" action="profile" params='[id: "${user.username}"]'>${user.topics.size()}</g:link>
                    </td>
                </tr>
            </table>
        </div>
        <g:if test="${hr}">
            <hr>
        </g:if>