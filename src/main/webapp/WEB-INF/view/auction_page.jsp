<%@ page import="java.text.NumberFormat" %>
<%@ page import="com.mybuy.model.Auction" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Locale" %>
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
            <% if(auction.getWinner() != 0) {%>
            <p class="card-text"><span>Winner:</span> <%= auction.getWinnerUsername() %></p>
            <% } else {%>
            <p class="card-text"><span>No winner</span></p>
            <% }%>
            <% } %>
            <p><span>Item:</span> ${auction.getItem().getColor()} ${auction.getItem().getBrand()} ${auction.getItem().getName()}</p>
            <p><span>Initial Price:</span> <%= currencyFormat.format(auction.getInitialPrice())%></p>
            <p id="auctionBidIncrement"><span>Bid Increment:</span> <%= currencyFormat.format(auction.getBidIncrement())%></p>
            <% if(auction.getStatus().equals("completed")) {%>
            <p><span>Selling Price:</span> <%= currencyFormat.format(auction.getCurrentPrice()) %></p>
            <% } else {%>
            <p id="currentPrice"><span>Current Price:</span> <%= currencyFormat.format(auction.getCurrentPrice()) %></p>
            <% } %>
            <p><span>Closing Date:</span> <%= dateFormat.format(auction.getAuctionClosingDate()) %></p>
            <p><span>Closing Time:</span> <%= timeFormat.format(auction.getAuctionClosingTime()) %> </p>
        </div>
    </div>

    <!-- Button for new bid modal -->
    <% if(request.getAttribute("endUserType").equals("buyer") && auction.getStatus().equals("active")) { %>
        <button type="button" class="btn btn-outline-light new-bid-btn" data-bs-toggle="modal" data-bs-target="#new-bid-modal">Create New Bid</button>
    <% } %>

    <!-- New bid modal -->
    <div class="modal fade" id="new-bid-modal" tabindex="-1" aria-labelledby="modal-title" aria-hidden="true">
        <div class="modal-dialog modal-dialog-scrollable">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title">Create a New Bid</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <form id="new-bid-form" method="post">
                        <div class="mb-3">
                            <input type="radio" class="btn-check" name="options-base" id="newBid" autocomplete="off" checked>
                            <label class="btn" for="newBid">Bid</label>
                        </div>

                        <div class="mb-3">
                            <input type="radio" class="btn-check" name="options-base" id="newAutoBid" autocomplete="off">
                            <label class="btn" for="newAutoBid">Autobid</label>
                        </div>

                        <div class="form-floating mb-3">
                            <input type="text" class="form-control" id="bidAmount" name="bidAmount">
                            <label for="bidAmount">Bid amount</label>
                            <div class="error"></div>
                        </div>

                        <div class="form-floating mb-3">
                            <input type="text" class="form-control" id="maxBidAmount" name="maxBidAmount" disabled>
                            <label for="maxBidAmount">Max bid amount</label>
                            <div class="error"></div>
                        </div>

                        <div class="form-floating mb-3">
                            <input type="text" class="form-control" id="bidIncrement" disabled>
                            <label for="bidIncrement">Bid increment (optional)</label>
                            <div class="error"></div>
                        </div>
                        <input type="hidden" name="auctionId" value="<%= auction.getAuctionId() %>">
                        <input type="hidden" id="bidType" name="bidType" value="bid">
                        <input type="hidden" id="hiddenBidIncrement" name="bidIncrement" value="<%= auction.getBidIncrement() %>">

                        <div class="cancelOrSubmit">
                            <button type="button" class="btn btn-outline-secondary bid-form-btn" data-bs-dismiss="modal">Cancel</button>
                            <button type="submit" class="btn btn-outline-primary bid-form-btn">Submit</button>
                        </div>
                    </form>
                </div>
            </div>
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
                        <p class="card-text"><span>Bid Amount:</span> <%= currencyFormat.format(bid.getBidAmount())%></p>
                        <p class="card-text"><span>Bidder Username:</span> <%= bid.getUsername()%></p>
                        <p class="card-text"><span>Bid Date:</span> <%= dateFormat.format(bid.getBidDate()) %></p>
                        <p class="card-text"><span>Bid Time:</span> <%= timeFormat.format(bid.getBidTime()) %> </p>
                    </div>
                </div>
            </div>
        </div>
        <% } %>
        <% } else {%>
        <h1>No bid history for this auction</h1>
        <% } %>
    </div>

    <div class="similar-items">
        <% List<Auction> similarAuctions = (List<Auction>) request.getAttribute("similarAuctions");%>
        <% if(!similarAuctions.isEmpty()) {%>
            <h1>Similar Items for Auction</h1>
            <% for(Auction simAuction : similarAuctions) {%>
                <div class="row">
                    <div class="col bid-col">
                        <div class="card">
                            <div class="card-body">
                                <a href="${pageContext.request.contextPath}/auction/<%= simAuction.getAuctionId() %>" class="auction-link">
                                    <h5 class="card-title">Auction #<%= simAuction.getAuctionId() %></h5>
                                </a>
                                <p><span>Item:</span> <%= simAuction.getItem().getColor()%> <%= simAuction.getItem().getBrand()%> <%= simAuction.getItem().getName()%></p>
                                <p><span>Initial Price:</span> <%= currencyFormat.format(simAuction.getInitialPrice())%></p>
                                <p><span>Bid Increment:</span> <%= currencyFormat.format(simAuction.getBidIncrement())%></p>
                                <p><span>Current Price:</span> <%= currencyFormat.format(simAuction.getCurrentPrice()) %></p>
                                <p><span>Closing Date:</span> <%= dateFormat.format(simAuction.getAuctionClosingDate()) %></p>
                                <p><span>Closing Time:</span> <%= timeFormat.format(simAuction.getAuctionClosingTime()) %> </p>
                            </div>
                        </div>
                    </div>
                </div>
            <% } %>
        <% } else {%>
            <h1>No similar items for auction</h1>
        <% } %>
    </div>
</div>

<script>
    const checkForAlert = () => {
        fetch(`${pageContext.request.contextPath}/alert`)
            .then(response => response.json())
            .then(data => {
                if (data && data.alertID) {
                    const message = data.message;

                    showAlert(message);
                }
            })
            .catch(error => {
                console.error('Error fetching alert:', error);
            });
    }

    checkForAlert();

    setInterval(checkForAlert, 30000); // 30000 milliseconds = 30 seconds

    const showAlert = (message) => {
        alert(message);
    }
</script>
<script src="${pageContext.request.contextPath}/js/auction_page_script.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js" integrity="sha384-I7E8VVD/ismYTF4hNIPjVp/Zjvgyol6VFvRkX/vR+Vc4jQkC+hVqc2pM8ODewa9r" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.min.js" integrity="sha384-0pUGZvbkm6XF6gxjEnlmuGrJXVbNuzT9qBBavbLwCsOGabYfZo0T0to5eqruptLy" crossorigin="anonymous"></script>
</body>
</html>