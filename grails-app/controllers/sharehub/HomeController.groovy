package sharehub

class HomeController {

    def index() {
            redirect(action: "dashboard")
    }
    def dashboard(){
        User user = User.findByUsername(session["username"]);
        render(view: "/dashboard",model: [username: user.username, name: user.name])
    }
}
