package sharehub

import grails.transaction.Transactional

@Transactional
class UserService {

    def grailsApplication
    def serviceMethod() {

    }
    def createDefaultUsers(){
        User user = new User(firstName: "Sachin", lastName: "Gulati", email: "sachin@email.com", username: "sachin", password: "12345678", confirmPassword: "12345678", admin: false, active: true, photoUrl: (grailsApplication.config.uploadImages+"sachin"))
        user.validate()
        if (user.hasErrors()){
            log.error("Validation errors in user!\n" + user.errors.allErrors)
        }
        user.save()
        user = new User(firstName: "Admin", lastName: "ShareHub", email: "admin@email.com", username: "admin", password: "12345678", confirmPassword: "12345678", admin: true, active: true)
        user.validate()
        if (user.hasErrors()){
            log.error("Validation errors in user!\n" + user.errors.allErrors)
        }
        user.save()
    }
    def register(params){
        User user = new User()
        user.properties = params
        user.validate()
        if(user.hasErrors()){
            return null
        }
        log.info(">>>>>>>>>>>>>>>>  New Logs  >>>>>>>>>>>>>>>>>>")
        user.firstName = user.firstName.capitalize()
        user.lastName = user.lastName.capitalize()
        user.username = user.username.toLowerCase()
        user.email = user.email.toLowerCase()
        String path = grailsApplication.config.uploadImages.toString() + params.username
//        String path = grailsApplication.config.userImages + params.username
        params.photo.transferTo(new File(path));
        user.photoUrl = path
        user.save()
        return user
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

    def createUser(params){

    }

}
