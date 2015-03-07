package sharehub

import com.sharehub.enums.ResourceType
import com.sharehub.enums.Visibility
import grails.transaction.Transactional

@Transactional
class ResourceService {

    def createDefaultResources() {
        String desc = "hi this is description of Resources. This is temporary description and will be replaced by actual description later on. so for the time being please co-operate :)"
        Topic.list().each { topic ->
            10.times {
                Resource r = new Resource(title: "Resource$it", description: "$desc", type: (it < 5 ? ResourceType.DOCUMENT : ResourceType.LINK), url: "google.com", createdBy: topic.createdBy, topic: topic)
                topic.addToResources(r)
                topic.createdBy.addToResources(r)
                topic.save(flush: true)
            }
        }
    }

    def createDefaultReadingItems() {
        int count = 0;
        ResourceStatus.findByIsRead(false).each { rs ->
            if (count++ < 3) {
                rs.isRead = true;
                rs.save()
            }
        }
    }

    def createDefaultRatings() {
        ResourceStatus.list().eachWithIndex { rs, index ->
            rs.score = index % 5 + 1;
            rs.save()
        }
    }

    def shareLink(def params, User user) {
        Resource resource = new Resource()
        resource.properties = params
        resource.type = ResourceType.LINK
        resource.createdBy = user
        resource.validate()
        if (resource.hasErrors()) {
            println(resource.errors.allErrors)
            return false
        } else {
            resource.save(failOnError: true)
            return true
        }
    }

    def shareDocument(def params, User user) {
        Resource resource = new Resource()
        resource.properties = params
        resource.type = ResourceType.DOCUMENT
        resource.createdBy = user
        resource.validate()
        if (resource.hasErrors()) {
            println(resource.errors.allErrors)
            return false
        } else {
            resource.save(failOnError: true)
            return true
        }
    }

    def unreadResourceList(String username) {
        def resources = Resource.createCriteria().list() {
            order("dateCreated", "desc")
            resourceStatus {
                eq("isRead", false)
                user{
                    eq("username",username)
                }
            }

        }
    }

    def recentPublicResourceList() {
        def resources = Resource.createCriteria().list(max: 5, offset: 0) {
            order("dateCreated", "desc")
            topic {
                eq("visibility", Visibility.PUBLIC)
            }
        }
    }

    def switchReadStatus(Long resourceId, String username) {
        ResourceStatus rs = ResourceStatus.createCriteria().get {
            resource{
                eq("id",resourceId)
            }
            user {
                eq("username", username)
            }
        }
        rs.isRead = !(rs.isRead)
        rs.save(failOnError: true)
    }
}
