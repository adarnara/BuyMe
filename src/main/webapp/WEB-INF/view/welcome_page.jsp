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
        <div class="row row-cols-1 row-cols-md-2 g-4 auction-grid">
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
                            <div class="form-floating mb-3">
                                <input type="text" class="form-control" id="initialPrice" placeholder="10" name="initialPrice" required>
                                <label for="initialPrice">Initial price</label>
                                <div class="error"></div>
                            </div>
                            <div class="form-floating mb-3">
                                <input type="text" class="form-control" id="minimumPrice" placeholder="5" name="minimumPrice" required>
                                <label for="minimumPrice">Hidden minimum price</label>
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
    let newAuctionForm = document.getElementById('new-auction-form');
    let initialPrice = document.getElementById('initialPrice');
    let minimumPrice = document.getElementById('minimumPrice');
    let closingDate = document.getElementById('closingDate');
    let closingTime = document.getElementById('closingTime');

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

    newAuctionForm.addEventListener('submit', async e => {
        e.preventDefault();
        if(validateTime() && validatePrice()) {
            console.log(newAuctionForm.submit());
        }
    });

    const setError = (element, message) => {
        let inputControl = element.parentElement;
        let errorDisplay = inputControl.querySelector('.error');

        errorDisplay.innerText = message;
        errorDisplay.classList.add('error');
        element.classList.add('is-invalid');
    }

    const validateTime = () => {
        const closingDateValue = new Date(closingDate.value);
        const closingTimeValue = closingTime.value.split(':');
        closingDateValue.setDate(closingDateValue.getDate() + 1);

        const hours = parseInt(closingTimeValue[0]);
        const minutes = parseInt(closingTimeValue[1]);
        closingDateValue.setHours(hours, minutes, 0, 0);

        const currentDateValue = new Date();

        if(currentDateValue < closingDateValue) {
            return true;
        } else if(currentDateValue.getMonth() === closingDateValue.getMonth() && currentDateValue.getDay() === closingDateValue.getDay() && currentDateValue.getFullYear() === closingDateValue.getFullYear()) {
            // if same day but future time
            if(currentDateValue.getHours() < closingDateValue.getHours() || currentDateValue.getHours() === closingDateValue.getHours() && currentDateValue.getMinutes() < closingDateValue.getMinutes()) {
                return true;
            } else {
                setError(closingTime, "Please choose a future time or date.");
            }
        } else {
            setError(closingDate, "Please choose a future date.");
            return false;
        }
    };

    const validatePrice = () => {
        const initialPriceValue = parseFloat(initialPrice.value.trim());
        const minimumPriceValue = parseFloat(minimumPrice.value.trim());

        if (isNaN(initialPriceValue)) {
            setError(initialPrice, "Please enter a valid initial price.");
            return false;
        } else if(isNaN(minimumPriceValue)) {
            setError(minimumPrice, "Please enter a valid minimum price.");
            return false;
        }

        console.log(initialPriceValue);
        console.log(minimumPriceValue);
        if(initialPriceValue <= 0) {
            setError(initialPrice, "Initial price must be greater than zero.");
            return false;
        } else if(minimumPriceValue <= 0) {
            setError(minimumPrice, "Minimum price must be greater than zero.");
            return false;
        }

        if(!isValidDecimalPlaces(initialPriceValue)) {
            setError(initialPrice, "Initial price must have up to two decimal places.");
            return false;
        } else if(!isValidDecimalPlaces(minimumPriceValue)) {
            setError(minimumPrice, "Minimum price must have up to two decimal places.");
            return false;
        }

        return true;
    }

    const isValidDecimalPlaces = (number) => {
        const decimalPlaces = (number.toString().split('.')[1] || '').length;
        return decimalPlaces <= 2;
    }

    closingDate.addEventListener('change', () => {
        resetForm(closingDate);
    })

    closingTime.addEventListener('change', () => {
        resetForm(closingTime);
    })

    initialPrice.addEventListener('change', () => {
        resetForm(initialPrice);
    })

    minimumPrice.addEventListener('change', () => {
        resetForm(minimumPrice);
    })

    const resetForm = (element) => {
        let inputControl = element.parentElement;
        let errorDisplay = inputControl.querySelector('.error');

        if(element.classList.contains('is-invalid')) {
            errorDisplay.innerText = "";
            element.classList.remove('is-invalid');
        }
    };

</script>

<script>

</script>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js" integrity="sha384-I7E8VVD/ismYTF4hNIPjVp/Zjvgyol6VFvRkX/vR+Vc4jQkC+hVqc2pM8ODewa9r" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.min.js" integrity="sha384-0pUGZvbkm6XF6gxjEnlmuGrJXVbNuzT9qBBavbLwCsOGabYfZo0T0to5eqruptLy" crossorigin="anonymous"></script>

</body>
</html>
