/**
 * Created by intelligrape on 19/3/15.
 */
$(document).on('keydown','#shareLinkForm #url', function(){
    var $url=$(this);
    var oldvalue = $url.val();
    if(oldvalue.indexOf('http://')!=0){
        $url.val("http://");
        return;
    }
    setTimeout(function(){
        if($url.val().indexOf('http://')!=0){
            $url.val(oldvalue)
        }
    },1);
});

$(document).ready(function(){
    $('#shareLinkForm').validate({
        rules:{
            title: "required",
            url: {
                required: true,
                url: true
            },
            description: {
                required: true,
                maxlength: 1000
            },
            topic: "required"
        },
        messages:{
            title: "Please provide a title for your Link",
            url: {
                required: "please enter a url",
                url: "please enter a valid url"
            },
            description: {
                required: "Please provide a description for your link",
                maxlength: "Description can not be more than 1000 characters!"
            },
            topic: "Please select a topic"
        }
    })
});