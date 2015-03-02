package sharehub

class DispatchFilters {

    def filters = {
        all(uri: "*.dispatch") {
            before = {
                //println "dispatch caught!"

            }
            after = { Map model ->

            }
            afterView = { Exception e ->

            }
        }
    }
}
