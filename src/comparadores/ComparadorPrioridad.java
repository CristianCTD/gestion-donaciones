package comparadores;

import java.util.Comparator;
import modelo.SolicitudAyuda;

/**
 * Comparator utilizado por la PriorityQueue para ordenar las solicitudes
 * segun su urgencia (Nivel 1: Urgente, 2: Alta, 3: Media, 4: Baja).
 * En caso de igual prioridad, desempata por orden cronologico de fecha e ID (FIFO).
 * 
 * Asignatura: Estructuras de Datos - Unidad 3
 * IES: Universidad de Cartagena
 * Tutor: John Carlos Arrieta Arrieta
 * Estudiante: Cristian Torres
 */
public class ComparadorPrioridad implements Comparator<SolicitudAyuda> {

    @Override
    public int compare(SolicitudAyuda s1, SolicitudAyuda s2) {
        if (s1 == null && s2 == null) return 0;
        if (s1 == null) return 1;
        if (s2 == null) return -1;

        // Menor valor de nivel representa mayor prioridad (1 = URGENTE)
        int cmpPrioridad = Integer.compare(s1.getPrioridad().getNivel(), s2.getPrioridad().getNivel());
        if (cmpPrioridad != 0) {
            return cmpPrioridad;
        }

        // Si tienen igual prioridad, se atiende primero la solicitud con fecha anterior
        int cmpFecha = s1.getFechaSolicitud().compareTo(s2.getFechaSolicitud());
        if (cmpFecha != 0) {
            return cmpFecha;
        }

        return s1.getIdSolicitud().compareTo(s2.getIdSolicitud());
    }
}
