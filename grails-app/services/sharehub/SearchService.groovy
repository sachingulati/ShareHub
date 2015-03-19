package sharehub

import grails.transaction.Transactional

@Transactional
class SearchService {

    def serviceMethod() {

    }
    def getResources(attr){
//        Attributes summary:
        /*
            isRead: for either read or unread resources
            username: for user specific search
            isSubscribed: for resources in subscribed topics
            --> searchByRating: search resources where rating is greater than equal to given rating
            resourceSearchString: for searching in resource title and description
            topicNameSearchString: for topic specific search
            createdByUsername: search by resourceCreator
            lastUpdated: search resources which are updated after a given date
            offset: for pagination
            max: for pagination
         */

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
            if(attr.isSubscribed && attr.username) {
                topic {
                    subscriptions {
                        user {
                            eq("username", attr.username)
                        }
                    }
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
    def searchResourcesOld(String username, String searchString,attr){
        List resources = Resource.createCriteria().list {
            resourceStatus{
                if(attr.inbox) {
                    eq("isRead", false)
                }
                user{
                    eq("username", username)
                }

            }
            if(attr.inbox) {
                topic {
                    subscriptions {
                        user {
                            eq("username", username)
                        }
                    }
                }
            }
            or {
                ilike("description","%$searchString%")
                topic{
                    ilike("name","%$searchString%")
                }
                ilike("title","%$searchString%")
            }
        }
        return resources
        // || resource.title.contains(searchString) || resource.topic.name.contains(searchString)
    }
}
