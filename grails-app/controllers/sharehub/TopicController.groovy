package sharehub

import com.sharehub.enums.Seriousness
import com.sharehub.enums.Visibility

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional
class TopicController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]
    def topicService
    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Topic.list(params), model: [topicInstanceCount: Topic.count()]
    }

//    @Transactional
    def createTopic(){

        String visibility = params.createTopicVisibility.toString();
        Topic topic = new Topic(name: params.createTopicName, createdBy: User.findByUsername(session["username"]),visibility: Visibility.valueOf(params.createTopicVisibility.toString().toUpperCase()))
        topic.validate()
        if(topic.hasErrors()){
            render topic.errors.allErrors
            //redirect(url: request.getRequestURL())
        }
        else{

            topic.save(failOnError: true)
            redirect(action: "showTopic", params: [id: topic.id])
            return false
        }
    }
    def subscribe(){
        if(!params.id) {
            render "Bad Request!"
            return false
        }
        User user = User.findByUsername(session["username"])
        Topic topic = Topic.findById(params.id)
        if(!user || !topic){
            render "Bad Request!"
            return false
        }
        Subscription subscription = new Subscription(user: user, topic: topic, seriousness: Seriousness.SERIOUS)
        user.addToSubscriptions(subscription)
        topic.addToSubscriptions(subscription)
        subscription.save()
        user.subscriptions
        render "Unsubscribe"
        //redirect(action: "showTopic", params: params)
    }
    def unsubscribe(){
        if(!params.id) {
            render "Bad Request!"
            return false
        }
        Subscription subscription = Subscription.findByUserAndTopic(User.findByUsername(session["username"]), Topic.findById(params.id))
        if(!subscription) {
            render "Bad Request!"
            return false
        }
        subscription.delete()
        render "Subscribe"
    }
    def showTopic(){
        Topic topic = Topic.findById(params.id)
        User user = User.findByUsername(session["username"])
        if(!topicService.show(user,topic)){
            redirect(controller: "home")
            return false
        }
        render (view: "/showTopic", model: [topic:topic])
    }
    def show(Topic topicInstance) {
        respond topicInstance
    }

    def create() {
        respond new Topic(params)
    }

//    @Transactional
    def save(Topic topicInstance) {
        if (topicInstance == null) {
            notFound()
            return
        }

        if (topicInstance.hasErrors()) {
            respond topicInstance.errors, view: 'create'
            return
        }

        topicInstance.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'topic.label', default: 'Topic'), topicInstance.id])
                redirect topicInstance
            }
            '*' { respond topicInstance, [status: CREATED] }
        }
    }

    def edit(Topic topicInstance) {
        respond topicInstance
    }

//    @Transactional
    def update(Topic topicInstance) {
        if (topicInstance == null) {
            notFound()
            return
        }

        if (topicInstance.hasErrors()) {
            respond topicInstance.errors, view: 'edit'
            return
        }

        topicInstance.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'Topic.label', default: 'Topic'), topicInstance.id])
                redirect topicInstance
            }
            '*' { respond topicInstance, [status: OK] }
        }
    }

//    @Transactional
    def delete(Topic topicInstance) {

        if (topicInstance == null) {
            notFound()
            return
        }

        topicInstance.delete flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'Topic.label', default: 'Topic'), topicInstance.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'topic.label', default: 'Topic'), params.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NOT_FOUND }
        }
    }
}
