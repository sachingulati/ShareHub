/**
 * Created by intelligrape on 21/3/15.
 */

var reportDelay = 5000;
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

function reLoadContent($div){
    $div.load($div.data('ajax-url'),$div.data('ajax-params'));
}

function hide($obj){
    $obj.hide();
}
function successReport(reportText){
    var $success = $('#successReport');
    $success.find('.reportText').html(reportText);
    $success.show();
    setTimeout(hide, reportDelay, $success);
}
function infoReport(reportText){
    var $info = $('#info');
    $info.find('.reportText').html(reportText);
    $info.show();
    setTimeout(hide, reportDelay, $info);
}
function warningReport(reportText){
    var $warningReport = $('#warningReport');
    $warningReport.find('.reportText').html(reportText);
    $warningReport.show();
    setTimeout(hide, reportDelay, $warningReport);
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
$(document).on('load','.topicSelector',function(){
    $(this).load(topicSelectorUrl);
});
$(document).ready(function(){
    $(".topicSelector").load();
    $rateBox = $('.rate');
    $ratingHearts = $('.ratingHearts');
    showRating($rateBox,-1);
    $ratingHearts.hover(rateOnHover);
    $rateBox.mouseout(rateReset);
    $ratingHearts.click(changeRating);
});
$(document).on('hover', '.rate',function(){
    console.log("rating");
});