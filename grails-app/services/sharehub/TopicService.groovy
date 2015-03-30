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

    Boolean show(User user, Topic topic) {
        return !(!topic || (topic.visibility == Visibility.PRIVATE && !(user?.admin) &&
                !Subscription.countByTopicAndUser(topic, user) && !Invite.findByTopicAndInviteToEmail(topic, user.email)))
    }

    def showTopic(username, topicId) {
        Topic topic = Topic.findById(topicId)
        User user = User.findByUsername(username)
        if (!topic || !show(user, topic)) {
            return null
        }
        return topic
    }

    def createDefaultSubscription() {
        User.list().each { User user ->
            3.times {
                int id = 7 - 5 * (user.id - 1) + it
                Topic topic = Topic.load(id)
                Subscription subscription = new Subscription(seriousness: Seriousness.SERIOUS)
                user.addToSubscriptions(subscription)
                topic.addToSubscriptions(subscription)
                topic.save(flush: true)
            }
        }
    }

    def invite(username, topicId, email, inviteTo, g) {
        User user = User.findByUsername(username)
        Topic topic = Topic.get(topicId)
        if (!user || !topic || !Subscription.findByUserAndTopic(user, topic)) {
            return false
        }
        Invite invite = new Invite(inviteToEmail: email, invitedBy: user, topic: topic, token: "token")
        if (invite.validate()) {
            invite.token = utilService.randomString
            String body = g.render(template: "/invites/emailInvite",
                    model: [user: user, topicId: topic.id, inviteTo: inviteTo, token: invite.token])
            if (utilService.sendMail(new MailCO(to:email, body:body, subject: "Share Hub invitation from ${user.name}"))) {
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
            offset = 0
            max = 5
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
}
