package sharehub

class AssetsFilters {

    def filters = {
        all(uri:"/assets/**") {
            before = {
                //println "caught assets!"
                request.assets = true
            }
            after = { Map model ->

            }
            afterView = { Exception e ->

            }
        }
    }
}
