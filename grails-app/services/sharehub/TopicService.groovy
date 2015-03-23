package sharehub

import com.sharehub.enums.Seriousness
import com.sharehub.enums.Visibility
import grails.transaction.Transactional
import java.security.SecureRandom;

@Transactional
class TopicService {

    def mailService
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
        if (topic.visibility == Visibility.PRIVATE && !(user?.admin)) {
            if (!Subscription.findByTopicAndUser(topic, user)) {
                return false
            }
        }
        return true
    }

    def showTopic(username, topicId){
        Topic topic = Topic.findById(topicId)
        User user = User.findByUsername(username)
        if (!topic){
            return null
        }
        if (topic.visibility == Visibility.PRIVATE && !(user?.admin)){
            if (!Subscription.findByTopicAndUser(topic,user)){
                if (!Invite.findByTopicAndInviteToEmail(topic,user.email)){
                    return null
                }
            }
        }
        return topic
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

    public String getToken() {
        SecureRandom random = new SecureRandom();
        return new BigInteger(130, random).toString(32);
    }

    def invite(username, topicId, email, inviteTo, g){
        User user = User.findByUsername(username);
        Topic topic = Topic.get(topicId)
        if (!user || !topic){
            return false
        }
        if (!user.admin){
            if (!Subscription.findByUserAndTopic(user,topic)){
                return false
            }
        }
        Invite invite = new Invite(inviteToEmail: email,invitedBy: user, topic: topic, token: "token")
        if (invite.validate()){
            invite.token = getToken()
            mailService.sendMail {
                async true
                to email
                subject "Share Hub invitation from ${user.name}"
                html "${g.render(template: "emailInvite", model: [user: user, topicId: topic.id, inviteTo: inviteTo, token: invite.token])}"
            }
            invite.save()
            return true
        }
        return false
    }

    def getTopicList(attr) {
/*
        * byCreatorUsername: topics created by a user
        * bySubscriberUsername: topics subscribed by a user
        * visibility: visibility of topic: private or public, instance of Visibility enum
*/
        List<Topic> topicList = Topic.createCriteria().listDistinct() {
            /*if (attr.max){
                maxResults (Integer.parseInt(attr.max))
            }
            if (attr.offset){
                firstResult (Integer.parseInt(attr.offset))
            }*/
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
        if (attr.max){
            Integer offset = Integer.parseInt(attr.offset)
            offset = offset>topicList.size()?0:offset
            Integer max = (Integer.parseInt(attr.max)+offset) % topicList.size()
            topicList = topicList.subList(offset,max)
        }
        return topicList
    }

    def getTrendingTopics(Boolean isPrivate = false, offset, Max) {
        def trendingTopicList = Resource.createCriteria().list(offset: offset, max: Max) {
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
