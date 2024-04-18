<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Welcome to MyBuy</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/stylesheets/loginAndRegisterStyle.css">
    <link href='https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css' rel='stylesheet'>
    <link href="https://fonts.googleapis.com/css2?family=Jost:wght@500&display=swap" rel="stylesheet">
    <style>
        .modal {
            display: none;
            position: fixed;
            z-index: 1;
            left: 0;
            top: 0;
            width: 100%;
            height: 100%;
            overflow: auto;
            background-color: rgba(0,0,0,0.4);
            backdrop-filter: blur(10px);
        }
        .modal-content {
            background-color: rgba(255, 255, 255, 0.5);
            border-radius: 15px;
            overflow: hidden;
            backdrop-filter: blur(5px);
            position: fixed;
            top: 50%;
            left: 50%;
            transform: translate(-50%, -50%);
            padding: 20px;
            border: 1px solid rgba(255, 255, 255, 0.18);
            width: 30%;
            height: auto;
            box-sizing: border-box;
            box-shadow: 0 4px 8px 0 rgba(0,0,0,0.2);
            text-align: center;
        }
        .close {
            color: #aaa;
            float: right;
            font-size: 28px;
            font-weight: bold;
        }
        .close:hover, .close:focus {
            color: black;
            text-decoration: none;
            cursor: pointer;
        }
        .button {
            padding: 10px 20px;
            width: 100px;
            height: 100px;
            cursor: pointer;
            display: inline-block;
            margin: 0 5px;
        }

        .buyer {
            background-color: blue;
            color: white;
        }
        .seller {
            background-color: red;
            color: white;
        }
        .account-selection {
            display: flex;
            justify-content: space-evenly;
            align-items: center;
            flex-wrap: wrap;
        }
        p {
            margin-top: 0;
            margin-bottom: 1rem;
        }
        p.choice-note {
            color: red;
            font-style: italic;
            font-weight: bold;
            margin-top: 1rem;
        }
    </style>
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
            <button type="submit">Sign up</button>
        </form>
    </div>

    <div class="login">
        <form action="login" method="post">
            <label for="chk" aria-hidden="true">Login</label>
            <input type="text" id="usernameOrEmail" name="usernameOrEmail" placeholder="Email or Username" required="">
            <input oninput="validateEmailOrUsername()" type="password" name="password" id="login-password" placeholder="Password" required="">
            <i class='bx bx-show bx-login' onclick="togglePasswordVisibility('login-password', this)" id="toggleLoginPassword"></i>
            <button type="submit">Login</button>
        </form>
    </div>
</div>

<!-- Account Type Selection Modal -->
<div id="accountTypeModal" class="modal">
    <div class="modal-content">
        <span class="close">&times;</span>
        <p>Would you like to stick to the default account type of buyer, or would you like to change to a seller account type?</p>
        <p class="choice-note">You may only choose one!</p>
        <div class="account-selection">
            <button class="button buyer">Stick to buyer account</button>
            <button class="button seller">Switch to seller account</button>
        </div>
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
        if (input.type === "password") {
            input.type = "text";
            toggleIcon.classList.replace('bx-show', 'bx-hide');
        } else {
            input.type = "password";
            toggleIcon.classList.replace('bx-hide', 'bx-show');
        }
    }

    let modal = document.getElementById('accountTypeModal');
    let span = document.getElementsByClassName('close')[0];

    span.onclick = function() {
        modal.style.display = "none";
    }

    window.onclick = function(event) {
        if (event.target == modal) {
            modal.style.display = "none";
        }
    }

    document.querySelector('.signup button[type="submit"]').addEventListener('click', function(event) {
        event.preventDefault();
        modal.style.display = "block";
    });

    document.querySelector('.buyer').addEventListener('click', function() {
        updateUserType('buyer');
    });

    document.querySelector('.seller').addEventListener('click', function() {
        updateUserType('seller');
    });

    function updateUserType(type) {
        let form = document.querySelector('.signup form');
        let input = document.createElement('input');
        input.type = 'hidden';
        input.name = 'userType';
        input.value = type;
        form.appendChild(input);
        form.submit();
    }

    document.addEventListener('DOMContentLoaded', function() {
        let registrationMessage = '<%= session.getAttribute("registrationMessage") %>';
        <% session.removeAttribute("registrationMessage"); %>
        if (registrationMessage && registrationMessage !== 'null') {
            let registerButton = document.querySelector('.signup button[type="submit"]');
            registerButton.textContent = registrationMessage;

            setTimeout(function() {
                registerButton.textContent = 'Sign up';
            }, 1000);
        }
    });
</script>
</body>
</html>
