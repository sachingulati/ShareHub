<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title> </title>
    <!-- jquery -->

    %{--<script src="../assets/javascripts/jquery-2.1.3.min.js"></script>--}%
    %{--<script src="${resource(dir:"javascripts", "jquery-2.1.3.min.js")}"></script>--}%
    <asset:javascript src="jquery-2.1.3.min.js"/>
    <!-- Latest compiled and minified CSS -->
    <asset:stylesheet src="bootstrap.min.css"/>

    <!-- Optional theme -->
    <asset:stylesheet src="bootstrap-theme.min.css"/>

    <!-- Latest compiled and minified JavaScript -->
    <asset:javascript src="bootstrap.min.js"/>

    <!-- my css -->
    <asset:stylesheet src="mycss.css"/>


</head>
<body>

<nav class="navbar navbar-default">
    <div class="container-fluid">
        <!-- Brand and toggle get grouped for better mobile display -->
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="#">Share Hub</a>
        </div>

        <!-- Collect the nav links, forms, and other content for toggling -->
        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
            <ul class="nav navbar-nav navbar-right">
                <li>
                    <g:form controller="login" action="validate" class="navbar-form navbar-left" role="search">
                        <div class="form-group">
                            ${error}
                            <g:textField name="username" class="form-control" placeholder="Username"/>
                            <g:passwordField name="password" class="form-control" placeholder="Password"/>
                        </div>
                        <g:submitButton name="Login" class="btn btn-default">Login</g:submitButton>
                    </g:form>

                </li>
            </ul>
        </div><!-- /.navbar-collapse -->
    </div><!-- /.container-fluid -->
</nav>

<div class="contentMargin">
    <div class="row">
        <div class ="col-lg-7">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title">Recent Posts</h3>
                </div>
                <div class="panel-body contentBox">
                    <% 4.times { %>
                    <div class="panel panel-default" style="margin-bottom:7px">
                        <div class="panel-body">
                            <div style="float:left">
                                <a href="#" id="recentShareUserImage"><asset:image src="user-default.png" class="img-media" alt="User image"/></a>
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
                                <a href="#" id="topPostUserImage"><asset:image src="user-default.png" class="img-media" alt="User image"/></a>
                            </div>
                            <div class="padding5">
                                <span style="float:right;"><a href="#" id="recentShareTopicName3">Topic Name</a></span>
                                <div>
                                    <a id="recentShareUserName3" href="#" id="recentShareUserName3">Firstname Lastname</a>
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
                        <div class="row padding5">
                            <div class = "col-lg-5">
                                <span class="valignstyle">First Name*</span>
                            </div>
                            <div class = "col-lg-7">
                                <input type="text" id="txtFirstName" class="form-control" placeholder=""/>
                            </div>
                        </div>
                        <div class="row padding5">
                            <div class = "col-lg-5">
                                Last Name*
                            </div>
                            <div class = "col-lg-7">
                                <input type="text" id="txtLastName" class="form-control" placeholder=""/>
                            </div>
                        </div>
                        <div class="row padding5">
                            <div class = "col-lg-5">
                                Email*
                            </div>
                            <div class = "col-lg-7">
                                <input type="text" id="txtEmail" class="form-control" placeholder=""/>
                            </div>
                        </div>
                        <div class="row padding5">
                            <div class = "col-lg-5">
                                Username*
                            </div>
                            <div class = "col-lg-7">
                                <input type="text" id="txtUsername" class="form-control" placeholder=""/>
                            </div>
                        </div>
                        <div class="row padding5">
                            <div class = "col-lg-5">
                                Password*
                            </div>
                            <div class = "col-lg-7">
                                <input type="text" id="txtPassword" class="form-control" placeholder=""/>
                            </div>
                        </div>
                        <div class="row padding5">
                            <div class = "col-lg-5">
                                Confirm Password*
                            </div>
                            <div class = "col-lg-7">
                                <input type="text" id="txtConfirmPassword" class="form-control" placeholder=""/>
                            </div>
                        </div>
                        <div class="row padding5">
                            <div class = "col-lg-5">
                                Photo
                            </div>
                            <div class = "col-lg-7">
                                <input type="file" id="photo" class="padding5"/>
                            </div>
                        </div>
                        <div class="row padding5">
                            <div class = "col-lg-5">
                                <div>Captcha</div>
                                <asset:image src="captcha.jpg" id="captcha"/>
                            </div>
                            <div class = "col-lg-7">
                                <div>Enter text shown in image:</div>
                                <div><input type="text" class="form-control"/></div>
                                <div style="margin-top:20px;" width="100%" align="center"><input type="submit" class="btn btn-default" style="width:100%" value="Click here to register" id="submit"/></div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div><!-- /.col-lg-6 -->
    </div><!-- /.row -->
</div>
</body>
</html>
