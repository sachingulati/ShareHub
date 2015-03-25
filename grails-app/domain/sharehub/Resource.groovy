package sharehub

import com.sharehub.enums.ResourceType
import com.sharehub.enums.Visibility

class Resource {
    String description
    String title
    Date dateCreated
    Date lastUpdated
    ResourceType type
    String url
    String filePath

    static belongsTo = [createdBy: User, topic: Topic]
    static hasMany = [resourceStatus: ResourceStatus]

    static namedQueries = {
        sortByDate{
            order("lastUpdated","desc")
        }

        today{
            gt("dateCreated", new Date().clearTime())
        }
        inLastOneWeek{
            gt("dateCreated", new Date().clearTime()-7)
        }
        inLastOneMonth{
            gt("dateCreated", new Date().clearTime()-30)
        }
        inLastOneYear{
            gt("dateCreated", new Date().clearTime()-365)
        }

        getAverageRating{id->
            eq("id",id)
            groupProperty("id")
            projections {
                resourceStatus {
                    count("score", "number")
                    avg("score", "rating")
                }
            }
        }

        byTopicId{id->
            createAlias("topic","topic")
            eq("topic.id",id)
        }

        byCreatedBy{username->
            createAlias("createdBy","user")
            eq("user.username",username)
        }

        subscribed{username->
            createAlias("topic","topic")
            createAlias("topic.subscriptions","subs")
            createAlias("subs.user","subUser")
            eq("subUser.username",username)
        }

        byIsRead{username,isRead->
            subscribed(username)
            createAlias("resourceStatus","rs")
            createAlias("rs.user","rsUser")
            eq("rs.isRead",isRead)
            eq("rsUser.username",username)
        }
        subscribedOrPublic{username->
            createAlias("topic","topic")
            createAlias("topic.subscriptions","subs")
            createAlias("subs.user","subUser")
            or {
                eq("subUser.username",username)
                eq("topic.visibility",Visibility.PUBLIC)
            }
        }
        searchInResource{searchString->
            or {
                ilike("description", "%${searchString}%")
                ilike("title", "%${searchString}%")
            }
        }
        searchInTopicOrResource{searchString->
            or {
                searchInResource(searchString)
                searchInTopic(searchString)
            }
        }
        /*searchInInboxWithTopicOrResource{username,searchString->
            byIsRead(username,false)
            or {
                searchInResource(searchString)
                ilike("topic.name","%${searchString}%")
            }
        }*/
        searchInInbox{username,searchString->
            createAlias("topic","topic")
            createAlias("topic.subscriptions","subs")
            createAlias("subs.user","subUser")
            eq("subUser.username",username)
            createAlias("resourceStatus","rs")
            createAlias("rs.user","rsUser")
            eq("rs.isRead",false)
            eq("rsUser.username",username)
            or {
                searchInResource(searchString)
                ilike("topic.name","%${searchString}%")
            }
        }
        searchInTopic{searchString->
            createAlias("topic","t")
            ilike("t.name","%${searchString}%")
        }
        byPublicTopic{
            createAlias("topic","t")
            eq("t.visibility",Visibility.PUBLIC)
        }
        sortByRating{
//            order(avgRating(property("id")), "desc")
        }
    }

    static mapping = {
        description type: 'text'
        sort(dateCreated:'desc')
    }
    static constraints = {
        description nullable: true;
        title unique: 'topic'
        description maxSize: 1024
        filePath nullable: true
        url nullable: true
    }

    def afterInsert = {
        topic.subscriptions.each {
            if (it.user == createdBy) {
                addToResourceStatus(user: it.user, isRead: true)
            } else {
                addToResourceStatus(user: it.user, isRead: false)
            }
        }
    }
    def beforeDelete = {
        if (type == ResourceType.DOCUMENT && filePath){
            new File(filePath).delete()
        }
    }
}