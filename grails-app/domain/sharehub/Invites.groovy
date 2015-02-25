package sharehub

import com.sharehub.enums.InviteStatus

class Invites {
    Date dateCreated, lastUpdated
    InviteStatus status;
    static belongsTo = [invitedBy: User, invitedTo: User, topic:Topic]
    static constraints = {
    }
}
