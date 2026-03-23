import java.sql.*;
import java.util.Scanner;

public class App {

    public static void main(String[] args) {

        // Use try-with-resources (fixes resource leaks)
        try (Scanner scanner = new Scanner(System.in)) {

            System.out.print("Enter username: ");
            String userInput = scanner.nextLine();

            // Read DB password from environment variable (secure)
            String dbPassword = System.getenv("DB_PASSWORD");

            if (dbPassword == null) {
                System.err.println("Database password not set in environment variables.");
                return;
            }

            String url = "jdbc:mysql://localhost:3306/testdb";
            String dbUser = "root";

            // Secure DB connection + PreparedStatement (prevents SQL Injection)
            try (Connection conn = DriverManager.getConnection(url, dbUser, dbPassword);
                 PreparedStatement pstmt = conn.prepareStatement(
                         "SELECT * FROM users WHERE username = ?")) {

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
                // Proper logging (avoid exposing sensitive data)
                System.err.println("Database error occurred.");
            }

            // Safe command execution (avoid injection)
            if (userInput.matches("^[a-zA-Z0-9._-]+$")) {
                ProcessBuilder pb = new ProcessBuilder("cmd.exe", "/c", "dir", userInput);
                pb.start();
            } else {
                System.out.println("Invalid input for command execution.");
            }

        }
    }
}
