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
                Resource r = new Resource(title: "Resource$it", description: "$desc", type: (it < 5 ? ResourceType.DOCUMENT : ResourceType.LINK), url: "http://google.com", filePath: grailsApplication.config.uploadFiles+"defaultFile", createdBy: topic.createdBy, topic: topic)
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

    def getRating(id){
        def rating = ResourceStatus.createCriteria().get(){
            projections{
                count("score","number")
                avg("score","rating")
            }
            resource{
                eq("id",id.toLong())
            }
            gt("score", 0)
        }
        return [totalCount: rating[0], avgRating: rating[1]]
    }
    def changeRating(resourceId, score, username){
        User user = User.findByUsername(username)
        Resource resource = Resource.get(resourceId)
        if (!resource || ! user){
            return false
        }
        Subscription subscription = Subscription.findByUserAndTopic(user,resource.topic)
        if (!subscription && resource.topic.visibility==Visibility.PRIVATE && !user.admin){
            return false
        }
        ResourceStatus resourceStatus = ResourceStatus.findOrCreateByUserAndResource(user,resource);
        resourceStatus.score = Integer.parseInt(score)%6
        resourceStatus.save()
        return true
    }

    def showPost(Long id,String username){
        Resource resource = Resource.get(id)
        if(!resource){
            return null
        }
        User user = User.findByUsername(username)
        if(!topicService.show(user,resource.topic)){
            return null
        }
        ResourceStatus resourceStatus = ResourceStatus.findByUserAndResource(user,resource)
        resourceStatus?.isRead = true
        resourceStatus?.save()
        return [resource: resource, rating: getRating(id), myRating: resourceStatus?resourceStatus.score:0]
    }
    def getResourceList(attr){
//        Attributes summary:
/*          isRead: for either read or unread resources
            username: for user specific search
            isSubscribed: for resources in subscribed topics
            --> searchByRating: search resources where rating is greater than equal to given rating (not implemented)
            resourceSearchString: for searching in resource title and description
            topicNameSearchString: for topic specific search
            createdByUsername: search by resourceCreator
            --> lastUpdated: search resources which are updated after a given date (not implemented)
            offset: for pagination
            max: for pagination
            topicId: search within given topic
 */
        if (!attr.max){
            attr.max = 10
        }
        if (!attr.offset){
            attr.offset = 0
        }
        Boolean isAdmin = false
        if (attr.username){
            User user = User.findByUsername(attr.username)
            if (user){
                isAdmin = user.isAdmin()
            }
        }
        def temp
        List resources = Resource.createCriteria().list(offset:attr.offset, max: attr.max) {
            if (attr.isRead!=null || attr.searchByRating) {
                resourceStatus {
                    if (attr.isRead != null && attr.username && attr.isSubscribed) {
                        eq("isRead", attr.isRead)
                        user {
                            eq("username", attr.username)
                        }
                    }
                    if (attr.searchByRating != null) {
                        gte("score", attr.searchByRating)
                    }
                }
            }
            topic {
                if(attr.topicId){
                    eq("id",Long.parseLong(attr.topicId))
                }
                if(attr.isSubscribed && attr.username) {
                    subscriptions {
                        user {
                            eq("username", attr.username)
                        }
                    }
                }
                else if (!attr.username || (!attr.isSubscribed && !isAdmin && attr.createdByUsername!=attr.username)){
                    eq("visibility",Visibility.PUBLIC)
                }
                /*
                else if (!isAdmin){
                    subscriptions{
                        user{
                            eq("username", attr.username)
                        }
                    }
                }*/
                /*
                * not logged in: attr.username = null
                * or
                * not admin : isAdmin = false
                * and
                * not subscribed to topic
                * */
            }
            if (attr.lastUpdated){
                gte("lastUpdated",attr.lastUpdated)
            }
            if (attr.createdByUsername){
                or {
                    createdBy {
                        eq("username", attr.createdByUsername)
                    }
                    topic {
                        createdBy {
                            eq("username", attr.createdByUsername)
                        }
                    }
                }
            }
            or {
                if (attr.resourceSearchString){
                    ilike("description","%${attr.resourceSearchString}%")
                    ilike("title","%${attr.resourceSearchString}%")
                }
                if (attr.topicNameSearchString){
                    topic{
                        ilike("name","%${attr.topicNameSearchString}%")
                    }
                }
            }
            order("lastUpdated","desc")
        }
        return resources
        // || resource.title.contains(searchString) || resource.topic.name.contains(searchString)
    }

    def editResource(attr){
        Resource resource = Resource.get(Long.parseLong(attr.resourceId))
        if (!resource){
            return null
        }
        resource.title = attr.title
        resource.description = attr.description
        if (resource.validate()){
            resource.save()
            return resource
        }
        return null
    }

    def deleteResource(id, username){
        Resource resource = Resource.get(id)
        User user = User.findByUsername(username)
        if (!resource || !user || (!user.isAdmin() && resource.createdBy != user)){
            return "Invalid request!"
        }
        String resourceTitle = resource.title
        resource.delete()
        return resourceTitle + " has been successfully deleted."
    }

    def shareLink(def params, User user) {
        if (!params){
            return 0
        }
        if (!Subscription.findByUserAndTopic(user, Topic.get(params.topic.toLong()))){
            return 0
        }
        Resource resource = new Resource()
        resource.properties = params
        resource.type = ResourceType.LINK
        resource.createdBy = user
        resource.validate()
        if (resource.hasErrors()) {
            //println(resource.errors.allErrors)
            return 0
        } else {
            resource.save(failOnError: true)
            return resource.id
        }
    }

    def shareDocument(def params, User user) {
        if (!params){
            return 0
        }
        if (!Subscription.findByUserAndTopic(user, Topic.get(params.topic.toLong()))){
            return 0
        }
        Resource resource = new Resource()
        resource.properties = params
        resource.type = ResourceType.DOCUMENT
        resource.createdBy = user
        if (!resource.validate()) {
            //println(resource.errors.allErrors)
            return 0
        } else {
            resource.save()
            File dir = new File(grailsApplication.config.uploadFiles + resource.id)
            dir.mkdir()
            File doc = new File(dir,params.file.getOriginalFilename())
            params.file.transferTo(doc)
            resource.filePath = doc.absolutePath
            resource.save()
            return resource.id
        }
    }
    def switchReadStatus(Long resourceId, String username, status = null) {
        ResourceStatus rs = ResourceStatus.createCriteria().get {
            resource{
                eq("id",resourceId)
            }
            user {
                eq("username", username)
            }
        }
        if (!rs){
            return null
        }
        rs.isRead = status?:!(rs.isRead)
        rs.save(failOnError: true)
        return rs.isRead
    }

    def download(resourceId, username) {
        Resource resource = Resource.findById(resourceId)
        if (!resource){
            return null
        }
        if (resource.topic.visibility==Visibility.PRIVATE && !Subscription.findByUserAndTopic(User.findByUsername(username), resource.topic)){
            return null
        }
        return new File(resource.filePath)
    }
}

