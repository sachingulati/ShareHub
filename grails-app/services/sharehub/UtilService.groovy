package sharehub

import grails.transaction.Transactional

@Transactional
class UtilService {

    def serviceMethod() {

    }
    def deleteFile(String fileUrl){
        new File(fileUrl).delete()
    }
    def saveFile(String fileUrl,data){
        data.transferTo(new File(fileUrl));
    }
}
