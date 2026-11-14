import java.text.SimpleDateFormat;
import java.util.*;
import modelo.Donante;
import modelo.PrioridadEnum;
import modelo.SolicitudAyuda;
import servicio.GestorDonaciones;

/**
 * Clase principal de consola e interfaz de usuario para el Sistema de Donaciones.
 * Implementa el menu con las 11 operaciones obligatorias y salida (0).
 * 
 * Asignatura: Estructuras de Datos - Unidad 3
 * IES: Universidad de Cartagena
 * Tutor: John Carlos Arrieta Arrieta
 * Estudiante: Cristian Torres
 */
public class Main {

    private final GestorDonaciones gestor;
    private final Scanner scanner;

    public Main() {
        this.gestor = new GestorDonaciones();
        this.scanner = new Scanner(System.in);
    }

    public void iniciar() {
        int opcion = -1;
        do {
            mostrarMenu();
            opcion = leerEntero("Seleccione una opcion (0-11): ");
            System.out.println();
            switch (opcion) {
                case 1:
                    opcionRegistrarSolicitud();
                    break;
                case 2:
                    opcionConsultarTodas();
                    break;
                case 3:
                    opcionBuscarPorIdentificador();
                    break;
                case 4:
                    opcionRegistrarOConsultarDatosUnicos();
                    break;
                case 5:
                    opcionMostrarInformacionOrdenada();
                    break;
                case 6:
                    opcionAgregarElementoParaProcesamientoPrioritario();
                    break;
                case 7:
                    opcionConsultarProximoElementoPrioritario();
                    break;
                case 8:
                    opcionProcesarSiguienteElementoPrioritario();
                    break;
                case 9:
                    opcionModificar();
                    break;
                case 10:
                    opcionEliminar();
                    break;
                case 11:
                    opcionMostrarInformacionDeLasColecciones();
                    break;
                case 0:
                    System.out.println("=======================================================================");
                    System.out.println(" Gracias por utilizar el Sistema de Gestion de Donaciones y Ayuda.");
                    System.out.println(" Universidad de Cartagena - Ingenieria de Software - Unidad 3.");
                    System.out.println("=======================================================================");
                    break;
                default:
                    System.out.println("[Error] Opcion invalida. Ingrese un valor entre 0 y 11.");
            }
            if (opcion != 0) {
                System.out.println("\nPresione ENTER para continuar...");
                scanner.nextLine();
            }
        } while (opcion != 0);
    }

    private void mostrarMenu() {
        System.out.println("\n=======================================================================");
        System.out.println(" SISTEMA DE GESTION DE DONACIONES Y AYUDA HUMANITARIA (COLLECTIONS)");
        System.out.println(" Caso N. 30 | Estudiante: Cristian Torres | Universidad de Cartagena");
        System.out.println("=======================================================================");
        System.out.println(" 1. Registrar nueva solicitud de donacion / ayuda");
        System.out.println(" 2. Consultar todas las solicitudes (List / ArrayList)");
        System.out.println(" 3. Buscar solicitud por identificador unico (Map / HashMap)");
        System.out.println(" 4. Registrar o consultar tipos de recursos unicos (Set / HashSet)");
        System.out.println(" 5. Mostrar solicitudes ordenadas por fecha e ID (TreeSet)");
        System.out.println(" 6. Agregar solicitud a cola de procesamiento prioritario");
        System.out.println(" 7. Consultar proxima solicitud prioritaria (PriorityQueue.peek)");
        System.out.println(" 8. Atender / procesar siguiente solicitud prioritaria (PriorityQueue.poll)");
        System.out.println(" 9. Modificar solicitud (garantizando consistencia)");
        System.out.println(" 10. Eliminar solicitud (sincronizando todas las colecciones)");
        System.out.println(" 11. Mostrar estado y resumen metrico de las colecciones");
        System.out.println(" 0. Salir");
        System.out.println("=======================================================================");
    }

