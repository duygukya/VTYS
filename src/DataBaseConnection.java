
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBaseConnection {
    public static Connection connect() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/yonetim";
            String user = "root";
            String password = "00308485Dd";
            return DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException ex) {
            throw new SQLException("MySQL JDBC Driver not found.", ex);
        }
    }
}

