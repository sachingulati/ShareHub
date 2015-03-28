package sharehub

class User {
    String firstName
    String lastName
    String email
    String username
    String password//, confirmPassword
    String photoUrl
    boolean admin = false
    boolean active = true
    Date dateCreated
    Date lastUpdated
    static hasMany = [topics: Topic, resources: Resource, subscriptions: Subscription, resourceStatus: ResourceStatus, invites: Invite]
    static hasOne = [passwordToken: PasswordToken]
    static transients = ['name']//,'confirmPassword']

    def getSubscriptionCount() {
        return subscriptions.size()
    }

    def getTopicCount() {
        return topics.size()
    }

    String getName() {
        return firstName + " " + lastName
    }/*
    String setConfirmPassword(String paswd){
        confirmPassword = paswd
    }*/
    static constraints = {
        username unique: true
        email(unique: true, email: true, blank: false)
        firstName blank: false, nullable: false
        lastName blank: false, nullable: false
        password(size: 8..20, blank: false)
        photoUrl nullable: true
        passwordToken nullable: true
        /*confirmPassword(validator:{val, user->
            return val.equals(user.password)
        })*/
    }
}
