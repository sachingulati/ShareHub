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
//        out << g.select([name: "topic", from: topicService.getTopics(session["username"]), optionKey: 0, optionValue: 1, value: 0])
        def topics = topicService.getTopics(session["username"])
        out << g.select([name: "topic", from: topics, optionKey: "id", optionValue: "name", value: "id"])

    }
}