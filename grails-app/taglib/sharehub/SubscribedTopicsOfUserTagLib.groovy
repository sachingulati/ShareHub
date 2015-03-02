package sharehub

class SubscribedTopicsOfUserTagLib {
    static defaultEncodeAs = [taglib:'html']
    static namespace = "sh"
    //static encodeAsForTags = [tagName: [taglib:'html'], otherTagName: [taglib:'none']]
    def subscriptionService
    def subscribedTopics = {attr->
        List topics=subscriptionService.getSubscribedTopics(session["username"]);
        out << topics

    }
}
