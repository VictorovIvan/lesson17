package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Class ConnectDataBase
 */
public class DBConnector {
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/Task01";
    private static final String USER = "postgres";
    private static final String PASS = "333999";

    /**
     * Connect to data base Task01
     *
     * @return Condition of the connection
     */

    public static Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(DB_URL, USER, PASS);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }
}
