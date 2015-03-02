<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Login</title>
    <meta name="layout" content="beforeLogin">

</head>

<body>

<div class="contentMargin">
    <div class="row">
        <div class="col-lg-7">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title">Recent Posts</h3>
                </div>

                <div class="panel-body contentBox">
                    <% 4.times { %>
                    <div class="panel panel-default" style="margin-bottom:7px">
                        <div class="panel-body">
                            <div style="float:left">
                                <a href="#"><asset:image src="user-default.png" class="img-media" alt="User image"/></a>
                            </div>

                            <div class="padding5">
                                <span style="float:right;"><a href="#" id="recentShareTopicName">Topic Name</a></span>

                                <div>
                                    <a id="recentShareUserName" href="#" id="recentShareUserName">Firstname Lastname</a>
                                </div>

                                <div id="recentShareDesc">
                                    hi this is description of Recent Share 1. This is temporary description and will be replaced by actual description later on. so for the time being please co-operate :)
                                </div>

                                <div style="float:right">
                                    <a href="#" id="recentShareResource">View post</a>
                                </div>
                            </div>
                        </div> <!-- panel-body -->
                    </div>  <!-- panel -->
                <% } %>
                </div>    <!-- panel-body -->
            </div>  <!-- panel -->
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title">Top Posts</h3>
                </div>

                <div class="panel-body contentBox">
                    <div class="panel panel-default">
                        <div class="panel-body">
                            <div style="float:left">
                                <a href="#" id="topPostUserImage"><asset:image src="user-default.png" class="img-media"
                                                                               alt="User image"/></a>
                            </div>

                            <div class="padding5">
                                <span style="float:right;"><a href="#" id="recentShareTopicName3">Topic Name</a></span>

                                <div>
                                    <a id="recentShareUserName3" href="#"
                                       id="recentShareUserName3">Firstname Lastname</a>
                                </div>

                                <div id="topPostDesc">
                                    hi this is description of Top Post. this is temporary description and will be replaced by actual description later on. so for the time being please co-operate :)
                                </div>

                                <div style="float:right">
                                    <a href="#" id="recentShareResource3">View post</a>
                                </div>
                            </div>
                        </div> <!-- panel-body -->
                    </div>  <!-- panel -->
                </div>    <!-- panel-body -->
            </div>  <!-- panel -->
        </div> <!-- col-lg-7 -->
        <div class="col-lg-5">
            <div>
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <h3 class="panel-title" align="center">Register here for new account</h3>
                    </div>

                    <div class="panel-body contentBox">
                        <g:form controller="user" action="register">
                            <div class="row padding5">
                                <div class="col-lg-5">
                                    <span class="valignstyle">First Name*</span>
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
                                    <input type="file" id="photo" class="padding5"/>
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
