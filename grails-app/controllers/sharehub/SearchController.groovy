package sharehub

class SearchController {

    def resourceService
    def index() {}

    def inboxSearch(){
//        render(template: "/resource/posts", model: [resources: searchService.searchResources(session["username"], params.searchString, [inbox: true]), header: "Inbox search: '${params.searchString}'", search: true])
        render(template: "/resource/resourceList", model: [resources: resourceService.getResourceList([isRead: false, isSubscribed: true, username: session["username"], resourceSearchString: params.searchString,topicNameSearchString: params.searchString]), header: "Inbox search: '${params.searchString}'", search: true])

        //render searchService.searchResources(session["username"], params.searchString, [inbox: true])
    }
    def searchResources(){

    }
}
