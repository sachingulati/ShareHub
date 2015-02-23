package sharehub

class User {
    String firstName,lastName
    String email
    String username, password
    byte[] photo
    boolean admin
    boolean active
    Date dateCreated, lastUpdated
    static hasMany = [topics: Topic,resources: Resource,subscriptions: Subscription]
    static transients = ['name']
    static constraints = {
        username unique: true
        email (unique: true,email: true, blank: false)
        password(size:8..20,blank: false)
        photo nullable: true
    }
    String getName(){
        return firstName + " " + lastName;
    }
}
