package sharehub

import com.sharehub.enums.ResourceType
import grails.transaction.Transactional

@Transactional
class ResourceService {

    def serviceMethod() {

    }

    def createDefaultResources(){
        String desc = "hi this is description of Resources. This is temporary description and will be replaced by actual description later on. so for the time being please co-operate :)"
        Topic.list().each { topic ->
            10.times {
                Resource r = new Resource(title: "Resource$it", description: "$desc", type: (it < 5 ? ResourceType.DOCUMENT : ResourceType.LINK), url: "someUrl.com", createdBy: topic.createdBy, topic: topic)
                topic.addToResources(r)
                topic.createdBy.addToResources(r)
                topic.save(flush: true)
            }
        }
    }

    def createDefaultReadingItems(){
        int count = 0;
        ResourceStatus.findByIsRead(false).each { rs ->
            if (count++ < 3) {
                rs.isRead = true;
                rs.save()
            }
        }
    }

    def shareLink(def params,User user) {
        Resource resource = new Resource()
        resource.properties = params
        resource.type = ResourceType.LINK
        resource.createdBy = user
        resource.validate()
        if (resource.hasErrors()) {
            println( resource.errors.allErrors)
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
            println( resource.errors.allErrors)
            return false
        } else {
            resource.save(failOnError: true)
            return true
        }
    }
    def createDefaultRatings(){
        ResourceStatus.list().eachWithIndex { rs, index ->
            rs.score = index % 5 + 1;
            rs.save()
        }
    }
}
