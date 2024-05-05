<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Item Alerts Setup</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <link href="https://cdn.jsdelivr.net/npm/remixicon@4.1.0/fonts/remixicon.css" rel="stylesheet"/>
    <style>
        body, html {
            margin: 0;
            padding: 0;
            min-height: 100%;
            width: 100%;
            display: flex;
            flex-direction: column;
        }
        body {
            font-family: 'Jost', sans-serif;
            background: linear-gradient(to bottom, #0f0c29, #302b63, #24243e);
        }
        .navbar {
            background-color: #24243e;
            width: 100%;
            margin-bottom: 160px;
        }
        .container {
            width: 60%;
            background-color: #fff;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            display: flex;
            flex-direction: column;
            align-items: center;
            margin: 10px auto 10px auto;
        }
        .form-control {
            width: 100%;
            height: 45px;
            font-size: 1.2em;
            background-color: #e0e0e0;
        }
        .btn-primary {
            background-color: #5c007a;
            border-color: #ffffff;
            width: 100%;
        }
        .btn-primary:hover {
            background-color: #472a57;
        }
        .title {
            color: #5c007a;
            font-family: 'Poppins', sans-serif;
            font-size: 3em;
            font-weight: bold;
            text-align: center;
        }
    </style>
</head>
<body>
<header>
    <nav class="navbar navbar-expand-lg navbar-dark">
        <div class="container-fluid">
            <a class="navbar-brand" href="#">MyBuy Alerts</a>
            <ul class="navbar-nav ml-auto">
                <li class="nav-item">
                    <a class="nav-link active" href="${pageContext.request.contextPath}/login">Home</a>
                </li>
            </ul>
        </div>
    </nav>
</header>
<div class="container">
    <h2 class="title">Set Item Alerts</h2>
    <form action="${pageContext.request.contextPath}/setItemAlert" method="post">
        <div class="form-group">
            <label for="itemName">Item Name:</label>
            <input type="text" class="form-control" id="itemName" name="itemName" placeholder="Enter item name">
        </div>
        <div class="form-group">
            <label for="itemBrand">Item Brand:</label>
            <input type="text" class="form-control" id="itemBrand" name="itemBrand" placeholder="Enter item brand">
        </div>
        <div class="form-group">
            <label for="categoryName">Category Name:</label>
            <input type="text" class="form-control" id="categoryName" name="categoryName" placeholder="Enter category name">
        </div>
        <div class="form-group">
            <label for="colorVariant">Color Variant:</label>
            <input type="text" class="form-control" id="colorVariant" name="colorVariant" placeholder="Enter color variant">
        </div>
        <button type="submit" class="btn btn-primary">Set Alert</button>
    </form>
</div>

<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.2/dist/js/bootstrap.bundle.min.js"></script>
<script>
    document.querySelector('form').addEventListener('submit', function(event) {
        event.preventDefault(); // Prevent default form submission
        const itemName = document.getElementById('itemName').value.trim();
        const itemBrand = document.getElementById('itemBrand').value.trim();
        const categoryName = document.getElementById('categoryName').value.trim();
        const colorVariant = document.getElementById('colorVariant').value.trim();

        if (!itemName && !itemBrand && !categoryName && !colorVariant) {
            alert('Please enter at least one detail for the alert.');
        } else {
            var xhr = new XMLHttpRequest();
            xhr.open('POST', '${pageContext.request.contextPath}/setItemAlert', true);
            xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
            xhr.onload = function() {
                if (xhr.status === 200) {
                    alert('Your alert request has been saved. We will notify you when items matching your criteria appear.');
                } else {
                    alert('There was an error processing your request. Please try again.');
                    console.error('Failed to save the alert:', xhr.responseText);
                }
            };
            const params = 'itemName=' + encodeURIComponent(itemName) + '&itemBrand=' + encodeURIComponent(itemBrand) +
                '&categoryName=' + encodeURIComponent(categoryName) + '&colorVariant=' + encodeURIComponent(colorVariant);
            xhr.send(params);
        }
    });
</script>
</body>
</html>
