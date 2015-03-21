/**
 * Created by intelligrape on 21/3/15.
 */

function openShareLink() {
    var options = {
        "backdrop": "true",
        "keyboard": "true"
    };
    $('#shareLink').modal(options);
    $('#shareLink.topicSelector').load();
}

function openShareDocument() {
    var options = {
        "backdrop": "true",
        "keyboard": "true"
    };
    $('#shareDocument').modal(options);
    $('#shareDocument.topicSelector').load();
}

function openCreateTopic() {
    var options = {
        "backdrop": "true",
        "keyboard": "true"
    };
    $('#createTopic').modal(options);
}

function openEditTopic(topicId, topicName, seriousness) {
    var options = {
        "backdrop": "true",
        "keyboard": "true"
    };
    var topic = $('#editTopic');
    topic.find('#id').val(topicId);
    topic.find('#name').val(topicName);
    topic.find('#visibility').val(seriousness);
    topic.modal(options);
}

function openSendInvite() {
    var options = {
        "backdrop": "true",
        "keyboard": "true"
    }
    $('#sendInvite').modal(options);
    $('#sendInvite.topicSelector').load();
}

function showMessage(){
    if("${message}".length>0){
        alert("${message}");
    }
}

function reLoadContent($div){
    $div.load($div.data('ajax-url'),$div.data('ajax-params'));
}

$(document).on('click','.pagination a',function(){
    var $obj = $(this);
    var $parent = $obj.closest('.applyPaginate');
    var $max = $parent.data("ajax-params").max;
    if($obj.text()!="Next" && $obj.text()!="Previous")
        $parent.data("ajax-params").offset = ($obj.text()-1) * $max;{}
//                alert($obj.text()*$max);
});

$(document).on('click', '.subscribe',
    function(){
        var url = subscribeUrl;
        var id = $(this).data('topic-id');
//                var parentDiv = $(this).closest('.subscriptionStatus'+id);
        jQuery.ajax({
            url:url,
            data : {topicId:id},
            success: function(data){
                $('.subscriptionStatus'+id).html(data);
                reLoadContent($('#subscriptionList'));
                reLoadContent($('#unreadResources'));
            }
        });
    }
);

$(document).on('click', '.unsubscribe',
    function(){
        var url = unsubscribeUrl;
        var id = $(this).data('topic-id');
//                var parentDiv = $(this).closest('.subscriptionStatus'+id);
        jQuery.ajax({
            url:url,
            data : {topicId:id},
            success: function(data){
//                         parentDiv.html(data);
                $('.subscriptionStatus'+id).html(data);
                reLoadContent($('#subscriptionList'));
                reLoadContent($('#unreadResources'));
            }
        });
    }
);

$(document).on('click', '.markReadLink',
    function(){
        var $id = $(this).data('resource-id');
        var $obj = $(this);
        $.post(switchReadUrl, {resource: $id})
            .done(function(response){
                if(response == "Bad Request!"){
                    alert(response)
                }
                else{
                    $obj.text(response)
                }
                reLoadContent($('#unreadResources'));
            }
        )
    }
);
$(document).on('load','.topicSelector',function(){
    $(this).load(topicSelectorUrl);
});
$(document).ready(function(){
    $(".topicSelector").load();
});