    // 1. Registrar
    private void opcionRegistrarSolicitud() {
        System.out.println("--- [1] REGISTRAR NUEVA SOLICITUD DE AYUDA ---");
        System.out.print("Ingrese ID de la solicitud (ej. SOL-116): ");
        String id = scanner.nextLine().trim();

        if (gestor.existeSolicitud(id)) {
            System.out.println("[Error de Duplicidad - Demostracion Map]:");
            System.out.println("   Ya existe una solicitud con el identificador '" + id + "'. El sistema impide duplicados.");
            return;
        }

        System.out.print("Nombre de la comunidad o institucion beneficiaria: ");
        String beneficiario = scanner.nextLine().trim();

        System.out.println("Tipos de recursos registrados actualmente (Set):");
        int idx = 1;
        List<String> recursosLista = new ArrayList<>(gestor.getTiposRecursosUnicos());
        for (String r : recursosLista) {
            System.out.println("   " + idx + ". " + r);
            idx++;
        }
        System.out.print("Seleccione numero de tipo de recurso o escriba uno nuevo: ");
        String entradaRecurso = scanner.nextLine().trim();
        String recursoFinal;
        try {
            int opcionRecurso = Integer.parseInt(entradaRecurso);
            if (opcionRecurso >= 1 && opcionRecurso <= recursosLista.size()) {
                recursoFinal = recursosLista.get(opcionRecurso - 1);
            } else {
                recursoFinal = entradaRecurso;
            }
        } catch (NumberFormatException e) {
            recursoFinal = entradaRecurso;
        }

        int cantidad = leerEntero("Cantidad requerida (unidades/raciones/kits): ");

        System.out.println("Nivel de prioridad:");
        System.out.println(" 1. URGENTE (Nivel 1 - Emergencia critica)");
        System.out.println(" 2. ALTA    (Nivel 2 - Vulnerabilidad severa)");
        System.out.println(" 3. MEDIA   (Nivel 3 - Atencion preventiva)");
        System.out.println(" 4. BAJA    (Nivel 4 - Apoyo complementario)");
        int opcPrioridad = leerEntero("Seleccione prioridad (1-4): ");
        PrioridadEnum prioridad;
        switch (opcPrioridad) {
            case 1: prioridad = PrioridadEnum.URGENTE; break;
            case 2: prioridad = PrioridadEnum.ALTA; break;
            case 4: prioridad = PrioridadEnum.BAJA; break;
            default: prioridad = PrioridadEnum.MEDIA; break;
        }

        System.out.print("Detalle o descripcion de la solicitud: ");
        String desc = scanner.nextLine().trim();

        // Asignar donante o crear uno
        System.out.println("Donantes disponibles:");
        for (Donante d : gestor.obtenerTodosLosDonantes()) {
            System.out.println(" - [" + d.getIdDonante() + "] " + d.getNombre());
        }
        System.out.print("Ingrese ID del donante (o presione ENTER para 'DNT-01'): ");
        String idDonante = scanner.nextLine().trim();
        if (idDonante.isEmpty()) idDonante = "DNT-01";
        Donante donante = gestor.buscarDonantePorId(idDonante);

        String fechaActual = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

        SolicitudAyuda nueva = new SolicitudAyuda(
            id, donante, beneficiario, recursoFinal, cantidad, prioridad, fechaActual, "REGISTRADA", desc
        );

        boolean exito = gestor.registrarSolicitud(nueva);
        if (exito) {
            System.out.println("[Exito] Solicitud registrada y sincronizada en List, Map, TreeSet y PriorityQueue.");
        }
    }

    // 2. Consultar todos (List)
    private void opcionConsultarTodas() {
        System.out.println("--- [2] CONSULTAR TODAS LAS SOLICITUDES (LIST / ARRAYLIST) ---");
        List<SolicitudAyuda> lista = gestor.getListaGeneral();
        System.out.println("Total de solicitudes registradas: " + lista.size());
        int i = 1;
        for (SolicitudAyuda s : lista) {
            System.out.println(" " + i + ". " + s);
            i++;
        }
    }

    // 3. Buscar por identificador (Map)
    private void opcionBuscarPorIdentificador() {
        System.out.println("--- [3] BUSCAR SOLICITUD POR IDENTIFICADOR UNICO (MAP / HASHMAP) ---");
        System.out.print("Ingrese el ID a buscar (ej. SOL-101, SOL-115 o SOL-999): ");
        String id = scanner.nextLine().trim();

        SolicitudAyuda resultado = gestor.buscarPorId(id);
        if (resultado != null) {
            System.out.println("[Encontrado en HashMap en tiempo O(1)]:");
            System.out.println("   Identificador : " + resultado.getIdSolicitud());
            System.out.println("   Beneficiario  : " + resultado.getBeneficiario());
            System.out.println("   Tipo Recurso  : " + resultado.getTipoRecurso());
            System.out.println("   Cantidad      : " + resultado.getCantidad());
            System.out.println("   Prioridad     : " + resultado.getPrioridad());
            System.out.println("   Fecha Registro: " + resultado.getFechaSolicitud());
            System.out.println("   Estado        : [" + resultado.getEstado() + "]");
            System.out.println("   Donante       : " + (resultado.getDonante() != null ? resultado.getDonante() : "Sin asignar"));
            System.out.println("   Descripcion   : " + resultado.getDescripcion());
        } else {
            System.out.println("[No encontrado] No existe ninguna solicitud registrada con el ID: " + id);
        }
    }

