<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Login</title>
    <meta name="layout" content="beforeLogin">
    <asset:javascript src="registration.js"/>
</head>

<body>

<div class="contentMargin">
    <div class="row">
        <div class="col-lg-7">
            <g:render template="/resource/resourceList" bean="${recentResources}" var="resources" model="[header:'Recent Posts', search:false]"/>
            <g:render template="/resource/resourceList" bean="${recentResources}" var="resources" model="[header:'Top Posts', search:false]"/>
        </div> <!-- col-lg-7 -->
        <div class="col-lg-5">
            <div>
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <h3 class="panel-title" align="center">Register here for new account</h3>
                    </div>

                    <div class="panel-body contentBox">

                        <g:form name="registration" class="validation" controller="login" action="register" enctype="multipart/form-data">
                            <div class="row padding5">
                                <div class="col-lg-5">
                                    <label for="firstName" class="control-label label-form">
                                        First Name*
                                    </label>
                                </div>
                                <div class="col-lg-7">
                                    <g:textField name="firstName" class="form-control"/>
                                </div>
                            </div>

                            <div class="row padding5">
                                <div class="col-lg-5">
                                    Last Name*
                                </div>

                                <div class="col-lg-7">
                                    <g:textField name="lastName" class="form-control"/>
                                </div>
                            </div>

                            <div class="row padding5">
                                <div class="col-lg-5">
                                    Email*
                                </div>

                                <div class="col-lg-7">
                                    <g:textField name="email" class="form-control"/>
                                </div>
                            </div>

                            <div class="row padding5">
                                <div class="col-lg-5">
                                    Username*
                                </div>

                                <div class="col-lg-7">
                                    <g:textField name="username" class="form-control"/>
                                </div>
                            </div>

                            <div class="row padding5">
                                <div class="col-lg-5">
                                    Password*
                                </div>

                                <div class="col-lg-7">
                                    <g:passwordField name="password" class="form-control"/>
                                </div>
                            </div>

                            <div class="row padding5">
                                <div class="col-lg-5">
                                    Confirm Password*
                                </div>

                                <div class="col-lg-7">
                                    <g:passwordField name="confirmPassword" class="form-control"/>
                                </div>
                            </div>

                            <div class="row padding5">
                                <div class="col-lg-5">
                                    Photo
                                </div>

                                <div class="col-lg-7">
                                    <input type="file" accept="image/*" name="photo" id="photo" class="padding5"/>
                                </div>
                            </div>

                            <div class="row padding5">
                                <div class="col-lg-5">
                                    <div>Captcha</div>
                                    <asset:image src="captcha.jpg" id="captcha"/>
                                </div>

                                <div class="col-lg-7">
                                    <div>Enter text shown in image:</div>

                                    <div>
                                        <g:textField name="captcha" class="form-control"/></div>

                                    <div style="margin-top:20px;" width="100%" align="center">
                                        <g:submitButton name="submit" value="Click here to register"
                                                        class="btn btn-default" style="width: 100%"/>
                                    </div>
                                </div>
                            </div>
                        </g:form>

                    </div>
                </div>
            </div>
        </div><!-- /.col-lg-6 -->
    </div><!-- /.row -->
</div>
</body>
</html>
