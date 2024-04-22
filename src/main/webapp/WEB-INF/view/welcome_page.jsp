<%@ page import="com.mybuy.model.Auction" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Welcome Page</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/stylesheets/welcome_style.css">
</head>
<body>
<%
    String userName = (String) session.getAttribute("username");
    if (userName == null) {
        response.sendRedirect("index.jsp");
        return;
    }
%>
<header>
    <a href="#" class="logo">Welcome, <%= userName %></a>
    <ul>
        <li><a href="#" class="active">Home</a></li>
        <li><a href="#">Alerts</a></li>
        <li><a href="#">Contact</a></li>
        <li><a href="#" onclick="document.getElementById('logout-form').submit(); return false;">Logout</a></li>
    </ul>
</header>
<form id="logout-form" action="${pageContext.request.contextPath}/logout" method="post" style="display: none;">
    <input type="hidden" name="logout" value="true">
</form>
<section>
    <img src="${pageContext.request.contextPath}/Images/stars.png" id="stars">
    <img src="${pageContext.request.contextPath}/Images/moon.png" id="moon">
    <img src="${pageContext.request.contextPath}/Images/mountains_behind.png" id="mountains_behind">
    <p id="main-message">Click below to explore</p>
    <a href="#sec" id="btn">Explore</a>
    <img src="${pageContext.request.contextPath}/Images/mountains_front.png" id="mountains_front">
</section>
<div class="sec" id="sec">
    <% if(request.getAttribute("userType").equals("buyer")) { %>
        <h2>Buyer Section</h2>
        <p>
            This is content for buyers.
        </p>
    <% } else { %>
        <h2><%= userName %>'s Auctions</h2>
        <div class="row row-cols-1 row-cols-md-2 g-4">
        <% List<Auction> auctions = (List<Auction>) request.getAttribute("auctions"); %>

        <% if (auctions != null && !auctions.isEmpty()) { %>
        <% for (Auction auction : auctions) { %>
            <div class="col">
                <div class="card">
                    <div class="card-body">
                        <h5 class="card-title">Auction #<%= auction.getAuctionId() %></h5>
                        <p class="card-text">Current price: <%= auction.getCurrentPrice() %></p>
                    </div>
                </div>
            </div>
        <% } %>
        <% } %>
        </div>
    <% } %>
</div>

<script>
    let stars = document.getElementById('stars');
    let moon = document.getElementById('moon');
    let mountains_behind = document.getElementById('mountains_behind');
    let mountains_front = document.getElementById('mountains_front');
    let btn = document.getElementById('btn');
    let header = document.querySelector('header');
    let main_message = document.getElementById('main-message');

    window.addEventListener('scroll', function (){
        let value = window.scrollY;
        stars.style.left = value * 0.25 + 'px';
        moon.style.top = value * 1.05 + 'px';
        mountains_behind.style.top = value * 0.5 + 'px';
        mountains_front.style.top = value * 0 + 'px';
        btn.style.marginTop = value * 1.5 + 'px';
        header.style.top = value * 0.5 + 'px';
        main_message.style.marginRight = value * 4 + 'px';
        main_message.style.marginTop = value * 1.5 + 'px';
    });

</script>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js" integrity="sha384-I7E8VVD/ismYTF4hNIPjVp/Zjvgyol6VFvRkX/vR+Vc4jQkC+hVqc2pM8ODewa9r" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.min.js" integrity="sha384-0pUGZvbkm6XF6gxjEnlmuGrJXVbNuzT9qBBavbLwCsOGabYfZo0T0to5eqruptLy" crossorigin="anonymous"></script>

</body>
</html>
