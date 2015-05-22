package sharehub

import com.sharehub.enums.Roles
import com.sharehub.enums.Visibility


class TopicController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]
    def topicService
    def utilService
    def springSecurityService
    // update required
    def getRecentSubscribedTopics() {
        def topicList = Topic.subscribedTopics(springSecurityService.currentUser).list(offset: params.offset, max: params.max)
        render(template: "/topic/topicList",
                model: [topics: topicList, header: "Subscriptions", search: true, searchSubscription: true, hr: true,
                        viewAll: true, doPaginate: true, ajaxController: "topic", ajaxAction: "getRecentSubscribedTopics",
                        totalCount: topicList.totalCount])
    }

    def getTrendingTopics() {
        List<Topic> topicList = topicService.getTrendingTopics(params.offset, params.max)
        render(template: "/topic/topicList", model: [header: "Trending Topics", hr: true], bean: topicList, var: "topics")
    }

    def createTopic() {
        Topic topic = new Topic(name: params.name, createdBy: springSecurityService.currentUser,
                visibility: Visibility.valueOf(params.visibility))
        topic.validate()
        if (topic.validate()) {
            topic.save(failOnError: true, flush: true)
            flash.success = "Topic created successfully."
            redirect(action: "showTopic", params: [id: topic.id])
        } else {
            flash.error = "Duplicate topic name!"
            redirect(url: "/")
        }
    }

    def editTopic() {
        Topic topic = Topic.findById(params.id)
        if (!topic || !(topic.createdBy == springSecurityService.currentUser || utilService.isUser(Roles.ADMIN))) {
            flash.error = "Invalid request!"
            redirect(url: "/")
            return false
        }
        topic.name = params.name
        topic.visibility = Visibility.valueOf(params.visibility)
        if (topic.validate()) {
            topic.save(failOnError: true, flush: true)
            flash.success = "Topic: " + topic.name + " successfully updated."
            redirect(action: "showTopic", params: [id: topic.id])
        }
        else {
            flash.error = "Duplicate name!"
            redirect(url: "/")
        }
    }

    def delTopic() {
        User user = springSecurityService.currentUser
        Topic topic = Topic.findById(params.id)
        if (topic && user && (topic.createdBy == user || utilService.isUser(Roles.ADMIN))) {
            topic.delete(flush: true)
            flash.success = topic.name + " is successfully deleted."
        }else {
            flash.error = "Error in deleting " + topic.name
        }
        redirect(url: "/")
    }

    def viewAllSubscribedTopics() {
        render(view: "/topic/topicList",
                model: [ajaxUrl: createLink(controller: "topic", action: "getRecentSubscribedTopics"), ajaxParams:
                        [offset: 0, max: 10] as grails.converters.JSON, header: "Subscribed Topics", search: true, doPaginate: true])
    }

    def showTopic() {
        Topic topic = topicService.showTopic(params.id)
        render(view: "/topic/showTopic", model: [topic: topic])
    }

    def getTopicsCreated() {
        def topicList = Topic.byCreatedBy(params.username)
        if (!utilService.isUser(Roles.ADMIN)) {
            topicList = topicList.publicOrSubscribed(springSecurityService.currentUser)
        }
        topicList = topicList.listDistinct()
        render(template: "/topic/topicList",
                model: [ajaxController: "topic", ajaxAction: "getTopicsCreated", ajaxUrl: createLink(controller: "topic",
                        action: "getRecentSubscribedTopics"), ajaxParams: [offset: 0, max: 5] as grails.converters.JSON,
                        topics: topicList, header: 'Topics Created', hr: true])
    }

    def getSubscribedTopics() {
        def topicList = springSecurityService.currentUser?.subscriptions.topic
        topicList = topicList.sort({topic->
            topic.resources.max({resource-> resource.dateCreated})
        })
        render g.select(name: "topic", from: topicList, optionKey: "id", optionValue: "name", value: "id", noSelection: ['': 'Select Topic'], class: "form-control")
    }
}
