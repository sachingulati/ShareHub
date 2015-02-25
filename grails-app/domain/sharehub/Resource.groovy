package sharehub

import com.sharehub.enums.ResourceType

class Resource {
    String description, title
    Date dateCreated, lastUpdated
    ResourceType type
    String url
    String filePath
    static belongsTo = [createdBy: User, topic: Topic]
    static hasMany = [resourceStatus: ResourceStatus]
    static constraints = {
        description nullable: true;
        title unique: 'topic'
        description maxSize: 1024
        filePath nullable: true
        url nullable: true
    }
    def afterInsert = {
        topic.subscriptions.each {
            if (it.user == createdBy) {
                addToResourceStatus(user: it.user, topic: it.topic, isRead: true)
            } else {
                addToResourceStatus(user: it.user, topic: it.topic, isRead: false)
            }
        }
    }
}