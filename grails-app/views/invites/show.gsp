
<%@ page import="sharehub.Invites" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'invites.label', default: 'Invites')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-invites" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="index"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-invites" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list invites">
			
				<g:if test="${invitesInstance?.dateCreated}">
				<li class="fieldcontain">
					<span id="dateCreated-label" class="property-label"><g:message code="invites.dateCreated.label" default="Date Created" /></span>
					
						<span class="property-value" aria-labelledby="dateCreated-label"><g:formatDate date="${invitesInstance?.dateCreated}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${invitesInstance?.invitedBy}">
				<li class="fieldcontain">
					<span id="invitedBy-label" class="property-label"><g:message code="invites.invitedBy.label" default="Invited By" /></span>
					
						<span class="property-value" aria-labelledby="invitedBy-label"><g:link controller="user" action="show" id="${invitesInstance?.invitedBy?.id}">${invitesInstance?.invitedBy?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${invitesInstance?.invitedTo}">
				<li class="fieldcontain">
					<span id="invitedTo-label" class="property-label"><g:message code="invites.invitedTo.label" default="Invited To" /></span>
					
						<span class="property-value" aria-labelledby="invitedTo-label"><g:link controller="user" action="show" id="${invitesInstance?.invitedTo?.id}">${invitesInstance?.invitedTo?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${invitesInstance?.lastUpdated}">
				<li class="fieldcontain">
					<span id="lastUpdated-label" class="property-label"><g:message code="invites.lastUpdated.label" default="Last Updated" /></span>
					
						<span class="property-value" aria-labelledby="lastUpdated-label"><g:formatDate date="${invitesInstance?.lastUpdated}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${invitesInstance?.status}">
				<li class="fieldcontain">
					<span id="status-label" class="property-label"><g:message code="invites.status.label" default="Status" /></span>
					
						<span class="property-value" aria-labelledby="status-label"><g:fieldValue bean="${invitesInstance}" field="status"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${invitesInstance?.topic}">
				<li class="fieldcontain">
					<span id="topic-label" class="property-label"><g:message code="invites.topic.label" default="Topic" /></span>
					
						<span class="property-value" aria-labelledby="topic-label"><g:link controller="topic" action="show" id="${invitesInstance?.topic?.id}">${invitesInstance?.topic?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form url="[resource:invitesInstance, action:'delete']" method="DELETE">
				<fieldset class="buttons">
					<g:link class="edit" action="edit" resource="${invitesInstance}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
