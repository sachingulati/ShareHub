package sharehub

class SearchController {

    def searchService
    def index() {}

    def inboxSearch(){
        render(template: "/resource/posts", model: [resources: searchService.searchResources(session["username"], params.searchString, [inbox: true]), header: "Inbox search: '${params.searchString}'", search: true])

        //render searchService.searchResources(session["username"], params.searchString, [inbox: true])
    }
}
