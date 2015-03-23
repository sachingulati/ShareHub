package sharehub


import grails.transaction.Transactional

class InvitesController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def mailService
    def topicService
    @Transactional
    def sendInvite(){
        if (topicService.invite(session["username"],params.topic,params.email, params.inviteTo,g)){
            render "done"
            return false
        }
        render("Error in sending Mail!")
        return false
    }
}
