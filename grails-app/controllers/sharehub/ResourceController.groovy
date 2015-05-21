package sharehub

import com.sharehub.enums.Roles


class ResourceController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def grailsApplication
    def resourceService
    def springSecurityService
    def utilService
    def renderResources() {
        def resources = Resource.byTopicId(params.topicId)
        if (!utilService.isUser(Roles.ADMIN)) {
            resources = resources.subscribedOrPublic(springSecurityService.currentUser.username)
        }
        render(template: "/resource/resourceList", model: [resources: resources.list(), header: params.header, search: true])
    }

    def renderUnreadResources() {
        List<Resource> unreadResourceList = Resource.byIsRead(springSecurityService.currentUser.username, false).sortByDate().list(offset: params.offset, max: params.max)
        render(template: "/resource/resourceList", model: [resources : unreadResourceList, header: 'Inbox', search: true,
                       doPaginate: true, ajaxController: "resource", ajaxAction: "renderUnreadResources", ajaxParams: []])
    }

    def renderTopPost() {
        List<Resource> topPost = Resource.subscribedOrPublic(springSecurityService.currentUser.username).sortByDate().list(offset: params.offset, max: params.max)
        render(template: "/resource/resourceList", model: [resources: topPost, header: 'Top Post'])
    }

    def renderResourcesCreated() {
        def resourceList = Resource.byCreatedBy(params.username).sortByDate().list(max: params.max, offset: params.offset)
        render(template: "/resource/resourceList", model: [resources: resourceList, header: 'Posts', hr: true])
    }

    def switchReadStatus() {
        Boolean result = resourceService.changeOrSwitchReadStatus(params.resource.toLong(), springSecurityService.currentUser.username)
        if (result) {
            render "Mark Unread"
        } else if (result == false) {
            render("Mark Read")
        } else {
            render("Invalid Request")
        }
    }

    def showPost() {
        render(view: "/resource/showPost", model: resourceService.showPost(params.id.toLong()))
    }

    def changeRating() {
        if (resourceService.changeRating(params.resourceId, params.rate, springSecurityService.currentUser.username)) {
            def rating = resourceService.getRating(params.resourceId)
            render("avgRating:" + rating.avgRating + ", totalCount:" + rating.totalCount)
        }
        else {
            render "Invalid Request!"
        }
    }

    def renderRating() {
        def rating = Resource.getAverageRating(Long.parseLong(params.id)).get()
        render "Avg. Rating: ${rating[0]} (${rating[1] ?: 0})"
    }

    def shareLink() {
        Resource resource= resourceService.shareLink(params, springSecurityService.currentUser)
        if (resource) {
            redirect(action: "showPost", params: [id: resource.id])
        } else {
            render "Error in sharing link!"
        }
    }

    def shareDocument() {
        Resource resource = resourceService.shareDocument(params, springSecurityService.currentUser)
        if (resource) {
            redirect(action: "showPost", params: [id: resource.id])
        } else {
            render "Error in sharing document!"
        }
    }

    def editResource() {
        Resource resource = resourceService.editResource(params)
        redirect(action: "showPost", params: [id: resource?.id])
    }

    def download() {
        File file = resourceService.download(params.resourceId, springSecurityService.currentUser.username)
        if (file?.isFile()) {
            response.setContentType("APPLICATION/OCTET-STREAM")
            response.setHeader("Content-Disposition", "Attachment;Filename=" + file.name)
            def outputStream = response.getOutputStream()
            outputStream << file.getBytes()
            outputStream.flush()
            outputStream.close()
        } else {
            flash.error = "File not found!"
            redirect(url:  "/")
        }
    }

    def deleteResource() {
        render resourceService.deleteResource(Long.parseLong(params.id), springSecurityService.currentUser.username)
    }
}
