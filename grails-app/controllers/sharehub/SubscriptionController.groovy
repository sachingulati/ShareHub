package sharehub

import com.sharehub.enums.Seriousness

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
