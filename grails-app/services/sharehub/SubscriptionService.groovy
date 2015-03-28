package sharehub

import com.sharehub.enums.Seriousness
import com.sharehub.enums.Visibility
import grails.transaction.Transactional


@Transactional
class SubscriptionService {

    def subscribe(username, topicId, seriousness = Seriousness.VERY_SERIOUS) {
        Topic topic = Topic.findById(topicId)
        User user = User.findByUsername(username)
        if (!topic || !user || !seriousness) {
            log.info("Invalid access request in Controller: subscription, action: subscribe, reason: " + (!user ? "No user found! " : "") + (!topic ? "No Topic found! " : ""))
            return null
        }
        List invite = Invite.findAllByTopicAndInviteToEmail(topic, user.email)

        if (topic.visibility == Visibility.PRIVATE && !user.isAdmin()) {
            if (!invite || invite.isEmpty()) {
                return null
            }
        }
        Subscription subscription = Subscription.findOrCreateByTopicAndUser(topic, user)
        subscription.seriousness = seriousness
        if (subscription.validate()) {
            subscription.save()
            invite.each { it.delete() }
            user.addToSubscriptions(subscription)
            topic.addToSubscriptions(subscription)
            topic.save(flush: true)
        } else {
            //println(subscription.errors.allErrors)
            return null
        }
        return subscription
    }

    def unsubscribe(username, topicId) {
        Topic topic = Topic.findById(topicId)
        User user = User.findByUsername(username)
        if (!topic || !user) {
            log.error("Error in Controller: subscription, action: unsubscribe, reason: " + (!user ? "No user found! " : "") + (!topic ? "No Topic found! " : ""))
            return null
        }
        if (topic.createdBy == user) {
            log.error("User cannot unsubscribe its own created topic! TopicId: ${topic.id}, UserId: ${user.id}")
            return null
        }
        Subscription subscription = Subscription.findByUserAndTopic(user, topic)
        if (!subscription) {
            log.error("Error in Controller: subscription, action: unsubscribe, reason: no subscription found! ")
            return null
        }
        user.removeFromSubscriptions(subscription)
        topic.removeFromSubscriptions(subscription)
        subscription.delete(flush: true)
        return subscription
    }
}
