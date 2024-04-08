package com.mybuy.dao;

import com.mybuy.model.Login;
import com.mybuy.utils.ApplicationDB;
import java.sql.*;

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
                    String password = rs.getString("password");
                    String salt = rs.getString("salt");
                    String username = rs.getString("username");
                    Login login = new Login(usernameOrEmail, password);
                    login.setSalt(salt);
                    login.setUserType(tableName);
                    login.setUsername(username);
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

        if ("Admin".equals(tableName)) {
            loginColumn = "admin_login";
            usernameColumn = "admin_login";
        }
        else if ("CustomerRep".equals(tableName)) {
            loginColumn = "CustomerRep_login";
            usernameColumn = "CustomerRep_login";
        }
        else if ("EndUser".equals(tableName)) {
            loginColumn = "endUser_login";
            usernameColumn = "endUser_login";
        }
        else {
            throw new IllegalArgumentException("Invalid table name");
        }

        String sqlQueryStmt = "SELECT password, salt, " + usernameColumn + " AS username FROM " + tableName + " WHERE " + loginColumn + " = ? OR email_address = ?";
        return sqlQueryStmt;
    }


}
