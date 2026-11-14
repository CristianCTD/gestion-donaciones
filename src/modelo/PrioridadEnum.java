package modelo;

/**
 * Enumeracion que define los niveles de urgencia y atencion para las solicitudes de ayuda.
 * Asocia un valor numerico para facilitar comparaciones y ordenamiento en PriorityQueue.
 * 
 * Asignatura: Estructuras de Datos - Unidad 3
 * IES: Universidad de Cartagena
 * Tutor: John Carlos Arrieta Arrieta
 * Estudiante: Cristian Torres
 */
public enum PrioridadEnum {
    URGENTE(1, "Urgente"),
    ALTA(2, "Alta"),
    MEDIA(3, "Media"),
    BAJA(4, "Baja");

    private final int nivel;
    private final String descripcion;

    PrioridadEnum(int nivel, String descripcion) {
        this.nivel = nivel;
        this.descripcion = descripcion;
    }

    public int getNivel() {
        return nivel;
    }

    public String getDescripcion() {
        return descripcion;
    }

    @Override
    public String toString() {
        return descripcion + " (Nivel " + nivel + ")";
    }
}
