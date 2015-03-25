package sharehub



class DailyJob {
    static triggers = {
        cron(name: "Very Serious", cronExpression: "0 0 8 1/1 * ? *")
    }

    def execute() {
        // execute job
    }
}
