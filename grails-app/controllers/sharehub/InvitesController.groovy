package sharehub


import grails.transaction.Transactional

class InvitesController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    static beforeInterceptor = {
        if (!session["username"]) {
            redirect(url: "/")
            return false
        }
    }

    def mailService
    def topicService
    @Transactional
    def sendInvite() {
        if (topicService.invite(session["username"], params.topic, params.email, params.inviteTo, g)) {
            render "Invitation sent successfully to " + params.inviteTo
        }
        else {
            render("Error in sending Invitation!")
        }
    }
}
