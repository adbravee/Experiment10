import java.sql.*;
import java.util.Scanner;
import java.io.IOException;

public class App {

    public static void main(String[] args) {

        // Use try-with-resources to prevent resource leak
        try (Scanner scanner = new Scanner(System.in)) {

            System.out.print("Enter username: ");
            String userInput = scanner.nextLine();

            // Get DB password from environment variable (secure)
            String dbPassword = System.getenv("DB_PASSWORD");

            if (dbPassword == null || dbPassword.isEmpty()) {
                System.err.println("Error: DB_PASSWORD environment variable not set.");
                return;
            }

            String url = "jdbc:mysql://localhost:3306/testdb";
            String dbUser = "root";

            // Database operation with PreparedStatement (prevents SQL Injection)
            try (Connection conn = DriverManager.getConnection(url, dbUser, dbPassword);
                 PreparedStatement pstmt = conn.prepareStatement(
                         "SELECT username FROM users WHERE username = ?")) {

                pstmt.setString(1, userInput);
                ResultSet rs = pstmt.executeQuery();

                boolean found = false;

                while (rs.next()) {
                    System.out.println("User found: " + rs.getString("username"));
                    found = true;
                }

                if (!found) {
                    System.out.println("No user found.");
                }

            } catch (SQLException e) {
                System.err.println("Database error occurred.");
            }

            // Prevent command injection
            try {
                if (userInput.matches("^[a-zA-Z0-9._-]+$")) {

                    ProcessBuilder pb = new ProcessBuilder("cmd.exe", "/c", "dir", userInput);
                    pb.inheritIO(); // shows output in console
                    pb.start();

                } else {
                    System.out.println("Invalid input for command execution.");
                }

            } catch (IOException e) {
                System.err.println("Error executing system command.");
            }

        }
    }
}
