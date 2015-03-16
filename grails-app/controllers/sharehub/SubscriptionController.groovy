package sharehub

import com.sharehub.enums.Seriousness

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

class SubscriptionController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

//    @Transactional
    def subscribe(){
        println ("in subscribe")
        Topic topic = Topic.findById(params.topicId)
        User user = User.findByUsername(session["username"])
        if (!topic || !user){
            println ("Error in Controller: subscription, action: subscribe, reason: " + (!user?"No user found! ":"")+(!topic?"No Topic found! ":""))
//            log.error("Error in Controller: subscription, action: subscribe, reason: " + (!user?"No user found! ":"")+(!topic?"No Topic found! ":""))
            render(sh.subscribe(topic: topic))
            return false
        }
        Subscription subscription = Subscription.findOrCreateByTopicAndUser(topic,user)
        subscription.validate()
        subscription.save(flush: true)
        user.addToSubscriptions(subscription)
        topic.addToSubscriptions(subscription)
        render(sh.subscribe(topic: topic))
    }
//    @Transactional
    def unsubscribe(){
        Topic topic = Topic.findById(params.topicId)
        User user = User.findByUsername(session["username"])
        if (!topic || !user){
            log.error("Error in Controller: subscription, action: unsubscribe, reason: " + (!user?"No user found! ":"")+(!topic?"No Topic found! ":""))
            render(sh.subscribe(topic: topic))
            return false
        }
        if (topic.createdBy==user){
            log.error("User cannot unsubscribe its own created topic! TopicId: ${topic.id}, UserId: ${user.id}")
            render(sh.subscribe(topic: topic))
            return false
        }
        Subscription subscription = Subscription.findByUserAndTopic(user, topic)
        if(!subscription) {
            log.error("Error in Controller: subscription, action: unsubscribe, reason: no subscription found! ")
            render (sh.subscribe(topic: topic))
            return false
        }
        user.removeFromSubscriptions(subscription)
        topic.removeFromSubscriptions(subscription)
        subscription.delete(flush: true)
        render(sh.subscribe(topic: topic))
    }
    @Transactional
    def modifySubscriptionSeriousness(){
        Topic topic = Topic.findById(params.topicId)
        User user = User.findByUsername(session["username"])
        Seriousness seriousness = Seriousness.valueOf(params.seriousness)
        if (!user || !topic || !seriousness){
            log.error("Error in Controller: subscription, action: subscribe, reason: " + (!user?"No user found! ":"")+(!topic?"No Topic found! ":"")+(!seriousness?"Invalid Seriousness ":""))
            render (sh.subscribe(topic: topic))
            return false
        }
        Subscription subscription = Subscription.findByTopicAndUser(topic,user)
        if (subscription){
            subscription.seriousness = seriousness
            subscription.save()
            render(sh.subscribe(topic: topic))
        }
        else {
            Subscription newSubscription = new Subscription(topic: topic,user: user,seriousness: seriousness)
            topic.addToSubscriptions(newSubscription)
            user.addToSubscriptions(newSubscription)
            subscription.save()
            render(sh.subscribe(topic: topic))
        }
    }

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Subscription.list(params), model: [subscriptionInstanceCount: Subscription.count()]
    }

    def show(Subscription subscriptionInstance) {
        respond subscriptionInstance
    }

    def create() {
        respond new Subscription(params)
    }

    @Transactional
    def save(Subscription subscriptionInstance) {
        if (subscriptionInstance == null) {
            notFound()
            return
        }

        if (subscriptionInstance.hasErrors()) {
            respond subscriptionInstance.errors, view: 'create'
            return
        }

        subscriptionInstance.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'subscription.label', default: 'Subscription'), subscriptionInstance.id])
                redirect subscriptionInstance
            }
            '*' { respond subscriptionInstance, [status: CREATED] }
        }
    }

    def edit(Subscription subscriptionInstance) {
        respond subscriptionInstance
    }

    @Transactional
    def update(Subscription subscriptionInstance) {
        if (subscriptionInstance == null) {
            notFound()
            return
        }

        if (subscriptionInstance.hasErrors()) {
            respond subscriptionInstance.errors, view: 'edit'
            return
        }

        subscriptionInstance.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'Subscription.label', default: 'Subscription'), subscriptionInstance.id])
                redirect subscriptionInstance
            }
            '*' { respond subscriptionInstance, [status: OK] }
        }
    }

    @Transactional
    def delete(Subscription subscriptionInstance) {

        if (subscriptionInstance == null) {
            notFound()
            return
        }

        subscriptionInstance.delete flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'Subscription.label', default: 'Subscription'), subscriptionInstance.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'subscription.label', default: 'Subscription'), params.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NOT_FOUND }
        }
    }
}
