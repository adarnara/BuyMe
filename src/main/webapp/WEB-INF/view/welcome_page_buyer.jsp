<%@ page import="com.mybuy.model.FilterOption" %>
<%@ page import="com.mybuy.model.Auction" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>



<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Welcome Page</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">

    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/stylesheets/welcome_style_buyer.css"/>
    <link href="https://cdn.jsdelivr.net/npm/remixicon@4.1.0/fonts/remixicon.css" rel="stylesheet"/>
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
        <h2>Test</h2>
        <div class="search-container">
            <input type="text" id="search-input" class="search-input" placeholder="Search auctions" />
            <a href="#" class="search-icon" id="search-icon"><i class="ri-search-line"></i></a>
        </div>
    </div>
    <div class="card filter-card my-4" style="width: 100%; max-width: 300px; position: absolute; left: 0; top: 200px;">
        <div class="card-body">
            <h4 class="card-title text-center" style="color: #5c007a;">Filters</h4>
            <form>
                <div class="form-group">
                    <label for="category" style="color: #5c007a;">Category Name</label>
                    <select id="category" class="form-control">
                        <option>Choose...</option>
                    </select>
                </div>
                <div class="form-group">
                    <label for="item-name" style="color: #5c007a;">Item Name</label>
                    <select id="item-name" class="form-control">
                        <option>Choose...</option>
                    </select>
                </div>
                <div class="form-group">
                    <label for="brand" style="color: #5c007a;">Item Brand</label>
                    <select id="brand" class="form-control">
                        <option>Choose...</option>
                    </select>
                </div>
                <div class="form-group">
                    <label style="color: #5c007a;">Price Range</label>
                    <div class="btn-group" role="group" aria-label="Price Range">
                        <button type="button" class="btn btn-outline-primary price-btn" style="background-color: #5c007a; color: #ffffff; border-color: #ffffff;" data-value="low"> 0 to 49</button>
                        <button type="button" class="btn btn-outline-primary price-btn" style="background-color: #5c007a; color: #ffffff; border-color: #ffffff;" data-value="medium">50 to 149</button>
                        <button type="button" class="btn btn-outline-primary price-btn" style="background-color: #5c007a; color: #ffffff; border-color: #ffffff;" data-value="high"> 150 <=  </button>
                    </div>
                </div>

                <div class="form-group">
                    <label for="color" style="color: #5c007a;">Color Variants</label>
                    <select id="color" class="form-control">
                        <option>Choose...</option>
                    </select>
                </div>
                <div class="form-group">
                    <label for="status" style="color: #5c007a;">Auction Status</label>
                    <select id="status" class="form-control">
                        <option>Choose...</option>
                    </select>
                </div>
            </form>
        </div>
    </div>

    <div class="spacer" style="height: 700px;"></div>

