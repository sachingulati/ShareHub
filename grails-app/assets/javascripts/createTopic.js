$(document).ready(function(){
    $('#createTopicForm').validate({
        rules:{
            name: "required"
        },
        messages:{
            name: "Please enter Topic Name!"
        }
    })
});
