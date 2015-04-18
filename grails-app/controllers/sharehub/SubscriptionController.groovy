package sharehub

import com.sharehub.enums.Seriousness

class SubscriptionController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    static afterInterceptor = {
        render((sh.subscribe(topic: Topic.get(params.topicId))))
    }
    def subscriptionService

    // update required
    def subscribe() {
        subscriptionService.subscribe(springSecurityService.currentUser.username, params.topicId)
    }

    def unsubscribe() {
        subscriptionService.unsubscribe(springSecurityService.currentUser.username, params.topicId)
    }

    def modifySubscriptionSeriousness() {
        subscriptionService.subscribe(springSecurityService.currentUser.username, params.topicId, Seriousness.valueOf(params.seriousness))
    }
}
