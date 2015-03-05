package sharehub

import grails.transaction.Transactional

@Transactional
class SearchService {

    def serviceMethod() {

    }
    def inbox(String username, String searchString){
        List resources = Resource.createCriteria().list {
            resourceStatus{
                eq("isRead",false)
            }
            topic{
                subscriptions{
                    user{
                        eq("username", username)
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
