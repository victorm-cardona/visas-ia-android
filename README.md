# Visas IA - Aplicación de Gestión de Visas con Inteligencia Artificial

## 📋 Descripción

Visas IA es una aplicación móvil Android que automatiza y simplifica el proceso de solicitud de visas para turistas, integrando inteligencia artificial para análisis de casos, procesamiento de documentos y asesoramiento personalizado.

## 🎯 Características Principales

### 🔐 Autenticación y Seguridad
- **Registro/Login**: Sistema de autenticación de usuarios
- **Autenticación Biométrica**: Soporte para huella dactilar
- **Gestión de Sesiones**: Control de acceso y seguridad

### 📱 Dashboard Principal
- **Menú Principal**: Navegación intuitiva a todas las funcionalidades
- **Estadísticas**: Resumen de casos activos y estado
- **Acceso Rápido**: Botones directos a funciones principales

### 🆕 Nuevo Caso (Flujo Completo)
1. **Selección País Destino**: Lista de países disponibles
2. **Selección Tipo Visa**: Turista, Negocios, Estudiante, Trabajo, Familiar
3. **Métodos de Ingreso de Datos**:
   - **Formulario Digital**: Entrada manual de información
   - **Subida Documentos**: Carga de archivos PDF con procesamiento OCR
   - **Escaneo Cámara**: Captura de documentos con cámara
4. **Análisis IA**: Procesamiento inteligente del caso
5. **Generación Resultados**: Análisis detallado y recomendaciones
6. **Guardar Caso**: Almacenamiento en base de datos

### 📋 Gestión de Casos Existentes
- **Lista de Casos**: Vista de todos los expedientes
- **Ver Detalles**: Información completa de cada caso
- **Actualizar Caso**: Modificación de información existente
- **Seguimiento**: Estado y progreso del trámite

### ⚙️ Configuración
- **Configurar Perfil**: Datos personales del usuario
- **Configurar Notificaciones**: Preferencias de alertas
- **Configurar Privacidad**: Control de datos personales

### 🤖 Consultoría IA
- **Chat con IA**: Asistente virtual para consultas
- **Consulta Base Conocimientos**: Información de visas por país
- **Respuesta IA**: Recomendaciones personalizadas
- **Guardar Consulta**: Historial de interacciones

## 🏗️ Arquitectura Técnica

### Tecnologías Utilizadas
- **Kotlin**: Lenguaje principal de desarrollo
- **Android Jetpack**: Componentes modernos de Android
- **Room Database**: Almacenamiento local de datos
- **Retrofit**: Comunicación con APIs
- **CameraX**: Captura de imágenes
- **ML Kit**: Procesamiento OCR
- **Material Design 3**: Interfaz de usuario moderna

### Estructura del Proyecto
```
app/src/main/java/com/example/visas_ia/
├── MainActivity.kt                    # Punto de entrada
├── data/
│   └── model/                        # Modelos de datos
│       ├── VisaCase.kt
│       ├── Country.kt
│       └── VisaType.kt
├── ui/
│   ├── auth/                         # Autenticación
│   │   └── LoginActivity.kt
│   ├── dashboard/                    # Dashboard principal
│   │   └── DashboardActivity.kt
│   ├── cases/                        # Gestión de casos
│   │   ├── NewCaseActivity.kt
│   │   └── ExistingCasesActivity.kt
│   ├── config/                       # Configuración
│   │   └── SettingsActivity.kt
│   └── ai/                           # Consultoría IA
│       └── ConsultationActivity.kt
└── utils/                            # Utilidades
    ├── SharedPreferencesManager.kt
    ├── AIAnalyzer.kt
    └── OCRProcessor.kt
```

## 🔄 Flujo de la Aplicación

### 1. Inicio y Autenticación
```
Usuario Abre App → ¿Usuario Registrado? → 
├─ Sí → Dashboard Principal
└─ No → Registro/Login → Autenticación → 
    ├─ Exitosa → Dashboard Principal
    └─ Fallida → Volver a Login
```

