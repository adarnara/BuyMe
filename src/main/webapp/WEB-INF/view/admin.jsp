<%@ page import="java.sql.*" %>
<%@ page import="java.util.*" %>
<%@ page import="com.mybuy.utils.ApplicationDB" %>
<%@ page import="com.mybuy.model.TopThings" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Admin Page</title>
    <style>
        body {
            margin: 0;
            padding: 0;
            display: flex;
            justify-content: flex-start;
            align-items: flex-start;
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
	<button id="editUser">Edit Users</button>
	<button id="salesReport">Sales Reports</button>
	<div id="userPart">
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
        					<td contenteditable="true" data-id=<%=rs.getInt("User_id")%> data-field="endUser_login" data-type="endUser" oninput="handleInput(this)"><%=rs.getString("endUser_login")%></td>
        					<td contenteditable="true" data-id=<%=rs.getInt("User_id")%> data-field="email_address" data-type="endUser" oninput="handleInput(this)"><%=rs.getString("email_address")%></td>
        					<td contenteditable="true" data-id=<%=rs.getInt("User_id")%> data-field="password" data-type="endUser" oninput="handleInput(this)"></td>
        					<td>End User</td>
        					<td>	
        						<form id="deleteForm" method="POST" action="<%=request.getContextPath() + "/delete?origin=admin"%>">
  									<input type="hidden" name="id" value="<%=rs.getInt("User_id")%>">
  									<input type="hidden" name="type" value="endUser">
  									<button type="button" onclick="submitDeleteForm(this);">Delete User</button>
								</form>
							</td>
        				</tr>
        			<%
        			}
        			        		String sql2 = "Select CustomerRep_ID, CustomerRep_login, email_address, password FROM CustomerRep";
        			        		ResultSet rs2 = stmt.executeQuery(sql2);
        			        		while(rs2.next()) {
        			%>
        				<tr>
        					<td contenteditable="true" data-id=<%=rs2.getInt("CustomerRep_ID")%> data-field="CustomerRep_login" data-type="customerRep" oninput="handleInput(this)"><%=rs2.getString("CustomerRep_login")%></td>
        					<td contenteditable="true" data-id=<%=rs2.getInt("CustomerRep_ID")%> data-field="email_address" data-type="customerRep" oninput="handleInput(this)"><%=rs2.getString("email_address")%></td>
        					<td contenteditable="true" data-id=<%=rs2.getInt("CustomerRep_ID")%> data-field="password" data-type="customerRep" oninput="handleInput(this)"></td>
        					<td>Customer Rep</td>
        					<td>	
        						<form id="deleteForm" method="POST" action="<%=request.getContextPath() + "/delete?origin=admin"%>">
  									<input type="hidden" name="id" value="<%=rs2.getInt("CustomerRep_ID")%>">
  									<input type="hidden" name="type" value="customerRep">
  									<button type="button" onclick="submitDeleteForm(this);">Delete User</button>
								</form>
							</td>
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
	<form id="editForm" method="POST" action="<%=request.getContextPath() + "/updateUser?origin=admin"%>">
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
    </div>
    <div id="salesPart" style="display: none;">
    	<form action="<%=request.getContextPath() + "/salesReport"%>" method="get">
        	<input type="hidden" name="action" value="totalEarnings">
        	<button type="submit">View Total Earnings</button>
    	</form>
    	<form action="<%=request.getContextPath() + "/salesReport"%>" method="get">
        	<input type="hidden" name="action" value="earningsPer">
        	<button type="submit">View Earnings Per Type</button>
    	</form>
    	<form action="<%=request.getContextPath() + "/salesReport"%>" method="get">
        	<input type="hidden" name="action" value="bestItems">
        	<button type="submit">View Best-Selling Items</button>
    	</form>
    	<form action="<%=request.getContextPath() + "/salesReport"%>" method="get">
        	<input type="hidden" name="action" value="bestBuyers">
        	<button type="submit">View Best Buyers</button>
    	</form>
    	<%
    		String data = (String) session.getAttribute("data");
    	    String activeTab = (String) session.getAttribute("activeTab");
    	    String activeTab2 = (String) session.getAttribute("activeTab2");	
    	%>
		<div id="totalEarnings" style="<%="totalEarnings".equals(activeTab) ? "display:block;" : "display:none;"%>">
    		<%=data%>
		</div>
		<div id="earningsPer" style="<%="earningsPer".equals(activeTab) ? "display:block;" : "display:none;"%>">
    		<form action="<%=request.getContextPath() + "/salesReport"%>" method="get">
        		<input type="hidden" name="action" value="perItem">
        		<button type="submit">View Gross Sales Per Item</button>
    		</form>
    		<form action="<%=request.getContextPath() + "/salesReport"%>" method="get">
        		<input type="hidden" name="action" value="perCategory">
        		<button type="submit">View Gross Sales Per Item Type</button>
    		</form>
    		<form action="<%=request.getContextPath() + "/salesReport"%>" method="get">
        		<input type="hidden" name="action" value="perBuyer">
        		<button type="submit">View Gross Sales Per Buyer</button>
    		</form>
    		<div id="perItem" style="<%="perItem".equals(activeTab2) ? "display:block;" : "display:none;"%>">
    		    	<table>
        <thead>
            <tr>
                <th>Item</th>
                <th>Total Spent</th>
            </tr>
        </thead>
        <tbody>
            <%
            List<TopThings> perItem = (List<TopThings>) request.getAttribute("perItem");
            	if (perItem != null) {
            		for (TopThings item : perItem) {
            %>
            <tr>
                <td><%= item.getUsername() %></td>
                <td><%= item.getTotalSales() %></td>
            </tr>
            <%  }
            } %>
        </tbody>
    	</table>
    		
			</div>
    		<div id="perCategory" style="<%="perCategory".equals(activeTab2) ? "display:block;" : "display:none;"%>">
    		    	<table>
        <thead>
            <tr>
                <th>Category</th>
                <th>Total Spent</th>
            </tr>
        </thead>
        <tbody>
            <%
            List<TopThings> perCategory = (List<TopThings>) request.getAttribute("perCategory");
            	if (perCategory != null) {
            		for (TopThings category : perCategory) {
            %>
            <tr>
                <td><%= category.getUsername() %></td>
                <td><%= category.getTotalSales() %></td>
            </tr>
            <%  }
            } %>
        </tbody>
    	</table>
			</div>
    		<div id="perBuyer" style="<%="perBuyer".equals(activeTab2) ? "display:block;" : "display:none;"%>">
    		    	<table>
        <thead>
            <tr>
                <th>Buyer</th>
                <th>Total Spent</th>
            </tr>
        </thead>
        <tbody>
            <%
            List<TopThings> perBuyer = (List<TopThings>) request.getAttribute("perBuyer");
            	if (perBuyer != null) {
            		for (TopThings buyer : perBuyer) {
            %>
            <tr>
                <td><%= buyer.getUsername() %></td>
                <td><%= buyer.getTotalSales() %></td>
            </tr>
            <%  }
            } %>
        </tbody>
    	</table>

			</div>
		</div>
		<div id="bestItems" style="<%="bestItems".equals(activeTab) ? "display:block;" : "display:none;"%>">
    		<form action="<%=request.getContextPath() + "/salesReport"%>" method="get">
    		<input type="hidden" name="action" value="bestItems">
    		Enter number of top items to display:
    		<input type="text" name="number1" placeholder="e.g., 5">
    		<button type="submit">Show Top Items</button>
			</form>
    	<table>
        <thead>
            <tr>
                <th>Name</th>
                <th>Total Spent</th>
            </tr>
        </thead>
        <tbody>
            <%
            List<TopThings> topItems = (List<TopThings>) request.getAttribute("topItems");
            	if (topItems != null) {
            		for (TopThings item : topItems) {
            %>
            <tr>
                <td><%= item.getUsername() %></td>
                <td><%= item.getTotalSales() %></td>
            </tr>
            <%  }
            } %>
        </tbody>
    	</table>
		</div>
		<div id="bestBuyers" style="<%="bestBuyers".equals(activeTab) ? "display:block;" : "display:none;"%>">
			<form action="<%=request.getContextPath() + "/salesReport"%>" method="get">
    		<input type="hidden" name="action" value="bestBuyers">
    		Enter number of top buyers to display:
    		<input type="text" name="number2" placeholder="e.g., 5">
    		<button type="submit">Show Top Buyers</button>
			</form>
    	<table>
        <thead>
            <tr>
                <th>Username</th>
                <th>Total Spent</th>
            </tr>
        </thead>
        <tbody>
            <%
            List<TopThings> topBuyers = (List<TopThings>) request.getAttribute("topBuyers");
                        if (topBuyers != null) {
                            for (TopThings buyer : topBuyers) {
            %>
            <tr>
                <td><%= buyer.getUsername() %></td>
                <td><%= buyer.getTotalSales() %></td>
            </tr>
            <%  }
            } %>
        </tbody>
    </table>
</div>
    </div>
	<form method="POST" action="<%= request.getContextPath() + "/logout" %>">
    	<input type="hidden" name="logout" value="true"/>
    	<button type="submit" class="logoutButton">Logout</button>
	</form>
	<%
		Boolean showSalesPart = (Boolean) request.getAttribute("showSalesPart");
		if (showSalesPart != null && showSalesPart) {
	%>
    <script>
        document.getElementById('userPart').style.display = 'none';
        document.getElementById('salesPart').style.display = 'block';
    </script>
	<%
		}
	%>
  	<script>
 
  	document.getElementById('editUser').addEventListener('click', function() {
  	    document.getElementById('userPart').style.display = 'block'; // Show Div 1
  	    document.getElementById('salesPart').style.display = 'none';   // Hide Div 2
  	});
  	document.getElementById('salesReport').addEventListener('click', function() {
  	    document.getElementById('salesPart').style.display = 'block'; // Show Div 1
  	    document.getElementById('userPart').style.display = 'none';   // Hide Div 2
  	});
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
  	</script>
</body>
</html>