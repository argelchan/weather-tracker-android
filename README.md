# Weather Android 

Es un proyecto enfocado en la visualización de datos del clima. 

https://github.com/user-attachments/assets/c4e27704-8b45-4919-8c6f-f7d4719f10f8

Incluye:

1. Librerías básicas como: ViewModel, LiveData, Navigation Components, Retrofit, Hilt, Google Json, Coil, Lifecycle process.
2. Utiliza el patrón de arquitectura MVVM.
3. La estructura del proyecto está basada en una clean architecture.
4. Utiliza inyección de dependencias.
5. Tiene vistas básicas para home, navegación entre pantallas con Botton Navigation.
6. Es un proyecto de una sola actividad. Lo que significa que no necesitas utilizar más actividades.
7. Se usa el framework de desarrollo de interfaces de usuario (UI) XML.

### Pasos para clonar el proyecto:

1. Diríjase al repositorio de Hybridge
3. Seleccione el botón <> Code y seleccione la pestaña HTTPS. Copie la URL del repositorio.
4. Diríjase a su explorador de archivos y ejecuté el comando:

   git clone https://github.com/argelchan/weather-tracker-android


### Pasos para construir el proyecto:

1. Asegúrese de tener la versión de Java 17 en su equipo
2. Sincroniza el proyecto para descargar las dependencias

### Cambiar las API del entorno

- En el archivo build.gradle.kts(:app) verás dos secciones (production y develop). Solo tienes que cambiar la URL que necesites en cualquiera de las secciones.

```
    flavorDimensions.add("weatherTracker")
    productFlavors {
        create("develop") {
            dimension = "weatherTracker"
            versionNameSuffix = "-dev"
            buildConfigField("String", "API_BASE_URL", "\"https://api.openweathermap.org/data/2.5/\"")
        }

        create("production") {
            dimension = "weatherTracker"
            versionNameSuffix = "-prod"
            buildConfigField("String", "API_BASE_URL", "\"https://api.openweathermap.org/data/2.5/\"")
        }
    }
```

# Contacto

Si tienes algún problema, puedes contactar a didier.chan@dacodes.com.mx.
