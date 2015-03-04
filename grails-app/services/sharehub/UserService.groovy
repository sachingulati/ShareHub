package sharehub

import grails.transaction.Transactional

@Transactional
class UserService {

    def serviceMethod() {

    }
    def createDefaultUsers(){
        2.times {
            User u = new User(firstName: "Name$it", lastName: "Last$it", email: "Name$it@email.com", username: "name$it", password: "12345678", confirmPassword: "12345678", admin: false, active: true)
            u.validate() && u.save(flush: true, failOnError: true)
        }
    }
    def createUser(User user){

    }
    def getName(String username){
        def name = User.createCriteria().get {
            projections{
                property("firstName")
                property("lastName")
            }
            eq("username",username)
            cache(true)
        }
        return (name[0]+" "+name[1])
    }
    def getId(String username){
        def id = User.createCriteria().get {
            projections{
                property("id")
            }
            eq("username",username)
            cache(true)
        }
        return id
    }
}
