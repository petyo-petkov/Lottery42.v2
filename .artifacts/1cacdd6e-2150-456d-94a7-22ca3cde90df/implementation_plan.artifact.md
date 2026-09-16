# Plan de Implementación: Patrón MVI

Este plan describe la migración de la capa de presentación actual (basada en múltiples StateFlows y estados locales) hacia el patrón **MVI (Model-View-Intent)**. Esto proporcionará un flujo de datos unidireccional más predecible y facilitará las pruebas y el mantenimiento.

## User Review Required

> [!IMPORTANT]
> MVI introduce una única fuente de verdad para el estado de la pantalla. Esto significa que cambios pequeños en el estado reconstruirán el objeto de estado completo. Se recomienda el uso de `distinctUntilChanged` en el ViewModel si el estado es muy complejo para optimizar el rendimiento de Compose.

> [!NOTE]
> Mantendremos la arquitectura actual de paquetes (`presentation`), pero añadiremos sub-archivos o clases internas para `State` e `Intent`.

## Proposed Changes

### 1. Definición de Base MVI
Crearemos una estructura común (opcionalmente una interfaz o clase base) para manejar los estados y los intents.

### 2. Migración de Home (Listado y Detalle)
Refactorizaremos el flujo actual de `HomeScreenViewModel` y `App.kt`.

#### [MODIFY] [HomeScreenViewModel.kt](file:///home/petyo/AndroidStudioProjects/Pruebas/app/src/main/java/com/example/pruebas/presentation/HomeScreenViewModel.kt)
- Definir `HomeUiState` (data class): Contendrá `tickets`, `selectedTicket`, `showDeleteDialog`, `isLoading`.
- Definir `HomeIntent` (sealed interface): Acciones como `SelectTicket`, `DeleteAll`, `ToggleDeleteDialog`.
- Centralizar la lógica en un método `onIntent(intent: HomeIntent)`.

#### [MODIFY] [App.kt](file:///home/petyo/AndroidStudioProjects/Pruebas/app/src/main/java/com/example/pruebas/presentation/App.kt)
- Observar el estado único desde el ViewModel.
- Pasar las acciones como intents al ViewModel en lugar de llamar a métodos directos.

### 3. Migración de Scanner
Refactorizaremos la lógica de escaneo para manejar estados de carga y error de forma explícita.

#### [MODIFY] [ScannerViewModel.kt](file:///home/petyo/AndroidStudioProjects/Pruebas/app/src/main/java/com/example/pruebas/presentation/ScannerViewModel.kt)
- Definir `ScannerUiState`: `isScanning`, `errorMessage`.
- Definir `ScannerIntent`: `StartScan`.

---

## Verification Plan

### Automated Tests
- **ViewModel Unit Tests**: Verificar que al enviar un `HomeIntent.DeleteAll`, el estado `isLoading` cambie a true y luego a false tras la operación.
- **State Transition Tests**: Asegurar que `SelectTicket` actualiza correctamente el `selectedTicket` en el estado.

### Manual Verification
- Probar el flujo completo: Escaneo -> Aparece en la lista -> Ver detalle -> Borrar todo.
- Verificar que el diálogo de borrado se muestra y oculta correctamente a través del estado MVI.
