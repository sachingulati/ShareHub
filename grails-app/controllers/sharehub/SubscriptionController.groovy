package sharehub

import com.sharehub.enums.Seriousness

class SubscriptionController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    static afterInterceptor = {
        render((sh.subscribe(topic: Topic.get(params.topicId))))
    }
    def subscriptionService

    def subscribe() {
        subscriptionService.subscribe(params.topicId.toLong())
    }

    def unsubscribe() {
        subscriptionService.unsubscribe(params.topicId)
    }

    def modifySubscriptionSeriousness() {
        subscriptionService.subscribe(params.topicId, Seriousness.valueOf(params.seriousness))
    }
}
