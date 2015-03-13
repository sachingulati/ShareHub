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
        <li role="presentation" class="divider"></li>
        <li role="presentation">
            <g:remoteLink update="subscriptionStatus${topicId}" controller="subscription" action="unsubscribe" params="[topicId: topicId]" role="menuitem" tabindex="-1">Unsubscribe</g:remoteLink>
            %{--<a role="menuitem" tabindex="-1" href="#">Unsubscribe</a></li>--}%
    </ul>
</div>