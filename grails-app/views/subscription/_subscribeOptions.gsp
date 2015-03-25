<%@ page import="com.sharehub.enums.Seriousness; sharehub.Subscription" %>
<div class="dropdown">
    <button class="btn btn-link dropdown-toggle" type="button" id="dropdownMenu1" data-toggle="dropdown" aria-expanded="true" style="margin-left: 0px; padding-left: 0px">
        Subscribed
        <span class="caret"></span>
    </button>
    <ul class="dropdown-menu" role="menu" aria-labelledby="dropdownMenu1">
        <li role="presentation" class="${subscriptionType==com.sharehub.enums.Seriousness.CASUAL?'active':''}">
            <g:remoteLink update="subscriptionStatus${topicId}" controller="subscription" action="modifySubscriptionSeriousness" params="[seriousness: com.sharehub.enums.Seriousness.CASUAL, topicId: topicId]" role="menuitem" tabindex="-1">${com.sharehub.enums.Seriousness.CASUAL.displayName}</g:remoteLink>
        </li>
        <li role="presentation" class="${subscriptionType==com.sharehub.enums.Seriousness.SERIOUS?'active':''}">
            <g:remoteLink update="subscriptionStatus${topicId}" controller="subscription" action="modifySubscriptionSeriousness" params="[seriousness: com.sharehub.enums.Seriousness.SERIOUS, topicId: topicId]" role="menuitem" tabindex="-1">${com.sharehub.enums.Seriousness.SERIOUS.displayName}</g:remoteLink>
        </li>
        <li role="presentation" class="${subscriptionType==com.sharehub.enums.Seriousness.VERY_SERIOUS?'active':''}">
            <g:remoteLink update="subscriptionStatus${topicId}" controller="subscription" action="modifySubscriptionSeriousness" params="[seriousness: com.sharehub.enums.Seriousness.VERY_SERIOUS, topicId: topicId]" role="menuitem" tabindex="-1">${com.sharehub.enums.Seriousness.VERY_SERIOUS.displayName}</g:remoteLink>
        </li>

        <li role="presentation" class="divider" style="margin: 2px 0px"></li>
        <li role="presentation">
            <a href="javascript:void(0)" onclick='openTopicInvite("${topicId}","${topicName}")'>Invite</a>
        </li>
        <g:if test="${canUnsubscribe}">
            <li role="presentation">
                <a class='unsubscribe' href='javascript:void(0)' data-topic-id="${topicId}">Unsubscribe</a>
            </li>
        </g:if>
            %{--<a role="menuitem" tabindex="-1" href="#">Unsubscribe</a></li>--}%
    </ul>
</div>
