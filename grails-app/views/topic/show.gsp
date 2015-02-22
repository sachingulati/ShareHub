
<%@ page import="sharehub.Topic" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'topic.label', default: 'Topic')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-topic" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="index"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-topic" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list topic">
			
				<g:if test="${topicInstance?.name}">
				<li class="fieldcontain">
					<span id="name-label" class="property-label"><g:message code="topic.name.label" default="Name" /></span>
					
						<span class="property-value" aria-labelledby="name-label"><g:fieldValue bean="${topicInstance}" field="name"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${topicInstance?.createdBy}">
				<li class="fieldcontain">
					<span id="createdBy-label" class="property-label"><g:message code="topic.createdBy.label" default="Created By" /></span>
					
						<span class="property-value" aria-labelledby="createdBy-label"><g:link controller="user" action="show" id="${topicInstance?.createdBy?.id}">${topicInstance?.createdBy?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${topicInstance?.dateCreated}">
				<li class="fieldcontain">
					<span id="dateCreated-label" class="property-label"><g:message code="topic.dateCreated.label" default="Date Created" /></span>
					
						<span class="property-value" aria-labelledby="dateCreated-label"><g:formatDate date="${topicInstance?.dateCreated}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${topicInstance?.lastUpdated}">
				<li class="fieldcontain">
					<span id="lastUpdated-label" class="property-label"><g:message code="topic.lastUpdated.label" default="Last Updated" /></span>
					
						<span class="property-value" aria-labelledby="lastUpdated-label"><g:formatDate date="${topicInstance?.lastUpdated}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${topicInstance?.resources}">
				<li class="fieldcontain">
					<span id="resources-label" class="property-label"><g:message code="topic.resources.label" default="Resources" /></span>
					
						<g:each in="${topicInstance.resources}" var="r">
						<span class="property-value" aria-labelledby="resources-label"><g:link controller="resource" action="show" id="${r.id}">${r?.encodeAsHTML()}</g:link></span>
						</g:each>
					
				</li>
				</g:if>
			
				<g:if test="${topicInstance?.subscriptions}">
				<li class="fieldcontain">
					<span id="subscriptions-label" class="property-label"><g:message code="topic.subscriptions.label" default="Subscriptions" /></span>
					
						<g:each in="${topicInstance.subscriptions}" var="s">
						<span class="property-value" aria-labelledby="subscriptions-label"><g:link controller="subscription" action="show" id="${s.id}">${s?.encodeAsHTML()}</g:link></span>
						</g:each>
					
				</li>
				</g:if>
			
				<g:if test="${topicInstance?.visibility}">
				<li class="fieldcontain">
					<span id="visibility-label" class="property-label"><g:message code="topic.visibility.label" default="Visibility" /></span>
					
						<span class="property-value" aria-labelledby="visibility-label"><g:fieldValue bean="${topicInstance}" field="visibility"/></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form url="[resource:topicInstance, action:'delete']" method="DELETE">
				<fieldset class="buttons">
					<g:link class="edit" action="edit" resource="${topicInstance}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
