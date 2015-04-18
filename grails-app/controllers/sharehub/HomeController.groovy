package sharehub

import grails.plugin.springsecurity.annotation.Secured

@Secured(['ROLE_ADMIN', 'ROLE_USER'])
class HomeController {
    def springSecurityService
    static defaultAction = "dashboard"
    def dashboard() {
        render(view: "/dashboard", model: [user: springSecurityService.currentUser])
    }
}
