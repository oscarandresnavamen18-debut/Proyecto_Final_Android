# Plan de Implementación: Gestor Personal de Tareas (MVVM + Firebase + Room + Hilt)

Este plan detalla el desarrollo completo de la aplicación Android basada en la arquitectura MVVM con persistencia local en Room, operaciones CRUD remotas en Cloud Firestore y autenticación con Firebase Authentication.

## User Review Required

> [!IMPORTANT]
> El paquete base actual en el proyecto es `com.example.myproyectfinal`. Utilizaremos este paquete para mantener consistencia con los archivos generados por el asistente de Android Studio, organizando la estructura de capas (`data`, `domain`, `ui`, `di`) bajo esta raíz.
>
> Para que Firebase funcione correctamente, necesitarás agregar tu propio archivo `google-services.json` en la carpeta `app/` después o configurar el proyecto en la consola de Firebase con el applicationId `com.example.myproyectfinal`.

## Open Questions
Ninguna en este momento. Procederemos con la configuración completa e implementación robusta siguiendo los requerimientos del taller.

## Proposed Changes

### Configuración del Proyecto y Dependencias

#### [MODIFY] [libs.versions.toml](file:///C:/Users/Jandr/AndroidStudioProjects/MyproyectFinal/gradle/libs.versions.toml)
Agregar las versiones, librerías y plugins necesarios para Firebase, Room, KSP, Hilt y Navigation.

#### [MODIFY] [build.gradle.kts (Proyecto)](file:///C:/Users/Jandr/AndroidStudioProjects/MyproyectFinal/build.gradle.kts)
Registrar los plugins de Google Services, Hilt y KSP a nivel de proyecto.

#### [MODIFY] [build.gradle.kts (App)](file:///C:/Users/Jandr/AndroidStudioProjects/MyproyectFinal/app/build.gradle.kts)
Aplicar los plugins, activar el procesamiento de anotaciones con KSP y declarar las dependencias de Hilt, Room, Firebase BoM, Auth, Firestore y Navigation Compose.

---

### Capa de Dominio (Domain Layer)

#### [NEW] [Task.kt](file:///C:/Users/Jandr/AndroidStudioProjects/MyproyectFinal/app/src/main/java/com/example/myproyectfinal/domain/model/Task.kt)
Modelo de dominio puro para tareas publicadas.

#### [NEW] [TaskDraft.kt](file:///C:/Users/Jandr/AndroidStudioProjects/MyproyectFinal/app/src/main/java/com/example/myproyectfinal/domain/model/TaskDraft.kt)
Modelo de dominio puro para borradores locales.

#### [NEW] [AuthRepository.kt](file:///C:/Users/Jandr/AndroidStudioProjects/MyproyectFinal/app/src/main/java/com/example/myproyectfinal/domain/repository/AuthRepository.kt)
Interfaz del repositorio de autenticación.

#### [NEW] [TaskRepository.kt](file:///C:/Users/Jandr/AndroidStudioProjects/MyproyectFinal/app/src/main/java/com/example/myproyectfinal/domain/repository/TaskRepository.kt)
Interfaz del repositorio de tareas en Firestore.

#### [NEW] [DraftRepository.kt](file:///C:/Users/Jandr/AndroidStudioProjects/MyproyectFinal/app/src/main/java/com/example/myproyectfinal/domain/repository/DraftRepository.kt)
Interfaz del repositorio de borradores locales en Room.

#### [NEW] Casos de Uso de Autenticación, Tareas y Borradores
Crear las clases de casos de uso requeridas:
- `RegisterUserUseCase`, `LoginUserUseCase`, `LogoutUserUseCase`, `GetCurrentUserUseCase`
- `CreateTaskUseCase`, `GetTasksUseCase`, `UpdateTaskUseCase`, `DeleteTaskUseCase`
- `SaveDraftUseCase`, `GetDraftsUseCase`, `UpdateDraftUseCase`, `DeleteDraftUseCase`, `PublishDraftUseCase`

---

