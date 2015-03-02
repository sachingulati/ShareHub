package sharehub

class SearchController {

    def searchService
    def index() {}

    def inboxSearch(){
        render searchService.inbox(session["username"], params.searchString)
    }
}
