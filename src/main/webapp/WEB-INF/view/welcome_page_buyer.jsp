<%@ page import="com.mybuy.model.FilterOption" %>
<%@ page import="com.mybuy.model.Auction" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>



<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Welcome Page</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">

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
        <li><a href="#">Current Auctions</a></li>
        <li><a href="#" onclick="document.getElementById('contact-form').submit(); return false;">Contact</a></li>
        <li><a href="#" onclick="document.getElementById('logout-form').submit(); return false;">Logout</a></li>
    </ul>
</header>
<form id="logout-form" action="${pageContext.request.contextPath}/logout" method="post" style="display: none;">
    <input type="hidden" name="logout" value="true">
</form>
<form id="contact-form" action="${pageContext.request.contextPath}/contactQuestions" method="post" style="display: none;"></form>

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
        <h2 style="color: #fff; font-family: 'Poppins', sans-serif; font-size: 3em; font-weight: bold;">Auction/Item Listings</h2>
        <div class="search-container">
            <input type="text" id="search-input" class="search-input" placeholder="Search auctions" />
            <a href="#" class="search-icon" id="search-icon">
                <i class="ri-search-line" style="font-size: 1.5em;"></i>
            </a>
        </div>
    </div>

    <div class="container">
        <div class="row">
            <div class="col-md-4 mb-4">
                <div class="card filter-card my-4">
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
                            <div class="form-group">
                                <div class="text-center">
                                    <button type="button" id="clear-filters" class="btn btn-secondary" style="background-color: #f8f9fa; border-color: #6c757d; color: #6c757d;">Clear Filters</button>
                                    <button type="button" id="apply-filters" class="btn btn-primary" style="background-color: #5c007a; border-color: #ffffff; color: #ffffff;">Apply Filters</button>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>

            <div class="col-md-8">
                <div class="container">
                    <div id="auction-cards-container" class="row">
                        <!-- Dynamic auction cards will be inserted here -->
                    </div>
                </div>
            </div>
        </div>
    </div>

</div>

