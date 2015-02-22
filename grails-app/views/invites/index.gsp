
<%@ page import="sharehub.Invites" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'invites.label', default: 'Invites')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-invites" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-invites" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
				<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
			<thead>
					<tr>
					
						<g:sortableColumn property="dateCreated" title="${message(code: 'invites.dateCreated.label', default: 'Date Created')}" />
					
						<th><g:message code="invites.invitedBy.label" default="Invited By" /></th>
					
						<th><g:message code="invites.invitedTo.label" default="Invited To" /></th>
					
						<g:sortableColumn property="lastUpdated" title="${message(code: 'invites.lastUpdated.label', default: 'Last Updated')}" />
					
						<g:sortableColumn property="status" title="${message(code: 'invites.status.label', default: 'Status')}" />
					
						<th><g:message code="invites.topic.label" default="Topic" /></th>
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${invitesInstanceList}" status="i" var="invitesInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${invitesInstance.id}">${fieldValue(bean: invitesInstance, field: "dateCreated")}</g:link></td>
					
						<td>${fieldValue(bean: invitesInstance, field: "invitedBy")}</td>
					
						<td>${fieldValue(bean: invitesInstance, field: "invitedTo")}</td>
					
						<td><g:formatDate date="${invitesInstance.lastUpdated}" /></td>
					
						<td>${fieldValue(bean: invitesInstance, field: "status")}</td>
					
						<td>${fieldValue(bean: invitesInstance, field: "topic")}</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${invitesInstanceCount ?: 0}" />
			</div>
		</div>
	</body>
</html>
