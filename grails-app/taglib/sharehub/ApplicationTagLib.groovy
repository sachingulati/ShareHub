package sharehub

class ApplicationTagLib {
    static namespace = "sh"
    static defaultEncodeAs = [taglib: 'raw']
    //static encodeAsForTags = [tagName: [taglib:'html'], otherTagName: [taglib:'none']]
    def userService
    def topicService
    def username = {
        out << userService.getName(session["username"])
    }
    def topicSelector = {
        def topics = topicService.getTopics(session["username"])
        out << g.select([name: "topic", from: topics, optionKey: "id", optionValue: "name", value: "id"])

    }
    def image={attr->
        File image = new File(attr.src);
        if(!image) return
        out << "<img src='data:img/png;base64,"
        out <<(image.getBytes().encodeBase64())
        out << "' width='80' height='80' />"
    }
}