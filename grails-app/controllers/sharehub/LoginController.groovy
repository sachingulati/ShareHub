package sharehub

class LoginController {

    def resourceService
    def userService
    def index() {
        render view: "/login", model: [recentResources: resourceService.recentPublicResourceList()]
    }
    def loginHandler(String username, String password){
        int u = User.countByUsernameAndPasswordAndActive(username,password,true)
        if(!u){
            User user = User.findByEmailAndPasswordAndActive(username,password,true)
            if(user){
                username = user.username;
                u = 1
            }
        }
        if(u){
            session["username"] = username
            redirect(controller: "Home", action: "dashboard");
        }
        else{
            flash.message = "Invalid username or password!"
            forward(controller: "login")
        }
    }

    def register(){
        User user = userService.register(params)
        if(user) {
            session["username"] = user.username
            redirect(controller: "home", action: "dashboard")
            return false
        }
        else
            render "Invalid Data"
    }
}
