package sharehub

class ResourceStatus {
    Boolean isRead;
    int score;
    static belongsTo = [user:User,resource:Resource]
    static constraints = {
        score nullable: true
    }
}
