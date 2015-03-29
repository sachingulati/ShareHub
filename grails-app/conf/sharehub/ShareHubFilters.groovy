package sharehub

class ShareHubFilters {

    def filters = {
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