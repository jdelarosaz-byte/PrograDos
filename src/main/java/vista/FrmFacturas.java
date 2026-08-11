/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista;
import dao.FacturaDAO;
import modelo.DetalleFactura;
import modelo.Factura;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author markd
 */
public class FrmFacturas extends JInternalFrame {

    private JTextField txtNumero, txtCliente, txtFecha, txtProducto, txtPrecio, txtCantidad;
    private JLabel lblSubtotal, lblTotal;
    private JTable tablaProductos;
    private DefaultTableModel modeloTabla;
    private FacturaDAO facturaDAO;

    public FrmFacturas() {
        super("Formulario de Facturación", true, true, true, true);
        this.setSize(750, 550);
        this.facturaDAO = new FacturaDAO();

        this.setLayout(new BorderLayout(10, 10));

        // --- PANEL NORTE: CABECERA DE FACTURA ---
        JPanel panelCabecera = new JPanel(new GridLayout(2, 4, 10, 10));
        panelCabecera.setBorder(BorderFactory.createTitledBorder("Datos de la Factura"));

        // Fila 1 (4 elementos)
        panelCabecera.add(new JLabel("No. Factura:"));
        txtNumero = new JTextField("FAC-00" + (facturaDAO.listar().size() + 1));
        txtNumero.setEditable(false);
        panelCabecera.add(txtNumero);

        panelCabecera.add(new JLabel("Fecha:"));
        txtFecha = new JTextField(new SimpleDateFormat("dd/MM/yyyy").format(new Date()));
        panelCabecera.add(txtFecha);

        // Fila 2 (4 elementos para completar las 8 casillas requeridas)
        panelCabecera.add(new JLabel("Cliente:"));
        txtCliente = new JTextField();
        panelCabecera.add(txtCliente);

        // Espaciadores vacíos para mantener la estructura de 2x4
        panelCabecera.add(new JLabel("")); 
        panelCabecera.add(new JLabel("")); 

        this.add(panelCabecera, BorderLayout.NORTH);

        // --- PANEL CENTRO: INGRESO DE PRODUCTOS Y TABLA ---
        JPanel panelCentro = new JPanel(new BorderLayout(5, 5));

        // Subpanel para entrada del ítem
        JPanel panelInputProducto = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelInputProducto.setBorder(BorderFactory.createTitledBorder("Agregar Artículo"));

        txtProducto = new JTextField(12);
        txtPrecio = new JTextField(5);
        txtCantidad = new JTextField(4);
        JButton btnAgregar = new JButton("Agregar");

        panelInputProducto.add(new JLabel("Producto:"));
        panelInputProducto.add(txtProducto);
        panelInputProducto.add(new JLabel("Precio:"));
        panelInputProducto.add(txtPrecio);
        panelInputProducto.add(new JLabel("Cantidad:"));
        panelInputProducto.add(txtCantidad);
        panelInputProducto.add(btnAgregar);

        // Tabla de ítems
        modeloTabla = new DefaultTableModel(new String[]{"Producto", "Precio", "Cantidad", "Subtotal"}, 0);
        tablaProductos = new JTable(modeloTabla);
        JScrollPane scrollTabla = new JScrollPane(tablaProductos);

        panelCentro.add(panelInputProducto, BorderLayout.NORTH);
        panelCentro.add(scrollTabla, BorderLayout.CENTER);

        this.add(panelCentro, BorderLayout.CENTER);

        // --- PANEL SUR: TOTALES Y BOTONES DE ACCIÓN ---
        JPanel panelSur = new JPanel(new BorderLayout());

        JPanel panelTotales = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        lblSubtotal = new JLabel("Subtotal: $0.00");
        lblTotal = new JLabel("TOTAL: $0.00");
        lblTotal.setFont(new Font("Tahoma", Font.BOLD, 14));

        panelTotales.add(lblSubtotal);
        panelTotales.add(Box.createHorizontalStrut(20));
        panelTotales.add(lblTotal);

        JPanel panelAcciones = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton btnEliminar = new JButton("Eliminar Seleccionado");
        JButton btnGuardar = new JButton("Guardar Factura");

        panelAcciones.add(btnEliminar);
        panelAcciones.add(btnGuardar);

        panelSur.add(panelTotales, BorderLayout.NORTH);
        panelSur.add(panelAcciones, BorderLayout.SOUTH);

        this.add(panelSur, BorderLayout.SOUTH);

        // --- EVENTOS DE BOTONES ---
        btnAgregar.addActionListener(e -> agregarProductoATabla());
        btnEliminar.addActionListener(e -> eliminarProductoDeTabla());
        btnGuardar.addActionListener(e -> guardarFactura());
    }

    private void agregarProductoATabla() {
        try {
            String prod = txtProducto.getText().trim();
            double precio = Double.parseDouble(txtPrecio.getText().trim());
            int cantidad = Integer.parseInt(txtCantidad.getText().trim());

            if (prod.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Ingrese el nombre del producto.");
                return;
            }

            double subtotal = precio * cantidad;
            modeloTabla.addRow(new Object[]{prod, precio, cantidad, subtotal});

            limpiarCamposProducto();
            actualizarTotales();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Precio y Cantidad deben ser valores numéricos válidos.");
        }
    }

    private void eliminarProductoDeTabla() {
        int filaSeleccionada = tablaProductos.getSelectedRow();
        if (filaSeleccionada >= 0) {
            modeloTabla.removeRow(filaSeleccionada);
            actualizarTotales();
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione una fila para eliminar.");
        }
    }

    private void actualizarTotales() {
        double subtotalAcumulado = 0;
        for (int i = 0; i < modeloTabla.getRowCount(); i++) {
            subtotalAcumulado += (double) modeloTabla.getValueAt(i, 3);
        }
        lblSubtotal.setText(String.format("Subtotal: Q.%.2f", subtotalAcumulado));
        lblTotal.setText(String.format("TOTAL: Q.%.2f", subtotalAcumulado));
    }

    private void guardarFactura() {
        String cliente = txtCliente.getText().trim();
        if (cliente.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debe ingresar el nombre del cliente.");
            return;
        }

        if (modeloTabla.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "Debe agregar al menos un producto a la factura.");
            return;
        }

        // Crear objeto Factura
        Factura nuevaFactura = new Factura(txtNumero.getText(), cliente, txtFecha.getText());

        for (int i = 0; i < modeloTabla.getRowCount(); i++) {
            String prod = (String) modeloTabla.getValueAt(i, 0);
            double prec = (double) modeloTabla.getValueAt(i, 1);
            int cant = (int) modeloTabla.getValueAt(i, 2);
            nuevaFactura.agregarDetalle(new DetalleFactura(prod, prec, cant));
        }

        // Guardar vía DAO en el ArrayList en memoria
        if (facturaDAO.guardar(nuevaFactura)) {
            JOptionPane.showMessageDialog(this, "Factura guardada exitosamente en memoria.");
            this.dispose(); // Cerrar el formulario tras guardar
        }
    }

    private void limpiarCamposProducto() {
        txtProducto.setText("");
        txtPrecio.setText("");
        txtCantidad.setText("");
        txtProducto.requestFocus();
    }
}
