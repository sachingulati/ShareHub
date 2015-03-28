package sharehub

import com.sharehub.enums.Seriousness
import com.sharehub.enums.Visibility

class Topic {
    String name
    static belongsTo = [createdBy: User]
    static hasMany = [resources: Resource, subscriptions: Subscription, invites: Invite]
    Date dateCreated
    Date lastUpdated
    Visibility visibility;
    static constraints = {
        name unique: 'createdBy'
    }
    def afterInsert = {
        addToSubscriptions(topic: this, user: createdBy, seriousness: Seriousness.VERY_SERIOUS)
    }
    static namedQueries = {
        byCreatedBy { username ->
            createdBy {
                eq("username", username)
            }
        }
        subscribedTopics { username ->
            subscriptions {
                user {
                    eq("username", username)
                }
            }
        }
        publicOrSubscribed { username ->
            distinct()
            or {
                subscribedTopics(username)
                publicTopics()
            }
        }
        publicTopics {
            eq("visibility", Visibility.PUBLIC)
        }
        privateTopics {
            eq("visibility", Visibility.PRIVATE)
        }
        sortByRecentResource {
            projections {
//                distinct()
                uniqueResult: true
            }
            distinct()
            groupProperty("id")
            resources {
                order("dateCreated", "desc")
            }
        }
        search { searchString ->
            ilike("name", "%${searchString}%")
        }
    }
}
