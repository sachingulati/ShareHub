<%@ page import="sharehub.ResourceStatus" %>



<div class="fieldcontain ${hasErrors(bean: resourceStatusInstance, field: 'score', 'error')} required">
	<label for="score">
		<g:message code="resourceStatus.score.label" default="Score" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="score" type="number" value="${resourceStatusInstance.score}" required=""/>

</div>

<div class="fieldcontain ${hasErrors(bean: resourceStatusInstance, field: 'isRead', 'error')} ">
	<label for="isRead">
		<g:message code="resourceStatus.isRead.label" default="Is Read" />
		
	</label>
	<g:checkBox name="isRead" value="${resourceStatusInstance?.isRead}" />

</div>

<div class="fieldcontain ${hasErrors(bean: resourceStatusInstance, field: 'resource', 'error')} required">
	<label for="resource">
		<g:message code="resourceStatus.resource.label" default="Resource" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="resource" name="resource.id" from="${sharehub.Resource.list()}" optionKey="id" required="" value="${resourceStatusInstance?.resource?.id}" class="many-to-one"/>

</div>

<div class="fieldcontain ${hasErrors(bean: resourceStatusInstance, field: 'user', 'error')} required">
	<label for="user">
		<g:message code="resourceStatus.user.label" default="User" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="user" name="user.id" from="${sharehub.User.list()}" optionKey="id" required="" value="${resourceStatusInstance?.user?.id}" class="many-to-one"/>

</div>

