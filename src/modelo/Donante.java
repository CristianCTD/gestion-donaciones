package modelo;

import java.util.Objects;

/**
 * Entidad de dominio relacionada que representa la persona natural o juridica
 * benefactora que financia o provee los recursos de donacion.
 * 
 * Asignatura: Estructuras de Datos - Unidad 3
 * IES: Universidad de Cartagena
 * Tutor: John Carlos Arrieta Arrieta
 * Estudiante: Cristian Torres
 */
public class Donante {
    private String idDonante;      // Identificador unico (Cedula o NIT)
    private String nombre;         // Nombre completo o razon social
    private String email;          // Correo electronico para control de unicidad
    private String telefono;       // Telefono de contacto
    private String tipoDonante;    // "INDIVIDUAL" o "EMPRESARIAL"

    public Donante() {
    }

    public Donante(String idDonante, String nombre, String email, String telefono, String tipoDonante) {
        this.idDonante = idDonante;
        this.nombre = nombre;
        this.email = email;
        this.telefono = telefono;
        this.tipoDonante = tipoDonante;
    }

    public String getIdDonante() {
        return idDonante;
    }

    public void setIdDonante(String idDonante) {
        this.idDonante = idDonante;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getTipoDonante() {
        return tipoDonante;
    }

    public void setTipoDonante(String tipoDonante) {
        this.tipoDonante = tipoDonante;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Donante donante = (Donante) o;
        return Objects.equals(idDonante, donante.idDonante);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idDonante);
    }

    @Override
    public String toString() {
        return nombre + " [" + idDonante + "] (" + tipoDonante + " - " + email + ")";
    }
}
