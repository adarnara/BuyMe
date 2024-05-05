<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.mybuy.model.Question" %>

<html>
<head>
    <title>Contact and Questions</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <link href="https://cdn.jsdelivr.net/npm/remixicon@4.1.0/fonts/remixicon.css" rel="stylesheet"/>
    <style>
        body, html {
            margin: 0;
            padding: 0;
            min-height: 100%;
            width: 100%;
            display: flex;
            flex-direction: column;
        }
        body {
            font-family: 'Jost', sans-serif;
            background: linear-gradient(to bottom, #0f0c29, #302b63, #24243e);

        }
        .navbar {
            background-color: #24243e;
            width: 100%;
            margin-bottom: 160px;
        }
        .container, .search-container {
            width: 60%;
            background-color: #fff;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            display: flex;
            flex-direction: column;
            align-items: center;
            margin: 10px auto 10px auto;
        }
        .search-input {
            flex-grow: 1;
            padding: 10px;
            margin-right: 10px;
            border: 2px solid #ccc;
            border-radius: 4px;
            background-color: #e0e0e0;
        }
        .search-button {
            padding: 10px 20px;
            background-color: #5c007a;
            color: #ffffff;
            border: none;
            border-radius: 4px;
            cursor: pointer;
        }
        .search-button:hover {
            background-color: #472a57;
        }
        .form-control {
            width: 1000px;
            height: 200px;
            font-size: 1.2em;
            background-color: #e0e0e0;
        }
        .btn-primary {
            background-color: #5c007a;
            border-color: #ffffff;
            width: 100%;
        }
        .btn-primary:hover {
            background-color: #472a57;
        }
        .status-tag {
            float: right;
            font-weight: bold;
        }
        .answered {
            color: blue;
        }
        .unanswered {
            color: red;
        }
        .title {
            color: #5c007a;
            font-family: 'Poppins', sans-serif;
            font-size: 3em;
            font-weight: bold;
            text-align: center;
        }

    </style>
</head>
<body>
<header>
    <nav class="navbar navbar-expand-lg navbar-dark">
        <div class="container-fluid">
            <a class="navbar-brand" href="#">MyBuy</a>
            <ul class="navbar-nav ml-auto">
                <li class="nav-item">
                    <a class="nav-link active" href="${pageContext.request.contextPath}/login">Home</a>
                </li>
            </ul>
        </div>
    </nav>
</header>
<div class="container">
    <h2 class="title">Contact Us and Question Portal</h2>
    <form action="${pageContext.request.contextPath}/question" method="post">
        <div class="form-group">
            <label for="question">Your Question:</label>
            <textarea class="form-control" id="question" name="question" rows="6" required placeholder="Write your question here..."></textarea>
        </div>
        <input type="hidden" name="origin" value="contact">
        <button type="submit" class="btn btn-primary">Submit Question</button>
    </form>
</div>

<div class="search-container">
    <div style="width: 100%; display: flex; align-items: center;">
        <input type="text" id="search-input" class="search-input" placeholder="Search questions or answers...">
        <button onclick="searchQuestions();" class="search-button">
            <i class="ri-search-line" style="font-size: 1.5em;"></i>
        </button>
    </div>
    <div id="question-cards-container" class="row" style="width: 100%;"></div>
</div>

<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
<script>
    document.addEventListener('DOMContentLoaded', function() {
        const searchInput = document.getElementById('search-input');

        searchInput.addEventListener('keypress', function(event) {
            if (event.key === 'Enter') {
                event.preventDefault();
                searchQuestions();
            }
        });

        window.searchQuestions = function() {
            const query = searchInput.value;
            fetch('${pageContext.request.contextPath}/searchQuestions?query=' + encodeURIComponent(query))
                .then(response => response.text())
                .then(data => {
                    const container = document.getElementById('question-cards-container');
                    container.innerHTML = ''; // Clear previous results
                    if (data.trim().length === 0) {
                        container.innerHTML = '<div class="col-md-12"><p>No results found.</p></div>';
                        return;
                    }
                    const lines = data.trim().split('\n');
                    let currentQuestion = null;
                    lines.forEach(line => {
                        const parts = line.match(/^(\w+): (.*)$/);
                        if (parts) {
                            let key = parts[1].trim();
                            let value = parts[2].trim();
                            if (key === 'Question') {
                                if (currentQuestion) {
                                    // Output the previous question card
                                    renderQuestionCard(currentQuestion, container);
                                }
                                // Start a new question
                                currentQuestion = { question: value, answer: null };
                            } else if (key === 'Answer' && currentQuestion) {
                                currentQuestion.answer = value;
                            }
                        }
                    });
                    if (currentQuestion) {
                        // Output the last question card
                        renderQuestionCard(currentQuestion, container);
                    }
                });
        };

        function renderQuestionCard(details, container) {
            const statusTag = details.answer ? '<span class="status-tag answered">Answered</span>' : '<span class="status-tag unanswered">Unanswered</span>';
            const answerText = details.answer || 'No answer yet';
            const cardHtml =
                '<div class="col-md-12 mb-4">' +
                '<div class="card" style="background-color: #342c63; border: none; border-radius: 10px; color: #fff;">' +
                '<div class="card-body" style="padding: 15px;">' +
                '<h5 class="card-title">' + details.question + ' ' + statusTag + '</h5>' +
                '<p class="card-text">' + answerText + '</p>' +
                '</div>' +
                '</div>' +
                '</div>';
            container.innerHTML += cardHtml;
        }
    });
</script>

</body>
</html>
