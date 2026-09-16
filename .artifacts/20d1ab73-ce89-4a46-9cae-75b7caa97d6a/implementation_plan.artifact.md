# Refactorización de Lógica de UI a ViewModel

Este plan detalla el traslado de la lógica de cálculo de altura y color de los tickets desde la capa de UI (`TicketUI`) hacia el `ViewModel` siguiendo las mejores prácticas de Clean Architecture y MVI/MVVM.

## Proposed Changes

### Presentation Layer

#### [MODIFY] [HomeUiState.kt](file:///home/petyo/AndroidStudioProjects/Pruebas/app/src/main/java/com/example/pruebas/presentation/homeScreen/HomeUiState.kt)
- Definir la clase `TicketUiModel` para encapsular los datos del ticket junto con sus propiedades de UI (altura, color, premio formateado).
- Actualizar `HomeUiState` para manejar una lista de `TicketUiModel` en lugar de `Ticket`.

#### [MODIFY] [HomeScreenViewModel.kt](file:///home/petyo/AndroidStudioProjects/Pruebas/app/src/main/java/com/example/pruebas/presentation/homeScreen/HomeScreenViewModel.kt)
- Mover la lógica de `when` para el color y la altura desde `TicketUI` al `ViewModel`.
- Implementar el mapeo de `Ticket` a `TicketUiModel` cuando se reciben los datos del repositorio.

#### [MODIFY] [HomeScreen.kt](file:///home/petyo/AndroidStudioProjects/Pruebas/app/src/main/java/com/example/pruebas/presentation/homeScreen/HomeScreen.kt)
- Actualizar la firma de `HomeScreen` para aceptar una lista de `TicketUiModel`.

#### [MODIFY] [TicketUI.kt](file:///home/petyo/AndroidStudioProjects/Pruebas/app/src/main/java/com/example/pruebas/presentation/homeScreen/TicketUI.kt)
- Simplificar el componente eliminando la lógica de cálculo.
- Cambiar el parámetro de entrada de `Ticket` a `TicketUiModel`.

## Verification Plan

### Automated Tests
- No hay tests existentes detectados para esta parte, pero se verificará mediante compilación.

### Manual Verification
- Desplegar la aplicación y verificar que los tickets sigan mostrando el color correcto según el juego y la altura variable según el premio.
- Comprobar que el formato de moneda se mantenga correcto.
