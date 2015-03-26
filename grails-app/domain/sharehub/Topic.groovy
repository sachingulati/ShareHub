package sharehub

import com.sharehub.enums.Seriousness
import com.sharehub.enums.Visibility

class Topic {
    String name
    static belongsTo = [createdBy:User]
    static hasMany = [resources: Resource,subscriptions: Subscription, invites: Invite]
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
        byCreatedBy{username->
            createAlias("createdBy","user")
            eq("user.username",username)
        }
        subscribedTopics{username->
            createAlias("subscriptions","s")
            createAlias("s.user","u")
            eq("u.username",username)
        }
        publicOrSubscribed{username->
            or{
                subscribedTopics(username)
                publicTopics()
            }
        }
        publicTopics{
            eq("visibility",Visibility.PUBLIC)
        }
        privateTopics{
            eq("visibility",Visibility.PRIVATE)
        }
        sortByRecentResource{
            createAlias("resources","r")
            projections{}
            groupProperty("id")
            order("r.dateCreated","desc")
        }
        search{searchString->
            ilike("name", "%${searchString}%")
        }
    }
}
