# Integración de Loterías API

Este plan detalla los pasos para integrar la API de `loteriasapi.com` en la aplicación Android, utilizando **Retrofit** para la comunicación de red y **Kotlinx Serialization** para el manejo de JSON.

## User Review Required

> [!IMPORTANT]
> Se requiere que el usuario proporcione su **API Key** en el lugar correspondiente (se usará un placeholder por ahora).
> También se asume que el usuario quiere ver los resultados de loterías internacionales (como EuroMillones, Powerball, etc.) ya que `loteriasapi.com` se enfoca en estas.

## Proposed Changes

### Dependencias y Configuración

#### [MODIFY] [libs.versions.toml](file:///home/petyo/AndroidStudioProjects/Pruebas/gradle/libs.versions.toml)
Añadir Retrofit y Kotlinx Serialization.

#### [MODIFY] [build.gradle.kts (app)](file:///home/petyo/AndroidStudioProjects/Pruebas/app/build.gradle.kts)
Aplicar el plugin de serialización y añadir las nuevas dependencias.

### Capa de Datos (Networking)

#### [NEW] [LotteryModels.kt](file:///home/petyo/AndroidStudioProjects/Pruebas/app/src/main/java/com/example/pruebas/data/LotteryModels.kt)
Definición de las clases de datos para el JSON de la API.

#### [NEW] [LotteryService.kt](file:///home/petyo/AndroidStudioProjects/Pruebas/app/src/main/java/com/example/pruebas/data/LotteryService.kt)
Interfaz de Retrofit para definir los endpoints.

#### [NEW] [LotteryRepository.kt](file:///home/petyo/AndroidStudioProjects/Pruebas/app/src/main/java/com/example/pruebas/data/LotteryRepository.kt)
Clase para gestionar las llamadas a la API y centralizar la lógica de datos.

### Capa de UI

#### [NEW] [LotteryViewModel.kt](file:///home/petyo/AndroidStudioProjects/Pruebas/app/src/main/java/com/example/pruebas/ui/LotteryViewModel.kt)
ViewModel para manejar el estado de la UI y la carga de datos.

#### [MODIFY] [MainActivity.kt](file:///home/petyo/AndroidStudioProjects/Pruebas/app/src/main/java/com/example/pruebas/MainActivity.kt)
Actualizar para mostrar los resultados de la lotería usando Compose.

## Verification Plan

### Automated Tests
- Ejecutar `app:assembleDebug` para verificar la compilación.
- (Opcional) Crear un test unitario para el Repository con MockWebServer.

### Manual Verification
- Ejecutar la app en un dispositivo/emulador y verificar que los datos se muestran en pantalla (o al menos se cargan sin errores).
