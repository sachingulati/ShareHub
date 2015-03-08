<div class="panel panel-default">
    <div class="panel-heading">

        <h3 class="panel-title">Users: ${topicName}</h3>
    </div> <!-- panel-heading  -->

    <div class="panel-body">
        <g:render template="/userInfo" collection="${users}" var="user" model="[hr: true]"/>

    </div>
</div>