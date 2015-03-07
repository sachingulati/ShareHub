<%--
  Created by IntelliJ IDEA.
  User: intelligrape
  Date: 7/3/15
  Time: 4:13 PM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title></title>
</head>

<body>
hi this is remote example
<div id="remotePage">
    <g:render template="/posts" bean="${resources}" var="resources" />
    <div>
      <util:remotePaginate total="100" update="remotePage" action="getResources" controller="resource" pageSizes="[5,10,20,50]"/>
</div>
</div>

</body>
</html>