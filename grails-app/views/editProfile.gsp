<%--
  Created by IntelliJ IDEA.
  User: intelligrape
  Date: 7/3/15
  Time: 5:57 PM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="afterLogin"/>
    <title>${user.name}</title>
</head>

<body>

<div class="contentMargin">
    <div class="row">
        <div class="col-lg-5">
            <!-- User Info -->
            <div class="panel panel-default" style="margin-bottom:7px">
                <div class="panel-body">
                    <g:render template="/userInfo" bean="${user}"/>
                </div> <!-- panel-body -->
            </div> <!-- panel -->

        <!-- subscription -->
        <g:render template="/topicList" model="[header: 'Topics', hr: true]" bean="${user.topicsCreated}" var="topics"/>
        </div> <!-- col-lgrailg-5 -->

        <div class="col-lg-7">


        <div class="panel panel-default">
            <div class="panel-heading">
                <h3 class="panel-title">Profile</h3>
            </div> <!-- panel-heading -->
            <div class="panel-body">
                <g:form>
                    <table width="100%" id="editProfile">
                        <tr>
                            <td>First name*</td>
                            <td><g:textField name="firstName" class="form-control"/> </td>
                        </tr>
                        <tr>
                            <td>Last name*</td>
                            <td><g:textField name="lastName" class="form-control"/> </td>
                        </tr>
                        <tr>
                            <td>Username*</td>
                            <td><g:textField name="user-name" readonly="true" class="form-control"/> </td>
                        </tr>
                        <tr>
                            <td>Photo</td>
                            <td><input type="file" name="photo" class="form-control"/>
                                <div style="margin-top: 10px">
                                    <g:checkBox name="removePhoto"/> Remove Photo</div>
                            </td>
                        </tr>
                        <tr>
                            <td colspan="2"><g:submitButton name="submit" value="Update" class="btn btn-default" style="width: 100%"/>
                            </td>
                        </tr>
                    </table>
                </g:form>
            </div>    <!-- panel-body -->
        </div>  <!-- panel -->

        <div class="panel panel-default">
            <div class="panel-heading">
                <h3 class="panel-title">Change password</h3>
            </div> <!-- panel-heading -->
            <div class="panel-body">
                <g:form>
                    <table width="100%" id="editProfile">
                        <tr>
                            <td>Current Pasword*</td>
                            <td><g:textField name="currentPassword" class="form-control"/> </td>
                        </tr>
                        <tr>
                            <td>New Password*</td>
                            <td><g:textField name="newPassword" class="form-control"/> </td>
                        </tr>
                        <tr>
                            <td>Confirm password*</td>
                            <td><g:textField name="confirmPassword" class="form-control"/> </td>
                        </tr>
                        <tr>
                            <td colspan="2"><g:submitButton name="submit" value="Update" class="btn btn-default" style="width: 100%"/>
                            </td>
                        </tr>
                    </table>
                </g:form>
            </div>    <!-- panel-body -->
        </div>  <!-- panel -->


        </div> <!-- col-lg-7 -->

    </div><!-- /.row -->
</div>

</body>
</html>