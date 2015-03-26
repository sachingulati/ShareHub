package sharehub


class ResourceController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]
    def grailsApplication
    def resourceService

    def renderResources(){
//        params.username = session["username"]
        /*println params
        println Re*/
        render(template: "/resource/resourceList", model: [resources: Resource.byTopicId(params.topicId).subscribedOrPublic(session["username"]), header: params.header, search: true])
    }

    def renderUnreadResources() {
        List<Resource> unreadResourceList = Resource.byIsRead(session["username"],false).sortByDate().list(offset: params.offset, max: params.max)
        render(template: "/resource/resourceList", model: [resources: unreadResourceList, header: 'Inbox', search: true,
                       doPaginate: true, ajaxController: "resource", ajaxAction: "renderUnreadResources", ajaxParams: []])
    }

    def renderTopPost(){
        List<Resource> topPost = Resource.subscribedOrPublic(session["username"]).sortByDate().list(offset: params.offset, max: params.max)
        render(template: "/resource/resourceList", model: [resources: topPost, header: 'Top Post'])
    }

    def renderResourcesCreated(){
        def resourceList = Resource.byCreatedBy(params.username?:session["username"]).sortByDate().list(max: params.max, offset: params.offset)
        render(template: "/resource/resourceList", model: [resources: resourceList, header: 'Posts', hr:true])
    }

    def switchReadStatus() {
        Boolean result= resourceService.switchReadStatus(params.resource.toLong(), session["username"])
        if(result){
            render "Mark Unread"
        }
        else if (result == false){
            render("Mark Read")
        }
        else {
            render("Invalid Request");
        }
        return false
    }

    def showPost(){
        render(view: "/resource/showPost", model: resourceService.showPost(params.id.toLong(),session["username"]))
        return false
    }

    def changeRating(){
//        data: {resourceId: resourceId, rate: id}
        if (resourceService.changeRating(params.resourceId, params.rate, session["username"])){
            def rating = resourceService.getRating(params.resourceId)
            render ("avgRating:"+rating.avgRating+", totalCount:"+rating.totalCount)
            return false
        };
        render "Invalid Request!"
        return false
    }

    def renderRating(){
        def rating = Resource.getAverageRating(Long.parseLong(params.id)).get()
        render "Avg. Rating: ${rating[0]} (${rating[1]?:0})"
    }

    def shareLink() {
        int id=resourceService.shareLink(params, User.findByUsername(session["username"]))
        if (id){
            redirect(action: "showPost", params: [id: id])
        }
        else "Error in sharing resource!"
    }

    def shareDocument() {
        int id=resourceService.shareDocument(params, User.findByUsername(session["username"]))
        if (id){
            redirect(action: "showPost", params: [id: id])
        }
        else render "Error in sharing resource!"
    }

    def editResource(){
        Resource resource = resourceService.editResource(params)
        redirect(action: "showPost", params: [id: resource?.id])
        return false;
    }

    def editLink(){}

    def download(){
        File file = resourceService.download(params.resourceId, session["username"])
        if (file)
        {
            response.setContentType("APPLICATION/OCTET-STREAM")
            response.setHeader("Content-Disposition", "Attachment;Filename="+ file.name)
            def outputStream = response.getOutputStream()
            outputStream << file.getBytes()
            outputStream.flush()
            outputStream.close()
        }
        else
            render "Bad request!"
    }

    def deleteResource(){
        render resourceService.deleteResource(Long.parseLong(params.id), session["username"])
        return false;
    }
}
