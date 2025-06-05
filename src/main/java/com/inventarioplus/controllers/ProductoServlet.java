package com.inventarioplus.controllers;

import com.inventarioplus.dao.ProductoDAO;
import com.inventarioplus.model.Producto;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(name = "ProductoServlet", urlPatterns = {"/productos"})
public class ProductoServlet extends HttpServlet {
    private final ProductoDAO dao = new ProductoDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        
        try {
            if ("list".equals(action)) {
                request.setAttribute("productos", dao.listarProductos());
                request.getRequestDispatcher("/WEB-INF/views/listar.jsp").forward(request, response);
            } else {
                request.getRequestDispatcher("/WEB-INF/views/formulario.jsp").forward(request, response);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProductoServlet.class.getName()).log(Level.SEVERE, null, ex);
            response.sendRedirect("error.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // Obtener parámetros del formulario
            String nombre = request.getParameter("nombre");
            int cantidad = Integer.parseInt(request.getParameter("cantidad"));
            double precio = Double.parseDouble(request.getParameter("precio"));

            // Crear objeto Producto
            Producto producto = new Producto(nombre, cantidad, precio);

            // Insertar en la base de datos
            boolean insertarProducto = dao.insertarProducto(producto);

            // Redireccionar según el resultado
            if (insertarProducto) {
                response.sendRedirect("exito.jsp");
            } else {
                response.sendRedirect("error.jsp");
            }
            
        } catch (NumberFormatException e) {
            Logger.getLogger(ProductoServlet.class.getName()).log(Level.SEVERE, "Error en formato de número", e);
            response.sendRedirect("error.jsp?mensaje=Error en los datos numéricos");
        } catch (SQLException ex) {
            Logger.getLogger(ProductoServlet.class.getName()).log(Level.SEVERE, null, ex);
            response.sendRedirect("error.jsp?mensaje=Error en la base de datos");
        }
    }
}