<div class="modal fade" id="createTopic" tabindex="-1" role="dialog" aria-labelledby="createTopicModal" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">Create Topic</h4>
            </div>
            <g:form controller="topic" action="createTopic">
                <div class="modal-body">

                    <div class="row padding5">
                        <div class="col-lg-5">
                            Name*
                        </div>

                        <div class="col-lg-7">
                            <g:textField name="name" class="form-control" placeholder="Name"/>
                        </div>
                    </div>

                    <div class="row padding5">
                        <div class="col-lg-5">
                            Visibility*
                        </div>

                        <div class="col-lg-7">
                            <g:select name="visibility" from="${com.sharehub.enums.Visibility.values()}" optionValue="displayName"
                                      class="form-control"/>%{--
                            <g:select name="createTopicVisibility" from="${com.sharehub.enums.Visibility.values()*.displayName}" value="${com.sharehub.enums.Visibility.values()}"
                                      class="form-control"/>--}%
                        </div>
                    </div>
                </div>

                <div class="modal-footer">
                    <button type="submit" class="btn btn-primary">Save</button>
                    <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
                </div>
            </g:form>
        </div>
    </div>
</div>