<script>
    const contextPath = "${pageContext.request.contextPath}";

    document.addEventListener('DOMContentLoaded', function() {

        fetchRandomItems();

        var xhr = new XMLHttpRequest();
        xhr.open('GET', "${pageContext.request.contextPath}" + '/auctionWinner', true);
        xhr.send();

        let stars = document.getElementById('stars');
        let moon = document.getElementById('moon');
        let mountains_behind = document.getElementById('mountains_behind');
        let mountains_front = document.getElementById('mountains_front');
        let btn = document.getElementById('btn');
        let header = document.querySelector('header');
        let main_message = document.getElementById('main-message');
        const searchInput = document.getElementById('search-input');
        const searchIcon = document.getElementById('search-icon');
        let isSearchExpanded = false;
        let autocompleteList = document.createElement('div');



        window.addEventListener('scroll', function() {
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

        const searchContainer = searchIcon.closest('.search-container');
        autocompleteList.id = 'autocomplete-list';
        autocompleteList.style.position = 'absolute';
        autocompleteList.style.zIndex = '1000';
        autocompleteList.style.backgroundColor = '#fff';
        autocompleteList.style.border = '1px solid #ccc';
        autocompleteList.style.display = 'none';
        autocompleteList.style.left = '0';
        autocompleteList.style.right = '0';
        autocompleteList.style.textAlign = 'center';
        searchInput.parentNode.appendChild(autocompleteList);

        searchIcon.addEventListener('click', function(event) {
            event.preventDefault();
            if (searchContainer.classList.contains('expanded')) {
                searchContainer.classList.remove('expanded');
            } else {
                searchContainer.classList.add('expanded');
                setTimeout(() => searchInput.focus(), 500);
            }
        });

        document.addEventListener('click', function(event) {
            if (!searchContainer.contains(event.target) && searchContainer.classList.contains('expanded')) {
                searchContainer.classList.remove('expanded');
            }
        });

        searchInput.addEventListener('transitionend', function(event) {
            if (event.propertyName === 'width' && !searchContainer.classList.contains('expanded')) {
                searchInput.value = '';
            }
        });

        searchInput.addEventListener('input', function() {
            const prefix = searchInput.value;
            if (prefix.length > 1) {
                autocompleteSearch(prefix);
            } else {
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
            let suggestions = Array.from(new Set(data.replace(/[\[\]]/g, '').split(',').map(s => s.trim()).filter(Boolean)));

            autocompleteList.innerHTML = '';
            autocompleteList.style.width = searchInput.offsetWidth + 'px';
            autocompleteList.style.top = (searchInput.offsetTop + searchInput.offsetHeight) + 'px';
            autocompleteList.style.left = searchInput.offsetLeft + 'px';
            autocompleteList.style.borderRadius = '5px';
            autocompleteList.style.boxShadow = '0 4px 6px rgba(0, 0, 0, 0.1)';

            if (suggestions.length) {
                suggestions.forEach(function(suggestion) {
                    let listItem = document.createElement('div');
                    listItem.textContent = suggestion;
                    listItem.style.cursor = 'pointer';
                    listItem.style.padding = '10px';
                    listItem.style.borderBottom = '1px solid #ccc';
                    listItem.style.background = '#fff';
                    listItem.style.color = '#5c007a';
                    listItem.style.transition = 'background-color 0.3s';

                    listItem.onmouseover = function() {
                        this.style.backgroundColor = '#f2f2f2';
                    };
                    listItem.onmouseout = function() {
                        this.style.backgroundColor = '#fff';
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
                    renderAuctionCards(xhr.responseText);
                }
            };
            xhr.send();
        }

        fetchFilterOptions('category', 'CategoryName');
        fetchFilterOptions('item-name', 'ItemName');
        fetchFilterOptions('brand', 'ItemBrand');
        fetchFilterOptions('color', 'ColorVariants');
        fetchFilterOptions('status', 'AuctionStatus');

        function fetchFilterOptions(dropdownId, optionType) {
            let xhr = new XMLHttpRequest();
            xhr.onreadystatechange = function() {
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
        function getSelectedFilters() {
            let filters = {};
            const filterMappings = {
                'category': 'Category_name',
                'item-name': 'Item_name',
                'brand': 'Item_brand',
                'color': 'Color_variants',
                'status': 'Auction_status'
            };

            // Map the selected values to their corresponding keys
            for (const elementId in filterMappings) {
                const select = document.getElementById(elementId);
                if (select && select.value !== 'Choose...') {
                    filters[filterMappings[elementId]] = select.value;
                }
            }

            // Get selected price range
            let priceRangeBtns = document.querySelectorAll('.price-btn');
            priceRangeBtns.forEach(button => {
                if (button.classList.contains('active')) {
                    filters['price_range'] = button.getAttribute('data-value');
                }
            });
            return filters;
        }


        function triggerSearchWithFilters(filters) {
            let params = '';
            let isFirstParam = true;
            for (const key in filters) {
                if (filters[key]) {
                    if (!isFirstParam) {
                        params += '&';
                    }
                    params += key + '=' + filters[key];
                    isFirstParam = false;
                }
            }

            let xhr = new XMLHttpRequest();
            xhr.open('GET', '${pageContext.request.contextPath}/search?'+ params, true);
            xhr.onreadystatechange = function() {
                if (xhr.readyState === 4 && xhr.status === 200) {
                    renderAuctionCards(xhr.responseText);

                }
            };
            xhr.send();
        }
        function clearFilters() {
            // Reset all filter options to default values
            document.querySelectorAll('.form-control').forEach(select => {
                select.value = 'Choose...';
            });

            // Remove active class from all price range buttons
            document.querySelectorAll('.price-btn').forEach(button => {
                button.classList.remove('active');
            });

            // Remove price range filter from filters object
            let filters = getSelectedFilters();
            delete filters['price_range'];

            // Trigger search with cleared filters
            triggerSearchWithFilters(filters);
        }




// Event listener for apply filters button
        document.getElementById('apply-filters').addEventListener('click', function() {
            console.log("Apply Filters button clicked");
            let filters = getSelectedFilters();
            console.log("Selected filters:", filters);
            triggerSearchWithFilters(filters);
        });

        document.getElementById('clear-filters').addEventListener('click', function() {
            console.log("Clear Filters button clicked");
            clearFilters();
        });



// Event listener for price range buttons
        document.querySelectorAll('.price-btn').forEach(button => {
            button.addEventListener('click', function() {
                // Remove 'active' class from all buttons
                document.querySelectorAll('.price-btn').forEach(btn => {
                    btn.classList.remove('active');
                });
                // Add 'active' class to clicked button
                this.classList.add('active');

            });
        });

        function renderAuctionCards(data) {
            const container = document.getElementById('auction-cards-container');
            container.innerHTML = '';  // Clear existing cards

            // Split data into lines and process each line
            const lines = data.trim().split('\n');
            lines.forEach(line => {
                try {
                    let details = line.split(', ').reduce((acc, current) => {
                        const parts = current.split(': ');
                        if (parts.length === 2) {
                            const [key, value] = parts;
                            acc[key.trim()] = value.trim();
                        }
                        return acc;
                    }, {});

                    if (Object.keys(details).length === 0) {
                        console.error('No details extracted from line:', line);
                        return;
                    }

                    console.log(details);

                    // Generate HTML for the auction card with inline styling and grid placement using '+' for concatenation
                    const cardHtml =
                        '<div class="col-md-4 mb-4">' +
                        '<div class="card" style="background-color: #342c63; border: none; border-radius: 10px; color: #fff;">' +
                        '<div class="card-body" style="padding: 15px;">' +
                        '<a href="${pageContext.request.contextPath}/auction/' + details['Auction ID'] + '" style="color: #fff;">' +
                        '<h5 class="card-title">' + details.Name + ' - ' + details.Brand + '</h5>' +
                        '</a>' +
                        '<p class="card-text">Category: ' + details.Category + '</p>' +
                        '<p class="card-text">Current Price: ' + details['Current Price'] + '</p>' +
                        '<p class="card-text">Status: ' + details['Auction Status'] + '</p>' +
                        '</div>' +
                        '</div>' +
                        '</div>';
                    container.innerHTML += cardHtml;
                } catch (error) {
                    console.error('Error processing auction data:', error);
                }
            });
        }


        function fetchRandomItems() {
            let xhr = new XMLHttpRequest();
            xhr.open('GET', contextPath + '/random-items', true);
            xhr.onreadystatechange = function() {
                if (xhr.readyState === 4 && xhr.status === 200) {
                    renderAuctionCards(xhr.responseText);
                }
            };
            xhr.send();
        }


    });
</script>

<script src="${pageContext.request.contextPath}/js/welcome_buyer_script.js"></script>
<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js" integrity="sha384-I7E8VVD/ismYTF4hNIPjVp/Zjvgyol6VFvRkX/vR+Vc4jQkC+hVqc2pM8ODewa9r" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.min.js" integrity="sha384-0pUGZvbkm6XF6gxjEnlmuGrJXVbNuzT9qBBavbLwCsOGabYfZo0T0to5eqruptLy" crossorigin="anonymous"></script>

</body>
</html>