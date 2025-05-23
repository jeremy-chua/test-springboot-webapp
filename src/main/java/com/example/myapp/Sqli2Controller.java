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
public class Sqli2Controller {

    private static final String DB_CONN_STR = "jdbc://h2:mem:test2db";
    private static final String DB_USER = "sa";
    private static final String DB_PASSWORD = "@#2kjndf3nkj!";

    @GetMapping("/sqli2")
    public String vulnerable(@RequestParam String str, @RequestParam int id) {
        StringBuilder result = new StringBuilder();

        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DriverManager.getConnection(DB_CONN_STR, DB_USER, DB_PASSWORD);
            String query = "SELECT id, name FROM tbl WHERE username = '" + str + "' AND id = " + Integer.toString(id);
            stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                result.append("User: ").append(rs.getString("username")).append("<br>");
            }

        } catch (SQLException e) {
            return "SQL Error: " + e.getMessage();
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        } finally {
            if (stmt != null)
                try {
                    stmt.close();
                } catch (SQLException e) {
                    // e.printStackTrace();
                }
            if (conn != null)
                try {
                    conn.close();
                } catch (SQLException e) {
                    // e.printStackTrace();
                }
        }

        return result.toString();
    }
}