package sharehub

class User {
    String firstName,lastName
    String email
    String username, password, confirmPassword
    byte[] photo
    boolean admin
    boolean active
    Date dateCreated, lastUpdated
    static hasMany = [topics: Topic,resources: Resource,subscriptions: Subscription]
    static transients = ['name','confirmPassword']

    String getName(){
        return firstName + " " + lastName
    }
    String setConfirmPassword(String paswd){
        confirmPassword = paswd
    }
    static constraints = {
        username unique: true
        email (unique: true,email: true, blank: false)
        password(size:8..20,blank: false)
        photo nullable: true
        confirmPassword(validator:{val, user->
            return val.equals(user.password)
        })
    }
    static mapping={
        photo type: 'blob'
    }
}
