Sistema de Gestión Hospitalaria – Android

1) Descripción del Proyecto

El Sistema de Gestión Hospitalaria es una aplicación móvil desarrollada en Android Studio usando Kotlin y Jetpack Compose, que permite administrar de forma eficiente los registros de pacientes, médicos, consultas y reportes dentro de un hospital o clínica.
La app utiliza una arquitectura por capas (MVVM + DAO), con SQLite como base de datos local, y aplica los principios de Clean Code y buenas prácticas de desarrollo móvil.

2) Objetivos del sistema

a) Facilitar la gestión de pacientes y médicos mediante formularios intuitivos.
b) Registrar y consultar consultas médicas.
c) Generar reportes por médico, paciente y especialidad.
d) Proporcionar una base sólida para la digitalización de procesos hospitalarios.

3) Tecnologias usadas

Lenguaje:  Kotlin
UI: jetpackCOmpose
Persistencia: SQLite +DAO
IDE: Android Studio
Control de Versiones: GitHub

4) Arquitectura del Proyecto

app/
├─ manifests/
├─ java/
│   └─ com.example.tercerparcial/
│       ├─ components/        # Elementos visuales (NavBar, TopAppBar)
│       ├─ DAO/               # Clases DAO (DaoPaciente, DaoMedico, etc.)
│       ├─ Entidades/         # Clases modelo (Paciente, Medico, Consulta)
│       ├─ model/             # Lógica de negocio
│       ├─ screens/           # Pantallas Compose (CRUD, reportes)
│       ├─ SQL/               # Clase AdminSQL (SQLiteOpenHelper)
│       └─ ui.theme/          # Estilos y colores
└─ res/                       # Recursos gráficos (layouts, strings, icons)


6) Instalacion y ejecucion

a) Requisitos previos

Android Studio instalado
JDK 17 o superior
Dispositivo o emulador Android (API 28 o superior)

b) Pasos para ejecutar

Clona el repositorio:
git clone https://github.com/tu-usuario/SistemaHospitalario.git
Ábrelo en Android Studio.
Espera a que Gradle sincronice las dependencias.
Ejecuta la app con Shift + F10 o el botón play.

7) Licencia
Este proyecto fue desarrollado como parte de la asignatura Ingeniería de Software de la Universidad Privada Boliviana (UPB) y puede ser utilizado con fines académicos.
8) Autores
Sebastián Catalá Miranda
Estudiante de Ingeniería de Sistemas Computacionales – UPB
Docente: Marcelo Bernardo López de la Rosa
9) Repositorio
10) 