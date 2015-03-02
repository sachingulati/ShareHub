package sharehub

import com.sharehub.enums.Seriousness
import com.sharehub.enums.Visibility
import grails.transaction.Transactional

@Transactional
class TopicService {

    def serviceMethod() {

    }
    def createDefaultTopics(){
        User.list().each { user ->
            5.times {
                Topic topic = new Topic(name: "Topic${it+5*(user.id-1)}", createdBy: user, visibility: Visibility.PUBLIC)
                user.addToTopics(topic)
            }
            user.save(flush: true)
        }
    }
    def createDefaultSubscription(){
        User user = User.get(1)
        3.times {
            Topic topic =  Topic.load(7 + it)
            Subscription subscription = new Subscription(seriousness: Seriousness.SERIOUS)
            user.addToSubscriptions(subscription)
            topic.addToSubscriptions(subscription)
            topic.save(flush: true, failOnError: true)
        }
        user = User.get(2)
        3.times {
            Topic topic =  Topic.load(2 + it)
            Subscription subscription = new Subscription(seriousness: Seriousness.SERIOUS)
            user.addToSubscriptions(subscription)
            topic.addToSubscriptions(subscription)
            topic.save(flush: true, failOnError: true)
        }
    }
    def getTopics(String username){
        User.findByUsername(username).subscriptions*.topic;
    }
}