</div>


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

        let xhr = new XMLHttpRequest();
        xhr.open('GET', "${pageContext.request.contextPath}" + '/auctionWinner', true);
        xhr.send();

        searchIcon.addEventListener('click', (event) => {
            event.preventDefault();
            if (searchContainer.classList.contains('expanded')) {
                searchContainer.classList.remove('expanded');
            } else {
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

    window.addEventListener('scroll', function () {
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

    document.addEventListener('DOMContentLoaded', function () {
        fetchFilterOptions('category', 'CategoryName');
        fetchFilterOptions('item-name', 'ItemName');
        fetchFilterOptions('brand', 'ItemBrand');
        fetchFilterOptions('color', 'ColorVariants');
        fetchFilterOptions('status', 'AuctionStatus');

        function fetchFilterOptions(dropdownId, optionType) {
            let xhr = new XMLHttpRequest();
            xhr.onreadystatechange = function () {
                if (xhr.readyState === 4 && xhr.status === 200) {
                    populateDropdown(dropdownId, xhr.responseText);
                }
            };
            xhr.open('GET', '${pageContext.request.contextPath}/filter-options?optionType=' + optionType, true);
            xhr.send();
        }

        function populateDropdown(dropdownId, data) {
            let dropdown = document.getElementById(dropdownId);
            let options = data.split('\n');
            dropdown.innerHTML = '<option>Choose...</option>';
            options.forEach(function(option) {
                if (option.trim()) {
                    let optElement = document.createElement('option');
                    optElement.value = optElement.textContent = option.trim();
                    dropdown.appendChild(optElement);
                }
            });
        }
    });

    document.addEventListener('DOMContentLoaded', function() {
        const searchInput = document.getElementById('search-input');
        const searchIcon = document.getElementById('search-icon');
        let autocompleteList = document.createElement('div');
        autocompleteList.id = 'autocomplete-list';
        autocompleteList.style.position = 'absolute';
        autocompleteList.style.zIndex = '1000';
        autocompleteList.style.backgroundColor = '#fff';
        autocompleteList.style.border = '1px solid #ccc';
        autocompleteList.style.display = 'none'; // Initially hidden
        autocompleteList.style.left = '0';
        autocompleteList.style.right = '0';
        autocompleteList.style.textAlign = 'center'; // Center text
        searchInput.parentNode.appendChild(autocompleteList);

        searchInput.addEventListener('input', function() {
            const prefix = searchInput.value;
            if (prefix.length > 1) {
                autocompleteSearch(prefix);
            } else {
                clearAutocomplete();
            }
        });

        searchIcon.addEventListener('click', function() {
            if (searchInput.value.trim().length > 0) {
                triggerSearch(searchInput.value.trim());
                clearAutocomplete();
            }
        });

        document.addEventListener('click', function(event) {
            if (event.target !== searchInput && event.target !== autocompleteList) {
                clearAutocomplete();
            }
        });

        function autocompleteSearch(prefix) {
            let xhr = new XMLHttpRequest();
            xhr.open('GET', '${pageContext.request.contextPath}/autocomplete?prefix=' + encodeURIComponent(prefix), true);
            xhr.onreadystatechange = function() {
                if (xhr.readyState === 4 && xhr.status === 200) {
                    displaySuggestions(xhr.responseText);
                }
            };
            xhr.send();
        }

        function displaySuggestions(data) {
            // Remove brackets and split into an array, then remove duplicates and trim each item
            let suggestions = Array.from(new Set(data.replace(/[\[\]]/g, '').split(',').map(s => s.trim()).filter(Boolean)));

            autocompleteList.innerHTML = '';
            autocompleteList.style.width = searchInput.offsetWidth + 'px';
            autocompleteList.style.top = (searchInput.offsetTop + searchInput.offsetHeight) + 'px';
            autocompleteList.style.left = searchInput.offsetLeft + 'px';

            if (suggestions.length) {
                suggestions.forEach(function(suggestion) {
                    let listItem = document.createElement('div');
                    listItem.textContent = suggestion;
                    listItem.style.cursor = 'pointer';
                    listItem.style.padding = '10px';
                    listItem.style.borderBottom = '1px solid #ccc';
                    listItem.style.background = '#fff'; // White background for each item
                    listItem.style.color = '#5c007a'; // Purple text
                    listItem.onclick = function() {
                        searchInput.value = suggestion;
                        clearAutocomplete();
                        triggerSearch(suggestion);
                    };
                    autocompleteList.appendChild(listItem);
                });
                autocompleteList.style.display = 'block';
            } else {
                clearAutocomplete();
            }
        }


        function displaySuggestions(data) {
            let suggestions = Array.from(new Set(data.replace(/[\[\]]/g, '').split(',').map(s => s.trim()).filter(Boolean)));

            autocompleteList.innerHTML = '';
            autocompleteList.style.width = searchInput.offsetWidth + 'px';
            autocompleteList.style.top = (searchInput.offsetTop + searchInput.offsetHeight) + 'px';
            autocompleteList.style.left = searchInput.offsetLeft + 'px';
            autocompleteList.style.borderRadius = '5px'; // Rounded corners for the dropdown
            autocompleteList.style.boxShadow = '0 4px 6px rgba(0, 0, 0, 0.1)'; // Optional: Adds a subtle shadow for depth

            if (suggestions.length) {
                suggestions.forEach(function(suggestion) {
                    let listItem = document.createElement('div');
                    listItem.textContent = suggestion;
                    listItem.style.cursor = 'pointer';
                    listItem.style.padding = '10px';
                    listItem.style.borderBottom = '1px solid #ccc';
                    listItem.style.background = '#fff'; // White background for each item
                    listItem.style.color = '#5c007a'; // Purple text
                    listItem.style.transition = 'background-color 0.3s'; // Smooth background color transition for hover effect

                    // Hover effect
                    listItem.onmouseover = function() {
                        this.style.backgroundColor = '#f2f2f2'; // Light grey background on hover
                    };
                    listItem.onmouseout = function() {
                        this.style.backgroundColor = '#fff'; // White background when not hovered over
                    };

                    listItem.onclick = function() {
                        searchInput.value = suggestion;
                        clearAutocomplete();
                        triggerSearch(suggestion);
                    };
                    autocompleteList.appendChild(listItem);
                });
                autocompleteList.style.display = 'block';
            } else {
                clearAutocomplete();
            }
        }



        function clearAutocomplete() {
            autocompleteList.innerHTML = '';
            autocompleteList.style.display = 'none';
        }

        function triggerSearch(query) {
            let xhr = new XMLHttpRequest();
            xhr.open('GET', '${pageContext.request.contextPath}/search?query=' + encodeURIComponent(query), true);
            xhr.onreadystatechange = function() {
                if (xhr.readyState === 4 && xhr.status === 200) {
                    // Process search results
                    console.log(xhr.responseText); // Placeholder: log the results or display them as needed
                }
            };
            xhr.send();
        }
    });


</script>

<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>

</body>
</html>