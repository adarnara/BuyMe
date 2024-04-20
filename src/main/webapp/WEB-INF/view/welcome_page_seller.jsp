<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <meta charset="UTF-8">
  <title>Welcome Page</title>
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
  <h2>Test</h2>
  <p>
    Item 1.
    <br><br>Item 2.<br><br>Item 3.</p>
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
</body>
</html>
