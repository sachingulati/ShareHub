$(document).on('click','.topicBox',function(){
    var topicId = $(this).data("topic-id");
    $.ajax({
            url: topicResourcesUrl,
            data: {topicId: $(this).data("topic-id"),
            header: $(this).data("topic-name")},
        success: function(data){
            $("#resourceList").html(data);
            $("#resourceList :hidden").val(topicId);
        }
    });
});
$(document).on('mouseover','.topic',function(){
    $(this).addClass("hoverEffect");
});