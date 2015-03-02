<div class="panel panel-default">
    <div class="panel-heading">

        <h3 class="panel-title">Users: "TopicName"</h3>
    </div> <!-- panel-heading  -->

    <div class="panel-body">
        <% 5.times { %>
        <g:render template="/userInfo"/>
        <hr>
        <% } %>
    </div>
</div>