package sharehub

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional
class ResourceController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]
    def resourceService

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Resource.list(params), model: [resourceInstanceCount: Resource.count()]
    }

    def remoteTest(){
        render(view: "/temp")
    }
    def getResources(){
        render(template: "/posts", model: [resources: Resource.list(params)])
    }
    def unreadResourceList() {
        render(template: "/posts", model: [resources: resourceService.unreadResourceList(session["username"], params.max, params.offset)])
    }

    def switchReadStatus() {
        resourceService.switchReadStatus(params.resource.toLong(), session["username"])
        render params.resource + " is switched"

    }

    def show(Resource resourceInstance) {
        respond resourceInstance
    }

    def create() {
        respond new Resource(params)
    }

    @Transactional
    def shareLink() {
        if (resourceService.shareLink(params, User.findByUsername(session["username"])))
            render "Link Shared!"
        else "Error in sharing resource!"
    }

    @Transactional
    def shareDocument() {
        if (resourceService.shareLink(params, User.findByUsername(session["username"])))
            render "Document Shared!"
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
