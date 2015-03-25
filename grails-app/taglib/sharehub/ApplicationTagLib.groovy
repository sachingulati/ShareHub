package sharehub

class ApplicationTagLib {
    static namespace = "sh"
    static defaultEncodeAs = [taglib: 'raw']
    //static encodeAsForTags = [tagName: [taglib:'html'], otherTagName: [taglib:'none']]
    def userService
    def userName = {
        out << userService.getName(session["username"])
    }
    def image = { attr ->
        String path = createLink(controller: "assets", action: "user-default.png", absolute: true)
        if (attr.src)
            path = createLink(controller: "user", action: "showImage", params: [photoUrl: attr.src], absolute: true)
        out << "<img src='" + path + "' class='img-media'/>"
    }

    def admin = {
        Boolean admin = session["admin"]
        if (admin == true) {
            out << "<a href='${createLink(controller: "user", action: "adminPanel")}'>Users</a>"
        }
    }
    def topBarSearch={
        if (session["admin"]){
            out<<g.textField(name: "search", class: "form-control", placeholder: "Search")
        }
        else{
            out<<g.textField(name: "search", class: "form-control", title: "Please enter some text to search", placeholder: "Search", required: "required")
        }
    }
    def markRead = { attr ->
        if (!session["username"])
            return
        ResourceStatus resourceStatus = ResourceStatus.findByResourceAndUser(Resource.load(attr.resourceId), User.findByUsername(session["username"]))
        if (!resourceStatus)
            return
        Boolean read = resourceStatus.isRead
        out << '<a class="inboxLinkStyle markReadLink" data-resource-id="' + attr.resourceId+ '" href="javascript:void(0)"> Mark ' + (read ? "un" : "") + "read </a>"
    }
    def subscribe = { attr ->
        if (!(attr.topic)){
            render ""
            return
        }
        Subscription subscription = Subscription.findByTopicAndUser(attr.topic, User.findByUsername(session["username"]))
        if (subscription) {
            out<< render(template: "/subscription/subscribeOptions", model: [subscriptionType: subscription.seriousness, topicId: attr.topic.id, topicName: attr.topic.name, canUnsubscribe:(attr.topic.createdBy.username!=session["username"])])
        } else {
            out<< "<a class='subscribe' href='javascript:void(0)' data-topic-id='" + attr.topic.id+"'>Subscribe</a>"
        }
    }

    def isEditableTopic ={attr, body->
        if (session["admin"] || attr.topic.createdBy.username == session["username"]){
            out<< (body())
        }
    }
    def isEditableResource ={attr, body->
        if (session["admin"] || attr.resource.topic.createdBy.username == session["username"] || attr.resource.createdBy.username == session["username"]){
            out<< (body())
        }
    }
    def date = { attr ->
        Date date = attr.date
        if (!date) return
        out << date.format("h:mm a, d MMM, yyyy")
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