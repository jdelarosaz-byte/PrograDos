/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;
import modelo.Factura;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author markd
 */
public class FacturaDAO {
 
    private static final List<Factura> baseDatosMemoria = new ArrayList<>();

    public boolean guardar(Factura factura) {
        return baseDatosMemoria.add(factura);
    }

    public List<Factura> listar() {
        return baseDatosMemoria;
    }
}