import com.sharehub.enums.ResourceType
import com.sharehub.enums.Seriousness
import com.sharehub.enums.Visibility
import sharehub.Resource
import sharehub.ResourceStatus
import sharehub.Subscription
import sharehub.Topic
import sharehub.User

class BootStrap {

    def userService
    def topicService
    def resourceService
    def init = { servletContext ->
        userService.createDefaultUsers()
        topicService.createDefaultTopics()
        topicService.createDefaultSubscription()
        resourceService.createDefaultResources()
        resourceService.createDefaultRatings()
        resourceService.createDefaultReadingItems()
    }
    def destroy = {
    }
}
