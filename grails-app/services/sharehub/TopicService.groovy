package sharehub

import com.sharehub.enums.Seriousness
import com.sharehub.enums.Visibility
import grails.transaction.Transactional

@Transactional
class TopicService {

    def userService
    def utilService

    def createDefaultTopics() {
        User.list().each { user ->
            5.times {
                Topic topic = new Topic(name: "Topic${it + 5 * (user.id - 1)}", createdBy: user, visibility: (it > 2 ? Visibility.PRIVATE : Visibility.PUBLIC))
                user.addToTopics(topic)
            }
            user.save(flush: true)
        }
    }

    def show(User user, Topic topic) {
        if (!topic) {
            return false
        }
        if (topic.visibility == Visibility.PRIVATE && !(user.admin)) {
            if (!Subscription.findByTopicAndUser(topic, user)) {
                return false
            }
        }
        return true
    }

    def createDefaultSubscription() {
        User user = User.get(1)
        3.times {
            Topic topic = Topic.load(7 + it)
            Subscription subscription = new Subscription(seriousness: Seriousness.SERIOUS)
            log.info("Adding subscriptions to user")
            user.addToSubscriptions(subscription)
            log.info("Adding subscriptions to topic")
            topic.addToSubscriptions(subscription)
            topic.save(flush: true, failOnError: true)
        }
        user = User.get(2)
        3.times {
            Topic topic = Topic.load(2 + it)
            Subscription subscription = new Subscription(seriousness: Seriousness.SERIOUS)
            log.info("Adding subscriptions to user")
            user.addToSubscriptions(subscription)
            log.info("Adding subscriptions to topic")
            topic.addToSubscriptions(subscription)
            topic.save(flush: true, failOnError: true)
        }
    }

    def getTopics(String username) {
        def topicsList = Subscription.executeQuery("select topic.id, topic.name from Subscription where user.username = ?", [username]) as List
        def topicsMap = topicsList.collect { [id: it[0], name: it[1]] }
        //def topics = User.findByUsername(username).subscriptions*.topic
        return topicsMap
    }

    def getTopicList(attr) {
/*
        * byCreatorUsername: topics created by a user
        * bySubscriberUsername: topics subscribed by a user
        * visibility: visibility of topic: private or public, instance of Visibility enum
*/
        List<Topic> topicList = Topic.createCriteria().listDistinct() {
            if (attr.max){
                maxResults attr.max
            }
            if (attr.offset){
                firstResult attr.max
            }
            if (attr.byCreatorUsername) {
                createdBy {
                    eq("username", attr.byCreatorUsername)
                }
            }
            subscriptions {
                user {
                    if (attr.bySubscriberUsername) {
                        eq("username", attr.bySubscriberUsername)
                    }
                }
            }
            if (attr.visibility) {
                eq("visibility", attr.visibility)
            }
            resources {
                groupProperty("topic")
                order("dateCreated", "desc")
            }
        }
        return topicList
    }

    def getTrendingTopics(Boolean isPrivate = false) {
        def trendingTopicList = Resource.createCriteria().list {

            projections {
                groupProperty("topic")
            }
            topic {
                if (!isPrivate) {
                    eq("visibility", Visibility.PUBLIC)
                }
            }
            rowCount("rows")
            order("rows", "desc")
        }.collect { it[0] }
        return trendingTopicList
    }

}
