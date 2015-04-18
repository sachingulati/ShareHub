package sharehub

class ShareHubFilters {

    def springSecurityService
    // update required
    def filters = {
        login(controller: "login", action: "*"){
            before={
                if(springSecurityService.isLoggedIn()){
                    redirect(controller: "home", action: "dashboard")
                    return false
                }
            }
        }
    }
}