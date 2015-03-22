package sharehub


import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

class InvitesController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def mailService
    @Transactional
    def sendInvite(){
        User user = User.findByUsername(session["username"]);
        Topic topic = Topic.get(params.topic)
        if (!user || !topic){
            render "Invalid Request!"
            return false
        }
        if (!session["admin"]){
            if (!Subscription.findByUserAndTopic(user,topic)){
                render "Invalid Request!"
                return false
            }
        }
        Invite invite = new Invite(inviteToEmail: params.email,invitedBy: user, topic: topic, token: "token")
        if (invite.validate()){
            def htmlcontent = g.render(template: "emailInvite", model: [user: user, topicId: topic.id, inviteTo: params.inviteTo, token: invite.token])
            mailService.sendMail {
                to params.email
                subject "hello"
                html "${htmlcontent}"
            }
            invite.save()
            render(htmlcontent)
            return false
        }
        render("Error in sending Main: \n")
        render(invite.errors.allErrors)
        println(invite.errors.allErrors)
        return false
    }
    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Invite.list(params), model: [invitesInstanceCount: Invite.count()]
    }

    def show(Invite invitesInstance) {
        respond invitesInstance
    }

    def create() {
        respond new Invite(params)
    }

    @Transactional
    def save(Invite invitesInstance) {
        if (invitesInstance == null) {
            notFound()
            return
        }

        if (invitesInstance.hasErrors()) {
            respond invitesInstance.errors, view: 'create'
            return
        }

        invitesInstance.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'invites.label', default: 'Invites'), invitesInstance.id])
                redirect invitesInstance
            }
            '*' { respond invitesInstance, [status: CREATED] }
        }
    }

    def edit(Invite invitesInstance) {
        respond invitesInstance
    }

    @Transactional
    def update(Invite invitesInstance) {
        if (invitesInstance == null) {
            notFound()
            return
        }

        if (invitesInstance.hasErrors()) {
            respond invitesInstance.errors, view: 'edit'
            return
        }

        invitesInstance.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'Invites.label', default: 'Invites'), invitesInstance.id])
                redirect invitesInstance
            }
            '*' { respond invitesInstance, [status: OK] }
        }
    }

    @Transactional
    def delete(Invite invitesInstance) {

        if (invitesInstance == null) {
            notFound()
            return
        }

        invitesInstance.delete flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'Invites.label', default: 'Invites'), invitesInstance.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'invites.label', default: 'Invites'), params.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NOT_FOUND }
        }
    }
}
