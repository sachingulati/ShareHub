package sharehub

import com.sharehub.enums.Roles
import com.sharehub.enums.Seriousness
import com.sharehub.enums.Visibility
import grails.plugin.springsecurity.annotation.Secured
import grails.transaction.Transactional


//@Transactional
@Secured(['ROLE_USER'])
class SubscriptionService {

    def springSecurityService
    def utilService
    def subscribe(Long topicId, seriousness = Seriousness.VERY_SERIOUS) {
        Topic topic = Topic.findById(topicId)
        User user = springSecurityService.currentUser
        List invite = Invite.findAllByTopicAndInviteToEmail(topic, user.email)
        if (!topic || !user || !seriousness || (topic.visibility == Visibility.PRIVATE && !utilService.isUser(Roles.ADMIN) && invite?.isEmpty())) {
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

    def unsubscribe(topicId) {
        Topic topic = Topic.findById(topicId)
        User user = springSecurityService.currentUser
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
