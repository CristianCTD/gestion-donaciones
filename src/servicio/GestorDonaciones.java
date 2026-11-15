package servicio;

import comparadores.ComparadorPrioridad;
import java.util.*;
import modelo.Donante;
import modelo.PrioridadEnum;
import modelo.SolicitudAyuda;

public class GestorDonaciones {

    private final List<SolicitudAyuda> listaGeneral;

    private final Set<String> tiposRecursosUnicos;
    private final Set<String> correosDonantes;

    private final Map<String, SolicitudAyuda> mapaPorId;

    private final TreeSet<SolicitudAyuda> conjuntoOrdenado;

    private final PriorityQueue<SolicitudAyuda> colaPrioridad;

    private final Map<String, Donante> donantesPorId;

    public GestorDonaciones() {
        this.listaGeneral = new ArrayList<>();
        this.tiposRecursosUnicos = new LinkedHashSet<>();
        this.correosDonantes = new HashSet<>();
        this.mapaPorId = new HashMap<>();
        this.conjuntoOrdenado = new TreeSet<>();
        this.colaPrioridad = new PriorityQueue<>(new ComparadorPrioridad());
        this.donantesPorId = new HashMap<>();

        inicializarTiposRecursosBase();
        cargar15DatosDePrueba();
    }

    private void inicializarTiposRecursosBase() {
        tiposRecursosUnicos.add("Alimentos No Perecederos");
        tiposRecursosUnicos.add("Medicamentos e Insumos Medicos");
        tiposRecursosUnicos.add("Ropa y Calzado");
        tiposRecursosUnicos.add("Kits Escolares");
        tiposRecursosUnicos.add("Aporte Economico");
        tiposRecursosUnicos.add("Materiales de Construccion");
    }

