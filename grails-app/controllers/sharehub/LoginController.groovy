package sharehub

class LoginController {

    def userService

    def index() {
        render view: "/login/login", model: [recentResources: Resource.byPublicTopic().sortByDate().list(max: 10, offset: 0)]
    }


    private setUserSession(User user){
        session["username"] = user.username
        session["admin"] = user.admin
        session["name"] = user.name
        if (params.keepMeLogin == "on") {
            session.setMaxInactiveInterval(-1)
        }
    }

    def loginHandler(String username, String password) {
        User user = User.findByUsernameAndPasswordAndActive(username, password, true)?:
                User.findByEmailAndPasswordAndActive(username, password, true)
        if (user) {
            setUserSession(user)
            redirect(controller: "Home", action: "dashboard")
        } else {
            flash.message = "Invalid username or password!"
            forward(controller: "login")
        }
    }

    def forgotPassword() {
        render(view: "/login/forgotPassword")
    }

    def forgotPasswordHandler() {
        User user = userService.forgotPassword(params.username, g)
        if (user == null) {
            flash.userNotFound = "User not found!"
            redirect(action: "forgotPassword")
            return false
        }
        flash.message = "Mail sent. Please check your mail box."
        redirect(action: "index")
    }

    def changePassword() {
        PasswordToken passwordToken = PasswordToken.findByToken(params.token)
        if (!passwordToken || !passwordToken.user.isActive()) {
            redirect(action: "index")
            return false
        }
        render(view: "/login/changePassword", model: [token: passwordToken.token])
    }

    def updatePassword() {
        User user = userService.changePasswordWithToken(params.token, params.password, params.confirmPassword)
        if (user) {
            setUserSession(user)
            redirect(controller: "home")
            return false
        }
        redirect(controller: "login")
    }

    def invalidChangePasswordRequest() {
        PasswordToken passwordToken = PasswordToken.findByToken(params.token)
        if (passwordToken) {
            passwordToken.delete(flush: true)
            flash.message = "Token deleted."
        }
        redirect(controller: "login")
    }

    def register() {
        User user = userService.register(params)
        if (user) {
            setUserSession(user)
            redirect(controller: "home", action: "dashboard")
        } else{
            render "Invalid Data"
        }
    }

    def checkEmail(String email) {
        render(User.findByEmail(email).toString())
    }

    def checkUsername(String username) {
        render(User.findByUsername(username).toString())
    }
}
