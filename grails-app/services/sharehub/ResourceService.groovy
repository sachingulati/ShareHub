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
        List resources = Resource.createCriteria().list(offset:attr.offset, max: attr.max) {
            resourceStatus{
                if(attr.isRead!=null && attr.username && attr.isSubscribed) {
                    eq("isRead", attr.isRead)
                    user{
                        eq("username", attr.username)
                    }
                }
                if (attr.searchByRating!=null){
                    gte("score",attr.searchByRating)
                }
            }
            topic {
                if(attr.topicId){
                    eq("id",attr.topicId)
                }
                if(attr.isSubscribed && attr.username) {
                    subscriptions {
                        user {
                            eq("username", attr.username)
                        }
                    }
                }
                else if (!attr.isSubscribed && !isAdmin){
                    eq("visibility",Visibility.PUBLIC)
                }
            }
            if (attr.lastUpdated){
                gte("lastUpdated",attr.lastUpdated)
            }
            if (attr.createdByUsername){
                or {
                    createdBy {
                        eq("username", attr.creatorUsername)
                    }
                    topic {
                        createdBy {
                            eq("username", attr.creatorUsername)
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

    def shareLink(def params, User user) {
        Resource resource = new Resource()
        resource.properties = params
        resource.type = ResourceType.LINK
        resource.createdBy = user
        resource.validate()
        if (resource.hasErrors()) {
            println(resource.errors.allErrors)
            return 0
        } else {
            resource.save(failOnError: true)
            return resource.id
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
            return 0
        } else {
            resource.save(failOnError: true)
            return resource.id
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
            topic{
                subscriptions{
                    user{
                        eq("username",username)
                    }
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
        return rs.isRead
    }
}