    private void cargar15DatosDePrueba() {

        Donante d1 = new Donante("DNT-01", "Fundacion Semillas del Sinu", "contacto@semillas.org", "3118901234", "EMPRESARIAL");
        Donante d2 = new Donante("DNT-02", "Supermercados San Jeronimo", "donaciones@sanjeronimo.com", "3004567890", "EMPRESARIAL");
        Donante d3 = new Donante("DNT-03", "Dra. Maria Elena Restrepo", "mrestrepo@gmail.com", "3156789012", "INDIVIDUAL");
        Donante d4 = new Donante("DNT-04", "Cooperativa Ganadera de Cerete", "gerencia@cooganac.com", "3201234567", "EMPRESARIAL");
        Donante d5 = new Donante("DNT-05", "Ing. Carlos Mendoza", "cmendoza@yahoo.es", "3189998877", "INDIVIDUAL");

        registrarDonante(d1);
        registrarDonante(d2);
        registrarDonante(d3);
        registrarDonante(d4);
        registrarDonante(d5);

        SolicitudAyuda[] pruebas = {
            new SolicitudAyuda("SOL-101", d1, "Comedor Comunitario Cerete", "Alimentos No Perecederos", 250, PrioridadEnum.URGENTE, "2026-11-01", "REGISTRADA", "Raciones de arroz, granos y aceite para 80 ninos"),
            new SolicitudAyuda("SOL-102", d3, "Puesto de Salud Rabolargo", "Medicamentos e Insumos Medicos", 120, PrioridadEnum.URGENTE, "2026-11-02", "REGISTRADA", "Antibioticos y sueros de rehidratacion oral"),
            new SolicitudAyuda("SOL-103", d2, "Hogar Geriatrico San Jose", "Alimentos No Perecederos", 180, PrioridadEnum.ALTA, "2026-11-03", "REGISTRADA", "Suplementos alimenticios y leche en polvo"),
            new SolicitudAyuda("SOL-104", d4, "Escuela Rural Manguelito", "Kits Escolares", 90, PrioridadEnum.MEDIA, "2026-11-04", "REGISTRADA", "Cuadernos, lapices y mochilas escolares"),
            new SolicitudAyuda("SOL-105", d5, "Comunidad Afectada por Inundacion", "Ropa y Calzado", 300, PrioridadEnum.URGENTE, "2026-11-05", "REGISTRADA", "Prendas impermeables, botas de caucho y cobijas"),
            new SolicitudAyuda("SOL-106", d1, "Junta de Accion Comunal El Quemado", "Materiales de Construccion", 50, PrioridadEnum.BAJA, "2026-11-06", "REGISTRADA", "Laminas de zinc y cemento para salon comunal"),
            new SolicitudAyuda("SOL-107", d3, "Clinica Infantil Monteria", "Medicamentos e Insumos Medicos", 80, PrioridadEnum.ALTA, "2026-11-07", "REGISTRADA", "Gasas, analgesicos pediatricos y jeringas"),
            new SolicitudAyuda("SOL-108", d2, "Refugio de Animales Cerete", "Alimentos No Perecederos", 100, PrioridadEnum.BAJA, "2026-11-08", "REGISTRADA", "Alimento concentrado para mascotas rescatadas"),
            new SolicitudAyuda("SOL-109", d4, "Fundacion Juventud con Futuro", "Aporte Economico", 15, PrioridadEnum.MEDIA, "2026-11-09", "REGISTRADA", "Subsidios para transporte de estudiantes universitarios"),
            new SolicitudAyuda("SOL-110", d5, "Comunidad Indigena Zenú", "Kits Escolares", 110, PrioridadEnum.ALTA, "2026-11-10", "REGISTRADA", "Material didactico bilingue y uniformes"),
            new SolicitudAyuda("SOL-111", d1, "Albergue Temporal Los Almendros", "Ropa y Calzado", 140, PrioridadEnum.MEDIA, "2026-11-11", "REGISTRADA", "Sabanas, toallas y ropa para adultos mayores"),
            new SolicitudAyuda("SOL-112", d3, "Brigada Medica Rural Severá", "Medicamentos e Insumos Medicos", 60, PrioridadEnum.URGENTE, "2026-11-12", "REGISTRADA", "Tratamientos para enfermedades tropicales e infecciosas"),
            new SolicitudAyuda("SOL-113", d2, "Jardin Infantil Mis Primeros Pasos", "Alimentos No Perecederos", 95, PrioridadEnum.ALTA, "2026-11-13", "REGISTRADA", "Cereales infantiles y galletas fortificadas"),
            new SolicitudAyuda("SOL-114", d4, "Centro Cultural y Deportivo Cerete", "Materiales de Construccion", 40, PrioridadEnum.BAJA, "2026-11-14", "REGISTRADA", "Pintura y baldosas para adecuacion de cancha"),
            new SolicitudAyuda("SOL-115", d5, "Hospital San Diego de Cerete", "Medicamentos e Insumos Medicos", 200, PrioridadEnum.URGENTE, "2026-11-14", "REGISTRADA", "Soluciones salinas e insumos para sala de urgencias")
        };

        for (SolicitudAyuda sol : pruebas) {
            registrarSolicitud(sol);
        }
    }

    public boolean registrarDonante(Donante donante) {
        if (donante == null || donante.getIdDonante() == null || donante.getIdDonante().trim().isEmpty()) {
            return false;
        }
        if (donantesPorId.containsKey(donante.getIdDonante())) {
            return false;
        }
        if (donante.getEmail() != null && !donante.getEmail().trim().isEmpty()) {
            if (!correosDonantes.add(donante.getEmail().trim().toLowerCase())) {

                return false;
            }
        }
        donantesPorId.put(donante.getIdDonante(), donante);
        return true;
    }

    public Donante buscarDonantePorId(String idDonante) {
        return donantesPorId.get(idDonante);
    }

    public Collection<Donante> obtenerTodosLosDonantes() {
        return donantesPorId.values();
    }

    public boolean registrarSolicitud(SolicitudAyuda solicitud) {
        if (solicitud == null || solicitud.getIdSolicitud() == null || solicitud.getIdSolicitud().trim().isEmpty()) {
            System.out.println("[Error] El identificador de la solicitud no puede estar vacio.");
            return false;
        }
        String id = solicitud.getIdSolicitud().trim();

        if (mapaPorId.containsKey(id)) {
            System.out.println("[Error] Ya existe una solicitud registrada con el ID: " + id);
            return false;
        }

        if (solicitud.getTipoRecurso() != null && !solicitud.getTipoRecurso().trim().isEmpty()) {
            tiposRecursosUnicos.add(solicitud.getTipoRecurso().trim());
        }

        listaGeneral.add(solicitud);
        mapaPorId.put(id, solicitud);
        conjuntoOrdenado.add(solicitud);
        colaPrioridad.offer(solicitud);

        return true;
    }

