package sharehub

import com.sharehub.enums.InviteStatus

class Invite {
    Date dateCreated
    Date lastUpdated
    String token = "token"
    String inviteToEmail
    static belongsTo = [invitedBy: User, topic:Topic]
    static constraints = {
        inviteToEmail(blank: false, email: true)
    }
}
