<%@ page import="sharehub.Topic" %>

<div class="panel panel-default">
    <div class="panel-heading">
        <span style="float:right;"><a href="#">View All</a></span>

        <h3 class="panel-title">Subscription</h3>
    </div> <!-- panel-heading -->
    <div class="panel-body">
    <% subscribedTopics.eachWithIndex{sharehub.Topic topic, index->
        if(index>4) return%>
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
                        <td></td>
                        <td class="tableData tableDataWidth"><a href="#">${topic.subscriptions.size()}</a></td>
                        <td class="tableData"><a href="#">${topic.resources.size()}</a></td>
                    </tr>
                </table>
            </div>
            <hr>
        </div>
    <% } %>
    </div>    <!-- panel-body -->
</div>  <!-- panel -->

