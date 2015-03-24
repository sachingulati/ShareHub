$(document).on('click','.topicBox',function(){
    console.log($(this).data("topic-id"));
    $.ajax({
            url: topicResourcesUrl,
            data: {topicId: $(this).data("topic-id"),
            header: $(this).data("topic-name")},
        success: function(data){
            $("#resourceList").html(data)
        }
    });
});
$(document).on('mouseover','.topic',function(){
    $(this).addClass("hoverEffect");
});