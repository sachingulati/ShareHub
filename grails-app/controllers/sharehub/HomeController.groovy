package sharehub

class HomeController {

    def index() {
        if(session["username"]){
            redirect(action: "dashboard")
        }
        else{
            redirect(controller: "Login")
        }
    }
    def dashboard(){
        render session["username"]
    }
}
