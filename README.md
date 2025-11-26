# Car Wash App

## Introducción

**Car Wash App** es una aplicación de Android diseñada para la gestión integral de un negocio de lavado de coches. Permite administrar clientes, sus vehículos y los tickets de servicio de lavado, proporcionando una solución digital para optimizar las operaciones diarias.

La aplicación se conecta a Firebase para la autenticación de usuarios y el almacenamiento de datos en tiempo real en Firestore.

---

## Características Principales

- **Autenticación de Usuarios**: Sistema completo de registro e inicio de sesión con Firebase Authentication.
- **Gestión de Usuarios**: Visualización de la lista de todos los usuarios registrados.
- **Gestión de Vehículos (CRUD)**:
    - **Crear**: Añadir nuevos coches al sistema, asignándoles un propietario.
    - **Leer**: Visualizar una lista completa de todos los coches registrados con sus detalles.
    - **Eliminar**: Funcionalidad para borrar coches (implementación pendiente, pero arquitectura preparada).
- **Gestión de Tickets de Servicio (CRUD)**:
    - **Crear**: Generar nuevos tickets de lavado, seleccionando un coche y un tipo de servicio.
    - **Leer**: Visualizar un historial de todos los tickets generados.
    - **Eliminar**: Borrar tickets del sistema con un diálogo de confirmación.
- **Identidad de Marca Personalizada**: La interfaz de usuario ha sido tematizada con los colores y el logo de la marca, incluyendo un modo oscuro.

---

## Arquitectura del Proyecto

El proyecto sigue una arquitectura **MVVM (Model-View-ViewModel)**, que promueve un código limpio, escalable y fácil de mantener, separando las responsabilidades de la lógica de negocio y la interfaz de usuario.

### 1. Model (Capa de Datos)

Esta capa se encarga de la lógica de negocio y el acceso a los datos.
- **POJOs**: Clases simples (`User`, `Car`, `Ticket`, `WashService`) que modelan la estructura de los datos almacenados en Firestore.
- **Repositories** (`UserRepository`, `CarRepository`, `TicketRepository`, etc.): Actúan como la única fuente de verdad para los datos. Se comunican con Firebase para obtener, crear, actualizar o eliminar datos, y exponen esta información a los ViewModels.
- **FirebaseDataSource**: Una clase centralizada que proporciona la instancia de Firestore, asegurando una única conexión.

### 2. View (Capa de UI)

La capa de la interfaz de usuario, responsable de mostrar los datos y capturar las interacciones del usuario.
- **Activities**: (`LoginActivity`, `CarListActivity`, etc.) Contienen la lógica de la UI, como la configuración de los `RecyclerViews` y la gestión de la navegación. No contienen lógica de negocio.
- **XML Layouts**: Definen la estructura y el diseño de cada pantalla.
- **Adapters**: (`CarAdapter`, `TicketAdapter`) Conectan los datos de las listas con los `RecyclerViews` para mostrarlos en pantalla.

### 3. ViewModel

Actúa como el puente entre la **View** y el **Model**.
- **ViewModels** (`LoginViewModel`, `CarListViewModel`, `AddTicketViewModel`, etc.):
    - Contienen los datos que la UI necesita mostrar, expuestos a través de `LiveData`.
    - Sobreviven a cambios de configuración (como rotaciones de pantalla), evitando la pérdida de estado.
    - Llaman a los métodos de los `Repositories` para ejecutar acciones (ej: `loadCars()`, `deleteTicket()`).
    - Nunca tienen una referencia directa a la `View` (Activities o Fragments).

---

## Configuración del Proyecto

Para levantar y ejecutar este proyecto, sigue estos pasos:

1.  **Clonar el Repositorio**:
    ```bash
    git clone <URL_DEL_REPOSITORIO_GITHUB>
    ```

2.  **Abrir en Android Studio**:
    - Abre Android Studio y selecciona `Open an existing project`.
    - Navega hasta la carpeta donde clonaste el repositorio y selecciónala.

3.  **Conectar con Firebase**:
    - Ve a la [Consola de Firebase](https://console.firebase.google.com/) y crea un nuevo proyecto.
    - Dentro de tu proyecto, añade una nueva aplicación de Android. Utiliza `com.example.car_wash` como el nombre del paquete.
    - Sigue los pasos para descargar el archivo `google-services.json`.
    - **Copia el archivo `google-services.json` y pégalo dentro de la carpeta `app/` de tu proyecto en Android Studio.**

4.  **Configurar Servicios de Firebase**:
    - En la Consola de Firebase, ve a la sección **Authentication** y habilita el proveedor de **Email/Contraseña**.
    - Ve a la sección **Firestore Database**, crea una nueva base de datos en modo de producción y navega a la pestaña **Reglas**.

5.  **Establecer Reglas de Seguridad en Firestore**:
    - Reemplaza las reglas por defecto con las siguientes y publícalas:

    ```
    rules_version = '2';
    service cloud.firestore {
      match /databases/{database}/documents {

        match /users/{userId} {
          allow get: if request.auth != null && request.auth.uid == userId;
          allow list: if request.auth != null;
          allow update: if request.auth != null && request.auth.uid == userId;
          allow create: if request.auth != null;
        }

        match /roles/{roleId} {
          allow read: if request.auth != null;
        }

        match /cars/{carId=**} {
          allow read, write: if request.auth != null;
        }

        match /services/{serviceId} {
          allow read: if request.auth != null;
        }

        match /tickets/{ticketId=**} {
          allow read, write: if request.auth != null;
        }
      }
    }
    ```

6.  **Poblar Datos Iniciales (Opcional pero Recomendado)**:
    - En Firestore, crea manualmente una colección llamada `services`.
    - Añade algunos documentos a esta colección para representar los tipos de lavado. Cada documento debe tener los campos `name` (String), `price` (Number) y `duration` (Number).

7.  **Construir y Ejecutar**:
    - Sincroniza el proyecto con Gradle y ejecútalo en un emulador o dispositivo físico.

---

## Tecnologías Utilizadas

- **Lenguaje**: Java
- **Arquitectura**: MVVM (Model-View-ViewModel)
- **Base de Datos**: Google Firestore
- **Autenticación**: Firebase Authentication
- **Librerías Principales**:
    - AndroidX AppCompat & Material Design
    - AndroidX Lifecycle (ViewModel & LiveData)
    - RecyclerView
