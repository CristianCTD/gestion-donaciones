package modelo;

import java.util.Objects;

public class Donante {
    private String idDonante;
    private String nombre;
    private String email;
    private String telefono;
    private String tipoDonante;

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
