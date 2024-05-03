<%@ page import="java.text.NumberFormat" %>
<%@ page import="com.mybuy.model.Auction" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Locale" %>
<%@ page import="com.mybuy.model.Item" %>
<%@ page import="com.mybuy.model.Bid" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Auction Details</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/stylesheets/auction_page.css">
</head>
<body>
<header>
    <nav class="navbar" style="background-color: #24243e;" data-bs-theme="dark">
        <div class="container-fluid">
            <span class="navbar-brand mb-0 h1">MyBuy</span>
            <ul class="navbar-nav">
                <li class="nav-item">
                    <a class="nav-link active" aria-current="page" href="${pageContext.request.contextPath}/login">Home</a>
                </li>
            </ul>
        </div>
    </nav>
</header>

<% NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(); %>
<% SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm a"); %>
<% SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM dd, yyyy", Locale.US); %>
<% Auction auction = (Auction) request.getAttribute("auction"); %>
<div class="container">
    <div class="auction-card">
        <h1>Auction #${auction.auctionId}</h1>
        <div class="auction-details">
            <p><span>Status:</span> <%= auction.getStatus().substring(0, 1).toUpperCase() + auction.getStatus().substring(1)%></p>
            <% if(auction.getStatus().equals("completed")) {%>
            <p><span>Winner:</span> <%= auction.getWinnerUsername()%></p>
            <% } %>
            <p><span>Item:</span> ${auction.getItem().getColor()} ${auction.getItem().getBrand()} ${auction.getItem().getName()}</p>
            <p><span>Initial Price:</span> <%= currencyFormat.format(auction.getInitialPrice())%></p>
            <p><span>Bid Increment:</span> <%= currencyFormat.format(auction.getBidIncrement())%></p>
            <% if(auction.getStatus().equals("completed")) {%>
            <p><span>Selling Price:</span> <%= currencyFormat.format(auction.getCurrentPrice()) %></p>
            <% } else {%>
            <p><span>Current Highest Bid:</span> <%= currencyFormat.format(auction.getCurrentPrice()) %></p>
            <% } %>
            <p><span>Closing Date:</span> <%= dateFormat.format(auction.getAuctionClosingDate()) %></p>
            <p><span>Closing Time:</span> <%= timeFormat.format(auction.getAuctionClosingTime()) %> </p>
        </div>
    </div>
</div>


<div class="row">
    <div class="col align-self-center">

    </div>
</div>

<div class="bid-history">
    <% List<Bid> bids = (List<Bid>) request.getAttribute("bids"); %>
    <% if(!bids.isEmpty()) {%>
        <h1>Bid History</h1>
        <% for(Bid bid: bids) {%>
            <div class="row">
                <div class="col bid-col">
                    <div class="card">
                        <div class="card-body">
                            <p class="card-text">Bid Amount: <%= currencyFormat.format(bid.getBidAmount())%></p>
                            <p class="card-text">Bidder Username: <%= bid.getUsername()%></p>
                            <p class="card-text">Bid Date: <%= dateFormat.format(bid.getBidDate()) %></p>
                            <p class="card-text">Bid Time: <%= timeFormat.format(bid.getBidTime()) %> </p>
                        </div>
                    </div>
                </div>
            </div>
        <% } %>
    <% } else {%>
        <h1>No bid history for this auction</h1>
    <% } %>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js" integrity="sha384-I7E8VVD/ismYTF4hNIPjVp/Zjvgyol6VFvRkX/vR+Vc4jQkC+hVqc2pM8ODewa9r" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.min.js" integrity="sha384-0pUGZvbkm6XF6gxjEnlmuGrJXVbNuzT9qBBavbLwCsOGabYfZo0T0to5eqruptLy" crossorigin="anonymous"></script>
</body>
</html>