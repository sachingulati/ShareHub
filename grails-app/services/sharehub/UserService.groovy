package sharehub

import com.sharehub.CO.MailCO
import grails.transaction.Transactional

@Transactional
class UserService {

    def grailsApplication
    def utilService
    def mailService // update required
    def springSecurityService

//    update required
    def createDefaultUsers() {
        def adminRole = new Role(authority: 'ROLE_ADMIN').save(flush: true)
        def userRole = new Role(authority: 'ROLE_USER').save(flush: true)


        User user = new User(firstName: "Sachin", lastName: "Gulati", email: "sachingulati21@gmail.com", username: "sachin",
                password: "12345678", photoUrl: (grailsApplication.config.userImages + "sachin"))
        if (user.validate()) {
            user.save(flush:true)
            UserRole.create user, userRole, true
        }
        user = new User(firstName: "Admin", lastName: "ShareHub", email: "sachin.gulati@tothenew.com", username: "admin",
                password: "12345678")
        if (user.validate()) {
            user.save(flush: true)
            UserRole.create user, adminRole, true
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
        user.admin = false
        if (params.photo.bytes.size() > 0) {
            String path = grailsApplication.config.uploadImages.toString() + params.username
            params.photo.transferTo(new File(path))
            user.photoUrl = path
        }
        user.save()
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

    def changePassword(String username, String newPassword, String confirmPassword, String currentPassword) {
        // update required
        User user = User.findByUsernameAndPassword(username, currentPassword)
        if (!user) {
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
