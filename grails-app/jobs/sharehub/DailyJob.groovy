package sharehub

import com.sharehub.enums.Seriousness


class DailyJob {
    def mailService
    static triggers = {
        cron(name: "Very Serious", cronExpression: "0 0 8 1/1 * ? *")
    }

    def execute() {
        Subscription.findAllBySeriousness(Seriousness.VERY_SERIOUS) {Subscription sub->
            mailService.sendMail {
                to "${sub.user.email}"
                async true
                subject "Your daily notifications from Share Hub"
                html   "${createHtml(sub.user)}"
            }
        }
    }
    def createHtml(User user){
        String html = "Recent posts on Share Hub:<br>"
        def recentShares = Resource.subscribed(true).byIsRead(false).sortByDate().list(max: 10, offset: 0)
        recentShares.each{Resource resource->
            html = html + resource.title + "<br>"
        }
    }
}
