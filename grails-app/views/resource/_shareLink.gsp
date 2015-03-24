
<asset:javascript src="shareLink.js"/>
<div class="modal fade" id="shareLink" tabindex="-1" role="dialog" aria-labelledby="shareLinkModal" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="myModalLabel">Share Link</h4>
            </div>
            <g:form controller="resource" action="shareLink" name="shareLinkForm">
                <div class="modal-body">

                    <div class="row padding5">
                        <div class="col-lg-5">
                            Title*
                        </div>

                        <div class="col-lg-7">
                            <g:textField name="title" class="form-control" placeholder="Title" required="required" expression="Please enter a title for your topic."/>
                        </div>
                    </div>

                    <div class="row padding5">
                        <div class="col-lg-5">
                            Link*
                        </div>

                        <div class="col-lg-7">
                                <g:textField name="url" class="form-control" value="http://" placeholder="Url" aria-describedby="basic-addon1" required="required"/>
                        </div>
                    </div>

                    <div class="row padding5">
                        <div class="col-lg-5">
                            Description*
                        </div>

                        <div class="col-lg-7">
                            <g:textArea name="description" class="form-control" placeholder="\n\nDescription" rows="5" required="required" maxlength="1000"/>
                        </div>
                    </div>

                    <div class="row padding5">
                        <div class="col-lg-5">
                            Topic*
                        </div>

                        <div class="col-lg-7">
                            <div class="topicSelector" data-ajax-url="${createLink(controller: "topic", action: "getSubscribedTopics")}">
                                <g:select name="topic" from="${["Select Topic"]}" class="form-control"/>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="modal-footer">
                    <g:submitButton name="submit" value="Share" class="btn btn-primary"/>
                    <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
                </div>
            </g:form>
        </div>
    </div>
</div>
