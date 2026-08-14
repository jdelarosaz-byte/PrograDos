/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author markd
 */
public class Empleado extends Persona {
    private String codigoEmpleado;
    private String puesto;

    public Empleado(int id, String nombre, String nit, String telefono, String codigoEmpleado, String puesto) {
        super(id, nombre, nit, telefono);
        this.codigoEmpleado = codigoEmpleado;
        this.puesto = puesto;
    }

    public String getCodigoEmpleado() { return codigoEmpleado; }
    public void setCodigoEmpleado(String codigoEmpleado) { this.codigoEmpleado = codigoEmpleado; }
    public String getPuesto() { return puesto; }
    public void setPuesto(String puesto) { this.puesto = puesto; }
//prueba
    public String mostrarInformacion() {
        return "Empleado: " + nombre + " [" + codigoEmpleado + "] - " + puesto;
    }
}

