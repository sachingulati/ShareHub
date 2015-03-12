package sharehub

import com.sharehub.enums.Visibility

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class UserController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]
    def userService

    def profile(){
        UserViewCommand user
        if(params.id == session["username"] || User.findByUsername(session["username"]).admin){
            user = new UserViewCommand(params.id,Visibility.PRIVATE)
        }
        else {
            user = new UserViewCommand(params.id,Visibility.PUBLIC)
        }
        if(!user.valid){
            redirect(action: "myProfile")
            return false
        }
        List<Resource> resources = user.subscribedTopics.size()?Resource.findAll({topic in user.subscribedTopics && createdBy.username==user.username }):[]
        render (view: "/profile", model: [user: user, resources: resources])
        return false
    }
    def myProfile(){
        forward(action: "profile", params: [id:session["username"]])
        return false
    }

    def editProfile(){
        UserViewCommand user = new UserViewCommand(session["username"], Visibility.PRIVATE)
        if (!user.valid){
            redirect(controller: "login")
            return false
        }

        render(view: "/editProfile", model: [user: user])
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
