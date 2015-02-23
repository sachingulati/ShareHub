import com.sharehub.enums.ResourceType
import com.sharehub.enums.Seriousness
import com.sharehub.enums.Visibility
import sharehub.Resource
import sharehub.ResourceStatus
import sharehub.Subscription
import sharehub.Topic
import sharehub.User

class BootStrap {

    def init = { servletContext ->
        createUsers()
        createTopics()
        subscribeTopic()
        Subscription.list().each {
            println ">>>>>>>>>>>>>${it.topicId} || ${it.userId}"
        }
        println "==============================="
        Topic.list().each {
            println it.id
            println it.subscriptions
        }
        println "==============================="
        createResources()
        createRating()
    }

    def createUsers() {
        2.times {
            User u = new User(firstName: "Name$it", lastName: "Last$it", email: "Name$it@email.com", username: "name$it", password: "12345678", admin: false, active: true)
            u.validate() && u.save(flush: true, failOnError: true)
        }
    }

    def createTopics() {
        User.list().each { user ->
            5.times {
                Topic topic = new Topic(name: "Topic$it", createdBy: user, visibility: Visibility.PUBLIC)
                user.addToTopics(topic)
            }
            user.save(flush: true)
        }
    }

    def subscribeTopic() {
        User user = User.get(1)
        3.times {
            println it
            Topic topic =  Topic.load(7 + it)
            Subscription subscription = new Subscription(seriousness: Seriousness.SERIOUS)
            user.addToSubscriptions(subscription)
            topic.addToSubscriptions(subscription)
            topic.save(flush: true, failOnError: true)
        }
    }

    def createResources() {
        Topic.findAllByIdInList([7, 8, 9]).each { topic ->
            10.times {
                Resource r = new Resource(title: "Resource$it", description: "description$it", type: (it < 5 ? ResourceType.DOCUMENT : ResourceType.LINK), url: "someUrl.com", createdBy: topic.createdBy, topic: topic)
//                r.save()
                topic.addToResources(r)
                topic.save(flush: true)
            }
        }
    }

    def createReadingItems() {
        int count = 0;
        ResourceStatus.findByIsRead(false).each { rs ->
            if (count++ < 3) {
                rs.isRead = true;
                rs.save()
            }

        }
    }

    def createRating() {
        ResourceStatus.list().eachWithIndex { rs, index ->
            rs.score = index % 5 + 1;
            rs.save()
        }
    }
    def destroy = {
    }
}
