package com.inventarioplus.dao;

import com.inventarioplus.model.Producto;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductoDAO {
    // 1. Configuración de conexión a MySQL
    private static final String URL = "jdbc:mysql://localhost:3306/inventarioplus";
    private static final String USER = "tu_usuario";
    private static final String PASS = "tu_contraseña";

    // 2. Método para obtener conexión
    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }

    // 3. INSERTAR Producto (Retorna boolean)
    public boolean insertarProducto(Producto producto) throws SQLException {
        String sql = "INSERT INTO productos (nombre, cantidad, precio) VALUES (?, ?, ?)";
        
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, producto.getNombre());
            pstmt.setInt(2, producto.getCantidad());
            pstmt.setDouble(3, producto.getPrecio());
            
            return pstmt.executeUpdate() > 0;
        }
    }

    // 4. LISTAR Productos (Para el método doGet)
    public List<Producto> listarProductos() throws SQLException {
        List<Producto> productos = new ArrayList<>();
        String sql = "SELECT * FROM productos";
        
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Producto p = new Producto();
                p.setId(rs.getInt("id"));
                p.setNombre(rs.getString("nombre"));
                p.setCantidad(rs.getInt("cantidad"));
                p.setPrecio(rs.getDouble("precio"));
                productos.add(p);
            }
        }
        return productos;
    }

    // 5. ACTUALIZAR Producto
    public boolean actualizarProducto(Producto producto) throws SQLException {
        String sql = "UPDATE productos SET nombre = ?, cantidad = ?, precio = ? WHERE id = ?";
        
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, producto.getNombre());
            pstmt.setInt(2, producto.getCantidad());
            pstmt.setDouble(3, producto.getPrecio());
            pstmt.setInt(4, producto.getId());
            
            return pstmt.executeUpdate() > 0;
        }
    }

    // 6. ELIMINAR Producto
    public boolean eliminarProducto(int id) throws SQLException {
        String sql = "DELETE FROM productos WHERE id = ?";
        
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;
        }
    }

    // 7. OBTENER Producto por ID (Para consultas)
    public Producto obtenerProducto(int id) throws SQLException {
        String sql = "SELECT * FROM productos WHERE id = ?";
        Producto producto = null;
        
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    producto = new Producto();
                    producto.setId(rs.getInt("id"));
                    producto.setNombre(rs.getString("nombre"));
                    producto.setCantidad(rs.getInt("cantidad"));
                    producto.setPrecio(rs.getDouble("precio"));
                }
            }
        }
        return producto;
    }
}