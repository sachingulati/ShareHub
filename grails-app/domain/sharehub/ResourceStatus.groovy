package sharehub

class ResourceStatus {
    Boolean isRead = false
    Integer score = 0
    Date dateCreated, lastUpdated
    static belongsTo = [user: User, resource: Resource]
    static constraints = {
        score nullable: true
        user unique: 'resource'
    }
    static mapping = {
        sort(dateCreated: 'desc')
    }
}
