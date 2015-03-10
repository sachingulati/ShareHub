package sharehub

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional
class ResourceController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]
    def resourceService
    def topicService
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
        if(resourceService.switchReadStatus(params.resource.toLong(), session["username"])){
            render "Mark Unread"
        }
        else{
            render("Mark Read")
        }
        return

    }

    def showPost(){
        Resource resource = Resource.findById(params.id)
        if(!resource){
            redirect(controller: "home")
            return false
        }
        User user = User.findByUsername(session["username"])
        if(!topicService.show(user,resource.topic)){
            redirect(controller: "home")
            return false
        }
        def rating = ResourceStatus.createCriteria().get(){
            projections{
                count("score","number")
                avg("score","rating")
            }
            eq("resource",resource)
        }
        Subscription subscription = Subscription.findByUserAndTopic(user,resource.topic)
        if(subscription){
            ResourceStatus resourceStatus = ResourceStatus.findByUserAndResource(user,resource)
            resourceStatus?.isRead = true
            resourceStatus.save()
        }
        render(view: "/showPost", model: [resource: resource, rating: rating])
    }
    def show(Resource resourceInstance) {
        respond resourceInstance
    }

    def create() {
        respond new Resource(params)
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
