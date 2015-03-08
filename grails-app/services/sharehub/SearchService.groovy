package sharehub

import grails.transaction.Transactional

@Transactional
class SearchService {

    def serviceMethod() {

    }
    def searchResources(String username, String searchString,attr){
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
