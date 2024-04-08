<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Welcome Page</title>
    <style>
        body {
            margin: 0;
            padding: 0;
            display: flex;
            justify-content: center;
            align-items: center;
            flex-direction: column;
            height: 100vh;
            font-family: 'Jost', sans-serif;
            background: linear-gradient(to bottom, #0f0c29, #302b63, #24243e);
            color: #fff;
        }
        .landingPageTitle {
            font-size: 90px;
            cursor: default;
            margin: 0;
        }
        .logoutButton {
            position: absolute;
            top: 20px;
            right: 20px;
            font-size: 18px;
            cursor: pointer;
            background: none;
            border: 1px solid #fff;
            color: #fff;
            padding: 10px 20px;
            text-decoration: none;
            transition: transform 0.3s ease;
        }
        .logoutButton:hover {
            transform: scale(1.1);
            background-color: #342c63;
        }
    </style>
</head>
<body>
<%
    String userName = (String) session.getAttribute("username");
    if (userName == null) {
        response.sendRedirect(request.getContextPath() + "/index.jsp");
        return;
    }
%>
<div class="landingPageTitle">Welcome, <%= userName %></div>
<form method="POST" action="<%= request.getContextPath() + "/logout" %>">
    <input type="hidden" name="logout" value="true"/>
    <button type="submit" class="logoutButton">Logout</button>
</form>
</body>
</html>