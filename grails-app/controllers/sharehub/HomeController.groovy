package sharehub

import com.sharehub.enums.Visibility

class HomeController {

    def topicService
    def resourceService
    def index() {
            forward(action: "dashboard")
    }
    def dashboard(){
        User user = User.findByUsername(session["username"])
        render(view: "/dashboard",model: [user:user])
    }
}
