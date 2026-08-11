/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author markd
 */
public class Producto {
    private int idProducto;
    private String nombre;
    private double precio;
    private int existencia;

    public Producto(int idProducto, String nombre, double precio, int existencia) {
        this.idProducto = idProducto;
        this.nombre = nombre;
        this.precio = precio;
        this.existencia = existencia;
    }

    public boolean hayExistencia(int cantidad) {
        return this.existencia >= cantidad;
    }

    // Getters y Setters
    public int getIdProducto() { return idProducto; }
    public void setIdProducto(int idProducto) { this.idProducto = idProducto; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public double getPrecio() { return precio; }
    public void setPrecio(double precio) { this.precio = precio; }
    public int getExistencia() { return existencia; }
    public void setExistencia(int existencia) { this.existencia = existencia; }
}
