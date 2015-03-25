package sharehub

class SearchController {

    def resourceService
    def index(){
        render(view: "/search", model: [search: params.search, ajaxController: "search", ajaxAction: "searchResources"])
    }
    def inboxSearch(){
        def resources = Resource.searchInInbox(session["username"],params.search).sortByDate().list(max: 10, offset: 0)
        render(template: "/resource/resourceList", model: [resources: resources, header: "Inbox search: '${params.search}'", search: true])
    }
    def searchResources(){
        List<Resource> searchResult = resourceService.getResourceList(username: session["username"], resourceSearchString: params.search,
                topicNameSearchString: params.search, offset: params.offset, max: params.max)
        render(template: "/resource/resourceList", model: [resources: searchResult, ajaxController: "search",
                           ajaxAction: "searchResources", header: "Search: "+params.search,search: false, doPaginate: true, ajaxParams: [search: params.search]])
    }
}
