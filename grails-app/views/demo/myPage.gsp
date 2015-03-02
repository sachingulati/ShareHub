<%--
  Created by IntelliJ IDEA.
  User: intelligrape
  Date: 20/2/15
  Time: 11:23 AM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title></title>
</head>

<body>
my page <br>
${message}
<br>
<g:render template="/template" model='[content1: "this is content1" +
        "<div> hello 1<div>", content2         : "this is content2" +
        "<div> hello 2<div>"]'/>
</body>
</html>