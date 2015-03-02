<div class="modal fade" id="sendInvite" tabindex="-1" role="dialog" aria-labelledby="sendInviteModal" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="myModalLabel">Send Invitation</h4>
            </div>
            <g:form>
                <div class="modal-body">

                    <div class="row padding5">
                        <div class="col-lg-5">
                            <span class="valignstyle">Email*</span>
                        </div>

                        <div class="col-lg-7">
                            <input type="text" id="txtFirstName" class="form-control" placeholder="Email"/>
                        </div>
                    </div>

                    <div class="row padding5">
                        <div class="col-lg-5">
                            Topic*
                        </div>

                        <div class="col-lg-7">
                            <g:select name="shareLinkTopic" from="${['hello', 'moto']}"
                                      noSelection="['': 'Select Topic']"
                                      class="form-control"/>
                        </div>
                    </div>
                </div>

                <div class="modal-footer">
                    <button type="button" class="btn btn-primary">Invite</button>
                    <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
                </div>
            </g:form>
        </div>
    </div>
</div>