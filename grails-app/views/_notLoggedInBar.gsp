<!-- Collect the nav links, forms, and other content for toggling -->
<div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
    <ul class="nav navbar-nav navbar-right">
        <li style="padding-top: 20px; color: #ff0000">
            ${flash.message}
        </li>
        <li>
            <g:form controller="login" action="loginHandler" class="navbar-form navbar-left" role="search">
                <div class="form-group">
                    <g:textField name="username" class="form-control" placeholder="Username"/>
                    <g:passwordField name="password" class="form-control" placeholder="Password"/>
                </div>
                <g:submitButton name="Login" class="btn btn-default">Login</g:submitButton>
                <div class="row">
                    <div class="col-lg-5">
                        <g:checkBox name="keepMeLogin"/> Keep me logged in
                    </div>
                    <div class="col-lg-7">
                        <g:link controller="login" action="forgotPassword">Forgot Password?</g:link>
                    </div>
                </div>
            </g:form>
        </li>
    </ul>
</div><!-- /.navbar-collapse -->
