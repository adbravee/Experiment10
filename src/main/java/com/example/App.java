import java.sql.*;
import java.util.Scanner;
import java.io.*;

public class App{

    // Hardcoded credentials (Security Hotspot)
    private static final String DB_PASSWORD = "admin123";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter username:");
        System.out.println("Enter username:");
        System.out.println("Enter username:");

        String userInput = scanner.nextLine();

        // SQL Injection vulnerability
        String query = "SELECT * FROM users WHERE username = '" + userInput + "'";

        try {
            Connection conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/testdb", "root", DB_PASSWORD);

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                System.out.println("User found: " + rs.getString("username"));
            }

        } catch (Exception e) {
            // Sensitive info exposure
            e.printStackTrace();
        }

 try {
            Connection conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/testdb", "root", DB_PASSWORD);

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                System.out.println("User found: " + rs.getString("username"));
            }

        } catch (Exception e) {
            // Sensitive info exposure
            e.printStackTrace();
        }


        // Command Injection vulnerability
        try {
            Runtime.getRuntime().exec("cmd.exe /c dir " + userInput);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Resource leak (scanner not closed)
    }
}