package sharehub

class UserController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]
    def userService

    def profile() {
        User user = User.findByUsername(params.id)
        if (!user) {
            redirect(action: "myProfile")
            return false
        }
        if (params.id == session["username"])
            params.myProfile = true
        render(view: "/user/profile", model: [user: user, myProfile: params.myProfile])
        return false
    }

    def myProfile() {
        forward(action: "profile", params: [id: session["username"], myProfile: true])
        return false
    }

    def editProfile() {
        User user = User.findByUsername(session["username"])
        render(view: "/user/editProfile", model: [user: user])
    }

    def updateUser() {
        if (!userService.updateUser(session["username"], params.firstName, params.lastName, params.email, (params.removePhoto == "on"), params.photo)) {
            render "Bad Request!"
            return false
        }
        render "Profile updated successfully"
        return false
    }

    def changePassword() {
        render userService.changePassword(session["username"], params.newPassword, params.confirmPassword, params.currentPassword)
        return false
    }

    def isLoggedIn() {
        if (session["username"]) {
            render("true")
            return false
        }
        render("false")
        return false
    }

    def showUserImage(String photoUrl) {
        if (!photoUrl) {
            render ""
        }
        File file = new File(photoUrl)
        response.contentLength = file.bytes.length
        response.outputStream << file.bytes
    }

    def adminPanel() {
        if (session["admin"] == false) {
            redirect(action: "myProfile")
            return false
        }
        render view: "/user/adminPanel", model: [users: User.list()]
    }

    def activate() {
        if (!session["admin"]) {
            redirect(controller: "home")
            return false
        }
        User user = User.findByUsername(params.username)
        if (!user) {
            render("User Not Found!")
            return false
        } else {
            user.active = true
            user.save flush: true
            render remoteLink(update: "manager_${user.username}", controller: "user", action: "deactivate") {
                "Deactivate"
            }
//            <g:remoteLink update="manager_${user.username}" controller="user" action="${user.active?"Deactivate":"Activate"}" params="[username: user.username]">${user.active?"Deactivate":"Activate"}</g:remoteLink>
            return false
        }
    }

    def deactivate() {
        if (!session["admin"]) {
            redirect(controller: "home")
            return false
        }
        User user = User.findByUsername(params.username)
        if (!user) {
            render("User Not Found!")
            return false
        } else {
            user.active = false
            user.save flush: true
            render remoteLink(update: "manager_${user.username}", controller: "user", action: "activate") { "Activate" }
//            <g:remoteLink update="manager_${user.username}" controller="user" action="${user.active?"Deactivate":"Activate"}" params="[username: user.username]">${user.active?"Deactivate":"Activate"}</g:remoteLink>
            return false
        }
    }

    def logout() {
        session.invalidate()
        redirect url: "/"
    }
}