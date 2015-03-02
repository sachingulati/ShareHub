<div class="modal fade" id="shareDocument" tabindex="-1" role="dialog" aria-labelledby="shareDocumentModal" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="myModalLabel">Share Document</h4>
            </div>
            <g:form controller="resource" action="shareDocument">
                <div class="modal-body">

                    <div class="row padding5">
                        <div class="col-lg-5">
                            <span class="valignstyle">Title*</span>
                        </div>

                        <div class="col-lg-7">
                            <g:textField name="title" class="form-control" placeholder="Title"/>
                        </div>
                    </div>

                    <div class="row padding5">
                        <div class="col-lg-5">
                            <span class="valignstyle">Link*</span>
                        </div>

                        <div class="col-lg-7">
                            <span class="btn btn-default btn-file">
                                <input type="file" id="file" placeholder="Link"/>
                            </span>

                        </div>
                    </div>

                    <div class="row padding5">
                        <div class="col-lg-5">
                            Description*
                        </div>

                        <div class="col-lg-7">
                            <g:textArea name="description" class="form-control" placeholder="\n\nDescription" rows="5"/>
                        </div>
                    </div>

                    <div class="row padding5">
                        <div class="col-lg-5">
                            Topic*
                        </div>

                        <div class="col-lg-7">
                            <g:select name="topic" from="${topics}" optionKey="id" optionValue="name"
                                      noSelection="['': 'Select Topic']"
                                      class="form-control"/>
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