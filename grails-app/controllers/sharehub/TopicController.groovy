package sharehub
import com.sharehub.enums.Visibility



class TopicController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]
    def topicService
    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Topic.list(params), model: [topicInstanceCount: Topic.count()]
    }

    def getRecentSubscribedTopics(){
        List<Topic> topicList = topicService.getTopicList(bySubscriberUsername: session["username"])
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
        Topic topic = topicService.showTopic(session["username"], params.id)
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
        List<Topic> topicList
        if (username != session["username"] && !session["admin"]){
            topicList = topicService.getTopicList(byCreatorUsername: username, visibility: Visibility.PUBLIC)
        }
        else {
            topicList = topicService.getTopicList(byCreatorUsername: username)
        }
        render(template: "/topic/topicList", model: [header: 'Topics Created', hr:true], bean:topicList, var:"topics")
    }

    def getSubscribedTopics(){
        def topicList = topicService.getTopicList(bySubscriberUsername: session["username"])
        render g.select(name: "topic", from: topicList, optionKey: "id", optionValue: "name", value: "id", noSelection: ['': 'Select Topic'], class: "form-control", required: "required")
    }
}
