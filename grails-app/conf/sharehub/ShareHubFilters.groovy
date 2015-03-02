package sharehub

class ShareHubFilters {

    static dependsOn = [AssetsFilters]
    def filters = {
        all(uri: "/login/**", invert:true) {
            before = {
                if(request.assets)
                    return;
               // println "invert login..."
                if(!session["username"]) {
                 //   println "redirect login"
                    //redirect controller: "Login", action: "index"
                }
            }
            after = { Map model ->
            }
            afterView = { Exception e ->
            }
        }
        all(uri: "/login/**" ){
            before = {
                //println "login"
                if(session["username"]) {
                  //  println "redirect Home.. "
                    redirect controller: "Home", action: "index"
                }
            }
        }
    }
}
//depends on