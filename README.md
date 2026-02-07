# AccesibilidadApp – Kotlin + Jetpack Compose

Aplicación Android desarrollada en Kotlin utilizando Jetpack Compose y Material Design 3.  
El proyecto corresponde a una aplicación de accesibilidad con funcionalidades de autenticación (Login, Registro y Recuperación de contraseña), estructurada con navegación mediante Jetpack Navigation Compose y separación por paquetes.

---

## Estructura general del proyecto

La aplicación se organiza en los siguientes paquetes:

- **data**: repositorios de datos y gestión de usuarios.
- **model**: modelos de datos (entidades).
- **navigation**: configuración de navegación con Jetpack Navigation Compose.
- **ui**: pantallas, componentes reutilizables y tema visual.
  - **screens**: pantallas de la aplicación (Splash, Login, Register, Recover).
  - **components**: componentes personalizados reutilizables.
  - **theme**: configuración de colores, tipografía y tema.

Esta separación permite mantener un código ordenado, escalable y fácil de mantener.

---

## Funcionalidades y conceptos aplicados

A continuación se detallan los **9 conceptos trabajados en la asignatura** y el lugar específico donde se aplican dentro del proyecto:

---

### 1. Funciones de orden superior

**Ubicación:**  
[ui/components/BottomButton.kt](app/src/main/java/com/example/accesibilidadapp/ui/components/BottomButton.kt)  
[ui/components/AppOutlinedTextField.kt](app/src/main/java/com/example/accesibilidadapp/ui/components/AppOutlinedTextField.kt)

**Descripción:**  
Los componentes reutilizables como `BottomButton` y `AppOutlinedTextField` reciben funciones como parámetros (`onClick`, `onValueChange`, `onTrailingIconClick`), lo que permite personalizar el comportamiento sin modificar la implementación del componente. Esto es un ejemplo de funciones de orden superior.

---

### 2. Filter

**Ubicación:**  
[domain/usecase/ValidateUserUseCase.kt](app/src/main/java/com/example/accesibilidadapp/domain/usecase/ValidateUserUseCase.kt)

**Descripción:**  
Se utiliza la función `filter` en el método `filterUsersByAccessibility` para generar una nueva lista de usuarios según criterios de accesibilidad (modo alto contraste, tipo de discapacidad). Este proceso no modifica la lista original, permitiendo un manejo seguro del estado.

---

### 3. Funciones inline

**Ubicación:**  
[util/SafeRun.kt](app/src/main/java/com/example/accesibilidadapp/util/SafeRun.kt)

**Descripción:**  
La función `safeRun` está declarada como `inline` para optimizar el uso de lambdas pequeñas y repetitivas. Su objetivo es ejecutar bloques de código de forma segura y eficiente, encapsulando el patrón try/catch de manera reutilizable.

---

### 4. Lambdas

**Ubicación:**

- [ui/screens/LoginScreen.kt](app/src/main/java/com/example/accesibilidadapp/ui/screens/LoginScreen.kt)
- [ui/screens/RegisterScreen.kt](app/src/main/java/com/example/accesibilidadapp/ui/screens/RegisterScreen.kt)
- [navigation/NavGraph.kt](app/src/main/java/com/example/accesibilidadapp/navigation/NavGraph.kt)

**Descripción:**  
Las lambdas se utilizan extensivamente para manejar eventos en la interfaz de usuario, como clics en botones, cambios en campos de texto y acciones de navegación. También se usan en la función `safeRun` para definir bloques de código que se ejecutan de manera controlada.

---

### 5. Lambda con etiqueta

**Ubicación:**  
[domain/usecase/ValidateUserUseCase.kt](app/src/main/java/com/example/accesibilidadapp/domain/usecase/ValidateUserUseCase.kt)

**Descripción:**  
Se aplica una lambda con etiqueta (`validaciones@`) dentro del método `validateUserData` para controlar el flujo de ejecución sin salir de la función principal. Permite evaluar múltiples condiciones de forma clara y controlada, utilizando `return@validaciones` para salir anticipadamente del bloque lambda específico.

---

### 6. Funciones de extensión

**Ubicación:**  
[util/Extensions.kt](app/src/main/java/com/example/accesibilidadapp/util/Extensions.kt)

**Descripción:**  
Se definen funciones de extensión para agregar comportamiento a tipos existentes: `formatFullName()` y `getDescription()` para User, `isValidEmail()` y `isStrongPassword()` para String. Estas funciones agregan funcionalidad sin modificar las clases originales.

---

### 7. Propiedades de extensión

**Ubicación:**  
[util/Extensions.kt](app/src/main/java/com/example/accesibilidadapp/util/Extensions.kt)

**Descripción:**  
Se implementa la propiedad de extensión `hasAccessibilityEnabled` para el modelo User, que indica si un usuario tiene configuraciones de accesibilidad activas. Esta propiedad no almacena estado, sino que calcula su valor dinámicamente basándose en las propiedades del usuario.

---

### 8. Excepciones

**Ubicación:**  
[domain/usecase/ValidateUserUseCase.kt](app/src/main/java/com/example/accesibilidadapp/domain/usecase/ValidateUserUseCase.kt)

**Descripción:**  
Se utiliza una excepción personalizada `InvalidUserException` para manejar situaciones inválidas en la lógica de validación, como emails incorrectos, contraseñas débiles o datos incompletos. Las excepciones se lanzan con `throw` evitando que la aplicación continúe en un estado inconsistente.

---

### 9. Try / Catch

**Ubicación:**

- [util/SafeRun.kt](app/src/main/java/com/example/accesibilidadapp/util/SafeRun.kt)
- [ui/screens/LoginScreen.kt](app/src/main/java/com/example/accesibilidadapp/ui/screens/LoginScreen.kt)

**Descripción:**  
El manejo de errores se realiza mediante bloques `try/catch`, encapsulados en la función inline `safeRun`. Esta función captura excepciones y ejecuta una acción de recuperación, permitiendo mostrar mensajes controlados en la interfaz sin que la aplicación falle. Se utiliza en LoginScreen para manejar validaciones y errores de autenticación.

---

## Consideraciones finales

La aplicación está diseñada con una arquitectura simple pero extensible, utilizando una sola Activity principal (`MainActivity`) y Jetpack Compose para la construcción de toda la interfaz.  
La estructura del proyecto permite incorporar fácilmente nuevas funcionalidades como persistencia de datos con Room, integración con Firebase, o implementación de ViewModels con arquitectura MVVM.

---

## Pantallas de la aplicación

1. **SplashScreen**: Pantalla inicial de bienvenida con animación.
2. **LoginScreen**: Autenticación de usuarios con validación de credenciales.
3. **RegisterScreen**: Registro de nuevos usuarios con formulario completo.
4. **RecoverPasswordScreen**: Recuperación de contraseña mediante email.

---

## Componentes reutilizables

- **AppOutlinedTextField**: Campo de texto personalizado con estilo consistente.
- **BottomButton**: Botón estilizado reutilizable para acciones principales.
- **SocialCircle**: Componente para botones de redes sociales circulares.

---

## Tecnologías utilizadas

- Kotlin
- Jetpack Compose
- Material Design 3
- Jetpack Navigation Compose
- Android Studio

---

## Autor

**Manuel Cornejo**  
Ingeniería en Desarrollo de Software – DUOC UC  
Asignatura: Aplicaciones Móviles
