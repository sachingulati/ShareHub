<script>
    var subscribeUrl = "${createLink(controller: "subscription", action: "subscribe")}";
    var unsubscribeUrl = "${createLink(controller: "subscription", action: "unsubscribe")}";
    var switchReadUrl = "${createLink(controller: "resource", action: "switchReadStatus")}";
    var topicSelectorUrl = "${createLink(controller: "topic", action: "getSubscribedTopics")}";
    var checkEmailForNewUser = "${createLink(controller: "login", action: "checkEmail")}";
    var checkUsernameForNewUser = "${createLink(controller: "login", action: "checkUsername")}";
    var assetsPath = "${createLink(controller: "assets")}";
    assetsPath = assetsPath.substring(0,assetsPath.lastIndexOf('/')+1);
    var rateOnImagePath = assetsPath + "RateOn.jpg";
    var rateHalfImagePath = assetsPath + "RateHalf.jpg";
    var rateOffImagePath = assetsPath + "RateOff.jpg";
    var changeRatingUrl = "${createLink(controller: "resource", action: "changeRating")}";
</script>