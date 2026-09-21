# Task Manager App - My ProyectFinal

Aplicación móvil nativa para Android diseñada para la gestión eficiente de tareas personales, integrando persistencia en la nube y almacenamiento local.

## 🚀 Casos de Uso Principales

### 1. Gestión de Identidad y Seguridad
*   **Registro de Usuario:** Permite a nuevos usuarios crear una cuenta utilizando su correo electrónico y una contraseña segura.
*   **Autenticación:** Acceso restringido mediante credenciales validadas contra Firebase Authentication.
*   **Persistencia de Sesión:** El sistema mantiene al usuario conectado entre aperturas de la aplicación para una mejor experiencia.

### 2. Ciclo de Vida de Tareas (CRUD en la Nube)
*   **Creación de Tareas:** El usuario puede añadir nuevas tareas con título y descripción que se sincronizan instantáneamente con Cloud Firestore.
*   **Seguimiento y Estado:** Posibilidad de marcar tareas como completadas (tachado visual) para un seguimiento efectivo del progreso.
*   **Edición y Limpieza:** Permite la eliminación de tareas individuales, manteniendo la lista organizada.

### 3. Productividad Offline (Borradores)
*   **Guardado en Local:** Permite guardar tareas como "Borradores" cuando no se desea publicarlas inmediatamente. Estos se almacenan localmente mediante Room Database.
*   **Publicación de Borradores:** Caso de uso donde una tarea preparada previamente en local se sube a la nube con un solo toque.
*   **Gestión Masiva:** Funcionalidad para limpiar todos los borradores locales de forma rápida.

### 4. Experiencia de Usuario (UX)
*   **Notificaciones en Tiempo Real:** El sistema informa mediante Snackbars el éxito de las operaciones (creación, eliminación).
*   **Confirmaciones Críticas:** Prevención de acciones accidentales mediante diálogos de confirmación antes del cierre de sesión o borrado masivo.

---

## 🛠️ Stack Tecnológico
*   **Lenguaje:** Kotlin
*   **UI:** Jetpack Compose (Material 3)
*   **Arquitectura:** Clean Architecture + MVVM
*   **Backend:** Firebase (Firestore & Auth)
*   **Local DB:** Room Database
*   **DI:** Hilt (Dagger)
*   **Asincronía:** Coroutines & Flow
