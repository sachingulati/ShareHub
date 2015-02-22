package sharehub


import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class ResourceStatusController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond ResourceStatus.list(params), model: [resourceStatusInstanceCount: ResourceStatus.count()]
    }

    def show(ResourceStatus resourceStatusInstance) {
        respond resourceStatusInstance
    }

    def create() {
        respond new ResourceStatus(params)
    }

    @Transactional
    def save(ResourceStatus resourceStatusInstance) {
        if (resourceStatusInstance == null) {
            notFound()
            return
        }

        if (resourceStatusInstance.hasErrors()) {
            respond resourceStatusInstance.errors, view: 'create'
            return
        }

        resourceStatusInstance.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'resourceStatus.label', default: 'ResourceStatus'), resourceStatusInstance.id])
                redirect resourceStatusInstance
            }
            '*' { respond resourceStatusInstance, [status: CREATED] }
        }
    }

    def edit(ResourceStatus resourceStatusInstance) {
        respond resourceStatusInstance
    }

    @Transactional
    def update(ResourceStatus resourceStatusInstance) {
        if (resourceStatusInstance == null) {
            notFound()
            return
        }

        if (resourceStatusInstance.hasErrors()) {
            respond resourceStatusInstance.errors, view: 'edit'
            return
        }

        resourceStatusInstance.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'ResourceStatus.label', default: 'ResourceStatus'), resourceStatusInstance.id])
                redirect resourceStatusInstance
            }
            '*' { respond resourceStatusInstance, [status: OK] }
        }
    }

    @Transactional
    def delete(ResourceStatus resourceStatusInstance) {

        if (resourceStatusInstance == null) {
            notFound()
            return
        }

        resourceStatusInstance.delete flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'ResourceStatus.label', default: 'ResourceStatus'), resourceStatusInstance.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'resourceStatus.label', default: 'ResourceStatus'), params.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NOT_FOUND }
        }
    }
}
