package sharehub

import grails.transaction.Transactional

import java.security.SecureRandom

@Transactional
class UtilService {

    def deleteFile(String fileUrl){
        new File(fileUrl).delete()
    }
    def saveFile(String fileUrl,data){
        data.transferTo(new File(fileUrl));
    }
    public String getRandomString() {
        SecureRandom random = new SecureRandom();
        return new BigInteger(130, random).toString(32);
    }
}
