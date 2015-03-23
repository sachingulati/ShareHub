package sharehub

import com.sharehub.enums.Seriousness
import com.sharehub.enums.Visibility
import grails.converters.JSON

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

class SubscriptionController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def subscriptionService
    def subscribe() {
        subscriptionService.subscribe(session["username"],params.topicId)
        render((sh.subscribe(topic: Topic.get(params.topicId))))
    }
    def unsubscribe(){
        subscriptionService.unsubscribe(session["username"],params.topicId)
        render(sh.subscribe(topic: Topic.get(params.topicId)))
    }
    def modifySubscriptionSeriousness(){
        subscriptionService.subscribe(session["username"],params.topicId,Seriousness.valueOf(params.seriousness))
        render(sh.subscribe(topic: Topic.get(params.topicId)))
    }
}
