package sharehub

class ShareHubFilters {

    static dependsOn = [AssetsFilters]
    def filters = {
        all(uri: "/login/**", invert:true) {
            before = {
                if(request.assets)
                    return;
                if(!session["username"]) {
                    redirect controller: "Login", action: "index"
                }
            }
            after = { Map model ->
            }
            afterView = { Exception e ->
            }
        }
        all(uri: "/login/**" ){
            before = {
                if(session["username"]) {
                    redirect controller: "Home", action: "index"
                }
            }
        }
    }
}
//depends on