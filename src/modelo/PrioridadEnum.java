package modelo;

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
