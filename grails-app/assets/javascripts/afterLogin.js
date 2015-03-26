/**
 * Created by intelligrape on 21/3/15.
 */
jQuery.ajaxSetup({
    complete: function(data){
        if(data.responseText == "Login failed!"){
            window.location.reload()
        }
    }
});
function openModal($modal){
    var options = {
        "backdrop": "true",
        "keyboard": "true"
    };
    $modal.modal(options);
}
function openShareLink() {
    openModal($('#shareLink'));
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

function openCreateTopic() {
    openModal($('#createTopic'));
}
function invitationSent(data){
    $('#sendInvite').modal('hide');
    successReport(data);
}
function openSendInvite() {
    var $invite = $("#sendInvite");
    $invite.find('#topicName').css("display","none");
    $invite.find('.topicSelector select').css("display","block").attr("readOnly",false).val('');
    $invite.find('.topicSelector').css("display","block");
    $invite.find('#sendInviteLabel').text("Send Invitation");
    openModal($invite);
}

function openTopicInvite(topicId, topicName) {
    var $invite = $("#sendInvite");
    $invite.find('#topicName').css("display","block").text(topicName);
    $invite.find('.topicSelector select').val(topicId).css("display","none").attr("readOnly",true);
    $invite.find('#sendInviteLabel').text("Send Invitation: " + topicName);
    openModal($invite);
}

function openEditResource(id, link){
    var $resource = $(link).closest('.resource');
    var $modal = ($resource.data("resource-type")=="link")?$('#shareLink'):$('#shareDocument');
    $modal.find("#title").val($resource.find(".resourceTitle").text());
    $modal.find("#description").val($resource.find(".resourceDescription").text());
    $modal.find(".topicSelector select").val($resource.find(".resourceTopicLink").data("topic-id"));
    $modal.find(":submit").attr("name","_action_editResource");
    $modal.find(".typeInfo").css("display","none");
    $modal.find("#resourceId").val($resource.data("resource-id"));
    $modal.find('.topicSelector select').attr("disabled", true);
    openModal($modal);
}

function openShareDocument(){
    var $modal = $('#shareDocument');
    $modal.find("#title").val('');
    $modal.find("#description").val('');
    $modal.find(".topicSelector select").val('');
    $modal.find(":submit").attr("name","_action_shareDocument");
    $modal.find(".typeInfo").css("display","block");
    $modal.find('.topicSelector select').attr("disabled", false);
    openModal($modal);
}

function reLoadContent($div){
    $div.load($div.data('ajax-url'),$div.data('ajax-params'));
}

function hide($obj){
    $obj.hide();
}
function updateProfileStatus(data){
    if(data == "Profile updated successfully"){
        successReport(data);
    }
    else{
        warningReport("Error in updating profile")
    }
}
function changePasswordStatus(data){
    if(data == "Password successfully changed."){
        successReport(data);
    }
    else{
        warningReport(data.length>50?"Error in changing password!":data);
    }
}
function showRating($rateBox, num){
    var myRating = $rateBox.data('my-rating');
    if(num>-1)
        myRating = num;
    var $ratingHearts = $rateBox.find('.ratingHearts');
    for(var a = 0;a<5;a++){
        $ratingHearts.eq(a).attr("src", a<myRating?rateOnImagePath:rateOffImagePath);
    }
}
function rateOnHover(){
    var $rateObj = $(this);
    var $rateBox = $rateObj.closest(".rate");
    var id = $rateObj.data('id');
    showRating($rateBox,id);
}
function rateReset(){
    showRating($(this),-1);
}
function changeRating(){
    var $rateObj = $(this);
    var id = $rateObj.data('id');
    var resourceId = $rateObj.data('resource-id');
    jQuery.ajax({
        url: changeRatingUrl,
        data: {resourceId: resourceId, rate: id},
        method: "post",
        success: function(data){
            if(data != "Invalid Request!" && data.length<30){
                var $rateBox = $rateObj.closest('.rate');
                $rateBox.data('my-rating',id);
                showRating($rateBox,-1);
                var avgRating = data.substring(data.indexOf(":")+1,data.indexOf(','));
                var totalCount = data.substring(data.lastIndexOf(":")+1);
                $rateBox.find('.avgRating').text(avgRating);
                $rateBox.find('.totalCount').text(totalCount);
            }
        }
    });
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
                reLoadContent($('#topicPagination'));
                reLoadContent($("[name=unreadResources]"));
                successReport("Subscribed successfully.")
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
                $('.subscriptionStatus'+id).html(data);
                reLoadContent($('#topicPagination'));
                reLoadContent($("[name=unreadResources]"));
                successReport("Unsubscribed successfully.");
            }
        });
    }
);
function afterResourceDelete(status){
    if(status == "Invalid request!"){
        warningReport(status)
    }
    else if(status != "Login failed!"){
        successReport(status);
        reLoadContent($('#resourceList'));
    }
}

$(document).on('click', '.markReadLink',
    function(){
        var $id = $(this).data('resource-id');
        var $obj = $(this);
        $.post(switchReadUrl, {resource: $id})
            .done(function(response){
                if(response == "Bad Request!"){
                    alert(response)
                }
                else if(response != "Login failed!"){
                    $obj.text(response)
                    reLoadContent($("[name=unreadResources]"));
                    successReport("Successfully Marked.");
                }
            }
        )
    }
);
$(document).on('click', '.deleteResource', function(){

});
$(document).ready(function(){
    $rateBox = $('.rate');
    $ratingHearts = $('.ratingHearts');
    showRating($rateBox,-1);
    $ratingHearts.hover(rateOnHover);
    $rateBox.mouseout(rateReset);
    $ratingHearts.click(changeRating);
});
$(document).ready(function(){
    $("[data-ajax-url]").each(function(){
        reLoadContent($(this));
    });
});
$(document).on('hover', '.rate',function(){
    console.log("rating");
});

