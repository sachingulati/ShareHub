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
                minlength: 3
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
                email: true
            }
        },
        messages:{
            firstName: "Please enter your First Name!",
            lastName: "Please enter your Last Name!",
            username: {
                required: "Please enter a username!",
                minlength: "Username should be at least 3 characters long!"
            },
            password: {
                required: "Password is required!",
                password: "Password should be at least 8 characters long!"
            },
            confirmPassword: "Password and confirm password do not match!",
            email: {
                required: "Email is required!",
                email: "Please enter a valid email id!"
            }
        }
    })
});