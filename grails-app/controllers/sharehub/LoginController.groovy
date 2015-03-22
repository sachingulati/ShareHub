package sharehub

class LoginController {

    def resourceService
    def userService
    def index() {
        def resourceList = resourceService.getResourceList(offset: 0, max: 5)
        render view: "/login", model: [recentResources: resourceList]
    }
    def loginHandler(String username, String password){
        User user = User.findByUsernameAndPasswordAndActive(username,password,true)
        if(!user){
            user = User.findByEmailAndPasswordAndActive(username,password,true)
            if(user){
                username = user.username;
            }
        }
        if(user){
            session["username"] = username
            session["admin"] = user.admin
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
    def checkEmail(){
        if (User.findByEmail(params.email)){
            render "false"
            return false
        }
        render "true"
        return false
    }
    def checkUsername(){
        println params
        if (User.findByUsername(params.username)){
            render "false"
            return false
        }
        render "true"
        return false
    }
}
