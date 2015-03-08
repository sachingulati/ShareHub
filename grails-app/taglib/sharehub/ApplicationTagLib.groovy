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
        out << g.select([name: "topic", from: topics, optionKey: "id", optionValue: "name", value: "id", noSelection: ['':'Select Topic'], class: "form-control"])
    }
    def image={attr->
        String path = "/ShareHub/assets/user-default.png"
        if(attr.src)
            path = "data:img/png;base64," + new File(attr.src).getBytes().encodeBase64()
        out << "<img src='" + path
        out << "' width='80' height='80' />"
    }
    def markRead={attr->
        if(!session["username"]) return
        ResourceStatus resourceStatus = ResourceStatus.findByResourceAndUser(Resource.load(attr.resourceId),User.findByUsername(session["username"]))
        if(!resourceStatus) return
        Boolean read = resourceStatus.isRead
        out<< '<a href="'
        out<< g.createLink(controller: "resource", action: "switchReadStatus", params: [resource:attr.resourceId], class: "inboxLinkStatus")
        out<< "\" class='inboxLinkStyle'/> Mark " + (read?"un":"") + "read </a>"
    }
    def resourceOptions={attr->
        if(attr.resource.createdBy.username==session["username"] || User.findByUsername(session["username"]).admin){
            out<< "<a href='#' class='inboxLinkStyle'>Delete</a>"
            out<< "<a href='#' class='inboxLinkStyle'>Edit</a>"
        }
    }
    def subscribe={attr->
        String body=""
        Subscription subscription =Subscription.findByTopicAndUser(attr.topic,User.findByUsername(session["username"]))
        if(subscription){
            body = "Unsubscribe"
        }
        else{
            body = "Subscribe"
        }
        out<< "<a id=topic" + attr.topic.id+ " href=\"${g.createLink(controller: "topic", action: "${body}", params: [id: attr.topic.id])}\">$body</a>"
//        out<< "<a onclick='subscription()' id=topic"+attr.topic.id+">$body</a>"
    }
    def date={attr->
        Date date = attr.date
        if(!date) return
        out<< date.format("h:mm a, d MMM, yyyy")
    }
    def rate={attr->
        int rating = attr.rating
        rating.times {
            out<< "<img src='/ShareHub/assets/RateOn.jpg' width='25' height='25'/>"
        }
        Boolean half = attr.rating > rating
        if(half){
            out<< "<img src='/ShareHub/assets/RateHalf.jpg' width='25' height='25'/>"
        }
        (5-rating-(half?1:0)).times {
            out<< "<img src='/ShareHub/assets/RateOff.jpg' width='25' height='25'/>"
        }
    }
}