    // 4. Registrar o consultar datos unicos (Set)
    private void opcionRegistrarOConsultarDatosUnicos() {
        System.out.println("--- [4] CONTROL DE DATOS UNICOS MEDIANTE SET (LINKEDHASHSET) ---");
        System.out.println("Tipos de recursos y categorias unicas registradas actualmente:");
        int i = 1;
        for (String tipo : gestor.getTiposRecursosUnicos()) {
            System.out.println("   " + i + ". " + tipo);
            i++;
        }
        System.out.println();
        System.out.print("Desea ingresar un nuevo tipo de recurso para probar unicidad? (S/N): ");
        String resp = scanner.nextLine().trim();
        if (resp.equalsIgnoreCase("S")) {
            System.out.print("Ingrese el nombre del tipo de recurso: ");
            String nuevo = scanner.nextLine().trim();
            boolean agregado = gestor.agregarTipoRecurso(nuevo);
            if (agregado) {
                System.out.println("[Exito] '" + nuevo + "' agregado exitosamente al Set.");
            } else {
                System.out.println("[Demostracion de Unicidad - Set Rechazo Duplicado]:");
                System.out.println("   El elemento '" + nuevo + "' ya existia en el Set, por lo que no fue duplicado.");
            }
        }
    }

    // 5. Mostrar informacion ordenada (TreeSet)
    private void opcionMostrarInformacionOrdenada() {
        System.out.println("--- [5] MOSTRAR SOLICITUDES ORDENADAS (TREESET - COMPARABLE) ---");
        System.out.println("Criterio de orden: Cronologico por fecha de solicitud y desempatado por idSolicitud.");
        Set<SolicitudAyuda> ordenadas = gestor.getSolicitudesOrdenadasPorFechaEId();
        int i = 1;
        for (SolicitudAyuda s : ordenadas) {
            System.out.println("   " + i + ". " + s.getFechaSolicitud() + " | " + s.getIdSolicitud() +
                               " | " + s.getBeneficiario() + " | " + s.getPrioridad() + " | " + s.getTipoRecurso());
            i++;
        }
    }

    // 6. Agregar elemento para procesamiento prioritario
    private void opcionAgregarElementoParaProcesamientoPrioritario() {
        System.out.println("--- [6] AGREGAR SOLICITUD EXISTENTE A COLA PRIORITARIA ---");
        System.out.print("Ingrese ID de la solicitud: ");
        String id = scanner.nextLine().trim();
        SolicitudAyuda sol = gestor.buscarPorId(id);
        if (sol == null) {
            System.out.println("[Error] No existe ninguna solicitud con ese ID.");
            return;
        }
        gestor.agregarAColaPrioridad(sol);
        System.out.println("[Exito] Solicitud '" + id + "' encolada en PriorityQueue con prioridad " + sol.getPrioridad());
    }

    // 7. Consultar proximo prioritario (peek)
    private void opcionConsultarProximoElementoPrioritario() {
        System.out.println("--- [7] CONSULTAR PROXIMA SOLICITUD PRIORITARIA (PRIORITYQUEUE.PEEK) ---");
        SolicitudAyuda proxima = gestor.consultarProximoPrioritario();
        if (proxima != null) {
            System.out.println("Proxima solicitud en ser atendida (sin extraer):");
            System.out.println("   " + proxima);
            System.out.println("Total en cola prioritaria: " + gestor.getTamanioColaPrioridad());
        } else {
            System.out.println("[Aviso] La cola de prioridad se encuentra vacia.");
        }
    }

