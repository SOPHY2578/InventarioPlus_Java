// ↓↓↓ REEMPLAZA LOS IMPORTS ACTUALES CON ESTOS ↓↓↓
import com.inventarioplus.model.Producto;
import com.inventarioplus.persistencia.ProductoDAO;
import java.sql.SQLException;
// ↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑


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
            System.err.println("❌ Error en operación CRUD: ");
            e.printStackTrace();
        }
    }
}
