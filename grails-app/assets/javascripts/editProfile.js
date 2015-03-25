$(document).ready(
    function(){
        $('#updateProfile').validate({
            rules:{
                firstName: "required",
                lastName: "required",
                email: {
                    required: true,
                    email: true
                }
            },
            messages:{
                firstName: "Please enter your first name!",
                lastName: "Please enter your last name!",
                email: "Please enter a valid email address!"
            }
        });
        $('#changePassword').validate({
            rules:{
                currentPassword: "required",
                newPassword: "required",
                confirmPassword: {
                    equalTo: "#changePassword #newPassword"
                }
            },
            messages:{
                currentPassword: "Please enter your current password",
                newPassword: "Please enter your new password",
                confirmPassword: "Password and confirm password do not match"
            }
        })
    }
);

