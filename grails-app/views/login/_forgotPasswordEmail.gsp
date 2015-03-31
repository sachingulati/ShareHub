<div style="border: 2px solid; border-radius: 10px; padding: 10px;">
    <span style="float: left; margin-right:10px; margin-bottom: 15px; margin-top: 10px">
        <sh:image src="${token.user.photoUrl}" />
    </span>
    <div>
        Hello ${token.user.name},<br>
        Somebody recently asked to reset your Share Hub password.<br><br>

        <a href="${createLink(controller: "login", action: "changePassword", absolute: true, params: [token: token.token])}">click here to change your password.</a><br><br>
        <label>Didn't Request this change?</label><br>
        If you didn't request a new password, <a href="${createLink(controller: "login", action: "deleteToken", params: [token: token.token], absolute: true)}">let us know immediately.</a><br>
    </div>
</div>