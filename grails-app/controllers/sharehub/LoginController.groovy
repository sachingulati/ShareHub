package sharehub

class LoginController {

    def index() {
        render view: "/login", model: [error:(params.error==1?"Invalid Username or Password":"")]
    }
    def validate(String username, String password){
        User u = User.findByUsernameAndPassword(username,password)
        if(u){
            forward(controller: "User", action: "loginHandler")
        }
        else{
            render view: "/login", model: [error: "Invalid username or password!"]
        }
    }
}
