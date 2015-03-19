
package sharehub
import com.sharehub.enums.Seriousness

import com.sharehub.enums.Visibility
import grails.transaction.Transactional
import org.xhtmlrenderer.css.parser.property.PrimitivePropertyBuilders
import sharehub.Topic
import sharehub.User

import static org.springframework.http.HttpStatus.*



class TopicController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]
    def topicService
    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Topic.list(params), model: [topicInstanceCount: Topic.count()]
    }

    def getRecentSubscribedTopics(){
        List<Topic> topicList = topicService.getRecentTopics(session["username"],true)
        render(template: "/topic/topicList", model: [header:"Subscriptions", hr:true], bean: topicList, var: "topics")
    }
    def getTrendingTopics(){
        List<Topic> topicList = topicService.getTrendingTopics(session["admin"])
        render(template: "/topic/topicList", model: [header:"Trending Topics", hr:true], bean: topicList, var: "topics")
    }

    def createTopic(){
        Topic topic = new Topic(name: params.name, createdBy: User.findByUsername(session["username"]),visibility: Visibility.valueOf(params.visibility))
        topic.validate()
        if(topic.hasErrors()){
            render topic.errors.allErrors
        }
        else{
            topic.save(failOnError: true, flush: true)
            redirect(action: "showTopic", params: [id: topic.id])
            return false
        }
    }

    def editTopic(){
        Topic topic = Topic.findById(params.id)
        if (!topic || !(topic.createdBy.username == session["username"] || session["admin"])){
            render "Invalid request!"
            return false
        }
        topic.name = params.name
        topic.visibility = Visibility.valueOf(params.visibility)
        topic.validate()
        if (topic.hasErrors()){
            render("Duplicate Name")
            return false
        }
        topic.save(failOnError: true, flush: true)
        redirect(action: "showTopic", params: [id: topic.id])
        return false
    }

    def delTopic(){
        Topic topic = Topic.findById(params.id)
        if (!topic){
            render("Invalid request!")
            return false
        }
        topic.delete(flush: true)
        render "done"
    }

    def showTopic(){
        Topic topic = Topic.findById(params.id)
        User user = User.findByUsername(session["username"])
        if(!topicService.show(user,topic)){
            redirect(controller: "home")
            return false
        }
        render (view: "/topic/showTopic", model: [topic:topic])
    }

    def getTopicsCreated(){
        String username
        if (params.username){
            username = params.username
        }
        else{
            username = session["username"]
        }
        List<Topic> topics = Topic.createCriteria().list{
            if (session["username"]!=params.username && !session["admin"]){
                eq("visibility",Visibility.PUBLIC)
            }
            createdBy{
                eq("username",username)
            }
        }
        render(template: "/topic/topicList", model: [header: 'Topics Created', hr:true], bean:topics, var:"topics")
    }

    def getSubscribedTopics(){
        render(select([name: "topic", from: topicService.getTopics(session["username"]), optionKey: "id", optionValue: "name", value: "id", noSelection: ['': 'Select Topic'], class: "form-control"], required: "required"))
    }
    def show(Topic topicInstance) {
        respond topicInstance
    }

    def create() {
        respond new Topic(params)
    }

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