    // 8. Procesar siguiente elemento prioritario (poll)
    private void opcionProcesarSiguienteElementoPrioritario() {
        System.out.println("--- [8] ATENDER / PROCESAR SIGUIENTE SOLICITUD PRIORITARIA (PRIORITYQUEUE.POLL) ---");
        SolicitudAyuda procesada = gestor.procesarSiguientePrioritario();
        if (procesada != null) {
            System.out.println("[Solicitud Atendida con Exito]:");
            System.out.println("   " + procesada);
            System.out.println("Estado actualizado a [ATENDIDA]. Restan en cola prioritaria: " + gestor.getTamanioColaPrioridad());
        } else {
            System.out.println("[Aviso] No hay solicitudes pendientes en la cola de prioridad.");
        }
    }

    // 9. Modificar
    private void opcionModificar() {
        System.out.println("--- [9] MODIFICAR SOLICITUD (CONSISTENCIA ENTRE COLECCIONES) ---");
        System.out.print("Ingrese el ID de la solicitud a modificar: ");
        String id = scanner.nextLine().trim();
        SolicitudAyuda sol = gestor.buscarPorId(id);
        if (sol == null) {
            System.out.println("[Error] No existe ninguna solicitud con ID: " + id);
            return;
        }

        System.out.println("Solicitud actual: " + sol);
        System.out.print("Nuevo beneficiario (ENTER para mantener '" + sol.getBeneficiario() + "'): ");
        String nuevoBen = scanner.nextLine().trim();

        System.out.print("Nueva cantidad (0 para mantener " + sol.getCantidad() + "): ");
        int nuevaCant = leerEntero("");

        System.out.println("Prioridad actual: " + sol.getPrioridad());
        System.out.println("Desea cambiar la prioridad? 1. URGENTE, 2. ALTA, 3. MEDIA, 4. BAJA, 0. Mantener");
        int opcPri = leerEntero("Opcion: ");
        PrioridadEnum nuevaPri = null;
        if (opcPri == 1) nuevaPri = PrioridadEnum.URGENTE;
        else if (opcPri == 2) nuevaPri = PrioridadEnum.ALTA;
        else if (opcPri == 3) nuevaPri = PrioridadEnum.MEDIA;
        else if (opcPri == 4) nuevaPri = PrioridadEnum.BAJA;

        System.out.print("Nueva descripcion (ENTER para mantener): ");
        String nuevaDesc = scanner.nextLine().trim();

        boolean exito = gestor.modificarSolicitud(id, nuevoBen, nuevaCant, nuevaPri, nuevaDesc);
        if (exito) {
            System.out.println("[Exito] Solicitud modificada. Si la prioridad cambio, la PriorityQueue fue re-balanceada.");
            System.out.println("Estado final sincronizado: " + gestor.buscarPorId(id));
        }
    }

    // 10. Eliminar
    private void opcionEliminar() {
        System.out.println("--- [10] ELIMINAR SOLICITUD (SINCRONIZACION COMPLETA) ---");
        System.out.print("Ingrese ID de la solicitud a eliminar: ");
        String id = scanner.nextLine().trim();

        if (!gestor.existeSolicitud(id)) {
            System.out.println("[Error] No existe ninguna solicitud con el ID: " + id);
            return;
        }

        boolean eliminada = gestor.eliminarSolicitud(id);
        if (eliminada) {
            System.out.println("[Exito] Solicitud '" + id + "' eliminada de forma sincronizada:");
            System.out.println("   - Removida de HashMap (Map)");
            System.out.println("   - Removida de ArrayList (List)");
            System.out.println("   - Removida de TreeSet (SortedSet)");
            System.out.println("   - Removida de PriorityQueue (Queue)");
            System.out.println("Verificacion: gestor.buscarPorId('" + id + "') = " + gestor.buscarPorId(id));
        }
    }

    // 11. Mostrar informacion de las colecciones
    private void opcionMostrarInformacionDeLasColecciones() {
        System.out.println("--- [11] ESTADO Y RESUMEN METRICO DE LAS COLECCIONES ---");
        Map<String, Integer> metricas = gestor.obtenerMetricasColecciones();
        for (Map.Entry<String, Integer> entry : metricas.entrySet()) {
            System.out.printf(" %-55s : %d elementos%n", entry.getKey(), entry.getValue());
        }
    }

    private int leerEntero(String mensaje) {
        while (true) {
            try {
                if (!mensaje.isEmpty()) {
                    System.out.print(mensaje);
                }
                String linea = scanner.nextLine().trim();
                return Integer.parseInt(linea);
            } catch (NumberFormatException e) {
                System.out.println("[Error] Entrada no valida. Ingrese un numero entero.");
            }
        }
    }

    public static void main(String[] args) {
        Main app = new Main();
        app.iniciar();
    }
}
