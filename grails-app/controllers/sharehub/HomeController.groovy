package sharehub

class HomeController {
    def index() {
            forward(action: "dashboard")
    }
    def dashboard(){
        User user = User.findByUsername(session["username"])
        render(view: "/dashboard",model: [user:user])
    }
}
