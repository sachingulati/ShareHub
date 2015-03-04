package sharehub

class ResourceStatus {
    Boolean isRead
    Integer score
    Date dateCreated, lastUpdated
    static belongsTo = [user:User,resource:Resource]
    static constraints = {
        score nullable: true
    }
    static mapping = {
        //sort(lastUpdated: 'desc')
        sort(dateCreated: 'desc')
    }
}
