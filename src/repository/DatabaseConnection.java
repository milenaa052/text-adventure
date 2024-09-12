package repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/text_adventure"; // Substitua pelo nome do seu banco
    private static final String USER = ""; // Substitua pelo seu usuário
    private static final String PASSWORD = ""; // Substitua pela sua senha

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
