package com.example.myapp;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@RestController
public class SqliController {

    private static final String DB_CONN_STR = "jdbc://h2:mem:testdb";
    private static final String DB_USER = "sa";
    private static final String DB_PASSWORD = "@#2kjndf3nkj!";
    private static final String SECRET = "asdasd2W#I@#nk";

    @GetMapping("/sqli")
    public String vulnerable(@RequestParam String username) {
        StringBuilder result = new StringBuilder();

        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            // ⚠️ Insecure: Direct concatenation of user input into SQL
            conn = DriverManager.getConnection(DB_CONN_STR, DB_USER, DB_PASSWORD);
            String query = "SELECT id, name FROM users WHERE username = ?";
            stmt = conn.prepareStatement(query);
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                result.append("User: ").append(rs.getString("username")).append("<br>");
            }

            result.append("Secret: " + SECRET);

            stmt.close();
            conn.close();
        } catch (SQLException e) {
            return "SQL Error: " + e.getMessage();
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        } finally {

        }

        return result.toString();
    }
}