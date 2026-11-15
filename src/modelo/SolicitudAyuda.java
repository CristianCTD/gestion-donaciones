package modelo;

import java.util.Objects;

public class SolicitudAyuda implements Comparable<SolicitudAyuda> {
    private String idSolicitud;
    private Donante donante;
    private String beneficiario;
    private String tipoRecurso;
    private int cantidad;
    private PrioridadEnum prioridad;
    private String fechaSolicitud;
    private String estado;
    private String descripcion;

    public SolicitudAyuda() {
    }

    public SolicitudAyuda(String idSolicitud, Donante donante, String beneficiario,
                          String tipoRecurso, int cantidad, PrioridadEnum prioridad,
                          String fechaSolicitud, String estado, String descripcion) {
        this.idSolicitud = idSolicitud;
        this.donante = donante;
        this.beneficiario = beneficiario;
        this.tipoRecurso = tipoRecurso;
        this.cantidad = cantidad;
        this.prioridad = prioridad;
        this.fechaSolicitud = fechaSolicitud;
        this.estado = estado;
        this.descripcion = descripcion;
    }

    public String getIdSolicitud() {
        return idSolicitud;
    }

    public void setIdSolicitud(String idSolicitud) {
        this.idSolicitud = idSolicitud;
    }

    public Donante getDonante() {
        return donante;
    }

    public void setDonante(Donante donante) {
        this.donante = donante;
    }

    public String getBeneficiario() {
        return beneficiario;
    }

    public void setBeneficiario(String beneficiario) {
        this.beneficiario = beneficiario;
    }

    public String getTipoRecurso() {
        return tipoRecurso;
    }

    public void setTipoRecurso(String tipoRecurso) {
        this.tipoRecurso = tipoRecurso;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public PrioridadEnum getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(PrioridadEnum prioridad) {
        this.prioridad = prioridad;
    }

    public String getFechaSolicitud() {
        return fechaSolicitud;
    }

    public void setFechaSolicitud(String fechaSolicitud) {
        this.fechaSolicitud = fechaSolicitud;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SolicitudAyuda that = (SolicitudAyuda) o;
        return Objects.equals(idSolicitud, that.idSolicitud);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idSolicitud);
    }

    @Override
    public int compareTo(SolicitudAyuda o) {
        if (o == null) return 1;
        int cmpFecha = this.fechaSolicitud.compareTo(o.fechaSolicitud);
        if (cmpFecha != 0) {
            return cmpFecha;
        }
        return this.idSolicitud.compareTo(o.idSolicitud);
    }

    @Override
    public String toString() {
        return "[" + idSolicitud + "] (" + fechaSolicitud + ") " + tipoRecurso + " (Cant: " + cantidad + ")" +
               " | Beneficiario: " + beneficiario +
               " | Prioridad: [" + prioridad.name() + "]" +
               " | Estado: [" + estado + "]" +
               " | Donante: " + (donante != null ? donante.getNombre() : "N/A") +
               " | Detalle: " + descripcion;
    }
}
