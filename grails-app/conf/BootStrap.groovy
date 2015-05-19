import sharehub.Role
import sharehub.User
import sharehub.UserRole

class BootStrap {

    def userService
    def topicService
    def resourceService
    def init = { servletContext ->

        if (User?.list().isEmpty()){
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
        }
    }
    def destroy = {
    }
}
