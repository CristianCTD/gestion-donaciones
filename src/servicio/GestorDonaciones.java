package servicio;

import comparadores.ComparadorPrioridad;
import java.util.*;
import modelo.Donante;
import modelo.PrioridadEnum;
import modelo.SolicitudAyuda;

/**
 * Servicio central del sistema: GestorDonaciones.
 * Implementa y coordina las diferentes colecciones del Java Collections Framework:
 * 1. List (ArrayList): Coleccion principal y secuencial de todas las solicitudes.
 * 2. Set (LinkedHashSet): Control estricto de categorias/tipos de recursos unicos sin duplicados.
 * 3. Map (HashMap): Busqueda directa O(1) por identificador unico idSolicitud.
 * 4. SortedSet (TreeSet): Coleccion auto-ordenada por fecha y codigo (orden natural Comparable).
 * 5. Queue (PriorityQueue): Procesamiento selectivo segun nivel de prioridad (Urgente > Alta > Media > Baja).
 * 
 * Garantiza consistencia y sincronizacion bidireccional entre todas las estructuras.
 * 
 * Asignatura: Estructuras de Datos - Unidad 3
 * IES: Universidad de Cartagena
 * Tutor: John Carlos Arrieta Arrieta
 * Estudiante: Cristian Torres
 */
public class GestorDonaciones {

    // 1. Coleccion principal: List
    private final List<SolicitudAyuda> listaGeneral;

    // 2. Control de datos unicos: Set
    private final Set<String> tiposRecursosUnicos;
    private final Set<String> correosDonantes;

    // 3. Busqueda rapida O(1): Map
    private final Map<String, SolicitudAyuda> mapaPorId;

    // 4. Coleccion auto-ordenada: TreeSet
    private final TreeSet<SolicitudAyuda> conjuntoOrdenado;

    // 5. Cola de procesamiento por prioridad: PriorityQueue
    private final PriorityQueue<SolicitudAyuda> colaPrioridad;

    // Mapa auxiliar de donantes registrados por ID
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

    /**
     * Carga obligatoria de 15 registros de prueba completos y realistas (Seccion 17 de la rubrica)
     */
    private void cargar15DatosDePrueba() {
        // Donantes muestra
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

        // 15 Solicitudes de prueba con variedad intencional de prioridades, fechas y tipos
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
                // Email duplicado detectado por Set
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

    /**
     * Operacion 1: Registrar Solicitud con insercion sincronizada en todas las colecciones.
     */
    public boolean registrarSolicitud(SolicitudAyuda solicitud) {
        if (solicitud == null || solicitud.getIdSolicitud() == null || solicitud.getIdSolicitud().trim().isEmpty()) {
            System.out.println("[Error] El identificador de la solicitud no puede estar vacio.");
            return false;
        }
        String id = solicitud.getIdSolicitud().trim();

        // 1. Validacion de duplicidad mediante Map O(1)
        if (mapaPorId.containsKey(id)) {
            System.out.println("[Error] Ya existe una solicitud registrada con el ID: " + id);
            return false;
        }

        // 2. Control del tipo de recurso mediante Set
        if (solicitud.getTipoRecurso() != null && !solicitud.getTipoRecurso().trim().isEmpty()) {
            tiposRecursosUnicos.add(solicitud.getTipoRecurso().trim());
        }

        // 3. Sincronizacion consistente en todas las estructuras
        listaGeneral.add(solicitud);                     // List
        mapaPorId.put(id, solicitud);                    // Map
        conjuntoOrdenado.add(solicitud);                 // TreeSet
        colaPrioridad.offer(solicitud);                  // PriorityQueue

        return true;
    }

    /**
     * Operacion 2: Consultar todos los elementos de la coleccion principal (List)
     */
    public List<SolicitudAyuda> getListaGeneral() {
        return Collections.unmodifiableList(listaGeneral);
    }

    /**
     * Operacion 3: Busqueda instantanea por identificador usando Map (O(1))
     */
    public SolicitudAyuda buscarPorId(String idSolicitud) {
        if (idSolicitud == null) return null;
        return mapaPorId.get(idSolicitud.trim());
    }

    public boolean existeSolicitud(String idSolicitud) {
        if (idSolicitud == null) return false;
        return mapaPorId.containsKey(idSolicitud.trim());
    }

    /**
     * Operacion 4: Registrar y consultar tipos de recursos unicos (Set)
     */
    public boolean agregarTipoRecurso(String nuevoTipo) {
        if (nuevoTipo == null || nuevoTipo.trim().isEmpty()) return false;
        return tiposRecursosUnicos.add(nuevoTipo.trim());
    }

    public Set<String> getTiposRecursosUnicos() {
        return Collections.unmodifiableSet(tiposRecursosUnicos);
    }

    /**
     * Operacion 5: Mostrar informacion ordenada (TreeSet)
     */
    public Set<SolicitudAyuda> getSolicitudesOrdenadasPorFechaEId() {
        return Collections.unmodifiableSet(conjuntoOrdenado);
    }

    /**
     * Operacion 6: Agregar explicitamente a la cola de prioridad
     */
    public void agregarAColaPrioridad(SolicitudAyuda solicitud) {
        if (solicitud != null) {
            colaPrioridad.offer(solicitud);
        }
    }

    /**
     * Operacion 7: Consultar proximo elemento prioritario (peek) sin removerlo
     */
    public SolicitudAyuda consultarProximoPrioritario() {
        return colaPrioridad.peek();
    }

    /**
     * Operacion 8: Procesar y atender el siguiente elemento prioritario (poll)
     */
    public SolicitudAyuda procesarSiguientePrioritario() {
        SolicitudAyuda atendido = colaPrioridad.poll();
        if (atendido != null) {
            atendido.setEstado("ATENDIDA");
            // Se mantiene sincronizado el estado en el mapa y en la lista (apuntan a la misma referencia en heap)
        }
        return atendido;
    }

    public int getTamanioColaPrioridad() {
        return colaPrioridad.size();
    }

    /**
     * Operacion 9: Modificar solicitud manteniendo total consistencia entre colecciones
     */
    public boolean modificarSolicitud(String idSolicitud, String nuevoBeneficiario,
                                      int nuevaCantidad, PrioridadEnum nuevaPrioridad,
                                      String nuevaDescripcion) {
        SolicitudAyuda sol = buscarPorId(idSolicitud);
        if (sol == null) {
            return false;
        }

        // Si la prioridad cambia, la PriorityQueue debe re-balancearse
        boolean cambiaPrioridad = (nuevaPrioridad != null && nuevaPrioridad != sol.getPrioridad());

        if (cambiaPrioridad) {
            colaPrioridad.remove(sol);
        }

        // Actualizar atributos
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

    /**
     * Operacion 10: Eliminar solicitud sincronizando y retirando de TODAS las colecciones
     */
    public boolean eliminarSolicitud(String idSolicitud) {
        SolicitudAyuda sol = buscarPorId(idSolicitud);
        if (sol == null) {
            return false;
        }

        // 1. Remover de Map
        mapaPorId.remove(idSolicitud.trim());

        // 2. Remover de List
        listaGeneral.remove(sol);

        // 3. Remover de TreeSet
        conjuntoOrdenado.remove(sol);

        // 4. Remover de PriorityQueue
        colaPrioridad.remove(sol);

        return true;
    }

    /**
     * Resumen estadistico del estado de cada coleccion
     */
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
