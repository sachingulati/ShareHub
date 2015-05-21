package sharehub

import org.springframework.security.access.annotation.Secured


//@Secured('permitAll')
class LoginController {

    def userService

    def index() {
        if (params.auth=="fail"){
            flash.error = "Invalid username or password!"
        }
        render view: "/login/login", model: [recentResources: Resource.byPublicTopic().sortByDate().list(max: 5, offset: 0),
                                             topPost: Resource.byPublicTopic().sortByRating().list(max: 5, offset: 0).collect{it[0]}]
    }

    private setUserSession (User user, boolean keepMeLogin = false) {
//        session["username"] = user.username
////        session["admin"] = user.admin
//        session["name"] = user.name
//        if (keepMeLogin) {
//            session.setMaxInactiveInterval(-1)
//        }
    }

    def loginHandler (String username, String password) {
        println "-----------------------------------------------------------------"
        println params
        User user = User.findByUsernameAndPasswordAndEnabled(username, password, true) ?:
                User.findByEmailAndPasswordAndEnabled(username, password, true)
        println "-----------------------------------------------------------------"
        println user
        if (user) {
            setUserSession(user, params.keepMeLogin == "on")
        } else {
            flash.error = "Invalid username or password!"
        }
        redirect(url: "/")
    }

    def forgotPassword() {
        render(view: "/login/forgotPassword")
    }

    def forgotPasswordHandler() {
        User user = userService.forgotPassword(params.username, g)
        if (user) {
            flash.success = "Mail sent. Please check your mail box."
        }else {
            flash.error = "User not found!"
        }
        redirect(action: "forgotPassword")
    }

    def changePassword() {
        PasswordToken passwordToken = PasswordToken.findByToken(params.token)
        if (!passwordToken || !passwordToken.user.isActive()) {
            flash.error = "Invalid password token!"
            redirect(action: "index")
        }
        else {
            render(view: "/login/changePassword", model: [token: passwordToken.token])
        }
    }

    def updatePassword() {
        User user = userService.changePasswordWithToken(params.token, params.password, params.confirmPassword)
        if (user) {
            setUserSession(user)
            flash.success = "Password updated successfully."
        }
        else {
            flash.error = "Error in updating password! Please try again.."
        }
        redirect(url: "/")
    }

    def deleteToken() {
        PasswordToken passwordToken = PasswordToken.findByToken(params.token)
        if (passwordToken) {
            passwordToken.delete(flush: true)
            flash.success = "Token deleted."
        }
        redirect(url: "/")
    }

    def register() {
        User user = userService.register(params)
        if (user) {
            setUserSession(user)
            flash.success = "You are successfully registered. Enjoy sharing :)"
        } else {
            flash.error = "Invalid data!"
        }
        redirect(url: "/")
    }

    def checkEmail(String email) {
        render((User.countByEmail(email) == 0).toString())
    }

    def checkUsername(String username) {
        render((User.countByUsername(username) == 0).toString())
    }
}
