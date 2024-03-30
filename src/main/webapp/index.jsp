<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    if ("POST".equalsIgnoreCase(request.getMethod())) {
        request.getRequestDispatcher("/WEB-INF/view/loginAndRegister.jsp").forward(request, response);
        return;
    }
%>
<!DOCTYPE html>
<html>
<head>
    <title>Welcome to MyBuy</title>
    <style>
        body {
            margin: 0;
            padding: 0;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            font-family: 'Jost', sans-serif;
            background: linear-gradient(to bottom, #0f0c29, #302b63, #24243e);
            color: #fff;
        }
        .buyMe {
            font-size: 72px;
            cursor: pointer;
            transition: transform 0.2s;
            background: none;
            border: none;
            color: #fff;
        }
        .buyMe:hover {
            transform: scale(1.1);
        }
    </style>
    <link href="https://fonts.googleapis.com/css2?family=Jost:wght@500&display=swap" rel="stylesheet">
</head>
<body>
<form method="post">
    <button type="submit" class="buyMe">BuyMe</button>
</form>
</body>
</html>
