import com.sharehub.enums.ResourceType
import com.sharehub.enums.Seriousness
import com.sharehub.enums.Visibility
import sharehub.Resource
import sharehub.ResourceStatus
import sharehub.Subscription
import sharehub.Topic
import sharehub.User

class BootStrap {

    def init = { servletContext ->
        createUsers()
        createTopics()
        subscribeTopic()
        createResources()
        createRating()
    }
    def createUsers(){
        2.times{
            User u = new User(firstName: "Name$it",lastName: "Last$it", email: "Name$it@email.com",username: "name$it", password: "12345678", admin: false, active: true)
            u.validate() && u.save(flush: true)
        }
    }
    def createTopics(){
        User.list().each {u->
            5.times{
                Topic t = new Topic(name: "Topic$it", createdBy: u, visibility: Visibility.PUBLIC);
                t.validate() && t.save(flush: true)
            }
        }
    }
    def subscribeTopic(){
        3.times{
            Subscription s = new Subscription(seriousness: Seriousness.SERIOUS, topic: Topic.get(7+it), user: User.get(1))
            s.validate() && s.save(flush: true)
        }
    }
    def createResources(){
        Topic.list().each{t->
            10.times {
                Resource r = new Resource(title: "Resource$it", description: "description$it", type: (it<5?ResourceType.DOCUMENT:ResourceType.LINK), url: "someUrl.com", createdBy: t.createdBy, topic: t)
                r.save(flush: true)
            }
        }
    }
    def createReadingItems(){
        int count = 0;
        ResourceStatus.findByIsRead(false).each{rs->
            if(count++<3){
                rs.isRead = true;
                rs.save(flush: true)
            }

        }
    }
    def createRating(){
        ResourceStatus.list().eachWithIndex {rs,index->
            rs.score = index % 5 +1;
            rs.save()
        }
    }
    def destroy = {
    }
}