### Capa de Datos (Data Layer)

#### [NEW] [TaskDraftEntity.kt](file:///C:/Users/Jandr/AndroidStudioProjects/MyproyectFinal/app/src/main/java/com/example/myproyectfinal/data/local/entity/TaskDraftEntity.kt)
Entidad de Room para el almacenamiento local de borradores.

#### [NEW] [TaskDraftDao.kt](file:///C:/Users/Jandr/AndroidStudioProjects/MyproyectFinal/app/src/main/java/com/example/myproyectfinal/data/local/dao/TaskDraftDao.kt)
Data Access Object (DAO) para las operaciones en la base de datos Room.

#### [NEW] [AppDatabase.kt](file:///C:/Users/Jandr/AndroidStudioProjects/MyproyectFinal/app/src/main/java/com/example/myproyectfinal/data/local/database/AppDatabase.kt)
Clase abstracta de configuración de la base de datos Room.

#### [NEW] [TaskDocument.kt](file:///C:/Users/Jandr/AndroidStudioProjects/MyproyectFinal/app/src/main/java/com/example/myproyectfinal/data/remote/model/TaskDocument.kt)
Modelo de datos para Firestore (mapeable/serializable).

#### [NEW] [TaskMapper.kt](file:///C:/Users/Jandr/AndroidStudioProjects/MyproyectFinal/app/src/main/java/com/example/myproyectfinal/data/mapper/TaskMapper.kt)
Clase encargada de convertir entre entidades locales, documentos remotos y modelos de dominio.

#### [NEW] Implementaciones de Repositorios
- `AuthRepositoryImpl.kt`: Integración con FirebaseAuth.
- `TaskRepositoryImpl.kt`: Integración con FirebaseFirestore.
- `DraftRepositoryImpl.kt`: Integración con Room DAO.

---

### Inyección de Dependencias (DI) y Configuración Base

#### [NEW] [TaskManagerApplication.kt](file:///C:/Users/Jandr/AndroidStudioProjects/MyproyectFinal/app/src/main/java/com/example/myproyectfinal/TaskManagerApplication.kt)
Clase Application de la app configurada con `@HiltAndroidApp`.

#### [NEW] Módulos de Hilt (`di/`)
- `FirebaseModule.kt`: Provee las instancias de FirebaseAuth y FirebaseFirestore.
- `DatabaseModule.kt`: Provee la base de datos Room y los DAOs.
- `RepositoryModule.kt`: Vincula las interfaces de repositorios con sus implementaciones concretas.

---

### Capa de Interfaz de Usuario (UI Layer)

#### [NEW] Estados de Pantalla (`ui/state/`)
Definir estados inmutables como `TaskListUiState` y el estado sellado `OperationState`.

#### [NEW] ViewModels (`ui/screen/...`)
- `LoginViewModel`, `RegisterViewModel`, `TaskListViewModel`, `TaskFormViewModel`, `DraftsViewModel`.

#### [NEW] Pantallas y Componentes Jetpack Compose
- `LoginScreen.kt`, `RegisterScreen.kt`, `TaskListScreen.kt`, `TaskFormScreen.kt`, `DraftsScreen.kt`.

#### [NEW] Navegación (`ui/navigation/`)
- `NavGraph.kt` y `Screen.kt` definiendo las rutas y protegiendo las pantallas privadas contra accesos no autenticados.

#### [MODIFY] [MainActivity.kt](file:///C:/Users/Jandr/AndroidStudioProjects/MyproyectFinal/app/src/main/java/com/example/myproyectfinal/MainActivity.kt)
Configurar la actividad principal como `@AndroidEntryPoint` y renderizar el `NavGraph`.

## Verification Plan

### Automated Tests
- Ejecutaremos `gradle_build` con las tareas de compilación para comprobar la validez de la sintaxis y la generación de código mediante KSP y Hilt.

### Manual Verification
- Verificación visual y funcional de pantallas ejecutando la aplicación y analizando el flujo de autenticación, CRUD y guardado de borradores.
