package com.inventarioplus.model;

public class Producto {
    private int id;
    private String nombre;
    private int cantidad;
    private double precio;

    // Constructor vacío (obligatorio para JDBC)
    public Producto() {}

    // Constructor con parámetros
    public Producto(String nombre, int cantidad, double precio) {
        this.nombre = nombre;  // ¡Corregido el typo "nomore"!
        this.cantidad = cantidad;
        this.precio = precio;
    }

    // Getters y Setters (métodos para acceder a los atributos)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }
}
