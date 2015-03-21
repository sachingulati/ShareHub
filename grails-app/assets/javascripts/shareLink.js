/**
 * Created by intelligrape on 19/3/15.
 */
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
            }
        },
        messages:{
            title: "Please provide a title for your Link",
            url: {
                required: "please enter a url!",
                url: "please enter a valid url!"
            },
            description: {
                required: "Please provide a description for your link",
                maxlength: "Description can not be more than 1000 characters!"
            }
        }
    })
});