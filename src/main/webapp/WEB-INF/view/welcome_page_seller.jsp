<%@ page import="com.mybuy.model.Auction" %>
<%@ page import="java.util.List" %>
<%@ page import="java.text.NumberFormat" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Locale" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <meta charset="UTF-8">
  <title>Welcome Page</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
  <link href="https://cdn.jsdelivr.net/npm/remixicon@4.1.0/fonts/remixicon.css" rel="stylesheet"/>
  <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/stylesheets/welcome_style_seller.css">
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
  <div class="welcome-container">
    <a href="#" class="logo">Welcome, <%= userName %></a>
    <p class="account-info"><span class="account-type-label">Account type:</span> Seller</p>
  </div>
  <ul>
    <li><a href="#" class="active">Home</a></li>
    <li><a href="#">Create Auction</a></li>
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
  <div class="header-search-container">
    <h2><%= userName %>'s Active Auctions</h2>
    <div class="search-container">
      <input type="text" id="search-input" class="search-input" placeholder="Search auctions" />
      <a href="#" class="search-icon" id="search-icon"><i class="ri-search-line"></i></a>
    </div>
  </div>

  <!-- Auction cards -->
  <% NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(); %>
  <% SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm a"); %>
  <% SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM dd, yyyy", Locale.US); %>
  <div class="row row-cols-1 row-cols-md-2 g-4 auction-grid">
    <% List<Auction> auctions = (List<Auction>) request.getAttribute("auctions"); %>

    <% if (auctions != null && !auctions.isEmpty()) { %>
      <% for (Auction auction : auctions) { %>
        <% if(auction.getStatus().equals("active")) { %>
          <div class="col">
            <div class="card">
              <div class="card-body">
                <a href="${pageContext.request.contextPath}/auction/<%= auction.getAuctionId() %>" class="auction-link">
                  <h5 class="card-title">Auction #<%=auction.getAuctionId()%></h5>
                </a>
                <p class="card-text">Item: <%= auction.getItem().getColor()%> <%= auction.getItem().getBrand()%> <%= auction.getItem().getName()%></p>
                <p class="card-text">Current price: <%=currencyFormat.format(auction.getCurrentPrice())%></p>
                <p class="card-text">Closing Date: <%= dateFormat.format(auction.getAuctionClosingDate()) %></p>
                <p class="card-text">Closing Time: <%= timeFormat.format(auction.getAuctionClosingTime()) %> </p>
              </div>
            </div>
          </div>
        <% } %>
      <% } %>
    <% } %>
  </div>

  <!-- Button to open new auction modal -->
  <button type="button" class="btn btn-outline-light" data-bs-toggle="modal" data-bs-target="#auction-modal">Create New Auction</button>

  <!-- New auction modal -->
  <div class="modal fade" id="auction-modal" tabindex="-1" aria-labelledby="modal-title" aria-hidden="true">
    <div class="modal-dialog modal-dialog-scrollable">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title">Create a New Auction</h5>
          <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
        </div>
        <div class="modal-body">
          <form id="new-auction-form" method="post">
            <select class="form-select mb-3" aria-label="Select category" id="itemCategory" name="itemCategory" required>
              <option selected>Item category</option>e
              <option value="Laptop">Laptop</option>
              <option value="Tablet">Tablet</option>
              <option value="Desktop">Desktop</option>
            </select>
            <select class="form-select mb-3" aria-label="Select brand" id="itemBrand" name="itemBrand" required disabled>
              <option selected>Item brand</option>
              <option value="Apple">Apple</option>
              <option value="Dell">Dell</option>
              <option value="Lenovo">Lenovo</option>
            </select>
            <select class="form-select mb-3" aria-label="Select name" id="itemName" name="itemName" required disabled>
              <option selected>Item name</option>
            </select>
            <select class="form-select mb-3" aria-label="Select color" id="itemColor" name="itemColor" required>
              <option selected>Item color</option>
              <option value="Black">Black</option>
              <option value="Silver">Silver</option>
              <option value="White">White</option>
            </select>
            <div class="form-floating mb-3">
              <input type="text" class="form-control" id="initialPrice" placeholder="10" name="initialPrice" required>
              <label for="initialPrice">Initial price</label>
              <div class="error"></div>
            </div>
            <div class="form-floating mb-3">
              <input type="text" class="form-control" id="bidIncrement" placeholder="5" name="bidIncrement" required>
              <label for="bidIncrement">Bid increment</label>
              <div class="error"></div>
            </div>
            <div class="form-floating mb-3">
              <input type="text" class="form-control" id="minimumPrice" placeholder="5" name="minimumPrice">
              <label for="minimumPrice">Hidden minimum price (not required)</label>
              <div class="error"></div>
            </div>
            <div class="form-floating mb-3">
              <input type="date" class="form-control" id="closingDate" placeholder="01/01/2025" name="closingDate" required>
              <label for="closingDate">Auction closing date</label>
              <div class="error"></div>
            </div>
            <div class="form-floating mb-3">
              <input type="time" class="form-control" id="closingTime" placeholder="12:00" name="closingTime" required>
              <label for="closingTime">Auction closing time</label>
              <div class="error"></div>
            </div>
            <input type="hidden" name="modalSubmit" value="true">

            <button type="button" class="btn btn-outline-secondary" data-bs-dismiss="modal">Cancel</button>
            <button type="submit" class="btn btn-outline-primary">Submit</button>
          </form>
        </div>
      </div>
    </div>
  </div>

  <div class="closed-auctions">
    <h2><%= userName %>'s Completed Auctions</h2>

    <!-- Auction cards -->
    <div class="row row-cols-1 row-cols-md-2 g-4 auction-grid">
      <% if (auctions != null && !auctions.isEmpty()) { %>
      <% for (Auction auction : auctions) { %>
      <% if(auction.getStatus().equals("completed")) { %>
      <div class="col">
        <div class="card">
          <div class="card-body">
            <a href="${pageContext.request.contextPath}/auction/<%= auction.getAuctionId() %>" class="auction-link">
              <h5 class="card-title">Auction #<%= auction.getAuctionId() %></h5>
            </a>
            <p class="card-text">Item: <%= auction.getItem().getColor()%> <%= auction.getItem().getBrand()%> <%=auction.getItem().getName()%></p>
            <p class="card-text">Final price: <%= currencyFormat.format(auction.getCurrentPrice()) %></p>
            <% if(auction.getWinner() != 0) {%>
              <p class="card-text">Winner: <%= auction.getWinnerUsername() %></p>
            <% } else {%>
              <p class="card-text">No winner</p>
            <% }%>
            <p class="card-text">Closing Date: <%= dateFormat.format(auction.getAuctionClosingDate()) %></p>
            <p class="card-text">Closing Time: <%= timeFormat.format(auction.getAuctionClosingTime()) %> </p>
          </div>
        </div>
      </div>
      <% } %>
      <% } %>
      <% } %>
    </div>
  </div>
</div>

<script src="${pageContext.request.contextPath}/js/welcome_script.js"></script>
<script>
  document.addEventListener('DOMContentLoaded', () => {
    var xhr = new XMLHttpRequest();
    xhr.open('GET', "${pageContext.request.contextPath}" + '/auctionWinner', true);
    xhr.send();

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
  })
</script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js" integrity="sha384-I7E8VVD/ismYTF4hNIPjVp/Zjvgyol6VFvRkX/vR+Vc4jQkC+hVqc2pM8ODewa9r" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.min.js" integrity="sha384-0pUGZvbkm6XF6gxjEnlmuGrJXVbNuzT9qBBavbLwCsOGabYfZo0T0to5eqruptLy" crossorigin="anonymous"></script>

</body>
</html>
