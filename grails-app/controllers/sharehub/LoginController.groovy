package sharehub

import com.sharehub.enums.Roles
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.security.access.annotation.Secured
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetails


//@Secured('permitAll')
class LoginController {

    def userService
    def utilService

    AuthenticationManager authenticationManager

//    @Autowired @Qualifier("authenticationManager") private AuthenticationManager authenticationManager;

    def beforeInterceptor = {
        if (utilService.isUser(Roles.ADMIN) || utilService.isUser(Roles.USER)){
            redirect(controller: "home")
            return false
        }
    }
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
        if (!passwordToken || !passwordToken.user.isEnabled()) {
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
        println user
        if (user) {
            flash.success = "You are successfully registered. Enjoy sharing :)"
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(user.username, params.password)
            request.getSession()
            token.setDetails(new WebAuthenticationDetails(request))
            Authentication authenticatedUser = authenticationManager.authenticate(token)
            SecurityContextHolder.getContext().setAuthentication(authenticatedUser)

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
