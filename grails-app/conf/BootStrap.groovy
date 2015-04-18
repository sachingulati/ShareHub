import sharehub.Role
import sharehub.User
import sharehub.UserRole

class BootStrap {

    def userService
    def topicService
    def resourceService
    def init = { servletContext ->

        def adminRole = new Role(authority: 'ROLE_ADMIN').save(flush: true)
        def userRole = new Role(authority: 'ROLE_USER').save(flush: true)

        User testUser = new User(firstName: "Sachin", lastName: "Gulati", email: "sachin@email.com", username: "sachin",
                password: "12345678")
        testUser.save(flush: true)

        UserRole.create testUser, adminRole, true

/*
        if (false && User.list().isEmpty()){
            log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>")
            userService.createDefaultUsers()
            log.info("Default Users added.")
            topicService.createDefaultTopics()
            log.info("Default topics added.")
            topicService.createDefaultSubscription()
            log.info("Default subscriptions added.")
            resourceService.createDefaultResources()
            log.info("Default resources added.")
            resourceService.createDefaultRatings()
            log.info("Default ratings added.")
            resourceService.createDefaultReadingItems()
            log.info("Default readingItems added.")
            log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>")
        }*/
    }
    def destroy = {
    }
}
