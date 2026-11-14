package modelo;

import java.util.Objects;

/**
 * Entidad principal del Caso 30: Solicitud de Ayuda / Donacion.
 * Implementa Comparable para ordenamiento natural (utilizado por TreeSet)
 * y sobrescribe equals() y hashCode() basados en idSolicitud para garantizar
 * la correcta operacion en HashSet, HashMap y colecciones del Java Collections Framework.
 * 
 * Asignatura: Estructuras de Datos - Unidad 3
 * IES: Universidad de Cartagena
 * Tutor: John Carlos Arrieta Arrieta
 * Estudiante: Cristian Torres
 */
public class SolicitudAyuda implements Comparable<SolicitudAyuda> {
    private String idSolicitud;        // Identificador unico (ej. "SOL-101")
    private Donante donante;           // Relacion con la clase Donante
    private String beneficiario;       // Nombre de la comunidad, institucion o persona atendida
    private String tipoRecurso;        // Categoria (ej. "Alimentos", "Medicamentos", "Ropa", etc.)
    private int cantidad;              // Cantidad de raciones, unidades o kits
    private PrioridadEnum prioridad;   // Nivel de urgencia (URGENTE, ALTA, MEDIA, BAJA)
    private String fechaSolicitud;     // Fecha en formato yyyy-MM-dd
    private String estado;             // "REGISTRADA", "EN_PROCESO", "ATENDIDA", "CANCELADA"
    private String descripcion;        // Detalle de la necesidad

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

    /**
     * Regla estricta: equals() basado en el identificador unico idSolicitud
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SolicitudAyuda that = (SolicitudAyuda) o;
        return Objects.equals(idSolicitud, that.idSolicitud);
    }

    /**
     * Consistencia obligatoria con equals(): mismo idSolicitud produce mismo hash
     */
    @Override
    public int hashCode() {
        return Objects.hash(idSolicitud);
    }

    /**
     * Orden natural para TreeSet: primero por fecha de solicitud cronologica y luego por idSolicitud
     */
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
