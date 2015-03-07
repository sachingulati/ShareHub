package sharehub

import com.sharehub.enums.Visibility

class HomeController {

    def topicService
    def resourceService
    def index() {
            forward(action: "dashboard")
    }
    def dashboard(){
        List unreadResources = resourceService.unreadResourceList(session["username"])
        UserViewCommand userViewCommand = new UserViewCommand(session["username"],Visibility.PRIVATE)
        render(view: "/dashboard",model: [user:userViewCommand,unreadResources: unreadResources])
    }
}
