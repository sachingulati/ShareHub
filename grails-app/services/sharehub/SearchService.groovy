package sharehub

import grails.transaction.Transactional

@Transactional
class SearchService {

    def serviceMethod() {

    }
    def inbox(String username, String searchString){
        User user = User.findByUsername(username)
        List<Resource> resources = ResourceStatus.findAll{user== user && isRead== false && (resource.description.contains(searchString) || resource.title.contains(searchString) || resource.topic.name.contains(searchString))}*.resource
        return resources
    }
}
