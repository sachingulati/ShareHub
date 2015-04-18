package sharehub

import grails.plugin.springsecurity.annotation.Secured
import grails.transaction.Transactional

@Secured(['ROLE_ADMIN','ROLE_USER'])
class InvitesController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def mailService
    def topicService
    @Transactional
    def sendInvite() {
        if (topicService.invite(params.topic, params.email, params.inviteTo, g)) {
            render "Invitation sent successfully to " + params.inviteTo
        }
        else {
            render("Error in sending Invitation!")
        }
    }
}
