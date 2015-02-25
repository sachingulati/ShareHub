package sharehub

class LoginController {

    def index() {
        render view: "/login", model: [error:(params.error==1?"Invalid Username or Password":"")]
    }
    def loginHandler(String username, String password){
        User u = User.findByUsernameAndPassword(username,password)
        if(u){
            session["username"] = username
            redirect(controller: "Home", action: "dashboard");
        }
        else{
            render view: "/login", model: [error: "Invalid username or password!"]
        }
    }
}
