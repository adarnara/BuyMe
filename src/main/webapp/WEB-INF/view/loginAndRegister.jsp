<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Welcome to MyBuy</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/stylesheets/loginAndRegisterStyle.css">
    <link href='https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css' rel='stylesheet'>
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
            <div class="input-wrapper">
                <input type="password" name="password" id="reg-password" placeholder="Password" required="">
                <i class='bx bx-show bx-register' onclick="togglePasswordVisibility('reg-password', this)" id="toggleRegPassword"></i>
            </div>
            <!-- <input type="password" name="password" placeholder="Password" required=""> -->
            <button type="submit">Sign up</button>
        </form>
    </div>

    <div class="login">
        <form action="login" method="post">
            <label for="chk" aria-hidden="true">Login</label>
            <input type="text" id="usernameOrEmail" name="usernameOrEmail" placeholder="Email or Username" required="">
            <div class="input-wrapper">
                <input type="password" name="password" id="login-password" placeholder="Password" required="">
                <i class='bx bx-show bx-login' onclick="togglePasswordVisibility('login-password', this)" id="toggleLoginPassword"></i>
            </div>
            <!-- <input type="password" name="password" placeholder="Password" required=""> -->
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

    function togglePasswordVisibility(passwordInputId, toggleIcon) {
        let input = document.getElementById(passwordInputId);
        let isRegister = toggleIcon.classList.contains('bx-register');
        let isLogin = toggleIcon.classList.contains('bx-login');
        if (input.type === "password") {
            input.type = "text";
            toggleIcon.classList.replace('bx-show', 'bx-hide');
        } else {
            input.type = "password";
            toggleIcon.classList.replace('bx-hide', 'bx-show');
        }
        if (isRegister) {
            toggleIcon.classList.add('bx-register');
        }
        if (isLogin) {
            toggleIcon.classList.add('bx-login');
        }
    }

    document.addEventListener('DOMContentLoaded', function() {
        let registrationMessage = '<%= request.getAttribute("registrationMessage") %>';
        let loginMessage = '<%= request.getAttribute("loginMessage") %>';
        let registerButton = document.querySelector('.signup button[type="submit"]');
        let loginButton = document.querySelector('.login button[type="submit"]');

        if (registrationMessage && registrationMessage !== 'null' && registrationMessage !== '') {
            registerButton.textContent = registrationMessage;
            setTimeout(function() {
                registerButton.textContent = 'Sign up';
            }, 1000);
        }

        if (loginMessage && loginMessage !== 'null' && loginMessage !== '') {
            registerButton.textContent = loginMessage;
            setTimeout(function() {
                registerButton.textContent = 'Sign up';
            }, 1000);
        }
    });

</script>
</body>
</html>
