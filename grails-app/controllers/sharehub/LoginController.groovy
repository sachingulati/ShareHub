package sharehub

class LoginController {

    def resourceService
    def userService
    def index() {
        def resourceList = resourceService.getResourceList(offset: 0, max: 5)
        render view: "/login/login", model: [recentResources: resourceList]
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
            if (params.keepMeLogin=="on"){
                session.setMaxInactiveInterval(-1)
            }
            redirect(controller: "Home", action: "dashboard");
        }
        else{
            flash.message = "Invalid username or password!"
            forward(controller: "login")
        }
    }

    def forgotPassword(){
        render(view: "/login/forgotPassword")
        return false
    }
    def requestPassword(){
        User user = userService.forgotPassword(params.username,g)
        if (user==null){
            flash.userNotFound = "User not found!"
            redirect(action: "forgotPassword")
            return false
        }
        flash.message = "Mail sent. Please check your mail box."
//        render(template: "forgotPasswordEmail", model: [token: PasswordToken.findByUser(user)])
        redirect(action: "index")
        return false
    }
    def changePassword(){
        PasswordToken passwordToken = PasswordToken.findByToken(params.token)
        if (!passwordToken || !passwordToken.user.isActive()){
            redirect(action: "index")
            return false
        }
        render(view: "/login/changePassword", model: [token: passwordToken.token])
        return false
    }
    def updatePassword(){
        User user = userService.changePasswordWithToken(params.token, params.password, params.confirmPassword)
        if (user){
            session["username"] = user.username
            session["isAdmin"] = user.isAdmin()
            redirect(controller: "home")
            return false
        }
        redirect(controller: "login")
        return false
    }
    def invalidChangePasswordRequest(){
        PasswordToken passwordToken = PasswordToken.findByToken(params.token)
        if (passwordToken){
            passwordToken.delete(flush: true)
            flash.message = "Token deleted."
        }
        redirect(controller: "login")
        return false
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
