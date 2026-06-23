package com.femcoders.config;

import com.femcoders.view.Colors;
import io.github.cdimascio.dotenv.Dotenv;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBManager {

    static Dotenv dotenv = Dotenv.load();

    private static final String URL = "jdbc:postgresql://localhost:5432/feminist_library";
    private static final String USER = dotenv.get("DB_USER");
    private static final String PASS = dotenv.get("DB_PASS");
    private static Connection connection;

    public static Connection getConnection() {
        try {
            // System.out.println("Conexión a la base de datos");
            connection = DriverManager.getConnection(URL, USER, PASS);
        } catch (Exception e) {
            System.out.println(Colors.RED + "❌ Database connection failed: " + e.getMessage() + Colors.RESET);
        }
        return connection;
    }

    public static void closeConnection(){
        try {
            // System.out.println("Conexión a la base de datos cerrada");
            connection.close();
        } catch (Exception e) {
            System.out.println(Colors.RED + "❌ Error closing connection: " + e.getMessage() + Colors.RESET);
        }
    }
}
