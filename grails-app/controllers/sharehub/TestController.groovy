package sharehub
import grails.plugin.springsecurity.annotation.Secured

class TestController {

    @Secured(['permitAll'])
    def insecure(){
        render("insecure")
    }

    @Secured(['ROLE_ADMIN','ROLE_USER'])
    def index() {
        render "hello"
    }
}
