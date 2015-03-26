package sharehub

class SearchController {

    def resourceService
    def topicService
    def index(){
        render(view: "/search", model: [search: params.search, ajaxController: "search", ajaxAction: "searchResources"])
    }
    def inboxSearch(){
        println params
        def resources
        def header
        if (params.topicId?.size()>0){
            resources = Resource.byTopicId(Long.parseLong(params.topicId)).searchInResource(params.search).sortByDate().list(max: params.max?:10, offset: params.offset?:0)
            header = "Search: '"
        }
        else {
            resources = Resource.searchInInbox(session["username"],params.search).sortByDate().list(max: params.max?:10, offset: params.offset?:0)
            header = "Inbox search: '"
        }
        header = header + params.search + "'"
        header = header.length()>45?header.substring(0,45) + "...'":header
        render(template: "/resource/resourceList", model: [resources: resources, header: header, search: true,
                       doPaginate: true, ajaxController: "search", ajaxAction: "inboxSearch", ajaxParams: [search: params.search, topicId: params.topicId]])
    }
    def searchResources(){
        List<Resource> searchResult = resourceService.getResourceList(username: session["username"], resourceSearchString: params.search,
                offset: params.offset, max: params.max)
        def header = "Search resources: '${params.search}'"
        header = header.length()>50?header.substring(0,50) + "...'":header
        render(template: "/resource/resourceList", model: [resources: searchResult, ajaxController: "search",
                                                           ajaxAction: "searchResources", header: header,search: false, doPaginate: true, ajaxParams: [search: params.search]])
    }
    def searchResourcesAndTopics(){/*
        List<Resource> searchResultResources = resourceService.getResourceList(username: session["username"], resourceSearchString: params.search,
                offset: params.offset, max: params.max)
        def resourceHeader = "Search resources: '${params.search}'"
        resourceHeader = resourceHeader.length()>50?resourceHeader.substring(0,50) + "...'":resourceHeader


        def searchResultTopics = Topic.search(params.search).sortByRecentResource().listDistinct()
        def totalCount = searchResultTopics.size()
        searchResultTopics = topicService.subList(searchResultTopics, params.max, params.offset)
        String topicHeader = "Search: '" + params.search + "'"
        topicHeader = topicHeader.length()>25?topicHeader.substring(0,25) + "...'":topicHeader


        render(template: "/resource/resourceList", model: [resources: searchResultResources,
                                                           ajaxAction: "searchResources", header: header,search: false, doPaginate: true, ajaxParams: [search: params.search]])*/
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
