package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySql {
    private static Connection connection;

    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/text_adventure",
                    "root",
                    ""
            );
            return connection;
        } catch (SQLException e) {
            System.out.println("Erro ao tentar conectar com o banco de dados.");
        } catch (ClassNotFoundException e) {
            System.out.println("Erro ao tentar importar o driver mysql");
        }
        return null;
    }
}