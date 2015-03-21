
<asset:javascript src="shareDocument.js"/>
<div class="modal fade" id="shareDocument" tabindex="-1" role="dialog" aria-labelledby="shareDocumentModal" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="myModalLabel">Share Document</h4>
            </div>
            <g:form controller="resource" action="shareDocument" name="shareDocumentForm">
                <div class="modal-body">

                    <div class="row padding5">
                        <div class="col-lg-5">
                            <span class="valignstyle">Title*</span>
                        </div>

                        <div class="col-lg-7">
                            <g:textField name="title" class="form-control" placeholder="Title" required="required" message="Please Enter title for topic"/>
                        </div>
                    </div>

                    <div class="row padding5">
                        <div class="col-lg-5">
                            <span class="valignstyle">Upload File*</span>
                        </div>

                        <div class="col-lg-7">
                            <span class="btn btn-default btn-file">
                                <input type="file" id="file" required="required"/>
                            </span>

                        </div>
                    </div>

                    <div class="row padding5">
                        <div class="col-lg-5">
                            Description*
                        </div>

                        <div class="col-lg-7">
                            <g:textArea name="description" class="form-control" placeholder="\n\nDescription" rows="5"  required="required"/>
                        </div>
                    </div>

                    <div class="row padding5">
                        <div class="col-lg-5">
                            Topic*
                        </div>

                        <div class="col-lg-7">
                            <div class="topicSelector">
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
<script>
    $('#shareDocument .topicSelector').on('load',function(){
        $(this).load("${createLink(controller: "topic", action: "getSubscribedTopics")}");
    });
    $('#shareDocument').on('show',function(){
        $(this).find(".topicSelector").load();
    })
</script>