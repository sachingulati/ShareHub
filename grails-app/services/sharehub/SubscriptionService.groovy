package sharehub

import grails.transaction.Transactional


@Transactional
class SubscriptionService {

    def serviceMethod() {

    }
    def getSubscribedTopics(String username){
        return User.findByUsername(username).subscriptions*.topic.asList();
    }
    def getSubscribedUsers(int topicId){
        return Topic.get(topicId).subscriptions*.user;
    }
}
