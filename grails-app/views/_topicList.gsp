<%@ page import="sharehub.Topic" %>

<div class="panel panel-default">
    <div class="panel-heading">
        <span style="float:right;"><a href="#">View All</a></span>

        <h3 class="panel-title">${header}</h3>
    </div> <!-- panel-heading -->
    <div class="panel-body">
    %{--<g:render template="/post" collection="${resources}" var="resource"/>--}%
    <g:render template="/topicInfo" collection="${topics}" var="topic" model="[subscribed:true]"/>
    </div>    <!-- panel-body -->
</div>  <!-- panel -->
