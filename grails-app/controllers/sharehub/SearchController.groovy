package sharehub

class SearchController {

    def resourceService
    def topicService

    def index() {
        render(view: "/search", model: [search: params.search, ajaxController: "search", ajaxAction: "searchResources"])
    }

    def inboxSearch() {
        def resources
        String header
        if (params.topicId?.size() > 0) {
            resources = Resource.byTopicId(Long.parseLong(params.topicId.toString())).searchInResource(params.search).sortByDate().list(max: params.max ?: 10, offset: params.offset ?: 0)
            header = "Search: '"
        } else {
            resources = Resource.searchInInbox(session["username"], params.search).sortByDate().list(max: params.max ?: 10, offset: params.offset ?: 0)
            header = "Inbox search: '"
        }
        header = header + params.search + "'"
        header = header.length() > 45 ? header[0..45] + "...'" : header
        render(template: "/resource/resourceList",
                model: [resources : resources, header: header, search: true, doPaginate: true, ajaxController: "search",
                        ajaxAction: "inboxSearch", ajaxParams: [search: params.search, topicId: params.topicId]])
    }

    def searchResources() {
        def searchResult = Resource.searchInResource(params.search)
        if (!session["admin"]) {
            searchResult = searchResult.subscribedOrPublic(session["username"])
        }
        searchResult = searchResult.list(offset: params.offset, max: params.max)
        String header = "Resources: '${params.search}'"
        header = header.length() > 50 ? header[0..50] + "...'" : header
        render(template: "/resource/resourceList",
                model: [resources : searchResult, ajaxController: "search", ajaxAction: "searchResources", header: header,
                        search: false, doPaginate: true, ajaxParams: [search: params.search]])
    }

    def searchTopic() {
        def searchResult = Topic.search(params.search).sortByRecentResource().listDistinct()
        def totalCount = searchResult.size()
        searchResult = topicService.subList(searchResult, params.max, params.offset)
        String header = "Topics: '" + params.search + "'"
        header = header.length() > 25 ? header[0..25] + "...'" : header
        render(template: "/topic/topicList",
                model: [topics: searchResult, header: "${header}", search: true, searchSubscription: params.searchSubscription,
                        hr: true, viewAll: true, doPaginate: true, ajaxController: "search", ajaxAction: "searchTopic",
                        ajaxParams: [search: params.search], totalCount: totalCount])
    }

}
