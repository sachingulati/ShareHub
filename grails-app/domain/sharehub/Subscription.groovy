package sharehub

import com.sharehub.enums.Seriousness

class Subscription {
    Date dateCreated
    Seriousness seriousness = Seriousness.VERY_SERIOUS;
    static belongsTo = [topic: Topic, user: User]
    static constraints = {
        topic unique: 'user'
    }

    static afterInsert = {
        topic.resources.each { resource ->
            ResourceStatus resourceStatus = new ResourceStatus(resource: resource, user: user)
            resource.addToResourceStatus(resourceStatus)
        }
    }
}
