package sharehub

class SearchController {

    def resourceService
    def index(){
        render(view: "/search", model: [search: params.search, ajaxController: "search", ajaxAction: "searchResources"])
    }
    def inboxSearch(){
        render(template: "/resource/resourceList", model: [resources: resourceService.getResourceList([isRead: false, isSubscribed: true, username: session["username"], resourceSearchString: params.searchString,topicNameSearchString: params.searchString]), header: "Inbox search: '${params.searchString}'", search: true])
    }
    def searchResources(){
        List<Resource> searchResult = resourceService.getResourceList(username: session["username"], resourceSearchString: params.search,
                topicNameSearchString: params.search, offset: params.offset, max: params.max)
        render(template: "/resource/resourceList", model: [resources: searchResult, ajaxController: "search",
                           ajaxAction: "searchResources", header: "Search: "+params.search,search: false, doPaginate: true, ajaxParams: [search: params.search]])
    }
}
