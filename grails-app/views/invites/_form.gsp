<%@ page import="sharehub.Invites" %>



<div class="fieldcontain ${hasErrors(bean: invitesInstance, field: 'invitedBy', 'error')} required">
    <label for="invitedBy">
        <g:message code="invites.invitedBy.label" default="Invited By"/>
        <span class="required-indicator">*</span>
    </label>
    <g:select id="invitedBy" name="invitedBy.id" from="${sharehub.User.list()}" optionKey="id" required=""
              value="${invitesInstance?.invitedBy?.id}" class="many-to-one"/>

</div>

<div class="fieldcontain ${hasErrors(bean: invitesInstance, field: 'invitedTo', 'error')} required">
    <label for="invitedTo">
        <g:message code="invites.invitedTo.label" default="Invited To"/>
        <span class="required-indicator">*</span>
    </label>
    <g:select id="invitedTo" name="invitedTo.id" from="${sharehub.User.list()}" optionKey="id" required=""
              value="${invitesInstance?.invitedTo?.id}" class="many-to-one"/>

</div>

<div class="fieldcontain ${hasErrors(bean: invitesInstance, field: 'status', 'error')} required">
    <label for="status">
        <g:message code="invites.status.label" default="Status"/>
        <span class="required-indicator">*</span>
    </label>
    <g:select name="status" from="${com.sharehub.enums.InviteStatus?.values()}"
              keys="${com.sharehub.enums.InviteStatus.values()*.name()}" required=""
              value="${invitesInstance?.status?.name()}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: invitesInstance, field: 'topic', 'error')} required">
    <label for="topic">
        <g:message code="invites.topic.label" default="Topic"/>
        <span class="required-indicator">*</span>
    </label>
    <g:select id="topic" name="topic.id" from="${sharehub.Topic.list()}" optionKey="id" required=""
              value="${invitesInstance?.topic?.id}" class="many-to-one"/>

</div>

