package sharehub

class User {
    String firstName,lastName
    String email
    String username, password//, confirmPassword
    String photoUrl
    boolean admin = false
    boolean active = true
    Date dateCreated, lastUpdated
    static hasMany = [topics: Topic,resources: Resource,subscriptions: Subscription, resourceStatus: ResourceStatus]
    static transients = ['name']//,'confirmPassword']

    def getSubscriptionCount(){
        return subscriptions.size()
    }
    def getTopicCount(){
        return topics.size()
    }
    String getName(){
        return firstName + " " + lastName
    }/*
    String setConfirmPassword(String paswd){
        confirmPassword = paswd
    }*/
    static constraints = {
        username unique: true
        email (unique: true,email: true, blank: false)
        password(size:8..20,blank: false)
        photoUrl nullable: true
        /*confirmPassword(validator:{val, user->
            return val.equals(user.password)
        })*/
    }
}
