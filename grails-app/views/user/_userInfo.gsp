
        <div style="float:left; margin-right:10px">
            <a href="${g.createLink(controller: "user", action: "profile", params: [id:user.username])}">
                <sh:image src="${user.photoUrl}"/>
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
                        : <a href="javascript:void(0)">${user.subscriptions.size()}</a>
                    </td>
                    <td class="userTableData">
                        <g:if test="${profileHeader}">My topics</g:if>
                        <g:else>Topics</g:else>
                        : <a href="javascript:void(0)">${user.topics.size()}</a>
                    </td>
                </tr>
            </table>
        </div>
        <g:if test="${hr}">
            <hr>
        </g:if>