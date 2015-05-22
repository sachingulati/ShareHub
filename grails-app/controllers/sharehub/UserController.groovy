package sharehub

import com.sharehub.enums.Roles
class UserController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]
    def userService
    def springSecurityService
    def utilService
    def profile() {
        User user = User.findByUsername(params.id)
        if (user) {
            render(view: "/user/profile", model: [user: user, myProfile: user == springSecurityService.currentUser])
        }
        else {
            redirect(action: "myProfile")
        }
    }

    @Secured(["ROLE_USER"])
    def myProfile() {
        forward(action: "profile", params: [id: springSecurityService.currentUser.username])
    }

    @Secured(["ROLE_USER"])
    def editProfile() {
        render(view: "/user/editProfile", model: [user: springSecurityService.currentUser])
    }

    @Secured(["ROLE_USER"])
    def updateUser() {
        if (userService.updateUser(params.firstName, params.lastName, params.email, (params.removePhoto == "on"), params.photo)) {
            render "Profile updated successfully"
        } else {
            render "Bad Request!"
        }
    }

    @Secured(["ROLE_USER"])
    def changePassword() {
        render userService.changePassword(params.newPassword, params.confirmPassword, params.currentPassword)
    }

    def isLoggedIn() {
        render(springSecurityService.currentUser ? "true" : "false")
    }

    def showUserImage(String photoUrl) {
        if (photoUrl) {
            File file = new File(photoUrl)
            response.contentLength = file.bytes.length
            response.outputStream << file.bytes
        } else {
            render ""
        }
    }

    @Secured(["ROLE_ADMIN"])
    def adminPanel() {
        render view: "/user/adminPanel", model: [users: User.list()]
    }

    @Secured(["ROLE_ADMIN"])
    def switchActivate() {
        User user = User.findByUsername(params.username)
        if (user) {
            user.enabled = !user.enabled
            user.save flush: true
            render remoteLink(update: "manager_${user.username}", controller: "user", action: "switchActivate") {
                (user.enabled ? "Deactivate" : "Activate")
            }
        } else {
            render("User Not Found!")
        }
    }
}


import grails.plugin.springsecurity.annotation.Secured
