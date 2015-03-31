package sharehub

class ApplicationTagLib {
    static namespace = "sh"
    static defaultEncodeAs = [taglib: 'raw']
    def image = { attr ->
        String path = createLink(controller: "assets", action: "user-default.png", absolute: true)
        if (attr.src) {
            path = createLink(controller: "user", action: "showUserImage", params: [photoUrl: attr.src], absolute: true)
        }
        out << "<img src='" + path + "' style='height:80px; width:80px;'/>"
    }

    def topBarSearch = {
        if (session["admin"]) {
            out << g.textField(name: "search", class: "form-control", placeholder: "Search")
        }
        else {
            out << g.textField(name: "search", class: "form-control", title: "Please enter some text to search", placeholder: "Search", required: "required")
        }
    }

    def markRead = { attr ->
        ResourceStatus resourceStatus = ResourceStatus.findByResourceAndUser(Resource.load(attr.resourceId), User.findByUsername(session["username"]))
        if (session["username"] && resourceStatus) {
            out << '<a class="inboxLinkStyle markReadLink" data-resource-id="' + attr.resourceId +
                    '" href="javascript:void(0)"> Mark ' + (resourceStatus.isRead ? "un" : "") + "read </a>"
        }
    }

    def subscribe = { attr ->
        Subscription subscription = Subscription.findByTopicAndUser(attr.topic, User.findByUsername(session["username"]))
        if (subscription) {
            out << render(template: "/subscription/subscribeOptions",
                    model: [subscriptionType: subscription.seriousness, topicId: attr.topic.id, topicName: attr.topic.name,
                            canUnsubscribe:(attr.topic.createdBy.username != session["username"])])
        } else {
            out << "<a class='subscribe' href='javascript:void(0)' data-topic-id='" + attr.topic.id + "'>Subscribe</a>"
        }
    }

    def isEditableTopic = { attr, body ->
        if (session["admin"] || attr.topic.createdBy.username == session["username"]) {
            out << (body())
        }
    }

    def isEditableResource = { attr, body ->
        if (session["admin"] || attr.resource.topic.createdBy.username == session["username"] || attr.resource.createdBy.username == session["username"]) {
            out << (body())
        }
    }

    def date = { attr ->
        Date date = attr.date
        if (date) {
            out << date.format("h:mm a, d MMM, yyyy")
        }
    }
}