package com.example.isp_app.entidades;
public class Contactos {
    private int id;
    private String nombre;
    private String apellido;
    private String pin;
    private String ubicacion;

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

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }
    public String getUbicacion() {return ubicacion; }
    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

}
