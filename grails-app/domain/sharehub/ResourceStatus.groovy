package sharehub

class ResourceStatus {
    Boolean isRead
    Integer score

    static belongsTo = [user:User,resource:Resource]
    static constraints = {
        score nullable: true
    }
}
