

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








