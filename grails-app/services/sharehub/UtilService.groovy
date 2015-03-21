package sharehub

import grails.transaction.Transactional

@Transactional
class UtilService {

    def serviceMethod() {

    }
    def mostRecent={collection->
        if (!collection || !(collection.dateCreated))
            return
        collection.max{it.dateCreated}
    }
}
