/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;
import modelo.DetalleFactura;
import modelo.Factura;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author markd
 */
public class FacturaDAO {
 
    public boolean guardar(Factura factura) {
        String sqlFactura = "INSERT INTO facturas (numero_factura, cliente, fecha, total) VALUES (?, ?, ?, ?)";
        String sqlDetalle = "INSERT INTO detalle_factura (factura_id, producto, precio, cantidad, subtotal) VALUES (?, ?, ?, ?, ?)";

        Connection con = Conexion.obtenerConexion();
        if (con == null) return false;

        try {
            con.setAutoCommit(false); // Iniciar transacción

            // 1. Insertar Cabecera de la Factura
            PreparedStatement psFactura = con.prepareStatement(sqlFactura, Statement.RETURN_GENERATED_KEYS);
            psFactura.setString(1, factura.getNumeroFactura());
            psFactura.setString(2, factura.getCliente());
            psFactura.setString(3, factura.getFecha());
           psFactura.setDouble(4, factura.calcularTotal());
            psFactura.executeUpdate();

            // Obtener el ID generado automáticamente por MySQL
            ResultSet rs = psFactura.getGeneratedKeys();
            int facturaId = 0;
            if (rs.next()) {
                facturaId = rs.getInt(1);
            }

            // 2. Insertar cada producto/detalle de la factura
            PreparedStatement psDetalle = con.prepareStatement(sqlDetalle);
            for (DetalleFactura df : factura.getDetalles()) {
                psDetalle.setInt(1, facturaId);
                psDetalle.setString(2, df.getProducto());
                psDetalle.setDouble(3, df.getPrecio());
                psDetalle.setInt(4, df.getCantidad());
                psDetalle.setDouble(5, df.getSubtotal());
                psDetalle.addBatch();
            }
            psDetalle.executeBatch();

            con.commit(); // Confirmar los cambios en la base de datos
            return true;

        } catch (SQLException e) {
            try {
                con.rollback(); // Deshacer cambios si ocurre algún error
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            System.err.println("Error al guardar la factura en la BD: " + e.getMessage());
            return false;
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public List<Factura> listar() {
        List<Factura> lista = new ArrayList<>();
        String sql = "SELECT * FROM facturas";

        try (Connection con = Conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Factura f = new Factura(
                    rs.getString("numero_factura"),
                    rs.getString("cliente"),
                    rs.getString("fecha")
                );
                lista.add(f);
            }
        } catch (SQLException e) {
            System.err.println("Error al consultar facturas desde la BD: " + e.getMessage());
        }
        return lista;
    }
}