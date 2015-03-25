/**
 * Created by intelligrape on 25/3/15.
 */
$(document).ready(function(){
    $('#sendInvite').validate({
        rules:{
            inviteTo: "required",
            email: "required",
            topic: "required"
        },
        messages:{
            inviteTo: "Please enter the name of the person you're inviting!",
            email: "Please enter an email address to send invite!",
            topic: "Please select a topic!"
        }
    })
});