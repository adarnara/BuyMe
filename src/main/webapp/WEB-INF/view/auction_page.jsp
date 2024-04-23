<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Auction Details</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/stylesheets/auction_page.css">
</head>
<body>
<div class="container">
    <h1>Auction Details</h1>
    <div class="auction-details">
        <p><span>Auction ID:</span> ${auction.auctionId}</p>
        <p><span>Current Price:</span> ${auction.currentPrice}</p>
        <p><span>Closing Date:</span> ${auction.auctionClosingDate}</p>
        <p><span>Closing Time:</span> ${auction.auctionClosingTime}</p>
        <!-- Add more auction details as needed -->
    </div>
</div>
</body>
</html>