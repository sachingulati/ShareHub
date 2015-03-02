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
}
