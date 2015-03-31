package sharehub

import com.sharehub.CO.MailCO
import com.sharehub.enums.Seriousness


class DailyJob {
    def utilService
    static triggers = {
        cron(name: "Very Serious", cronExpression: "0 0/3 * 1/1 * ? *")// "0 0 8 1/1 * ? *")
    }

    def execute() {
        println("hello")
        //utilService.sendMail(new MailCO(to: "sachingulati21@gmail.com", subject: "cron notification", body: "hello"))
    }
}
