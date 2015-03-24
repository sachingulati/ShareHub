package sharehub


class ResourceController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]
    def grailsApplication
    def resourceService

    def getResources(){
        params.username = session["username"]
        println "----------------------------------------------"
        println params
        println "----------------------------------------------"
        render(template: "/resource/resourceList", model: [resources: resourceService.getResourceList(params), header: params.header, search: true])
    }
    def unreadResourceList() {
        List<Resource> unreadResourceList = resourceService.getResourceList(isSubscribed: true, isRead: false, username: session["username"], offset: params.offset, max: params.max)
        render(template: "/resource/resourceList", bean: unreadResourceList, var: "resources", model: [header: 'Inbox', search: true,
                       doPaginate: true, ajaxController: "resource", ajaxAction: "unreadResourceList", ajaxParams: []])
    }

    def getTopPost(){
        List<Resource> topPost = resourceService.getResourceList(username: session["username"], offset: params.offset, max: params.max)
        render(template: "/resource/resourceList", bean: topPost, var: "resources", model: [header: 'Top Post'])
    }

    def getResourcesCreated(){
        def resourceList = resourceService.getResourceList(username: session["username"], createdByUsername: (params.username?:session["username"]), max: params.max, offset: params.offset);
        render(template: "/resource/resourceList", bean:resourceList, var: "resources", model: [header: 'Posts', hr:true])
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

    def getRating(){
        def rating = resourceService.getRating(params.id)
        render "Avg. Rating: ${rating.totalCount} (${rating.avgRating?:0})"
    }

    def shareLink() {
        int id=resourceService.shareLink(params, User.findByUsername(session["username"]))
        if (id){
            redirect(action: "showPost", params: [id: id])
        }
        else "Error in sharing resource!"
    }

    def shareDocument() {
//        println(params)
        int id=resourceService.shareDocument(params, User.findByUsername(session["username"]))
        if (id){
            redirect(action: "showPost", params: [id: id])
        }
        else render "Error in sharing resource!"
    }

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
}
