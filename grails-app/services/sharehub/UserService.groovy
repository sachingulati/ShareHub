package sharehub

import com.sharehub.CO.MailCO
import com.sharehub.enums.Roles
import grails.plugin.springsecurity.annotation.Secured
import grails.transaction.Transactional
import org.springframework.security.provisioning.JdbcUserDetailsManager

@Transactional
class UserService {

    def grailsApplication
    def utilService
    def mailService // update required
    def springSecurityService
//    update required
    def createDefaultUsers() {
        Roles.each{
            new Role(authority: it.toString()).save(flush: true)
        }

        User user = new User(firstName: "Sachin", lastName: "Gulati", email: "sachingulati21@gmail.com", username: "sachin",
                password: "12345678", photoUrl: (grailsApplication.config.userImages + "sachin"))
        if (user.validate()) {
            user.save(flush:true)
            UserRole.create user, Role.findByAuthority(Roles.USER.toString()), true
        }
        user = new User(firstName: "Admin", lastName: "ShareHub", email: "sachin.gulati@tothenew.com", username: "admin",
                password: "12345678")
        if (user.validate()) {
            user.save(flush: true)
            UserRole.create user,  Role.findByAuthority(Roles.USER.toString()), true
            UserRole.create user,  Role.findByAuthority(Roles.ADMIN.toString()), true
        }

    }

    // update required
    def register(params) {
        User user = new User()
        user.properties = params
        if (!user.validate()) {
            return null
        }
        user.firstName = user.firstName.capitalize()
        user.lastName = user.lastName.capitalize()
        user.username = user.username.toLowerCase()
        user.email = user.email.toLowerCase()
        if (params.photo.bytes.size() > 0) {
            String path = grailsApplication.config.uploadImages.toString() + params.username
            params.photo.transferTo(new File(path))
            user.photoUrl = path
        }
        user.save()
        UserRole.create user, Role.findByAuthority(Roles.USER.toString()), true
        return user
    }

    def forgotPassword(username, g) {
        User user = User.findByUsernameOrEmail(username, username)
        if (!user?.enabled) {
            return null
        }
        PasswordToken passwordToken = PasswordToken.findOrCreateByUser(user)
        if (!passwordToken) {
            return null
        }
        passwordToken.token = utilService.randomString
        passwordToken.save(failOnError: true)
        utilService.sendMail(new MailCO(to: user.email, subject: "Share Hub: Change password request.", body: g.render(template: "/login/forgotPasswordEmail", model: [token: passwordToken])))
        return user
    }

    @Secured(["ROLE_USER"])
    def updateUser(String fname, String lname, String email, Boolean removePhoto, def photo) {
        User user = springSecurityService.currentUser
        if (!user) {
            return false
        }
        user.firstName = fname
        user.lastName = lname
        user.email = email
        String photoUrl = user.photoUrl
        if (removePhoto) {
            user.photoUrl = null
        }
        if (user.validate()) {
            user.save()
            if (removePhoto) {
                utilService.deleteFile(photoUrl)
            }
            return true
        }
        return false
    }

    @Secured(["ROLE_USER"])
    def changePassword(String newPassword, String confirmPassword, String currentPassword) {
        // update required
        User user = springSecurityService.currentUser
        if (!springSecurityService.passwordEncoder.isPasswordValid(user.password,currentPassword,null)) {
            return "Invalid Password!"
        }
        if (confirmPassword != newPassword) {
            return "New Password and Confirm Password do not match!"
        }
        user.password = newPassword
        if (user.validate()) {
            user.save(failOnError: true)
            return "Password successfully changed."
        }
        return "Password must be at least 8 characters long!"
    }

    def changePasswordWithToken(String token, String password, String confirmPassword) {
        PasswordToken passwordToken = PasswordToken.findByToken(token)
        if (!passwordToken || password != confirmPassword || password.size() < 8) {
            return null
        }
        User user = passwordToken.user
        user.password = password
        if (user.validate()) {
            user.save()
            return user
        }
        return null
    }
}
