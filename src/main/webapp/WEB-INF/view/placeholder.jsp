<%@ page import="com.mybuy.model.Auction" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Welcome Page</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/stylesheets/welcome_style_buyer.css"/>
    <link href="https://cdn.jsdelivr.net/npm/remixicon@4.1.0/fonts/remixicon.css" rel="stylesheet"/>
    <style>
        body {
            position: relative;
        }
        .filter-sidebar {
            height: auto;
            background-color: white;
            color: #5c007a; /* dark purple */
            padding: 20px;
            position: absolute;
            top: 120px; /* Adjust this value based on your header's height */
            left: 0;
            z-index: 100;
            transition: transform 0.3s ease-out;
        }
        .filter-sidebar.collapsed {
            transform: translateX(-100%);
        }
        #toggle-filters {
            cursor: pointer;
            position: absolute;
            top: 130px; /* Adjust this value to align with the filter bar */
            left: 0;
            z-index: 101;
            color: #5c007a;
            background-color: white;
            border: none;
            padding: 5px 10px;
        }
    </style>
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
        <p class="account-info"><span class="account-type-label">Account type:</span> Buyer</p>
    </div>
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

<!-- Filter sidebar and toggle button -->
<div class="filter-sidebar" id="filter-sidebar">
    <h4>Filters</h4>
    <div class="form-group">
        <label for="category">Category Name</label>
        <select id="category" class="form-control">
            <option>Choose...</option>
            <!-- Options -->
        </select>
    </div>
    <div class="form-group">
        <label for="subcategory">Sub Category Name</label>
        <select id="subcategory" class="form-control">
            <option>Choose...</option>
            <!-- Options -->
        </select>
    </div>
    <div class="form-group">
        <label for="item-name">Item Name</label>
        <input type="text" class="form-control" id="item-name">
    </div>
    <div class="form-group">
        <label for="brand">Item Brand</label>
        <input type="text" class="form-control" id="brand">
    </div>
    <div class="form-group">
        <label for="price-range">Price Range</label>
        <input type="range" class="form-control" id="price-range">
    </div>
    <div class="form-group">
        <label for="color">Color Variants</label>
        <select id="color" class="form-control">
            <option>Choose...</option>
            <!-- Options -->
        </select>
    </div>
    <div class="form-group">
        <label for="status">Auction Status</label>
        <select id="status" class="form-control">
            <option>Choose...</option>
            <!-- Options -->
        </select>
    </div>
</div>
<button id="toggle-filters" onclick="toggleFilterSidebar()">&#9664;</button>

<section>
    <img src="${pageContext.request.contextPath}/Images/stars.png" id="stars">
    <img src="${pageContext.request.contextPath}/Images/moon.png" id="moon">
    <img src="${pageContext.request.contextPath}/Images/mountains_behind.png" id="mountains_behind">
    <p id="main-message">Click below to explore</p>
    <a href="#sec" id="btn">Explore</a>
    <img src="${pageContext.request.contextPath}/Images/mountains_front.png" id="mountains_front">
</section>

<div class="header-search-container">
    <h2>Test</h2>
    <div class="search-container">
        <input type="text" id="search-input" class="search-input" placeholder="Search auctions" />
        <a href="#" class="search-icon" id="search-icon"><i class="ri-search-line"></i></a>
    </div>
</div>
<!-- Other content here -->

<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.2/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
<script>
    let stars = document.getElementById('stars');
    let moon = document.getElementById('moon');
    let mountains_behind = document.getElementById('mountains_behind');
    let mountains_front = document.getElementById('mountains_front');
    let btn = document.getElementById('btn');
    let header = document.querySelector('header');
    let main_message = document.getElementById('main-message');

    document.addEventListener('DOMContentLoaded', (event) => {
        const searchIcon = document.getElementById('search-icon');
        const searchContainer = searchIcon.closest('.search-container');
        const searchInput = document.getElementById('search-input');

        searchIcon.addEventListener('click', (event) => {
            event.preventDefault();
            if (searchContainer.classList.contains('expanded')) {
                searchContainer.classList.remove('expanded');
            }
            else {
                searchContainer.classList.add('expanded');
                setTimeout(() => searchInput.focus(), 500);
            }
        });

        document.addEventListener('click', (event) => {
            if (!searchContainer.contains(event.target) && searchContainer.classList.contains('expanded')) {
                searchContainer.classList.remove('expanded');
            }
        });

        searchInput.addEventListener('transitionend', (event) => {
            if (event.propertyName === 'width' && !searchContainer.classList.contains('expanded')) {
                searchInput.value = '';
            }
        });
    });

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

    function toggleFilterSidebar() {
        let sidebar = document.getElementById('filter-sidebar');
        sidebar.classList.toggle('collapsed');
        let toggleButton = document.getElementById('toggle-filters');
        if (sidebar.classList.contains('collapsed')) {
            toggleButton.innerHTML = '&#9654;'; // Right-pointing arrow when collapsed
        } else {
            toggleButton.innerHTML = '&#9664;'; // Left-pointing arrow when expanded
        }
    }
</script>

</body>
</html>
