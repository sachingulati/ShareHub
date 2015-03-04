package sharehub

class LoginController {

    def index() {
        render view: "/login", model: [error:(params.error?:"")]
    }
    def loginHandler(String username, String password){
        int u = User.countByUsernameAndPassword(username,password)
        if(u){
            session["username"] = username
            redirect(controller: "Home", action: "dashboard");
        }
        else{
            render view: "/login", model: [error: "Invalid username or password!"]
        }
    }
}