### 2. Menú Principal
```
Dashboard Principal → Menú Principal → 
├─ Nuevo Caso
├─ Ver Casos Existentes
├─ Configuración
├─ Consultoría IA
└─ Cerrar Sesión
```

### 3. Flujo de Nuevo Caso
```
Nuevo Caso → 
├─ Selección País Destino
├─ Selección Tipo Visa
└─ Método Ingreso Datos →
    ├─ Formulario Digital → Validación Datos
    ├─ Subida Documentos → Procesamiento OCR
    └─ Escaneo Cámara → Procesamiento Imagen
    ↓
Análisis IA → Generación Resultados → 
Recomendaciones → Guardar Caso → Dashboard Actualizado
```

### 4. Gestión de Casos Existentes
```
Ver Casos Existentes → Seleccionar Caso → 
Ver Detalles → Actualizar Caso → Análisis IA
```

### 5. Consultoría IA
```
Consultoría IA → Chat con IA → 
Consulta Base Conocimientos → Respuesta IA → Guardar Consulta
```

## 🚀 Instalación y Configuración

### Requisitos Previos
- Android Studio Arctic Fox o superior
- Android SDK API 24+
- Kotlin 1.8+

### Pasos de Instalación
1. Clonar el repositorio
2. Abrir el proyecto en Android Studio
3. Sincronizar dependencias de Gradle
4. Ejecutar la aplicación en un dispositivo o emulador

### Configuración de Permisos
La aplicación requiere los siguientes permisos:
- `CAMERA`: Para escaneo de documentos
- `READ_EXTERNAL_STORAGE`: Para subida de archivos
- `USE_BIOMETRIC`: Para autenticación biométrica
- `INTERNET`: Para comunicación con APIs

## 🎨 Interfaz de Usuario

### Diseño Material Design 3
- **Colores**: Paleta moderna con colores primarios y de estado
- **Tipografía**: Jerarquía clara de textos
- **Componentes**: Cards, botones y elementos interactivos
- **Navegación**: Flujo intuitivo entre pantallas

### Pantallas Principales
1. **Splash Screen**: Logo y carga inicial
2. **Login**: Autenticación de usuarios
3. **Dashboard**: Menú principal con estadísticas
4. **Nuevo Caso**: Formulario paso a paso
5. **Casos Existentes**: Lista y gestión de expedientes
6. **Consultoría IA**: Chat interactivo
7. **Configuración**: Ajustes de la aplicación

## 🔧 Funcionalidades Técnicas

### Procesamiento OCR
- Extracción de texto de documentos PDF
- Reconocimiento de información clave
- Validación automática de documentos

### Análisis de IA
- Evaluación de probabilidad de aprobación
- Recomendaciones personalizadas
- Análisis de requisitos por país

### Base de Datos
- Almacenamiento local con Room
- Sincronización con servidor
- Backup automático de datos

## 📊 Estados de Visa

- **PENDIENTE**: Caso recién creado
- **EN_PROCESO**: En revisión por autoridades
- **APROBADA**: Visa otorgada
- **RECHAZADA**: Solicitud denegada

## 🔮 Próximas Funcionalidades

- [ ] Integración con APIs de embajadas
- [ ] Notificaciones push en tiempo real
- [ ] Modo offline completo
- [ ] Soporte multiidioma
- [ ] Integración con servicios de pago
- [ ] Análisis predictivo avanzado

## 🤝 Contribución

1. Fork el proyecto
2. Crear una rama para tu feature (`git checkout -b feature/AmazingFeature`)
3. Commit tus cambios (`git commit -m 'Add some AmazingFeature'`)
4. Push a la rama (`git push origin feature/AmazingFeature`)
5. Abrir un Pull Request

## 📄 Licencia

Este proyecto está bajo la Licencia MIT - ver el archivo [LICENSE.md](LICENSE.md) para detalles.

## 📞 Soporte

Para soporte técnico o consultas:
- Email: soporte@visas-ia.com
- Documentación: [docs.visas-ia.com](https://docs.visas-ia.com)
- Issues: [GitHub Issues](https://github.com/visas-ia/app/issues)

---

**Desarrollado con ❤️ para simplificar el proceso de visas** 