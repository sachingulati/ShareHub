package sharehub

import com.sharehub.enums.ResourceType
import com.sharehub.enums.Visibility
import grails.transaction.Transactional

@Transactional
class ResourceService {

    def topicService
    def grailsApplication

    def createDefaultResources() {
        String desc = "hi this is description of Resources. This is temporary description and will be replaced by actual description later on. so for the time being please co-operate :)"
        Topic.list().each { topic ->
            10.times {
                Resource r = new Resource(title: "Resource$it", description: "$desc",
                        type: (it < 5 ? ResourceType.DOCUMENT : ResourceType.LINK), url: "http://google.com",
                        filePath: grailsApplication.config.uploadFiles + "defaultFile", createdBy: topic.createdBy, topic: topic)
                topic.addToResources(r).createdBy.addToResources(r).save()
            }
        }
    }

    def createDefaultReadingItems() {
        int count = 0
        ResourceStatus.findByIsRead(false).each { rs ->
            if (count++ < 3) {
                rs.isRead = true
                rs.save()
            }
        }
    }

    def createDefaultRatings() {
        ResourceStatus.list().eachWithIndex { rs, index ->
            rs.score = index % 5 + 1
            rs.save()
        }
    }

    def getRating(id) {
        def rating = ResourceStatus.createCriteria().get {
            projections {
                count("score", "number")
                avg("score", "rating")
            }
            resource {
                eq("id", id.toLong())
            }
            gt("score", 0)
        }
        return [totalCount: rating[0], avgRating: rating[1]]
    }

    def changeRating(resourceId, score, username) {
        User user = User.findByUsername(username)
        Resource resource = Resource.get(resourceId)
        Subscription subscription = Subscription.findByUserAndTopic(user, resource.topic)
        if (!resource || !user || (!subscription && resource.topic.visibility == Visibility.PRIVATE && !user.admin)) {
            return false
        }
        ResourceStatus resourceStatus = ResourceStatus.findOrCreateByUserAndResource(user, resource)
        resourceStatus.score = Integer.parseInt(score) % 6
        resourceStatus.save()
        return true
    }

    def showPost(Long id, String username) {
        Resource resource = Resource.get(id)
        User user = User.findByUsername(username)
        if (!resource || !topicService.show(user, resource.topic)) {
            return null
        }
        ResourceStatus resourceStatus = ResourceStatus.findByUserAndResource(user, resource)
        resourceStatus?.isRead = true
        resourceStatus?.save()
        return [resource: resource, rating: getRating(id), myRating: resourceStatus ? resourceStatus.score : 0]
    }

    def editResource(attr) {
        Resource resource = Resource.get(Long.parseLong(attr.resourceId))
        if (!resource) {
            return null
        }
        resource.title = attr.title
        resource.description = attr.description
        if (resource.validate()) {
            return resource.save()
        }
        return null
    }

    def deleteResource(id, username) {
        Resource resource = Resource.get(id)
        User user = User.findByUsername(username)
        if (!resource || !user || (!user.isAdmin() && resource.createdBy != user)) {
            return "Invalid request!"
        }
        String resourceTitle = resource.title
        resource.delete()
        return resourceTitle + " has been successfully deleted."
    }

    Resource shareResource(def params, User user, ResourceType resourceType) {
        if (!params || !Subscription.findByUserAndTopic(user, Topic.get(params.topic.toLong()))) {
            return null
        }
        Resource resource = new Resource()
        resource.properties = params
        resource.type = resourceType
        resource.createdBy = user
        if (resource.validate()) {
            return resource.save()
        }
        return null
    }
    Resource shareLink(def params, User user) {
        return shareResource(params, user, ResourceType.LINK)
    }

    Resource shareDocument(def params, User user) {
        Resource resource = shareResource(params, user, ResourceType.DOCUMENT)
        if (resource) {
            File dir = new File(grailsApplication.config.uploadFiles + resource.id)
            dir.mkdir()
            File doc = new File(dir, params.file.getOriginalFilename())
            params.file.transferTo(doc)
            resource.filePath = doc.absolutePath
            return resource.save()
        }
    }

    def changeOrSwitchReadStatus(Long resourceId, String username, status = null) {
        ResourceStatus rs = ResourceStatus.createCriteria().get {
            resource {
                eq("id", resourceId)
            }
            user {
                eq("username", username)
            }
        }
        if (!rs) {
            return null
        }
        rs.isRead = status ?: !(rs.isRead)
        rs.save()
        return rs.isRead
    }

    def download(resourceId, username) {
        Resource resource = Resource.findById(resourceId)
        if (!resource || (resource.topic.visibility == Visibility.PRIVATE && !Subscription.findByUserAndTopic(User.findByUsername(username), resource.topic))) {
            return null
        }
        File file
        try {
            file = new File(resource.filePath)
        }
        catch (Exception e){
            file = null
        }
        return file
    }
}

