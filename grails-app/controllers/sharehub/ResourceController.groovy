package sharehub

import com.sharehub.enums.Visibility

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

class ResourceController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]
    def resourceService
    def topicService
    def getResources(){
        render(template: "/resource/resourceList", model: [resources: Resource.list(params)])
    }
    def unreadResourceList() {
        List<Topic> unreadResourceList = resourceService.getResourceList(isSubscribed: true, isRead: false, username: session["username"], offset: params.offset, max: params.max)
        render(template: "/resource/resourceList", bean: unreadResourceList, var: "resources", model: [header: 'Inbox', search: true, doPaginate: true])
    }
    def getResourcesCreated(){
        def resourceList = resourceService.getResourceList(username: session["username"], createdByUsername: (params.username?:session["username"]), max: params.max, offset: params.offset);
        render(template: "/resource/resourceList", bean:resourceList, var: "resources", model: [header: 'Posts', hr:true])
    }


    def switchReadStatus() {
        if(resourceService.switchReadStatus(params.resource.toLong(), session["username"])){
            render "Mark Unread"
        }
        else{
            render("Mark Read")
        }
        return false
    }

    def showPost(){
        render(view: "/resource/showPost", model: [resource: resourceService.showPost(params.id.toLong(),session["username"])])
        return false
    }

    def getRating(){
        def rating = resourceService.getRating(params.id)
        render "Avg. Rating: ${rating.totalCount} (${rating.avgRating})"
    }

    @Transactional
    def shareLink() {
        int id=resourceService.shareLink(params, User.findByUsername(session["username"]))
        if (id)
             redirect(action: "showPost", params: [id: id])
        else "Error in sharing resource!"
    }

    @Transactional
    def shareDocument() {
        int id=resourceService.shareDocument(params, User.findByUsername(session["username"]))
        if (id)
            redirect(action: "showPost", params: [id: id])
        else render "Error in sharing resource!"

    }

    def showResources() {
        render Resource.list(offset: 0, max: 5);

    }

    @Transactional
    def save(Resource resourceInstance) {
        if (resourceInstance == null) {
            notFound()
            return
        }

        if (resourceInstance.hasErrors()) {
            respond resourceInstance.errors, view: 'create'
            return
        }

        resourceInstance.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'resource.label', default: 'Resource'), resourceInstance.id])
                redirect resourceInstance
            }
            '*' { respond resourceInstance, [status: CREATED] }
        }
    }

    def edit(Resource resourceInstance) {
        respond resourceInstance
    }

    @Transactional
    def update(Resource resourceInstance) {
        if (resourceInstance == null) {
            notFound()
            return
        }

        if (resourceInstance.hasErrors()) {
            respond resourceInstance.errors, view: 'edit'
            return
        }

        resourceInstance.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'Resource.label', default: 'Resource'), resourceInstance.id])
                redirect resourceInstance
            }
            '*' { respond resourceInstance, [status: OK] }
        }
    }

    @Transactional
    def delete(Resource resourceInstance) {

        if (resourceInstance == null) {
            notFound()
            return
        }

        resourceInstance.delete flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'Resource.label', default: 'Resource'), resourceInstance.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'resource.label', default: 'Resource'), params.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NOT_FOUND }
        }
    }
}
