package sharehub

class ShareHubFilters {

    def filters = {
        all(controller: "login|assets", action: "*", invert: true){
            before={
                if(!session['username']){
                    flash.message = "Please login to proceed!"
                    redirect(controller: "login", action: "index")
                    return false
                }
            }
        }
        login(controller: "login", action: "*"){
            before={
                if(session["username"]){
                    redirect(controller: "home", action: "dashboard")
                    return false
                }
            }
        }
    }
}