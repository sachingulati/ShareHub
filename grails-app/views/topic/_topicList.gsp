<%@ page import="sharehub.Topic" %>

<div class="panel panel-default">
    <div class="panel-heading">
        <span style="float:right;"><a href="#">View All</a></span>

        <h3 class="panel-title">${header}</h3>
    </div> <!-- panel-heading -->
    <div class="panel-body">
    %{--<g:render template="/post" collection="${resources}" var="resource"/>--}%
    <g:render template="/topic/topicInfo" collection="${topics}" var="topic"/>
    </div>    <!-- panel-body -->
</div>  <!-- panel -->

