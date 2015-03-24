<div class="modal fade m" id="sendInvite" tabindex="-1" role="dialog" aria-labelledby="sendInviteModal" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="myModalLabel">Send Invitation</h4>
            </div>
            <g:formRemote name="invite" url="[controller: 'invites', action: 'sendInvite']" onSuccess="invitationSent(data)">
                <div class="modal-body">
                    <div class="row padding5">
                        <div class="col-lg-5">
                            To*
                        </div>

                        <div class="col-lg-7">
                            <g:textField name="inviteTo" class="form-control" placeholder="Name"/>
                        </div>
                    </div>
                    <div class="row padding5">
                        <div class="col-lg-5">
                            Email*
                        </div>

                        <div class="col-lg-7">
                            <g:textField name="email" class="form-control" placeholder="Email"/>
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
                    <g:submitButton name="submit" value="submit" class="btn btn-primary"/>
                    <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
                </div>
            </g:formRemote>
        </div>
    </div>
</div>
