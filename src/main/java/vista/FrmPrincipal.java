/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista;

/**
 *
 * @author markd
 */

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FrmPrincipal extends JFrame {

    private JDesktopPane desktopPane;

    public FrmPrincipal() {
        super("Sistema de Ventas - Menú MDI");
        this.setSize(1024, 768);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        desktopPane = new JDesktopPane();
        this.setContentPane(desktopPane);

        JMenuBar menuBar = new JMenuBar();
        JMenu menuVentas = new JMenu("Ventas");
        JMenuItem itemFacturacion = new JMenuItem("Facturación");

        itemFacturacion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirFormularioFactura();
            }
        });

        menuVentas.add(itemFacturacion);
        menuBar.add(menuVentas);
        this.setJMenuBar(menuBar);
    }

    private void abrirFormularioFactura() {
        FrmFacturas frmFactura = new FrmFacturas();
        
        desktopPane.add(frmFactura);
        frmFactura.setVisible(true);
        
        try {
            frmFactura.setSelected(true);
        } catch (java.beans.PropertyVetoException ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new FrmPrincipal().setVisible(true);
        });
    }
}
