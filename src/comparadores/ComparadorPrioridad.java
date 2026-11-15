package comparadores;

import java.util.Comparator;
import modelo.SolicitudAyuda;

public class ComparadorPrioridad implements Comparator<SolicitudAyuda> {

    @Override
    public int compare(SolicitudAyuda s1, SolicitudAyuda s2) {
        if (s1 == null && s2 == null) return 0;
        if (s1 == null) return 1;
        if (s2 == null) return -1;

        int cmpPrioridad = Integer.compare(s1.getPrioridad().getNivel(), s2.getPrioridad().getNivel());
        if (cmpPrioridad != 0) {
            return cmpPrioridad;
        }

        int cmpFecha = s1.getFechaSolicitud().compareTo(s2.getFechaSolicitud());
        if (cmpFecha != 0) {
            return cmpFecha;
        }

        return s1.getIdSolicitud().compareTo(s2.getIdSolicitud());
    }
}
