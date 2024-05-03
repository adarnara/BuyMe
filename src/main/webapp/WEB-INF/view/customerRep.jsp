<%@ page import="java.sql.*" %>
<%@ page import="java.util.*" %>
<%@ page import="com.mybuy.utils.ApplicationDB" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Customer Rep Page</title>
    <style>
        body {
            margin: 0;
            padding: 0;
            display: flex;
            justify-content: center;
            align-items: center;
            flex-direction: column;
            height: 100vh;
            font-family: 'Jost', sans-serif;
            background: linear-gradient(to bottom, #0f0c29, #302b63, #24243e);
            color: #fff;
        }
        .landingPageTitle {
            font-size: 90px;
            cursor: default;
            margin: 0;
        }
        .logoutButton {
            position: absolute;
            top: 20px;
            right: 20px;
            font-size: 18px;
            cursor: pointer;
            background: none;
            border: 1px solid #fff;
            color: #fff;
            padding: 10px 20px;
            text-decoration: none;
            transition: transform 0.3s ease;
        }
        .logoutButton:hover {
            transform: scale(1.1);
            background-color: #342c63;
        }
    </style>
</head>
<body>
	<button onclick="showQuestions()">Show Questions</button>
    <button onclick="showAuctions()">Show Auctions</button>
    <button onclick="showBids()">Show Bids</button>
    <button onclick="showUsers()">Show Users</button>
    <div id="questions" style="display: none;">
    	<table border="1">
    		<tr>
    			<th>Question Asker</th>
    			<th>Question</th>
    			<th>Response</th>
    		</tr>
			<%
				try (Connection conn = ApplicationDB.getConnection(); Statement stmt = conn.createStatement()) {
					String sql = "Select q.question_ID, e.endUser_login, q.question_text FROM Question q JOIN endUser e on e.User_Id = q.User_Id WHERE q.answer_text IS NULL";
					ResultSet rs = stmt.executeQuery(sql);
	        		while(rs.next()) {
	        			%>
	        				<tr>
	        					<td><%= rs.getString("e.endUser_login") %></td>
	        					<td><%= rs.getString("q.question_text") %></td>
    							<td contenteditable="true" data-id=<%= rs.getInt("q.question_ID") %>></td>
    							<td>   
        							<form id="responseForm<%= rs.getInt("q.question_ID") %>" method="POST" action="<%= request.getContextPath() + "/response?origin=customerRep" %>">
            							<input type="hidden" name="id" value="<%= rs.getInt("q.question_ID") %>">
            							<input type="hidden" name="answer" value="">
            							<button type="button" onclick="submitResponseForm(this)">Submit Response</button>
        							</form>
   								</td>
	        				</tr>
	        			<%
	        		}
	        		conn.close();
	        		stmt.close();
	        		rs.close();
	        	} catch (SQLException e) {
	        		e.printStackTrace();
	        	}
			%>

    	</table>
    	</div>
	<div id="bids" style="display: none;">
		<table border="1">
			<tr>
				<th>Bid Creator</th>
				<th>Auction Creator</th>
				<th>Item</th>
				<th>Bid Date</th>
				<th>Bid Time</th>
				<th>Bid Amount</th>
			</tr>
			<%
				try (Connection conn = ApplicationDB.getConnection(); Statement stmt = conn.createStatement()) {
					String sql = "Select b.bid_id, e.endUser_login, u.endUser_login, i.name, b.bid_date, b.bid_time, b.bid_amount FROM Bid b JOIN EndUser e ON b.User_ID = e.User_ID JOIN Auction a ON b.Auction_ID = a.Auction_ID JOIN EndUser u ON a.User_ID = u.User_ID JOIN Items i ON a.Item_ID = i.Item_ID";
					ResultSet rs = stmt.executeQuery(sql);
	        		while(rs.next()) {
	        			%>
	        				<tr>
	        					<td><%= rs.getString("e.endUser_login") %></td>
	        					<td><%= rs.getString("u.endUser_login") %></td>
	        					<td><%= rs.getString("i.name") %></td>
	        					<td><%= rs.getString("b.bid_date") %></td>
	        					<td><%= rs.getString("b.bid_time") %></td>
	        					<td><%= rs.getString("b.bid_amount") %></td>
        						<td>	
        							<form id="deleteForm" method="POST" action="<%= request.getContextPath() + "/delete?origin=customerRep" %>">
  										<input type="hidden" name="id" value="<%= rs.getInt("b.bid_id") %>">
  										<input type="hidden" name="type" value="bid">
  										<button type="button" onclick="submitDeleteForm(this);">Delete Bid</button>
									</form>
								</td>
	        				</tr>
	        			<%
	        		}
	        		conn.close();
	        		stmt.close();
	        		rs.close();
	        	} catch (SQLException e) {
	        		e.printStackTrace();
	        	}
			%>
		</table>

	</div>
	<div id="auctions" style="display: none;">
		<table border="1">
			<tr>
				<th>Auction Creator</th>
				<th>Item</th>
				<th>Current Price</th>
				<th>Status</th>
			</tr>
			<%
				try (Connection conn = ApplicationDB.getConnection(); Statement stmt = conn.createStatement()) {
					String sql = "Select a.Auction_ID, e.endUser_login, i.name, a.Current_Price, a.auction_status FROM Auction a JOIN Items i on a.Item_ID = i.Item_ID JOIN EndUser e on a.User_ID = e.User_ID";
					ResultSet rs = stmt.executeQuery(sql);
	        		while(rs.next()) {
	        			%>
	        				<tr>
	        					<td><%= rs.getString("e.endUser_login") %></td>
	        					<td><%= rs.getString("i.name") %></td>
	        					<td><%= rs.getString("a.Current_Price") %></td>
	        					<td><%= rs.getString("a.auction_status") %></td>
        						<td>	
        							<form id="deleteForm" method="POST" action="<%= request.getContextPath() + "/delete?origin=customerRep" %>">
  										<input type="hidden" name="id" value="<%= rs.getInt("a.Auction_ID") %>">
  										<input type="hidden" name="type" value="auction">
  										<button type="button" onclick="submitDeleteForm(this);">Delete Auction</button>
									</form>
								</td>
	        				</tr>
	        			<%
	        		}
	        		conn.close();
	        		stmt.close();
	        		rs.close();
	        	} catch (SQLException e) {
	        		e.printStackTrace();
	        	}
			%>
		</table>
	</div>
	<div id="main" style="display: block;">
	<h1>You can edit usernames, passwords directly in the table. Click Save Changes to confirm.</h1>
    <table border="1">
        <tr>
            <th>Name</th>
            <th>Email</th>
            <th>Password</th>
        </tr>
        <%
        	try (Connection conn = ApplicationDB.getConnection(); Statement stmt = conn.createStatement()) {
        		String sql = "Select User_id, endUser_login, email_address, password FROM enduser";
        		ResultSet rs = stmt.executeQuery(sql);
        		while(rs.next()) {
        			%>
        				<tr>
        					<td contenteditable="true" data-id=<%= rs.getInt("User_id") %> data-field="endUser_login" data-type="endUser" oninput="handleInput(this)"><%= rs.getString("endUser_login") %></td>
        					<td contenteditable="true" data-id=<%= rs.getInt("User_id") %> data-field="email_address" data-type="endUser" oninput="handleInput(this)"><%= rs.getString("email_address") %></td>
        					<td contenteditable="true" data-id=<%= rs.getInt("User_id") %> data-field="password" data-type="endUser" oninput="handleInput(this)"></td>
        					<td>	
        						<form id="deleteForm" method="POST" action="<%= request.getContextPath() + "/delete?origin=customerRep" %>">
  									<input type="hidden" name="id" value="<%= rs.getInt("User_id") %>">
  									<input type="hidden" name="type" value="endUser">
  									<button type="button" onclick="submitDeleteForm(this);">Delete User</button>
								</form>
							</td>
        				</tr>
        			<%
        		}
        		conn.close();
        		stmt.close();
        		rs.close();
        	} catch (SQLException e) {
        		e.printStackTrace();
        	}
        %>
    </table>
	<form id="editForm" method="POST" action="<%= request.getContextPath() + "/updateUser?origin=customerrep" %>">
  		<input type="hidden" id="editedData" name="editedData" value="">
  		<button type="button" onclick="sendEditedData()">Save Changes</button>
	</form>
	</div>
	<form method="POST" action="<%= request.getContextPath() + "/logout" %>">
    	<input type="hidden" name="logout" value="true"/>
    	<button type="submit" class="logoutButton">Logout</button>
	</form>
  	<script>
  	function handleInput(td) {
  		td.setAttribute('data-edited', 'true');
  	}
  	
  	function sendEditedData() {
  		const cells = document.querySelectorAll('td[data-edited="true"]');
  		const data = [];
  		cells.forEach(cell => {
  			const id = cell.dataset.id;
  			const field = cell.dataset.field;
  			const value = cell.innerText;
  			const type = cell.dataset.type;
  			data.push({ id: id, field: field, value: value, type: type });
  			cell.removeAttribute('data-edited');
  		});
  		  		
  		document.getElementById('editedData').value = JSON.stringify(data);
  		document.getElementById('editForm').submit();
  	}
  	
  	function submitDeleteForm(button) {
  	    button.closest('form').submit();
  	}
  	
  	function submitResponseForm(button) {
        const editedValue = button.parentElement.parentElement.previousElementSibling.innerText.trim();
        button.previousElementSibling.value = editedValue;
        
        button.closest('form').submit();
	}
  	
  	function showQuestions() {
  		document.getElementById('questions').style.display = 'block';
        document.getElementById('auctions').style.display = 'none';
        document.getElementById('bids').style.display = 'none';
        document.getElementById('main').style.display = 'none';
  	}
  	
  	function showAuctions() {
  		document.getElementById('questions').style.display = 'none';
        document.getElementById('auctions').style.display = 'block';
        document.getElementById('bids').style.display = 'none';
        document.getElementById('main').style.display = 'none';
    }
  	
  	function showBids() {
  		document.getElementById('questions').style.display = 'none';
        document.getElementById('auctions').style.display = 'none';
        document.getElementById('bids').style.display = 'block';
        document.getElementById('main').style.display = 'none';
    }
  	
  	function showUsers() {
  		document.getElementById('questions').style.display = 'none';
        document.getElementById('auctions').style.display = 'none';
        document.getElementById('bids').style.display = 'none';
        document.getElementById('main').style.display = 'block';
    }
  	</script>
</body>
</html>