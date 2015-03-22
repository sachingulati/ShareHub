/**
 * Created by intelligrape on 19/3/15.
 */
$(document).ready(function(){
    $('#registration').validate({
        rules:{
            firstName: "required",
            lastName: "required",
            username: {
                required: true,
                minlength: 3,
                remote: checkUsernameForNewUser
            },
            password: {
                required: true,
                minlenth: 8
            },
            confirmPassword: {
                equalTo: "#registration #password"
            },
            email: {
                required: true,
                email: true,
                remote: checkEmailForNewUser
            }
        },
        messages:{
            firstName: "Please enter your First Name!",
            lastName: "Please enter your Last Name!",
            username: {
                required: "Please enter a username!",
                minlength: "Username should be at least 3 characters long!",
                remote: "Username already taken!"
            },
            password: {
                required: "Password is required!",
                password: "Password should be at least 8 characters long!"
            },
            confirmPassword: "Password and confirm password do not match!",
            email: {
                required: "Email is required!",
                email: "Please enter a valid email id!",
                remote: "Email Id already registered!"
            }
        }
    })
});