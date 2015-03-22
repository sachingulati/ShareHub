
<div style="border: 2px solid; border-radius: 10px; padding: 10px; display: inline-block">
    <span style="float: left; margin-right:10px">
        <sh:image src="${user.photoUrl}" width="100px" height="100px"/>
    </span
    <div>
        hello ${inviteTo},<br>
        <a href="${createLink(controller: "user", action: "profile", params: [username: user.username], absolute: true)}">${user.name}</a> has sent you an invitation on <a href="${createLink(url: "/", absolute: true)}">Share Hub</a> for topic: <a href="${createLink(controller: "topic", action: "showTopic", params: [id: topicId, token: token], absolute: true)}">topicName</a><br>
        To know more about this topic <br>
        <a href="${createLink(controller: "topic", action: "showTopic", params: [id: topicId, token: token], absolute: true)}">Click here to visit and subscribe..</a></br>
        Have a good day.. :)
    </div>
</div>