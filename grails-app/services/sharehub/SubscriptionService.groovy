package sharehub

import com.sharehub.enums.Seriousness
import com.sharehub.enums.Visibility
import grails.transaction.Transactional


@Transactional
class SubscriptionService {

    def subscribe(username, topicId, seriousness = Seriousness.VERY_SERIOUS) {
        Topic topic = Topic.findById(topicId)
        User user = User.findByUsername(username)
        List invite = Invite.findAllByTopicAndInviteToEmail(topic, user.email)
        if (!topic || !user || !seriousness || (topic.visibility == Visibility.PRIVATE && !user.isAdmin() && (!invite || invite.isEmpty()))) {
            return null
        }
        Subscription subscription = Subscription.findOrCreateByTopicAndUser(topic, user)
        subscription.seriousness = seriousness
        if (subscription.validate()) {
            subscription.save()
            invite?.each { it.delete() }
            user.addToSubscriptions(subscription)
            topic.addToSubscriptions(subscription)
            topic.save(flush: true)
            return subscription
        }
        return null
    }

    def unsubscribe(username, topicId) {
        Topic topic = Topic.findById(topicId)
        User user = User.findByUsername(username)
        Subscription subscription = Subscription.findByUserAndTopic(user, topic)
        if (!topic || !user || !subscription || topic.createdBy == user) {
            return false
        }
        user.removeFromSubscriptions(subscription)
        topic.removeFromSubscriptions(subscription)
        subscription.delete(flush: true)
        return true
    }
}
