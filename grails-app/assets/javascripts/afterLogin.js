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
function openShareLink() {
    var options = {
        "backdrop": "true",
        "keyboard": "true"
    };
    $('#shareLink').modal(options);
}

function openShareDocument() {
    var options = {
        "backdrop": "true",
        "keyboard": "true"
    };
    $('#shareDocument').modal(options);
}

function openCreateTopic() {
    var options = {
        "backdrop": "true",
        "keyboard": "true"
    };
    $('#createTopic').modal(options);
}
function invitationSent(data){
    $('#sendInvite').modal('hide');
    successReport(data);
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
                reLoadContent($('#subscriptionList'));
                reLoadContent($('#unreadResources'));
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
//                         parentDiv.html(data);
                $('.subscriptionStatus'+id).html(data);
                reLoadContent($('#subscriptionList'));
                reLoadContent($('#unreadResources'));
                successReport("Unsubscribed successfully.")
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
                successReport("Successfully Marked.")
            }
        )
    }
);
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
