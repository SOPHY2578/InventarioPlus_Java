package com.inventarioplus.persistencia;

import com.inventarioplus.model.Producto;
import com.inventarioplus.util.ConexionBD;
import java.sql.*;

public class ProductoDAO {

    // Insertar un producto (retorna boolean para confirmación)
    public boolean insertarProducto(Producto producto) throws SQLException {
        String sql = "INSERT INTO productos (nombre, cantidad, precio) VALUES (?, ?, ?)";
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setString(1, producto.getNombre());
            stmt.setInt(2, producto.getCantidad());
            stmt.setDouble(3, producto.getPrecio());
            int affectedRows = stmt.executeUpdate();

            // Obtener ID generado
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        producto.setId(generatedKeys.getInt(1));
                    }
                }
                return true;
            }
            return false;
        }
    }

    // Actualizar un producto
    public boolean actualizarProducto(Producto producto) throws SQLException {
        String sql = "UPDATE productos SET nombre = ?, cantidad = ?, precio = ? WHERE id = ?";
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, producto.getNombre());
            stmt.setInt(2, producto.getCantidad());
            stmt.setDouble(3, producto.getPrecio());
            stmt.setInt(4, producto.getId());
            
            return stmt.executeUpdate() > 0;
        }
    }

    // Eliminar un producto
    public boolean eliminarProducto(int id) throws SQLException {
        String sql = "DELETE FROM productos WHERE id = ?";
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        }
    }

    // Consultar un producto por ID
    public Producto obtenerProducto(int id) throws SQLException {
        String sql = "SELECT * FROM productos WHERE id = ?";
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Producto producto = new Producto();
                    producto.setId(rs.getInt("id"));
                    producto.setNombre(rs.getString("nombre"));
                    producto.setCantidad(rs.getInt("cantidad"));
                    producto.setPrecio(rs.getDouble("precio"));
                    return producto;
                }
            }
        }
        return null;
    }
}