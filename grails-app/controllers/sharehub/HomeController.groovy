package sharehub

class HomeController {
    static beforeInterceptor = {
        if (!session["username"]) {
            redirect(controller: "login")
            return false
        }
    }
    static defaultAction = "dashboard"

    def dashboard() {
        render(view: "/dashboard", model: [user: User.findByUsername(session["username"])])
    }
}
