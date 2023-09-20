

# Basket

Servicio Web de Sistema de Baloncesto en Spring Boot

## Introducción

Este proyecto es un servicio web desarrollado en Spring Boot para un sistema de baloncesto.

## Inicio Rápido

Para empezar con este proyecto, sigue estos pasos:

1. Clona el repositorio en tu máquina local.
2. Configura la base de datos (por ejemplo, MySQL) según sea necesario.
3. Asegúrate de tener Java y Maven instalados.
4. Ejecuta la aplicación localmente.
```bash
mvn spring-boot:run
```
# Uso

## Ligas
**Obtener Temporadas de una Liga**


```bash
GET /Ligas/obtenerTemporadas?idLiga=1
```

**Parámetros:**

- `idLiga` (**requerido**): El identificador de la liga de la que deseas obtener las temporadas.


```bash

Resultado:
[
    {
        "clave": 1,
        "nombreTemporada": "Temporada 2023-2024",
        "fechaInicio": "2023-09-01",
        "fechaTermino": "2024-06-30",
        "estado": "En Curso",
        "cantidadEquipos": 10,
        "cantidadEliminados": 4,
        "categoria": "Juvenil",
        "rama": "Masculina"
    }
]
```
**Modificar Datos de una Liga**

```bash
PUT /Ligas/actualizarLiga
```

**Parámetros:**

- `idLiga` (**requerido**): El identificador de la liga que deseas modificar.
- `nombre` (**requerido**): El nombre de la liga que deseas modificar.

```bash
{
    "ligaId": 1,
    "nombre": "Liga de Baloncesto"
}
```



**Asignarle Administradores a una Liga**

```bash
POST /Ligas/asignarAdmin
```

**Parámetros:**

- `idLiga` (**requerido**): El identificador de la liga a la que deseas asignarle un administrador.
- `idUsuario` (**requerido**): El identificador del usuario que deseas asignarle como administrador de la liga.

```bash
{
    "idLiga": 1,
    "idUsuario": "user"
}
```
**Crear Liga**

```bash
POST /Ligas/crearLiga
```

**Parámetros:**

- `nombre` (**requerido**): El nombre de la liga que deseas crear.

```bash
{
    "nombre": "Liga de Baloncesto"
}
```


## Equipos
**Obtener Jugadores por Nombre del Equipo**
```bash
GET /Equipo/{nombreEquipo}/jugadores
```


#### Parámetros de Ruta

- `{nombreEquipo}` (**cadena,requerido**): El nombre del equipo del cual deseas obtener los jugadores.

#### Ejemplo

**Respuesta*


```bash
[
    {
        "usuario": {
            "usuario": "usuario1",
            "email": "usuario1@example.com",
            "nombre": "Nombre1",
            "apellido": "Apellido1",
            "genero": "MASCULINO",
            "rol": "JUGADOR",
            "edad": 33
        },
        "posicion": "Delantero"
    }
]
```

**Crear un Equipo**
```bash
POST /Equipo/crearEquipo
```
**Parámetros:**

- `nombre` (**requerido**): El nombre del equipo que deseas crear.
- `admin_equipo` (**requerido**): El identificador del usuario que deseas asignarle como administrador del equipo.

```bash
{
    "nombre": "NombreDelEquipo",
    "admin_equipo": {
        "usuario": "Manuel321"
    }
}
```

**Asignarle Jugadores a un Equipo**
```bash
POST /jugadores-equipo/crearJugadoresEquipo
```
**Parámetros:**
- `nombreEquipo` (**requerido**): El nombre del equipo al que deseas asignarle un jugador.
- `idUsuario` (**requerido**): El identificador del usuario que deseas asignarle como jugador del equipo.
- `posicion` (**requerido**): La posicion del jugador que deseas asignarle al equipo.

```bash
{
    "equipo": {
        "nombre": "equipes"
    },
    "jugador": {
        "usuario": "usuario1"
    },
    "posicion": "Delantero"
}
```







## Temporadas
**Crear una Temporada**
```bash
POST /Temporadas/crearTemporada
```
**Parámetros:**

- `nombreTemporada` (**requerido**): El nombre de la temporada que deseas crear.
- `fechaInicio` (**requerido**): La fecha de inicio de la temporada que deseas crear.
- `fechaTermino` (**requerido**): La fecha de termino de la temporada que deseas crear.
- `cantidadEquipos` (**requerido**): La cantidad de equipos de la temporada que deseas crear.
- `categoria` (**requerido**): La categoria de la temporada que deseas crear.
- `rama` (**requerido**): La rama de la temporada que deseas crear.

