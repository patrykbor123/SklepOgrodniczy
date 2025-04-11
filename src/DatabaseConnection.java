import java.sql.*;

public class DatabaseConnection {
    // Database connection parameters
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/sklepogrodniczy";
    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = "postgres";
    
    private static Connection connection = null;
    
    // Initialize the database with required tables
    static {
        try {
            // Load the JDBC driver
            Class.forName("org.postgresql.Driver");
            
            // Check if tables exist or create them
            createTablesIfNotExist();
        } catch (ClassNotFoundException e) {
            System.err.println("PostgreSQL JDBC driver not found: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("Database initialization error: " + e.getMessage());
        }
    }
    
    // Get connection to database
    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        }
        return connection;
    }
    
    // Close database connection
    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            System.err.println("Error closing connection: " + e.getMessage());
        }
    }
    
    // Create necessary tables if they don't exist
    private static void createTablesIfNotExist() throws SQLException {
        // Create users table
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            
            // Create users table
            String createUsersTable = 
                "CREATE TABLE IF NOT EXISTS users (" +
                "id SERIAL PRIMARY KEY, " +
                "first_name VARCHAR(50) NOT NULL, " +
                "last_name VARCHAR(50) NOT NULL, " +
                "email VARCHAR(100) NOT NULL UNIQUE, " +
                "username VARCHAR(50) NOT NULL UNIQUE, " +
                "password VARCHAR(100) NOT NULL, " +
                "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                ")";
            stmt.execute(createUsersTable);
            
            // Create garden advice table
            String createAdviceTable = 
                "CREATE TABLE IF NOT EXISTS garden_advice (" +
                "id SERIAL PRIMARY KEY, " +
                "title VARCHAR(100) NOT NULL, " +
                "content TEXT NOT NULL, " +
                "user_added BOOLEAN DEFAULT FALSE, " +
                "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                ")";
            stmt.execute(createAdviceTable);
            
            System.out.println("Database tables created successfully (if they didn't exist)");
        }
    }
}