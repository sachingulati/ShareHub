package sharehub

import com.sharehub.enums.Seriousness
import com.sharehub.enums.Visibility

class Topic {
    String name
    static belongsTo = [createdBy: User]
    static hasMany = [resources: Resource, subscriptions: Subscription, invites: Invite]
    Date dateCreated
    Date lastUpdated
    Visibility visibility
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

        subscribedTopics { user ->
            subscriptions {
                eq("user",user)
            }
        }

        publicOrSubscribed { user ->
            distinct()
            or {
                subscribedTopics(user)
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

        sortByNumberOfPost{
            resources{
                groupProperty("topic")
                count("id", "resource")
            }
            order("resource","desc")
        }

        search { searchString ->
            ilike("name", "%${searchString}%")
        }
    }
}
