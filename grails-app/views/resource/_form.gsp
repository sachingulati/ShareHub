<%@ page import="sharehub.Resource" %>



<div class="fieldcontain ${hasErrors(bean: resourceInstance, field: 'description', 'error')} ">
	<label for="description">
		<g:message code="resource.description.label" default="Description" />
		
	</label>
	<g:textField name="description" value="${resourceInstance?.description}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: resourceInstance, field: 'title', 'error')} required">
	<label for="title">
		<g:message code="resource.title.label" default="Title" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="title" required="" value="${resourceInstance?.title}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: resourceInstance, field: 'createdBy', 'error')} required">
	<label for="createdBy">
		<g:message code="resource.createdBy.label" default="Created By" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="createdBy" name="createdBy.id" from="${sharehub.User.list()}" optionKey="id" required="" value="${resourceInstance?.createdBy?.id}" class="many-to-one"/>

</div>

<div class="fieldcontain ${hasErrors(bean: resourceInstance, field: 'resourceStatus', 'error')} ">
	<label for="resourceStatus">
		<g:message code="resource.resourceStatus.label" default="Resource Status" />
		
	</label>
	
<ul class="one-to-many">
<g:each in="${resourceInstance?.resourceStatus?}" var="r">
    <li><g:link controller="resourceStatus" action="show" id="${r.id}">${r?.encodeAsHTML()}</g:link></li>
</g:each>
<li class="add">
<g:link controller="resourceStatus" action="create" params="['resource.id': resourceInstance?.id]">${message(code: 'default.add.label', args: [message(code: 'resourceStatus.label', default: 'ResourceStatus')])}</g:link>
</li>
</ul>


</div>

<div class="fieldcontain ${hasErrors(bean: resourceInstance, field: 'topic', 'error')} required">
	<label for="topic">
		<g:message code="resource.topic.label" default="Topic" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="topic" name="topic.id" from="${sharehub.Topic.list()}" optionKey="id" required="" value="${resourceInstance?.topic?.id}" class="many-to-one"/>

</div>

<div class="fieldcontain ${hasErrors(bean: resourceInstance, field: 'type', 'error')} required">
	<label for="type">
		<g:message code="resource.type.label" default="Type" />
		<span class="required-indicator">*</span>
	</label>
	<g:select name="type" from="${com.sharehub.enums.ResourceType?.values()}" keys="${com.sharehub.enums.ResourceType.values()*.name()}" required="" value="${resourceInstance?.type?.name()}" />

</div>

<div class="fieldcontain ${hasErrors(bean: resourceInstance, field: 'url', 'error')} required">
	<label for="url">
		<g:message code="resource.url.label" default="Url" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="url" required="" value="${resourceInstance?.url}"/>

</div>

