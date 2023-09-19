

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
    "usuario": {
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
1. Registrar Administradores de Ligas -- X
2. Modificar datos de Administradores de Ligas
3. Registrar Liga    -- X
4. Modificar datos de una liga -- X
5. Registrar Temporada  -- X
6. Modificar datos de una Temporada
7. Registrar Administrador de equipo   -- X
8. Modificar datos de Administrador de Equipo
9. Crear Equipo   -- X
10. Modificar datos de un equipo
11. Registrar Jugadores   -- X
12. Modificar datos de un Jugador
13. Registrar Árbitros  -- X
14. Modificar datos de un Arbitro
15. Asignar una temporada a una liga
16. Iniciar Temporada
17. Concluir una temporada
18. Asignar jugadores a equipos  -- X
19. Modificar jugadores de equipos
20. Asignar Equipos a temporada
21. Modificar Equipos de una temporada
22. Agendar partidos dentro de una temporada
23. Reagendar partido dentro de una temporada
24. Asignar arbitro a un partido
25. Arbitro da por iniciado un partido
26. Arbitro registra datos de un partido
27. Arbitro modifica datos de un partido
28. Arbitro da por terminado algunos de los tiempos del partido
29. Arbitro inicia algunos de los tiempos del partido
30. Arbitro finaliza un partido
31. Ver calendario de partidos
32. Ver jugadores   -- X
33. Ver estadísticas de puntos de un jugador por temporada
34. Ver estadísticas de asistencias de un jugador por temporada
35. Ver estadísticas de tiros de 3 puntos de un jugador por temporada
36. Ver estadísticas de equipo por temporada
37. Ver perfil de un jugador
38. Ver perfil de un equipo
39. Ver perfil de un arbitro
40. Ver ranking de los equipos
41. Buscar Equipo por nombre
42. Buscar Jugador por nombre
43. Buscar liga por nombre
44. Buscar Temporada por nombre







