<div class="modal fade" id="editTopic" tabindex="-1" role="dialog" aria-labelledby="createTopicModal" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">Edit Topic</h4>
            </div>
            <g:form controller="topic" action="editTopic">
                <div class="modal-body">
                    <g:hiddenField name="id" value="hidden field"/>
                    <div class="row padding5">
                        <div class="col-lg-5">
                            Name*
                        </div>

                        <div class="col-lg-7">
                            <g:textField name="name" class="form-control" placeholder="Topic name"/>
                        </div>
                    </div>

                    <div class="row padding5">
                        <div class="col-lg-5">
                            Visibility*
                        </div>

                        <div class="col-lg-7">
                            <g:select name="visibility" from="${com.sharehub.enums.Visibility.values()}" optionValue="displayName"
                                      class="form-control"/>
                        </div>
                    </div>
                </div>

                <div class="modal-footer">
                    <button type="submit" class="btn btn-primary">Update</button>
                    <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
                </div>
            </g:form>
        </div>
    </div>
</div>