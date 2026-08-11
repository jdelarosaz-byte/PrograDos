/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author markd
 */
public class Factura {
    private String numeroFactura;
    private String cliente;
    private String fecha;
    private List<DetalleFactura> detalles;

    public Factura(String numeroFactura, String cliente, String fecha) {
        this.numeroFactura = numeroFactura;
        this.cliente = cliente;
        this.fecha = fecha;
        this.detalles = new ArrayList<>();
    }

    public void agregarDetalle(DetalleFactura detalle) {
        detalles.add(detalle);
    }

    public double calcularTotal() {
        double total = 0;
        for (DetalleFactura d : detalles) {
            total += d.getSubtotal();
        }
        return total;
    }

    // Getters
    public String getNumeroFactura() { return numeroFactura; }
    public String getCliente() { return cliente; }
    public String getFecha() { return fecha; }
    public List<DetalleFactura> getDetalles() { return detalles; } //rigo hueco
}