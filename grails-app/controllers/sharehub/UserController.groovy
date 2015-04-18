package sharehub


class UserController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]
    def userService

    def profile() {
        User user = User.findByUsername(params.id) ?: User.findByUsername(session["username"])
        if (user) {
            render(view: "/user/profile", model: [user: user, myProfile: user.username == session["username"]])
        }
        else {
            redirect(action: "myProfile")
        }
    }

    def myProfile() {
        forward(action: "profile", params: [id: session["username"], myProfile: true])
    }

    def editProfile() {
        render(view: "/user/editProfile", model: [user: User.findByUsername(session["username"])])
    }

    def updateUser() {
        if (userService.updateUser(session["username"], params.firstName, params.lastName, params.email, (params.removePhoto == "on"), params.photo)) {
            render "Profile updated successfully"
        } else {
            render "Bad Request!"
        }
    }

    def changePassword() {
        render userService.changePassword(session["username"], params.newPassword, params.confirmPassword, params.currentPassword)
    }

    def isLoggedIn() {
        render(session["username"] ? "true" : "false")
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

    def adminPanel() {
        if (session["admin"] == false) {
            redirect(action: "myProfile")
            return false
        }
        render view: "/user/adminPanel", model: [users: User.list()]
    }

    def switchActivate() {
        if (!session["admin"]) {
            redirect(url: "/")
            return false
        }
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

    def logout() {
        session.invalidate()
        redirect url: "/"
    }
}