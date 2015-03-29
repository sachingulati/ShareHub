package sharehub

import com.sharehub.CO.MailCO
import com.sharehub.enums.Seriousness
import com.sharehub.enums.Visibility
import grails.transaction.Transactional

@Transactional
class TopicService {

    def mailService
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
        if (topic.visibility == Visibility.PRIVATE && !(user?.admin)) {
            if (!Subscription.findByTopicAndUser(topic, user)) {
                return false
            }
        }
        return true
    }

    def showTopic(username, topicId) {
        Topic topic = Topic.findById(topicId)
        User user = User.findByUsername(username)
        if (!topic) {
            return null
        }
        if (topic.visibility == Visibility.PRIVATE && !(user?.admin)) {
            if (!Subscription.findByTopicAndUser(topic, user)) {
                if (!Invite.findByTopicAndInviteToEmail(topic, user.email)) {
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

    def invite(username, topicId, email, inviteTo,g) {
        User user = User.findByUsername(username);
        Topic topic = Topic.get(topicId)
        if (!user || !topic || !Subscription.findByUserAndTopic(user, topic)) {
            return false
        }
        Invite invite = new Invite(inviteToEmail: email, invitedBy: user, topic: topic, token: "token")
        if (invite.validate()) {
            invite.token = utilService.randomString
            String body = g.render(template: "/invites/emailInvite",model: [user: user, topicId: topic.id, inviteTo: inviteTo, token: invite.token])
            if (utilService.sendMail(new MailCO(to:email,body:body, subject: "Share Hub invitation from ${user.name}"))){
                invite.save()
                return true
            }
        }
        return false
    }

    def subList(def topicList, def paramsMax, def paramsOffset) {
        int size = topicList.size()
        Integer offset = 0, max = 5
        try {
            offset = Integer.parseInt(paramsOffset ?: 0)
            max = Integer.parseInt(paramsMax ?: 5)
        }
        catch (Exception e) {
        }
        if (size < offset) {
            return null
        }
        if (size < max + offset) {
            max = size - offset
        }
        topicList[offset..<max]
    }

    def getTrendingTopics(offset, max) {
        def trendingTopicList = Resource.createCriteria().list(offset: offset, max: max) {
            projections {
                groupProperty("topic")
            }
            topic {
                eq("visibility", Visibility.PUBLIC)
            }
            rowCount("rows")
            order("rows", "desc")
        }.collect { it[0] }
        return trendingTopicList
    }

    def getSubscribedTopics(offset, max, username) {
/*
        Topic.createCriteria().list(offset: offset, max: max){
            order
        }*/
        Resource.createCriteria().list(offset: offset, max: max) {
            projections {
                groupProperty("topic")
            }
            order("dateCreated", "desc")
            topic {
                subscriptions {
                    user {
                        eq("username", username)
                    }
                }
            }
        }
    }


}
