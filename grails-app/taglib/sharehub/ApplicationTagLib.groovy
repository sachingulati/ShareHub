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
        Boolean read = ResourceStatus.findByResourceAndUser(Resource.load(attr.resourceId),User.findByUsername(session["username"])).isRead
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
}