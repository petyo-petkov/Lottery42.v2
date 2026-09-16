# Aclaración y Refactorización de Modelos de Datos

El usuario tiene dudas sobre si necesita dos clases (`Ticket` y `TicketEntity`) o si puede usar solo una. Actualmente, el proyecto sigue una estructura de **Clean Architecture**, donde los modelos de datos (Data) están separados de los modelos de dominio (Domain).

## Análisis de la situación actual

1.  **`TicketEntity`**: Es el modelo de Room. Contiene las anotaciones de la base de datos.
2.  **`Ticket`**: Es el modelo de dominio. Es el que se debería usar en la UI y en la lógica de negocio.
3.  **`LotteryDAO`**: Tiene un error conceptual: está intentando insertar y recuperar objetos `Ticket` (dominio) en lugar de `TicketEntity` (data). Room fallará al compilar porque `Ticket` no tiene la anotación `@Entity`.
4.  **`LotteryDatabaseRepo`**: Es una interfaz en el dominio que define cómo interactuar con los tickets desde la lógica de negocio.

## Propuesta: Mantener ambas clases (Recomendado)

Mantener ambas clases es la mejor práctica para:
-   **Desacoplar** la base de datos de la lógica de negocio.
-   **Flexibilidad**: Puedes cambiar la estructura de la tabla en la base de datos sin afectar a toda la aplicación.
-   **Limpieza**: El modelo de dominio no depende de librerías externas como Room.

### Cambios propuestos

1.  **Corregir [LotteryDAO.kt](file:///home/petyo/AndroidStudioProjects/Pruebas/app/src/main/java/com/example/pruebas/data/db/LotteryDAO.kt)**: Debe trabajar con `TicketEntity`.
2.  **Crear `LotteryDatabaseRepoImpl.kt`**: Una implementación en la capa de `data` que implemente la interfaz `LotteryDatabaseRepo` del dominio, encargándose de mapear entre `TicketEntity` y `Ticket`.

---

## Cambios Detallados

### Capa de Datos (Data Layer)

#### [MODIFY] [LotteryDAO.kt](file:///home/petyo/AndroidStudioProjects/Pruebas/app/src/main/java/com/example/pruebas/data/db/LotteryDAO.kt)
Cambiar el tipo de datos de `Ticket` a `TicketEntity`.

#### [NEW] [LotteryDatabaseRepoImpl.kt](file:///home/petyo/AndroidStudioProjects/Pruebas/app/src/main/java/com/example/pruebas/data/db/LotteryDatabaseRepoImpl.kt)
Implementar la interfaz del dominio usando el DAO y realizando las conversiones de modelos.

## Plan de Verificación

### Verificación Manual
1.  Compilar el proyecto para asegurar que Room reconoce correctamente las entidades en el DAO.
2.  Verificar que el repositorio realiza correctamente la conversión `toDomain()` y `toEntity()`.
