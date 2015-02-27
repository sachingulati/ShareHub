<div class="modal fade" id="shareLink" tabindex="-1" role="dialog" aria-labelledby="shareLinkModal" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="myModalLabel">Share Link</h4>
            </div>

            <div class="modal-body">

                <div class="row padding5">
                    <div class="col-lg-5">
                        <span class="valignstyle">Link*</span>
                    </div>

                    <div class="col-lg-7">
                        <input type="text" id="txtFirstName" class="form-control" placeholder="Link"/>
                    </div>
                </div>

                <div class="row padding5">
                    <div class="col-lg-5">
                        Description*
                    </div>

                    <div class="col-lg-7">
                        <g:textArea name="shareLinkDescription" class="form-control" placeholder="\n\nDescription"
                                    rows="5"/>
                    </div>
                </div>

                <div class="row padding5">
                    <div class="col-lg-5">
                        Topic*
                    </div>

                    <div class="col-lg-7">
                        <g:select name="shareLinkTopic" from="${['hello', 'moto']}" noSelection="['': 'Select Topic']"
                                  class="form-control"/>
                    </div>
                </div>
            </div>

            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
                <button type="button" class="btn btn-primary">Share</button>
            </div>
        </div>
    </div>
</div>