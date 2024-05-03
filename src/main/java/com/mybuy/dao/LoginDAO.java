package com.mybuy.dao;

import com.mybuy.model.Auction;
import com.mybuy.model.Login;
import com.mybuy.utils.ApplicationDB;
import com.mybuy.model.UserType;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class LoginDAO implements ILoginDAO {

    @Override
    public Login getUserByUsernameOrEmail(String usernameOrEmail) {
        Login login = null;
        login = getUserFromTable(usernameOrEmail, "Admin");
        if (login == null) {
            login = getUserFromTable(usernameOrEmail, "CustomerRep");
            if (login == null) {
                login = getUserFromTable(usernameOrEmail, "EndUser");
            }
        }
        return login;
    }

    //This is query helper function for getUserByUsernameOrEmail()
    private Login getUserFromTable(String usernameOrEmail, String tableName) {
        String sqlQueryStmt = getTableString(tableName);

        try (Connection conn = ApplicationDB.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sqlQueryStmt)) {

            pstmt.setString(1, usernameOrEmail);
            pstmt.setString(2, usernameOrEmail);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    String username = rs.getString("username");
                    String password = rs.getString("password");
                    String salt = rs.getString("salt");
                    Login login = new Login(usernameOrEmail, username, password, salt);
                    if (tableName.equals("EndUser")) {
                        String userType = rs.getString("user_type");
                        login.setUserType(UserType.fromString(userType));
                    } else {
                    	login.setUserType(UserType.fromString(tableName));
                    }
                    return login;
                }
            }
        }
        catch (SQLException e) {
            System.out.println("Couldn't fetch login data from " + tableName + ": " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    //Simpler checker for table string to get the right login column type, helper for getUserFromTable()
    private static String getTableString(String tableName) {
        String loginColumn;
        String usernameColumn;
        String userTypeColumn;

        if ("Admin".equals(tableName)) {
            loginColumn = "admin_login";
            usernameColumn = "admin_login";
            userTypeColumn = " ";
        }
        else if ("CustomerRep".equals(tableName)) {
            loginColumn = "CustomerRep_login";
            usernameColumn = "CustomerRep_login";
            userTypeColumn = " ";
        }
        else if ("EndUser".equals(tableName)) {
            loginColumn = "endUser_login";
            usernameColumn = "endUser_login";
            userTypeColumn = ", user_type ";
        }
        else {
            throw new IllegalArgumentException("Invalid table name");
        }
        
        String sqlQueryStmt = "SELECT password, salt, " + usernameColumn + " AS username" + userTypeColumn + "FROM " + tableName + " WHERE " + loginColumn + " = ? OR email_address = ?";
        return sqlQueryStmt;
    }

    @Override
    public String getEndUserType(String username) {
        String tableName = "EndUser";
        String sql = "SELECT * FROM " + tableName + " WHERE endUser_login = ?";

        try (Connection conn = ApplicationDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("user_type");
                }
            }
        } catch (SQLException e) {
            System.out.println("Error fetching end user by username: " + e.getMessage());
        }
        return "";
    }

    @Override
    public int getUserId(String username) {
        String tableName = "EndUser";
        String sql = "SELECT * FROM " + tableName + " WHERE endUser_login = ?";

        try (Connection conn = ApplicationDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("User_Id");
                }
            }
        } catch (SQLException e) {
            System.out.println("Error fetching user id by username: " + e.getMessage());
        }
        return -1;
    }

    @Override
    public List<Auction> getAuctions(String username) {
        String auctionTable = "Auction";
        String endUserTable = "EndUser";
        String sql = "SELECT a.*, eu_winner.endUser_login AS winner_username " +
                "FROM " + auctionTable + " AS a " +
                "INNER JOIN " + endUserTable + " AS eu ON a.User_Id = eu.User_Id " +
                "LEFT JOIN " + endUserTable + " AS eu_winner ON a.Winner = eu_winner.User_Id " +
                "WHERE eu.endUser_login = ?";

        List<Auction> auctions = new ArrayList<>();

        try (Connection conn = ApplicationDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);
            try (ResultSet rs = pstmt.executeQuery()) {
                while(rs.next()) {
                    Auction auction = new Auction(
                            rs.getInt("Auction_ID"),
                            rs.getDouble("Initial_Price"),
                            rs.getDouble("Current_Price"),
                            rs.getDate("Auction_Closing_Date"),
                            rs.getTime("Auction_Closing_Time"),
                            rs.getDouble("Bid_Increment"),
                            rs.getDouble("Minimum"),
                            rs.getInt("Winner"),
                            rs.getInt("User_Id"),
                            rs.getInt("Item_ID"),
                            rs.getString("auction_status")
                    );
                    if(auction.getWinner() != 0) {
                        auction.setWinnerUsername(rs.getString("winner_username"));
                    }
                    auctions.add(auction);
                }
                return auctions;
            }
        } catch (SQLException e) {
            System.out.println("Error fetching list of auctions by username: " + e.getMessage());
        }
        return null;
    }

    @Override
    public int addAuction(Auction auction) {
        String auctionTable = "Auction";
        String sql = "INSERT INTO " + auctionTable + " (Current_Price, Auction_Closing_Date, Auction_Closing_time, Bid_Increment, Initial_Price, Minimum, User_Id, Item_ID) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = ApplicationDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

                pstmt.setDouble(1, auction.getInitialPrice());
                pstmt.setDate(2, new java.sql.Date(auction.getAuctionClosingDate().getTime()));
                pstmt.setTime(3, new java.sql.Time(auction.getAuctionClosingTime().getTime()));
                pstmt.setDouble(4, auction.getBidIncrement());
                pstmt.setDouble(5, auction.getInitialPrice());
                pstmt.setDouble(6, auction.getMinimum());
                pstmt.setInt(7, auction.getUserId());
                pstmt.setInt(8, auction.getItemId());

                int rowsInserted = pstmt.executeUpdate();

                if (rowsInserted > 0) {
                    ResultSet rs = pstmt.getGeneratedKeys();
                    if (rs.next()) {
                        return rs.getInt(1);
                    }
                }
        }
        catch (SQLException e) {
            System.out.println("Error adding auction: " + e.getMessage());
        }

        return -1;
    }
}