package sharehub

class SearchController {

    def resourceService
    def topicService
    def index(){
        render(view: "/search", model: [search: params.search, ajaxController: "search", ajaxAction: "searchResources"])
    }
    def inboxSearch(){
        def resources = Resource.searchInInbox(session["username"],params.search).sortByDate().list(max: params.max?:10, offset: params.offset?:0)
        def header = "Inbox search: '${params.search}'"
        header = header.length()>45?header.substring(0,45) + "...'":header
        render(template: "/resource/resourceList", model: [resources: resources, header: header, search: true,
                       doPaginate: true, ajaxController: "search", ajaxAction: "inboxSearch", ajaxParams: [search: params.search]])
    }
    def searchResources(){
        List<Resource> searchResult = resourceService.getResourceList(username: session["username"], resourceSearchString: params.search,
                offset: params.offset, max: params.max)
        def header = "Search resources: '${params.search}'"
        header = header.length()>50?header.substring(0,50) + "...'":header
        render(template: "/resource/resourceList", model: [resources: searchResult, ajaxController: "search",
                           ajaxAction: "searchResources", header: header,search: false, doPaginate: true, ajaxParams: [search: params.search]])
    }
    def searchTopic(){
        def searchResult = Topic.search(params.search).sortByRecentResource().listDistinct()
        def totalCount = searchResult.size()
        searchResult = topicService.subList(searchResult, params.max, params.offset)
        String header = "Search: '" + params.search + "'"
        header = header.length()>25?header.substring(0,25) + "...'":header
        render(template: "/topic/topicList", model: [topics: searchResult, header:"${header}", search: true, searchSubscription: params.searchSubscription,
             hr:true, viewAll: true, doPaginate: true, ajaxController: "search", ajaxAction: "searchTopic", ajaxParams: [search: params.search], totalCount: totalCount])
    }

}
