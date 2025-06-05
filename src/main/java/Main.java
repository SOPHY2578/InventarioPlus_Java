import com.inventarioplus.model.Producto;
import com.inventarioplus.dao.ProductoDAO;
import java.sql.SQLException;
public class Main {
    public static void main(String[] args) {
        // 1. Crear instancia del DAO
        ProductoDAO dao = new ProductoDAO();
        
        // 2. Crear producto de prueba
        Producto producto = new Producto("Monitor 24\"", 10, 350.99);
        
        try {
            // 3. Insertar producto
            if (dao.insertarProducto(producto)) {
                System.out.println("✅ Insertado - ID: " + producto.getId());
                
                // 4. Actualizar producto
                producto.setCantidad(8);
                if (dao.actualizarProducto(producto)) {
                    System.out.println("✅ Actualizado - Nueva cantidad: " + producto.getCantidad());
                    
                    // 5. Consultar producto
                    Producto consultado = dao.obtenerProducto(producto.getId());
                    if (consultado != null) {
                        System.out.println("✅ Consultado - Nombre: " + consultado.getNombre());
                        
                        // 6. Eliminar producto (opcional)
                        if (dao.eliminarProducto(producto.getId())) {
                            System.out.println("✅ Eliminado - ID: " + producto.getId());
                        }
                    }
                }
            }
        } catch (SQLException e) {
            // ★★★ NUEVO MANEJO DE ERRORES (Opción 3) ★★★
            switch (e.getSQLState()) {
                case "23000": // Violación de constraint (ej: duplicados)
                    System.err.println("Error: El producto ya existe");
                    break;
                case "08001": // Error de conexión
                    System.err.println("Error al conectar con la base de datos");
                    break;
                case "23505": // Violación de unique key (otro tipo de duplicado)
                    System.err.println("Error: El ID o nombre del producto ya está registrado");
                    break;
                default:
                    System.err.printf("Error SQL [%s]: %s%n", 
                        e.getSQLState(), e.getMessage());
            }
            // (Opcional) Para depuración:
        }
    }
}