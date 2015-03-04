package sharehub

class HomeController {

    def topicService
    def index() {
            forward(action: "dashboard")
    }
    def dashboard(){
        User user = User.findByUsername(session["username"]);
        List<Topic> topics = user.subscriptions*.topic
        int subCount = user.subscriptions.size()
        int topicCount = user.topics.size()
        List<Subscription> subscribedTopics = user.subscriptions.topic
        List<Resource> unreadResources = ResourceStatus.findAll{user== user && isRead== false}*.resource
        render(view: "/dashboard",model: [username: user.username, name: user.name, topics: topics, subCount:
                subCount, topicCount: topicCount, subscribedTopics: subscribedTopics, unreadResources: unreadResources])
    }
}
