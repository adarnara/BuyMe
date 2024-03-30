<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Welcome to MyBuy</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/stylesheets/loginAndRegisterStyle.css">
    <link href="https://fonts.googleapis.com/css2?family=Jost:wght@500&display=swap" rel="stylesheet">
</head>
<body>
<div class="main">
    <input type="checkbox" id="chk" aria-hidden="true">

    <div class="signup">
        <form action="register" method="post">
            <label for="chk" aria-hidden="true">Register</label>
            <input type="text" name="username" placeholder="User name" required="">
            <input type="email" name="email" placeholder="Email" required="">
            <input type="password" name="password" placeholder="Password" required="">
            <button type="submit">Sign up</button>
        </form>
    </div>

    <div class="login">
        <form action="login" method="post">
            <label for="chk" aria-hidden="true">Login</label>
            <input type="text" id="usernameOrEmail" name="usernameOrEmail" placeholder="Email or Username" required="">
            <input type="password" name="password" placeholder="Password" required="">
            <button type="submit">Login</button>
        </form>
    </div>
</div>

<script>
    function validateEmailOrUsername() {
        let inputField = document.getElementById('usernameOrEmail');
        let value = inputField.value;
        let emailPattern = /^[a-zA-Z0-9_\.\-]+@[a-zA-Z0-9\-]+\.[a-zA-Z]{2,}$/i;

        if (emailPattern.test(value)) {
            inputField.setAttribute('type', 'email');
        } else {
            inputField.setAttribute('type', 'text');
        }
    }

    document.addEventListener('DOMContentLoaded', function() {
        var registrationMessage = '<%= request.getAttribute("registrationMessage") %>';
        var loginMessage = '<%= request.getAttribute("loginMessage") %>';
        var registerButton = document.querySelector('.signup button[type="submit"]');
        var loginButton = document.querySelector('.login button[type="submit"]');

        if (registrationMessage) {
            registerButton.textContent = registrationMessage;
            setTimeout(function() {
                registerButton.textContent = 'Sign up';
            }, 1000);
        }

        if (loginMessage) {
            registerButton.textContent = loginMessage;
            setTimeout(function() {
                loginButton.textContent = 'Login';
            }, 1000);
        }
    });

</script>
</body>
</html>
