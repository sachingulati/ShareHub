
<%@ page import="sharehub.ResourceStatus" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'resourceStatus.label', default: 'ResourceStatus')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-resourceStatus" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-resourceStatus" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
				<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
			<thead>
					<tr>
					
						<g:sortableColumn property="score" title="${message(code: 'resourceStatus.score.label', default: 'Score')}" />
					
						<g:sortableColumn property="isRead" title="${message(code: 'resourceStatus.isRead.label', default: 'Is Read')}" />
					
						<th><g:message code="resourceStatus.resource.label" default="Resource" /></th>
					
						<th><g:message code="resourceStatus.user.label" default="User" /></th>
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${resourceStatusInstanceList}" status="i" var="resourceStatusInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${resourceStatusInstance.id}">${fieldValue(bean: resourceStatusInstance, field: "score")}</g:link></td>
					
						<td><g:formatBoolean boolean="${resourceStatusInstance.isRead}" /></td>
					
						<td>${fieldValue(bean: resourceStatusInstance, field: "resource")}</td>
					
						<td>${fieldValue(bean: resourceStatusInstance, field: "user")}</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${resourceStatusInstanceCount ?: 0}" />
			</div>
		</div>
	</body>
</html>
