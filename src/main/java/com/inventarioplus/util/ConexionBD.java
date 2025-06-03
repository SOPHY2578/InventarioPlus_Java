package com.inventarioplus.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {
    // Datos de conexión (ajusta según tu configuración)
    private static final String URL = "jdbc:mysql://localhost:3306/InventarioPlus_Java";
    private static final String USER = "root";
    private static final String PASSWORD = ""; // Tu contraseña si tiene
    
    public static Connection getConnection() throws SQLException {
        try {
            // Registrar el driver (opcional en versiones recientes de JDBC)
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            throw new SQLException("Error al cargar el driver JDBC", e);
        }
    }
}