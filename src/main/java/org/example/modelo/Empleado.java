package org.example.modelo;

public class Empleado {

    private String ssn;
    private String nombre;
    private String direccion;
    private String telefono;
    private double salario;
    private String numeroUnion;

    public Empleado() {
    }

    public Empleado(String ssn, String nombre, String direccion,
                    String telefono, double salario, String numeroUnion) {

        this.ssn = ssn;
        this.nombre = nombre;
        this.direccion = direccion;
        this.telefono = telefono;
        this.salario = salario;
        this.numeroUnion = numeroUnion;
    }

    public String getSsn() {
        return ssn;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public double getSalario() {
        return salario;
    }

    public void setSalario(double salario) {
        this.salario = salario;
    }

    public String getNumeroUnion() {
        return numeroUnion;
    }

    public void setNumeroUnion(String numeroUnion) {
        this.numeroUnion = numeroUnion;
    }
}