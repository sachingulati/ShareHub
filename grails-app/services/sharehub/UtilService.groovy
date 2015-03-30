package sharehub

import com.sharehub.CO.MailCO
import grails.gsp.PageRenderer
import grails.transaction.Transactional

import java.security.SecureRandom

@Transactional
class UtilService {
    def mailService
    PageRenderer groovyPageRenderer
    String pageRenderer(String template, model){
        String content
        try{
            content = groovyPageRenderer.render(template: template, model: model)
        }
        catch (Exception e){
            println e
        }
        return content
    }
    def sendMail(MailCO mailCO){
        try{
            mailService.sendMail {
                async true
                to mailCO.to
                subject mailCO.subject
                html mailCO.body
            }
        }
        catch (Exception e){
            log.error(e)
            return false
        }
        return true
    }
    def deleteFile(String fileUrl) {
        new File(fileUrl).delete()
    }

    def saveFile(String fileUrl, data) {
        data.transferTo(new File(fileUrl));
    }

    public String getRandomString() {
        SecureRandom random = new SecureRandom();
        return new BigInteger(130, random).toString(32);
    }
}