package sharehub

import com.sharehub.enums.InviteStatus
import com.sharehub.enums.Seriousness
import com.sharehub.enums.Visibility

class Subscription {
    Date dateCreated
    Seriousness seriousness;
    static belongsTo = [topic:Topic,user:User]
    static constraints = {
        topic unique: 'user'
    }
    static afterInsert={
         Invites i = Invites.findByInvitedToAndTopicAndStatus(user,topic,InviteStatus.PENDING)
        if(i){
            i.status = InviteStatus.SUBSCRIBED;
            i.save()
        }
    }
    static afterDelete={
        Invites i = Invites.findByInvitedToAndTopicAndStatus(user,topic,InviteStatus.SUBSCRIBED)
        if(i){
            i.status = InviteStatus.UNSUBSCRIBED;
            i.save()
        }
    }
}
