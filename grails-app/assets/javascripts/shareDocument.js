/**
 * Created by intelligrape on 19/3/15.
 */
$(document).ready(function(){
    $('#shareDocumentForm').validate({
        rules:{
            title: "required",
            file: "required",
            description: {
                required: true,
                maxlength: 1000
            }
        },
        messages:{
            title: "Please provide a title for your Link",
            file: "Please select a file",
            description: {
                required: "Please provide a description for your link",
                maxlength: "Description can not be more than 1000 characters!"
            }
        }
    })
});