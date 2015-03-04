package sharehub

class ShareHubFilters {

    def filters = {
        all(controller: "login|assets", action: "*", invert: true){
            before={
                if(!session['username']){
                    redirect(controller: "login", action: "index", params: [error:"Please login to proceed!"])
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