    public List<SolicitudAyuda> getListaGeneral() {
        return Collections.unmodifiableList(listaGeneral);
    }

    public SolicitudAyuda buscarPorId(String idSolicitud) {
        if (idSolicitud == null) return null;
        return mapaPorId.get(idSolicitud.trim());
    }

    public boolean existeSolicitud(String idSolicitud) {
        if (idSolicitud == null) return false;
        return mapaPorId.containsKey(idSolicitud.trim());
    }

    public boolean agregarTipoRecurso(String nuevoTipo) {
        if (nuevoTipo == null || nuevoTipo.trim().isEmpty()) return false;
        return tiposRecursosUnicos.add(nuevoTipo.trim());
    }

    public Set<String> getTiposRecursosUnicos() {
        return Collections.unmodifiableSet(tiposRecursosUnicos);
    }

    public Set<SolicitudAyuda> getSolicitudesOrdenadasPorFechaEId() {
        return Collections.unmodifiableSet(conjuntoOrdenado);
    }

    public void agregarAColaPrioridad(SolicitudAyuda solicitud) {
        if (solicitud != null) {
            colaPrioridad.offer(solicitud);
        }
    }

    public SolicitudAyuda consultarProximoPrioritario() {
        return colaPrioridad.peek();
    }

    public SolicitudAyuda procesarSiguientePrioritario() {
        SolicitudAyuda atendido = colaPrioridad.poll();
        if (atendido != null) {
            atendido.setEstado("ATENDIDA");

        }
        return atendido;
    }

    public int getTamanioColaPrioridad() {
        return colaPrioridad.size();
    }

    public boolean modificarSolicitud(String idSolicitud, String nuevoBeneficiario,
                                      int nuevaCantidad, PrioridadEnum nuevaPrioridad,
                                      String nuevaDescripcion) {
        SolicitudAyuda sol = buscarPorId(idSolicitud);
        if (sol == null) {
            return false;
        }

        boolean cambiaPrioridad = (nuevaPrioridad != null && nuevaPrioridad != sol.getPrioridad());

        if (cambiaPrioridad) {
            colaPrioridad.remove(sol);
        }

        if (nuevoBeneficiario != null && !nuevoBeneficiario.trim().isEmpty()) {
            sol.setBeneficiario(nuevoBeneficiario.trim());
        }
        if (nuevaCantidad > 0) {
            sol.setCantidad(nuevaCantidad);
        }
        if (nuevaPrioridad != null) {
            sol.setPrioridad(nuevaPrioridad);
        }
        if (nuevaDescripcion != null && !nuevaDescripcion.trim().isEmpty()) {
            sol.setDescripcion(nuevaDescripcion.trim());
        }

        if (cambiaPrioridad) {
            colaPrioridad.offer(sol);
        }

        return true;
    }

    public boolean eliminarSolicitud(String idSolicitud) {
        SolicitudAyuda sol = buscarPorId(idSolicitud);
        if (sol == null) {
            return false;
        }

        mapaPorId.remove(idSolicitud.trim());

        listaGeneral.remove(sol);

        conjuntoOrdenado.remove(sol);

        colaPrioridad.remove(sol);

        return true;
    }

    public Map<String, Integer> obtenerMetricasColecciones() {
        Map<String, Integer> metricas = new LinkedHashMap<>();
        metricas.put("List (ArrayList - Registros Totales)", listaGeneral.size());
        metricas.put("Map (HashMap - Indices por ID)", mapaPorId.size());
        metricas.put("Set (LinkedHashSet - Tipos de Recursos Unicos)", tiposRecursosUnicos.size());
        metricas.put("SortedSet (TreeSet - Solicitudes Ordenadas)", conjuntoOrdenado.size());
        metricas.put("Queue (PriorityQueue - Solicitudes por Prioridad)", colaPrioridad.size());
        metricas.put("Map Donantes (Donantes registrados)", donantesPorId.size());
        return metricas;
    }
}
