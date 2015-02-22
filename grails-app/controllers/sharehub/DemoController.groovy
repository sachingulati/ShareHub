package sharehub

class DemoController {

    def index() {}
    def myAction(){
        render (view: "myPage", model: [message: "message from controller"])
    }
}
