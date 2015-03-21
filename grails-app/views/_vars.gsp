<script>
    var subscribeUrl = "${createLink(controller: "subscription", action: "subscribe")}";
    var unsubscribeUrl = "${createLink(controller: "subscription", action: "unsubscribe")}";
    var switchReadUrl = "${createLink(controller: "resource", action: "switchReadStatus")}";
    var topicSelectorUrl = "${createLink(controller: "topic", action: "getSubscribedTopics")}";
</script>