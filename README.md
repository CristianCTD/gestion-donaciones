# Sistema de Gestión de Donaciones y Ayuda Humanitaria

**Asignatura:** Estructuras de Datos  
**Unidad:** Unidad 3 - Java Collections Framework  
**Actividad:** Actividad 1 - Protocolo Individual / Colaborativo (30%)  
**Programa:** Ingeniería de Software  
**IES:** Universidad de Cartagena (Centro Tutorial Cereté)  
**Tutor:** John Carlos Arrieta Arrieta  
**Estudiante:** Cristian Torres  
**Caso Asignado:** N.º 30 - Sistema de Gestión de Donaciones (`SolicitudAyuda` / `Donante`)

---

## 1. Descripción del Proyecto
Aplicación Java de consola que implementa un sistema empresarial para la administración de donaciones y solicitudes de ayuda humanitaria, empleando de manera estratégica y justificada cinco familias del **Java Collections Framework**:

1. **`List` (`ArrayList`):** Registro secuencial completo de solicitudes recibidas cronológicamente.
2. **`Set` (`LinkedHashSet`):** Control de unicidad de tipos de recursos e insumos humanitarios sin duplicados.
3. **`Map` (`HashMap`):** Búsqueda de alta eficiencia en tiempo constante $O(1)$ por identificador único (`idSolicitud`).
4. **`SortedSet` (`TreeSet`):** Colección auto-ordenada por fecha y código aplicando la interfaz `Comparable`.
5. **`Queue` (`PriorityQueue`):** Despacho y atención prioritaria de solicitudes según criticidad (URGENTE > ALTA > MEDIA > BAJA) mediante `Comparator`.

El sistema incluye sincronización bidireccional estricta para garantizar la coherencia de datos ante actualizaciones y eliminaciones, e incorpora 15 objetos de prueba precargados.

---

## 2. Compilación y Ejecución

Desde la carpeta raíz del proyecto (`unidad3_gestion_donaciones`):

### Compilación:
```bash
javac -d bin src/modelo/*.java src/comparadores/*.java src/servicio/*.java src/Main.java
```

### Ejecución:
```bash
java -cp bin Main
```
