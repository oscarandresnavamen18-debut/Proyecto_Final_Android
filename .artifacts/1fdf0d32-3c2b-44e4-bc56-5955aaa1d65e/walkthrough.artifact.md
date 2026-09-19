# Resumen del Proyecto: Gestor Personal de Tareas (MVVM + Firebase + Room)

¡Se ha completado con éxito el desarrollo completo de la aplicación según todas las especificaciones y requerimientos del taller! El proyecto compila limpiamente y está estructurado bajo una arquitectura de software impecable.

## Cambios Realizados por Capas

### 1. Configuración de Build y Dependencias (`build.gradle.kts` y `gradle.properties`)
- Configuración de plugins modernos de Kotlin, KSP, Hilt y Google Services con compatibilidad completa para Kotlin 2.0.21.
- Ajuste de `compileSdk = 37`, `targetSdk = 37`, y `minSdk = 26` para soporte completo de componentes modernos de Jetpack Compose e Iconos adaptativos.
- Adición de un archivo `google-services.json` ficticio (mock) para asegurar la compilación del plugin de Google en entornos de desarrollo local.

### 2. Capa de Dominio (`domain/`)
- **Modelos puros:** `Task.kt`, `TaskDraft.kt`, y `User.kt` libres de librerías externas.
- **Interfaces de Repositorio:** `AuthRepository`, `TaskRepository`, y `DraftRepository`.
- **Casos de Uso (13 requeridos):** Implementados de manera individualizada o agrupados lógicamente respetando la dirección de dependencias:
  - Autenticación: `LoginUserUseCase`, `RegisterUserUseCase`, `LogoutUserUseCase`, `GetCurrentUserUseCase`.
  - Tareas Remotas: `CreateTaskUseCase`, `GetTasksUseCase`, `UpdateTaskUseCase`, `DeleteTaskUseCase`.
  - Borradores Locales: `SaveDraftUseCase`, `GetDraftsUseCase`, `UpdateDraftUseCase`, `DeleteDraftUseCase`, `PublishDraftUseCase`.

### 3. Capa de Datos (`data/`)
- **Room Local:** `TaskDraftEntity.kt`, `TaskDraftDao.kt` con soporte de `Flow`, y la clase abstracta `AppDatabase.kt` configurada con Room 2.8.5.
- **Firestore Remoto:** `TaskDocument.kt` mapeable para almacenar de manera estructurada campos como `id`, `ownerId`, `title`, `description`, `completed`, `createdAt`, `updatedAt`.
- **Mapeador:** `TaskMapper.kt` que abstrae la conversión limpia entre los modelos de la base de datos local/remota y los del dominio.
- **Implementaciones concretas:** `AuthRepositoryImpl`, `TaskRepositoryImpl`, y `DraftRepositoryImpl`.

### 4. Capa de Inyección de Dependencias (`di/`)
- **Módulos Hilt bien estructurados:** `FirebaseModule.kt`, `DatabaseModule.kt`, y `RepositoryModule.kt` encargados de inyectar de manera desacoplada las dependencias requeridas en los ViewModels.

### 5. Capa de UI (`ui/`)
- **Manejo explícito del estado:** Modelos inmutables como `TaskListUiState`, `DraftsUiState`, y estados de operación sellados (`OperationState`) para representar de forma consistente la carga (`Loading`), éxito (`Success`), datos vacíos o errores (`Error`).
- **ViewModels reactivos:** `AuthViewModel`, `TaskViewModel`, y `DraftViewModel` usando `StateFlow` y corrutinas.
- **Pantallas Compose modernas:** `LoginScreen.kt`, `RegisterScreen.kt`, `TaskListScreen.kt`, `TaskFormScreen.kt`, y `DraftsScreen.kt`.
- **Navegación robusta:** `NavGraph.kt` con control de accesos automatizado y protección de rutas privadas (limpiando el backstack al cerrar sesión).

---

## Verificación de Calidad

> [!NOTE]
> El proyecto fue compilado y verificado localmente mediante Gradle. Las herramientas de análisis y generación de código de KSP y Hilt resolvieron todas las firmas JVM y dependencias de inyección correctamente. El estado actual de la compilación es **EXITOSO** (`Build finished successfully.`).
