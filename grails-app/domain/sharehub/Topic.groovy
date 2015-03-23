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
}
