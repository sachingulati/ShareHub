package sharehub

class ApplicationTagLib {
    static namespace = "sh"
    static defaultEncodeAs = [taglib: 'raw']
    //static encodeAsForTags = [tagName: [taglib:'html'], otherTagName: [taglib:'none']]
    def userService
    def topicService
    def userName = {
        out << userService.getName(session["username"])
    }
    def topicSelector = {
        def topics = topicService.getTopics(session["username"])
        out << g.select([name: "topic", from: topics, optionKey: "id", optionValue: "name", value: "id", noSelection: ['': 'Select Topic'], class: "form-control"])
    }
    def image = { attr ->
        String path = createLink(controller: "assets", action: "user-default.png")
        if (attr.src)
            path = "data:img/png;base64," + new File(attr.src).getBytes().encodeBase64()
        out << "<img src='" + path
        out << "' width='80' height='80' />"
    }

    def admin = {
        Boolean admin = session["admin"]
        if (admin == true) {
            out << "<a href='${createLink(controller: "user", action: "adminPanel")}'>Users</a>"
        }
    }

    def markRead = { attr ->
        if (!session["username"])
            return
        ResourceStatus resourceStatus = ResourceStatus.findByResourceAndUser(Resource.load(attr.resourceId), User.findByUsername(session["username"]))
        if (!resourceStatus)
            return
        Boolean read = resourceStatus.isRead
        out << '<a class="inboxLinkStyle markReadLink" data-resource-id="' + attr.resourceId+ '"/> Mark ' + (read ? "un" : "") + "read </a>"
    }
    def resourceOptions = { attr ->
        if (attr.resource.createdBy.username == session["username"] || User.findByUsername(session["username"]).admin) {
            out << "<a href='#' class='inboxLinkStyle'>Delete</a>"
            out << "<a href='#' class='inboxLinkStyle'>Edit</a>"
        }
    }
    def subscribe = { attr ->
        if (!(attr.topic)) return
        Subscription subscription = Subscription.findByTopicAndUser(attr.topic, User.findByUsername(session["username"]))
        if (subscription) {
            out<< render(template: "/subscription/subscribeOptions", model: [subscriptionType: subscription.seriousness, topicId: attr.topic.id])
        } else {
            out<< g.remoteLink(update: "subscriptionStatus${attr.topic.id}", controller: "subscription", action: "subscribe", params: [topicId: attr.topic.id]){"Subscribe"}
        }
    }

    def isEditable={attr, body->
        if (session["admin"] || attr.topic.createdBy.username == session["username"]){
            out<< (body())
        }
    }
    def date = { attr ->
        Date date = attr.date
        if (!date) return
        out << date.format("h:mm a, d MMM, yyyy")
    }
    def rate={attr->
    }
    /*
    def rate = { attr ->
        int rating = attr.rating
        rating.times {
            out << "<img src='/ShareHub/assets/RateOn.jpg' width='25' height='25'/>"
        }
        Boolean half = attr.rating > rating
        if (half) {
            out << "<img src='/ShareHub/assets/RateHalf.jpg' width='25' height='25'/>"
        }
        (5 - rating - (half ? 1 : 0)).times {
            out << "<img src='/ShareHub/assets/RateOff.jpg' width='25' height='25'/>"
        }
    }*/
}