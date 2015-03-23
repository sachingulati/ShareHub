package sharehub

import com.sharehub.enums.Visibility
import grails.validation.Validateable

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional


class UserController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]
    def userService

    def profile(){
        User user = User.findByUsername(params.id)
        if (!user){
            redirect(action: "myProfile")
            return false
        }
        if (params.id == session["username"])
            params.myProfile = true
        render (view: "/user/profile", model: [user: user, myProfile: params.myProfile])
        return false
    }
    def myProfile(){
        forward(action: "profile", params: [id:session["username"], myProfile:true])
        return false
    }
    def editProfile(){
        User user = User.findByUsername(session["username"])
        render(view: "/user/editProfile", model: [user: user])
    }
    def updateUser(){
        if (!userService.updateUser(session["username"], params.firstName, params.lastName, params.email, (params.removePhoto=="on"), params.photo)){
            render "Bad Request!"
            return false
        }
        render "Profile updated successfully"
        return false
    }
    def changePassword(){
        render userService.changePassword(session["username"], params.newPassword,  params.confirmPassword, params.currentPassword)
        return false
    }
    def isLoggedIn(){
        if (session["username"]){
            render("true")
            return false
        }
        render("false")
        return false
    }
    def showImage(){
        if (!params.photoUrl){
            return false
        }
        File file = new File(params.photoUrl)
        response.contentLength = file.bytes.length
        response.outputStream << file.bytes
    }

    def adminPanel(){
        if (session["admin"] == false){
            redirect(action: "myProfile")
            return false
        }
        render view: "/user/adminPanel", model: [users:User.list()]
    }

    def activate(){
        if (!session["admin"]){
            redirect(controller: "home")
            return false
        }
        User user = User.findByUsername(params.username)
        if (!user){
            render("User Not Found!")
            return false
        }

        else{
            user.active = true
            user.save flush:true
            render remoteLink(update: "manager_${user.username}", controller: "user", action: "deactivate"){"Deactivate"}
//            <g:remoteLink update="manager_${user.username}" controller="user" action="${user.active?"Deactivate":"Activate"}" params="[username: user.username]">${user.active?"Deactivate":"Activate"}</g:remoteLink>
            return false
        }

    }
    def deactivate(){
        if (!session["admin"]){
            redirect(controller: "home")
            return false
        }
        User user = User.findByUsername(params.username)
        if (!user){
            render("User Not Found!")
            return false
        }

        else{
            user.active = false
            user.save flush:true
            render remoteLink(update: "manager_${user.username}", controller: "user", action: "activate"){"Activate"}
//            <g:remoteLink update="manager_${user.username}" controller="user" action="${user.active?"Deactivate":"Activate"}" params="[username: user.username]">${user.active?"Deactivate":"Activate"}</g:remoteLink>
            return false
        }

    }
    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond User.list(params), model:[userInstanceCount: User.count()]
    }
    def show(User userInstance) {
        respond userInstance
    }

    def showUser(){/*
        String username = params.id
        render (new UserViewCommand(User.createCriteria().get {
            projections {
                property("username")
            }
            eq("username", username)
        },Visibility.PUBLIC))*/

    }
    def create() {
        respond new User(params)
    }

    @Transactional
    def save(User userInstance) {
        if (userInstance == null) {
            notFound()
            return
        }

        if (userInstance.hasErrors()) {
            respond userInstance.errors, view:'create'
            return
        }

        userInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'user.label', default: 'User'), userInstance.id])
                redirect userInstance
            }
            '*' { respond userInstance, [status: CREATED] }
        }
    }

    def logout(){
        session.invalidate()
        redirect controller: "Login"
    }
    def edit(User userInstance) {
        respond userInstance
    }

    @Transactional
    def update(User userInstance) {
        if (userInstance == null) {
            notFound()
            return
        }

        if (userInstance.hasErrors()) {
            respond userInstance.errors, view:'edit'
            return
        }

        userInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'User.label', default: 'User'), userInstance.id])
                redirect userInstance
            }
            '*'{ respond userInstance, [status: OK] }
        }
    }

    @Transactional
    def delete(User userInstance) {

        if (userInstance == null) {
            notFound()
            return
        }

        userInstance.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'User.label', default: 'User'), userInstance.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'user.label', default: 'User'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }

}

class UserCommand {
    String firstName,lastName
    String email
    String username, password, confirmPassword
    byte[] photo
    boolean admin
    boolean active
    static hasMany = [topics: Topic,resources: Resource,subscriptions: Subscription]
    static constraints = {
        username unique: true
        email (unique: true,email: true, blank: false)
        password(size:8..20,blank: false)
        photo nullable: true
        confirmPassword(validator:{val, user->
            return val.equals(user.password)
        })
    }
}
class EditUserCommand{
    String username
    String firstName, lastName
    String photoUrl
    boolean photoCheck
    static constraints={
        firstName nullable: false
        lastName nullable: false
        username nullable: false
    }
}
@Validateable
class ChangePasswordCommand{
    String username
    String currentPassword
    String newPassword, confirmPassword
    User user
    static constraints={
        username nullable: false
        currentPassword nullable: false
        newPassword nullable: false
        confirmPassword nullable: false
        confirmPassword(validator:{val, user->
            return val.equals(user.newPassword)
        })
        user nullable: false
    }
    def getUser(){
        user = User.findByUsername(username)
    }
}
class UserViewCommand {
    String username
    String firstName, lastName
    String photoUrl
    Boolean admin
    List<Topic> topicsCreated
    List<Topic> subscribedTopics
    Boolean valid = false
    def getName(){
        return firstName+" " + lastName
    }
    def getSubscriptionCount(){
        return subscribedTopics.size()
    }
    def getTopicCount(){
        return topicsCreated.size()
    }
    def UserViewCommand(String username,Visibility access){
        this.username = username
        User user = User.findByUsername(username)
        if(!user) return
        valid = true
        firstName = user.firstName
        lastName = user.lastName
        photoUrl = user.photoUrl
        admin = user.admin
        subscribedTopics = user.subscriptions?.topic
        topicsCreated = user.topics as List
        if(access == Visibility.PRIVATE)
            return
        def findPublicTopics = {Topic topic->
            topic.visibility == Visibility.PUBLIC
        }
        topicsCreated = topicsCreated?.findAll(findPublicTopics)
        subscribedTopics = subscribedTopics?.findAll(findPublicTopics)
    }

}
