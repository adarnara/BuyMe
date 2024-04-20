<%@ page import="java.sql.*" %>
<%@ page import="java.util.*" %>
<%@ page import="com.mybuy.utils.ApplicationDB" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Admin Page</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/stylesheets/loginAndRegisterStyle.css">
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
	<h1>You can edit usernames, passwords directly in the table. Click Save Changes to confirm.</h1>
    <table border="1">
        <tr>
            <th>Name</th>
            <th>Email</th>
            <th>Password</th>
            <th>User Type</th>
        </tr>
        <%
        	try (Connection conn = ApplicationDB.getConnection(); Statement stmt = conn.createStatement()) {
        		String sql = "Select User_id, endUser_login, email_address FROM enduser";
        		ResultSet rs = stmt.executeQuery(sql);
        		while(rs.next()) {
        			%>
        				<tr>
        					<td contenteditable="true" data-id=<%= rs.getInt("User_id") %> data-field="endUser_login" data-type="endUser" oninput="handleInput(this)"><%= rs.getString("endUser_login") %></td>
        					<td contenteditable="true" data-id=<%= rs.getInt("User_id") %> data-field="email_address" data-type="endUser" oninput="handleInput(this)"><%= rs.getString("email_address") %></td>
        					<td contenteditable="true" data-id=<%= rs.getInt("User_id") %> data-field="password" data-type="endUser" oninput="handleInput(this)"></td>
        					<td>End User</td>
        				</tr>
        			<%
        		}
        		String sql2 = "Select CustomerRep_ID, CustomerRep_login, email_address, password FROM CustomerRep";
        		ResultSet rs2 = stmt.executeQuery(sql2);
        		while(rs2.next()) {
        			%>
        				<tr>
        					<td contenteditable="true" data-id=<%= rs2.getInt("CustomerRep_ID") %> data-field="CustomerRep_login" data-type="customerRep" oninput="handleInput(this)"><%= rs2.getString("CustomerRep_login") %></td>
        					<td contenteditable="true" data-id=<%= rs2.getInt("CustomerRep_ID") %> data-field="email_address" data-type="customerRep" oninput="handleInput(this)"><%= rs2.getString("email_address") %></td>
        					<td contenteditable="true" data-id=<%= rs2.getInt("CustomerRep_ID") %> data-field="password" data-type="customerRep" oninput="handleInput(this)"></td>
        					<td>Customer Rep</td>
        				</tr>
        			<%
        		}
        		rs2.close();
        		rs.close();
        		stmt.close();
        		conn.close();
        	} catch (SQLException e) {
        		e.printStackTrace();
        	}
        %>
    </table>
	<form id="editForm" method="POST" action="<%= request.getContextPath() + "/updateUser?origin=admin" %>">
  		<input type="hidden" id="editedData" name="editedData" value="">
  		<button type="button" onclick="sendEditedData()">Save Changes</button>
	</form>
    <div class="signup">
        <form action="registerRep" method="post">
            <label for="chk" aria-hidden="true">Register New Customer Rep</label>
            <input type="text" name="username" placeholder="User name" required="">
            <input type="email" name="email" placeholder="Email" required="">
            <input type="password" name="password" id="reg-password" placeholder="Password" required="">
            <button type="submit">Register</button>
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
  	</script>
</body>
</html>