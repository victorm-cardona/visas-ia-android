# 🛂 Visas IA - Aplicación Android para Gestión de Visas

[![Android](https://img.shields.io/badge/Android-3DDC84?style=for-the-badge&logo=android&logoColor=white)](https://developer.android.com/)
[![Kotlin](https://img.shields.io/badge/Kotlin-0095D5?style=for-the-badge&logo=kotlin&logoColor=white)](https://kotlinlang.org/)
[![Firebase](https://img.shields.io/badge/Firebase-039BE5?style=for-the-badge&logo=firebase&logoColor=white)](https://firebase.google.com/)
[![Material Design](https://img.shields.io/badge/Material_Design-757575?style=for-the-badge&logo=material-design&logoColor=white)](https://material.io/design)

## 📋 Descripción del Proyecto

**Visas IA** es una aplicación Android completa para la gestión de visas turísticas, desarrollada con tecnologías modernas y arquitectura escalable. La aplicación incluye autenticación robusta, interfaz de usuario moderna, integración con IA para consultas y un sistema completo de gestión de casos de visa.

## 🎯 Características Principales

### ✅ **Sistema de Autenticación Completo**
- 🔐 Registro de usuarios con email/password
- 🔑 Inicio de sesión con validación de credenciales
- 📧 Recuperación de contraseña por email
- 🚪 Cerrar sesión con confirmación
- 💾 Persistencia de sesión entre sesiones
- 🛡️ App Check y reCAPTCHA para seguridad

### 📱 **Interfaces de Usuario Modernas**
- 🎨 Material Design 3 implementado
- 📱 Diseño responsive y accesible
- 🔄 Navegación fluida entre pantallas
- ⚡ Indicadores de carga y feedback visual
- 🎯 UX optimizada para usuarios

### 🤖 **Inteligencia Artificial Integrada**
- 💬 Chat con IA para consultas de visas
- 📄 Procesamiento OCR de documentos
- 🔍 Análisis inteligente de documentos
- 💡 Recomendaciones basadas en casos similares
- 🧠 Respuestas contextuales sobre requisitos

### 📊 **Gestión de Casos de Visa**
- ➕ Creación de nuevos casos
- 📋 Lista de casos existentes
- 🔍 Filtros y búsqueda avanzada
- 📈 Estados de procesamiento
- 📁 Gestión de documentos

## 🏗️ Arquitectura del Proyecto

### **Estructura de Paquetes**
```
com.example.visas_ia/
├── data/
│   ├── models/          # Modelos de datos
│   └── model/           # Modelos adicionales
├── ui/
│   ├── auth/            # Autenticación
│   ├── dashboard/       # Pantalla principal
│   ├── cases/           # Gestión de casos
│   ├── config/          # Configuración
│   ├── ai/              # Consultoría IA
│   └── adapters/        # Adaptadores de RecyclerView
├── utils/               # Utilidades y helpers
└── VisasIAApplication   # Clase de aplicación
```

### **Patrón de Arquitectura**
- **MVVM (Model-View-ViewModel)** implementado
- **Repository Pattern** para gestión de datos
- **Dependency Injection** preparado
- **Clean Architecture** principios aplicados

## 🛠️ Tecnologías Implementadas

### **Lenguaje y Framework**
- [**Kotlin**](https://kotlinlang.org/) - Lenguaje principal
- [**Android SDK**](https://developer.android.com/) - Framework nativo
- [**Material Design 3**](https://material.io/design) - UI/UX moderna

### **Backend y Base de Datos**
- [**Firebase Auth**](https://firebase.google.com/docs/auth) - Autenticación de usuarios
- [**Firebase Firestore**](https://firebase.google.com/docs/firestore) - Base de datos en la nube
- [**Firebase Storage**](https://firebase.google.com/docs/storage) - Almacenamiento de archivos
- [**Room Database**](https://developer.android.com/training/data-storage/room) - Base de datos local

### **UI y Navegación**
- [**View Binding**](https://developer.android.com/topic/libraries/view-binding) - Binding de vistas
- [**Navigation Component**](https://developer.android.com/guide/navigation) - Navegación entre pantallas
- [**RecyclerView**](https://developer.android.com/guide/topics/ui/layout/recyclerview) - Listas eficientes

### **Redes y APIs**
- [**Retrofit**](https://square.github.io/retrofit/) - Cliente HTTP para APIs
- [**OkHttp**](https://square.github.io/okhttp/) - Cliente HTTP
- [**Gson**](https://github.com/google/gson) - Serialización JSON

### **Multimedia y Cámara**
- [**Glide**](https://bumptech.github.io/glide/) - Carga de imágenes
- [**CameraX**](https://developer.android.com/training/camerax) - Funcionalidad de cámara
- [**ML Kit**](https://developers.google.com/ml-kit) - OCR y procesamiento de texto

### **Programación Asíncrona**
- [**Coroutines**](https://kotlinlang.org/docs/coroutines.html) - Programación asíncrona
- [**LiveData**](https://developer.android.com/topic/libraries/architecture/livedata) - Datos observables
- [**ViewModel**](https://developer.android.com/topic/libraries/architecture/viewmodel) - Gestión de estado

### **Seguridad y Autenticación**
- [**Firebase App Check**](https://firebase.google.com/docs/app-check) - Verificación de app
- [**reCAPTCHA**](https://developers.google.com/recaptcha) - Prevención de bots
- [**Biometric Authentication**](https://developer.android.com/training/sign-in/biometric-auth) - Autenticación biométrica

## 📱 Pantallas Implementadas

### **1. LoginActivity**
- Diseño Material Design 3
- Campos de email y contraseña con validación
- Botones de registro y recuperación de contraseña
- Indicadores de carga durante autenticación
- Manejo específico de errores de Firebase

### **2. DashboardActivity**
- Menú principal con tarjetas interactivas
- Acceso a todas las funcionalidades
- Información del usuario logueado
- Botón de cerrar sesión con confirmación

### **3. NewCaseActivity**
- Formulario completo para crear nuevos casos
- Selección de país y tipo de visa
- Captura de documentos con cámara integrada
- Procesamiento OCR de documentos automático

### **4. ExistingCasesActivity**
- Lista de casos existentes con RecyclerView
- Filtros y búsqueda avanzada
- Detalles de cada caso
- Estados de procesamiento visuales

### **5. ConsultationActivity**
- Chat interactivo con IA para consultas
- Procesamiento inteligente de preguntas
- Respuestas contextuales sobre visas
- Historial de conversaciones

### **6. SettingsActivity**
- Configuraciones de la aplicación
- Preferencias del usuario
- Información de la aplicación
- Opciones de privacidad y seguridad

## 🗄️ Modelos de Datos

### **VisaCase**
```kotlin
data class VisaCase(
    val id: String,
    val userId: String,
    val country: String,
    val visaType: String,
    val status: String,
    val documents: List<String>,
    val createdAt: Long,
    val updatedAt: Long
)
```

### **Country**
```kotlin
data class Country(
    val id: String,
    val name: String,
    val code: String,
    val flag: String
)
```

### **VisaType**
```kotlin
data class VisaType(
    val id: String,
    val name: String,
    val description: String,
    val requirements: List<String>
)
```

## 🔐 Sistema de Seguridad

### **Autenticación**
- Validación de email con regex
- Contraseña mínima de 6 caracteres
- Confirmación de contraseña obligatoria
- Manejo específico de errores de Firebase
- Persistencia de sesión segura

### **Protección**
- App Check para verificar legitimidad de la app
- reCAPTCHA para prevenir ataques automatizados
- Validación de entrada en todos los campos
- Sanitización de datos antes de procesamiento

## 📊 Estado Actual del Proyecto

### ✅ **COMPLETADO (100% Funcional)**
- [x] Sistema de Autenticación completo
- [x] Interfaces de Usuario implementadas
- [x] Navegación entre pantallas
- [x] Configuración Firebase
- [x] Gestión de datos local y remota
- [x] Seguridad y validaciones
- [x] Integración con IA básica
- [x] Procesamiento OCR

### 🔄 **EN DESARROLLO**
- [ ] Integración con APIs de embajadas
- [ ] Sistema de notificaciones push
- [ ] Reportes y estadísticas avanzadas
- [ ] Modo offline completo
- [ ] Tests unitarios y de integración

### 📋 **PENDIENTE**
- [ ] Backend completo para gestión de casos
- [ ] Sistema de pagos integrado
- [ ] Integración con servicios de terceros
- [ ] Analytics y métricas de uso
- [ ] Documentación técnica completa

## 🚀 Instalación y Configuración

### **Requisitos Previos**
- Android Studio Arctic Fox o superior
- Android SDK API 24+
- JDK 8 o superior
- Cuenta de Firebase

### **Pasos de Instalación**

1. **Clonar el repositorio**
```bash
git clone https://github.com/victorm-cardona/visas-ia-android.git
cd visas-ia-android
```

2. **Configurar Firebase**
   - Crear proyecto en [Firebase Console](https://console.firebase.google.com/)
   - Descargar `google-services.json`
   - Colocar en `app/` directory
   - Habilitar Authentication con email/password
   - Configurar App Check con Play Integrity

3. **Configurar reCAPTCHA**
   - Ir a [Google Cloud Console](https://console.cloud.google.com/)
   - Crear clave de reCAPTCHA v3 para Android
   - Configurar en Firebase Console

4. **Compilar y ejecutar**
```bash
./gradlew assembleDebug
```

### **Configuración de Desarrollo**

1. **Abrir en Android Studio**
2. **Sincronizar proyecto con Gradle**
3. **Configurar dispositivo/emulador**
4. **Ejecutar aplicación**

## 📈 Métricas del Proyecto

### **Código**
- **89 archivos** en el repositorio
- **4,633 líneas** de código
- **100% Kotlin**
- **Arquitectura MVVM** implementada

### **Funcionalidades**
- **6 pantallas principales** implementadas
- **3 diálogos** de autenticación
- **5 adaptadores** para listas
- **8 modelos** de datos
- **10+ utilidades** y helpers

## 🔧 Configuración Técnica

### **Dependencias Principales**
```kotlin
// Firebase
implementation("com.google.firebase:firebase-auth-ktx")
implementation("com.google.firebase:firebase-firestore-ktx")
implementation("com.google.firebase:firebase-storage-ktx")
implementation("com.google.firebase:firebase-appcheck-playintegrity")

// UI
implementation("com.google.android.material:material:1.11.0")
implementation("androidx.navigation:navigation-fragment-ktx:2.7.6")

// Base de datos
implementation("androidx.room:room-runtime:2.6.1")
implementation("androidx.room:room-ktx:2.6.1")

// Redes
implementation("com.squareup.retrofit2:retrofit:2.9.0")
implementation("com.squareup.retrofit2:converter-gson:2.9.0")

// Imágenes y cámara
implementation("com.github.bumptech.glide:glide:4.16.0")
implementation("androidx.camera:camera-core:1.3.1")

// IA y ML
implementation("com.google.mlkit:text-recognition:16.0.0")
```

### **Configuración Firebase**
- `google-services.json` configurado
- App Check habilitado con Play Integrity
- reCAPTCHA configurado para Android
- Authentication con email/password habilitado

## 🛣️ Roadmap del Proyecto

### **Corto Plazo (1-2 semanas)**
- [ ] Completar tests unitarios
- [ ] Implementar notificaciones push
- [ ] Mejorar manejo de errores
- [ ] Optimizar rendimiento

### **Mediano Plazo (1-2 meses)**
- [ ] Backend completo con APIs
- [ ] Sistema de pagos integrado
- [ ] Analytics y métricas
- [ ] Modo offline robusto

### **Largo Plazo (3-6 meses)**
- [ ] Integración con embajadas
- [ ] Sistema de tracking de casos
- [ ] IA avanzada para recomendaciones
- [ ] Versión web complementaria

## 🤝 Contribución

### **Cómo Contribuir**
1. Fork el proyecto
2. Crear una rama para tu feature (`git checkout -b feature/AmazingFeature`)
3. Commit tus cambios (`git commit -m 'Add some AmazingFeature'`)
4. Push a la rama (`git push origin feature/AmazingFeature`)
5. Abrir un Pull Request

### **Estándares de Código**
- Usar Kotlin para todo el código nuevo
- Seguir las convenciones de Android
- Documentar funciones complejas
- Escribir tests para nuevas funcionalidades

## 📄 Licencia

Este proyecto está bajo la Licencia MIT. Ver el archivo `LICENSE` para más detalles.

## 👨‍💻 Autor

**Victor Manuel Cardona Espitia**
- GitHub: [@victorm-cardona](https://github.com/victorm-cardona)
- LinkedIn: [Victor Cardona](https://linkedin.com/in/victor-cardona)

## 🙏 Agradecimientos

- [Firebase](https://firebase.google.com/) por la infraestructura backend
- [Google ML Kit](https://developers.google.com/ml-kit) por las capacidades de IA
- [Material Design](https://material.io/) por el sistema de diseño
- [Android Developer Community](https://developer.android.com/) por el soporte

## 📞 Contacto

- **Email**: victor.cardona@example.com
- **Proyecto**: [https://github.com/victorm-cardona/visas-ia-android](https://github.com/victorm-cardona/visas-ia-android)
- **Issues**: [https://github.com/victorm-cardona/visas-ia-android/issues](https://github.com/victorm-cardona/visas-ia-android/issues)

---

**⭐ Si este proyecto te resulta útil, por favor dale una estrella en GitHub!** 