```bash
{
    "nombreTemporada": "Temporada 2023-2024",
    "fechaInicio": "2023-09-01",
    "fechaTermino": "2024-06-30",
    "cantidadEquipos": 10,
    "categoria": "SENIOR",
    "rama": "MASCULINO"
}
```
**ASIGNAR TEMPORADA A UNA LIGA**
```bash
PUT /Temporadas/asignarLiga
```
```bash
{
    "ligaId": 1,
    "temporadaId": 1,
}
```

**Modificar Datos de una Temporada**
```bash
PUT /Temporadas/modificarDatosTemporada
```
**Parámetros:**
- `temporadaId` (**requerido**): El identificador de la temporada que deseas modificar.
- `estado` (**requerido**): El estado de la temporada que deseas modificar.

```bash
{
  "temporadaId": 1,
  "estado": "FINALIZADA"
}
```



## Usuarios
**Registrar Usuarios**
```bash
POST /usuarios/registrarUsuario
```
**Parámetros:**

- `usuario` (**requerido**): El nombre de usuario del usuario que deseas registrar.
- `email` (**requerido**): El email del usuario que deseas registrar.
- `password` (**requerido**): La contraseña del usuario que deseas registrar.
- `nombre` (**requerido**): El nombre del usuario que deseas registrar.
- `fechaNacimiento` (**requerido**): La fecha de nacimiento del usuario que deseas registrar.
- `apellido` (**requerido**): El apellido del usuario que deseas registrar.
- `genero` (**requerido**): El genero del usuario que deseas registrar.
- `rol` (**requerido**): El rol del usuario que deseas registrar.


```bash
{
    "usuario": "nombre_de_usuario101",
    "email": "correo_electronico101@example.com",
    "password": "contrasena_segura",
    "nombre": "Nombre del Usuario",
    "fechaNacimiento": "1990-01-01", 
    "apellido": "Apellido del Usuario",
    "genero": "MASCULINO", 
    "rol": "JUGADOR" 
}
```
**Modificar Datos de un Usuario**

```bash
PUT /usuarios/ActualizarUsuario
```
**Parámetros:**

- `usuario` (**requerido**): El nombre de usuario del usuario que deseas modificar.
- `nombre` (**opcional**): El nombre del usuario que deseas modificar.
- `apellido` (**opcional**): El apellido del usuario que deseas modificar.

```bash
{
    "usuario": "Jesus123",
    "nombre": "NuevoNombre2",
    "apellido": ""
}
```


- [x] Registrar Administradores de Ligas
- [x] Modificar datos de Administradores de Ligas
- [x] Registrar Liga
- [x] Modificar datos de una liga
- [x] Registrar Temporada
- [x] Modificar datos de una Temporada
- [x] Registrar Administrador de equipo
- [x] Modificar datos de Administrador de Equipo
- [x] Crear Equipo
- [ ] Modificar datos de un equipo
- [x] Registrar Jugadores
- [x] Modificar datos de un Jugador
- [x] Registrar Árbitros
- [x] Modificar datos de un Arbitro
- [x] Asignar una temporada a una liga
- [x] Iniciar Temporada
- [x] Concluir una temporada
- [x] Asignar jugadores a equipos
- [ ] Modificar jugadores de equipos
- [ ] Asignar Equipos a temporada
- [ ] Modificar Equipos de una temporada
- [ ] Agendar partidos dentro de una temporada
- [ ] Reagendar partido dentro de una temporada
- [ ] Asignar arbitro a un partido
- [ ] Arbitro da por iniciado un partido
- [ ] Arbitro registra datos de un partido
- [ ] Arbitro modifica datos de un partido
- [ ] Arbitro da por terminado algunos de los tiempos del partido
- [ ] Arbitro inicia algunos de los tiempos del partido
- [ ] Arbitro finaliza un partido
- [ ] Ver calendario de partidos
- [x] Ver jugadores
- [ ] Ver estadísticas de puntos de un jugador por temporada
- [ ] Ver estadísticas de asistencias de un jugador por temporada
- [ ] Ver estadísticas de tiros de 3 puntos de un jugador por temporada
- [ ] Ver estadísticas de equipo por temporada
- [ ] Ver perfil de un jugador
- [ ] Ver perfil de un equipo
- [ ] Ver perfil de un arbitro
- [ ] Ver ranking de los equipos
- [ ] Buscar Equipo por nombre
- [ ] Buscar Jugador por nombre
- [ ] Buscar liga por nombre
- [ ] Buscar Temporada por nombre








