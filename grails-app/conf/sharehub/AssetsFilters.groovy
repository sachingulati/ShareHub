package sharehub

class AssetsFilters {

    def filters = {
        all(uri:"/assets/**") {
            before = {
                request.assets = true
            }
            after = { Map model ->

            }
            afterView = { Exception e ->

            }
        }
    }
}
