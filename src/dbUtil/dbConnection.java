package dbUtil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class dbConnection {

    private static final String username = "postgres";
    private static final String password = "10A3*beDF$3x";
    private static final String connection = "jdbc:postgresql://80.211.193.148:5432/przychodnia";

    /**
     * Metoda ustanawiająca połączenie z bazą na podstawie wartości pól w klasie.
     * @return Ustanawia połączenie z bazą lub w prrzypadku niepowodzenia zwraca null.
     * @throws SQLException
     */
    public static Connection getConnection() throws SQLException
    {
        try
        {
            Class.forName("org.postgresql.Driver");
            return DriverManager.getConnection(connection, username, password);
        }
        catch (ClassNotFoundException ex)
        {
            ex.printStackTrace();
        }
        return null;
    }
}
