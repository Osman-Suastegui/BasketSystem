

# Basket

Servicio Web de Sistema de Baloncesto en Spring Boot

## Introducción

Este proyecto es un servicio web desarrollado en Spring Boot para un sistema de baloncesto.

# Uso

### Ligas

- [Obtener Temporadas de una Liga](#obtener-tournaments-de-una-liga)
- [Buscar Liga por Nombre](#buscar-liga-por-nombre)
- [Modificar Datos de una Liga](#modificar-datos-de-una-liga)
- [Asignarle Administradores a una Liga](#asignarle-administradores-a-una-liga)
- [Crear Liga](#crear-liga)
- [Obtener Ligas de un Adninistrador de Ligas](#obtener-ligas-de-un-administrador-de-ligas)
- [Obtener Admins que no esten en una liga](#Obtener-Admins-que-no-estan-en-una-liga)
- [Obtener Admins que estan en una liga](#Obtener-Admins-que-estan-en-una-liga)

### Jugadores
- [Ver estadisticas de jugador por tournament](#ver-estadisticas-de-jugador-por-tournament)
- [Ver estadisticas de jugador general](#ver-estadisticas-de-jugador-general)
### Equipos

- [Obtener Jugadores por Nombre del Equipo](#obtener-jugadores-por-nombre-del-team)
- [Buscar Equipos por Nombre](#buscar-teams-por-nombre)
- [Crear un Equipo](#crear-un-team)
- [Asignarle Jugadores a un Equipo](#asignarle-jugadores-a-un-team)
- [Eliminar Jugadores de un Equipo](#eliminar-jugadores-de-un-team)
- [Obtener jugadores que no estan en un determinado team]

### Temporadas

- [Crear una Temporada](#crear-una-tournament)
- [Asignar Temporada a una Liga](#asignar-tournament-a-una-liga)
- [Modificar Datos de una Temporada](#modificar-datos-de-una-tournament)
- [Buscar una Temporada por nombre](#buscar-una-tournament-por-nombre)
- [Asignar Equipo a una Temporada](#asignar-team-a-una-tournament)
- [Eliminar Equipos de una Temporada](#eliminar-teams-de-una-tournament)
- [Generar matches de una tournament](#generar-matches-de-una-tournament)
- [Obtener Todos los datos de las  Temporadas de una liga](#obtener-tournaments-de-una-liga)
- [Obtener nombre de tournaments de una liga](#obtener-tournaments-de-una-liga-1)
- [Obtener teams de una tournament](#obtener-teams-de-una-tournament)
- [Obtener Equipos no en tournament](#obtener-teams-no-en-tournament)
- [Eliminar un arbitro de una tournament](#eliminar-un-arbitro-de-una-tournament)

### Usuarios
- [Log in Usuario](#log-in-usuario)
- [Registrar Usuarios](#registrar-usuarios)
- [Buscar Jugador por Usuario](#buscar-USUARIO-por-nombre-de-usuario-y-rol)
- [Modificar Datos de un Usuario](#modificar-datos-de-un-usuario)
- [Obtener Tipo de jugador](#obtener-tipo-de-jugador)

### Arbitros

- [Agregar arbitro a una tournament](#agregar-arbitro-a-una-tournament)
- [Obtener arbitros de una tournament](#obtener-arbitros-de-una-tournament)
- [Obtener matches de un arbitro (calendario)](#obtener-matches-de-un-arbitro-calendario)
- [Arbitro registra datos de un match](#arbitro-registra-datos-de-un-match)
- [Obtener matches de un jugador (calendario)](#obtener-matches-de-un-jugador-calendario)
- [Obtener matches de una tournament](#obtener-matches-de-una-tournament)
- [Agendar match](#agendar-match)
- [Asignar arbitro a un match](#asignar-arbitro-a-un-match)
- [Arbitro modifica datos de un match](#arbitro-modifica-datos-de-un-match)

### Partidos
- [Obtener todos los jugadores de un match y team](#obtener-todos-los-jugadores-de-un-match-y-un-team)
- [Obtener match por id](#obtener-match-por-id)
- [Obtener los matches de un team](#obtener-los-matches-de-un-team)
## Ligas
### Obtener Temporadas de una Liga


```bash
GET /Ligas/obtenerTemporadas?idLiga=1
```

**Parámetros:**

- `idLiga` (**requerido**): El identificador de la liga de la que deseas obtener las tournaments.


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
### Buscar Liga por Nombre

```bash
GET /Ligas/buscarLigaPorNombre?nombre=liga
```

**Parámetros:**
- `nombre` (**requerido**): El nombre de la liga que deseas buscar.

```bash
Resultado:
[
    {
        "idLiga": 3,
        "nombre": "Liga de Baloncesto"
    },
    {
        "idLiga": 2,
        "nombre": "liga de basket nba"
    },
    {
        "idLiga": 1,
        "nombre": "NuevoNombreDeLaLiga2"
    }
]
```





### Modificar Datos de una Liga

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



### Asignarle Administradores a una Liga

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
### Crear Liga

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

### Obtener Ligas de un administrador de ligas

```bash
GET /Ligas/obtenerLigasDeAdmin?usuario=Jesus123
```
**Parámetros:**
- `usuario` (**requerido**): El nombre de usuario 

**Resultado:**
```bash
[
    {
        "idLiga": 1,
        "nombreLiga": "NuevoNombreDeLaLiga2",
        "idTemporada": 1,
        "nombreTemporada": "Temporada de Prueba"
    },
    {
        "idLiga": 1,
        "nombreLiga": "NuevoNombreDeLaLiga2",
        "idTemporada": 22,
        "nombreTemporada": "Temporada de Prueba"
    }
]
```

### Obtener Admins que no estan en una liga

```bash
GET /Ligas/obtenerAdminsNoEnLiga?idLiga=1
```
**Parámetros:**
- `idLiga` (**requerido**): El identificador de la liga de la que deseas obtener los administradores.


**Resultado:**
```bash
[
    {
        "usuario": "Jesus123"
    }
]
```
### Obtener Admins que estan en una liga

```bash
GET /Ligas/obtenerAdminsDeLiga?idLiga=1
```
**Parámetros:**
- `idLiga` (**requerido**): El identificador de la liga de la que deseas obtener los administradores.

```bash
Resultado:
[
    {
        "usuario": "Jesus123"
    }
]
```




## Jugadores
### Ver estadisticas de jugador por tournament
```bash
GET estadisticas/jugador-tournament?idJugador=usuario1&idTemporada=1
```
**Parámetros:**
- `idJugador` (**requerido**): El identificador del jugador del que deseas obtener las estadisticas.
- `idTemporada` (**requerido**): El identificador de la tournament de la que deseas obtener las estadisticas.

**Resultado:**
```bash
{
    "totalPuntosPorTemporada": 19,
    "tirosDe3puntosPorTemporada": 4,
    "tirosDe2puntosPorTemporada": 3,
    "tirosLibresPorTemporada": 1
    "asistenciasPorTemporada": 2,
    "faltasPorTemporada": 5,
}
```

### Ver estadisticas de jugador general
```bash
GET /estadisticas/jugador-general?idJugador=usuario1
```
**Parámetros:**
- `idJugador` (**requerido**): El identificador del jugador del que deseas obtener las estadisticas.

**Resultado:**

```bash
{
    "tirosDe3puntosGenerales": 4,
    "tirosDe2puntosGenerales": 3,
    "totalPuntosGenerales": 19,
    "tirosLibresGenerales": 1,
    "asistenciasGenerales": 2,
    "faltasGenerales": 5
}
```

## Equipos
### Obtener Jugadores por Nombre del Equipo
```bash
GET /Equipo/{nombreEquipo}/jugadores
```


#### Parámetros de Ruta

- `{nombreEquipo}` (**cadena,requerido**): El nombre del team del cual deseas obtener los jugadores.

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

### Buscar Equipos por Nombre
```bash
GET /Equipo/buscarEquipoPorNombre?nombre=e
```
**Parámetros:**
- `nombre` (**requerido**): El nombre del team que deseas buscar.

```bash
Resultado:
[
    {
        "nombre": "equipe"
    },
    {
        "nombre": "equipes"
    },
    {
        "nombre": "NombreDelEquipo"
    }
]
```


### Crear un Equipo
```bash
POST /Equipo/crearEquipo
```
**Parámetros:**

- `nombre` (**requerido**): El nombre del team que deseas crear.
- `admin_equipo` (**requerido**): El identificador del usuario que deseas asignarle como administrador del team.

```bash
{
    "nombre": "tiburoones jaja",
    "admin_equipo": {
        "usuario": "Manuel321"
    },
    "rama": "MASCULINO",
    "categoria": "SENIOR"
}
```

### Asignarle Jugadores a un Equipo
```bash
POST /jugadores-team/crearJugadoresEquipo
```
**Parámetros:**
- `nombreEquipo` (**requerido**): El nombre del team al que deseas asignarle un jugador.
- `idUsuario` (**requerido**): El identificador del usuario que deseas asignarle como jugador del team.
- `posicion` (**requerido**): La posicion del jugador que deseas asignarle al team.

```bash
{
    "team": {
        "nombre": "equipes"
    },
    "jugador": {
        "usuario": "usuario1"
    },
    "posicion": "Delantero"
}
```

### Eliminar Jugadores de un Equipo
```bash
DELETE /Equipo/eliminarJugador
```
**Parámetros:**
- `nombreEquipo` (**requerido**): El nombre del team al que deseas eliminarle un jugador.
- `nombreJugador` (**requerido**): El identificador del usuario que deseas eliminarle como jugador del team.

```bash
{
  "nombreEquipo": "equipes",
  "nombreJugador": "nombre_de_usuario"
}
```

### Obtener jugadores que no estan en un determinado team
```bash
GET /Equipo/obtenerJugadoresParaEquipo?nombreEquipo=equipe
```
**Parámetros:**
- `nombreEquipo` (**requerido**): El nombre del team del que deseas obtener los jugadores.

**Respuesta**
```bash
[
    {
        "usuario": "gabygabss",
        "email": "gabyjefedegrupo@gmail.com",
        "nombre": "gaby ",
        "fechaNacimiento": "2002-07-12",
        "apellido": "beltran mirafuente",
        "genero": "FEMENINO",
        "rol": "JUGADOR",
        "enabled": true,
        "authorities": [
            {
                "authority": "ROLE_JUGADOR"
            }
        ],
        "username": "gabygabss",
        "accountNonExpired": true,
        "credentialsNonExpired": true,
        "accountNonLocked": true,
        "edad": 21
    }
]
```








## Temporadas
### Crear una Temporada
```bash
POST /Temporadas/crearTemporada
```
**Parámetros:**

- `nombreTemporada` (**requerido**): El nombre de la tournament que deseas crear.
- `fechaInicio` (**requerido**): La fecha de inicio de la tournament que deseas crear.
- `fechaTermino` (**requerido**): La fecha de termino de la tournament que deseas crear.
- `cantidadEquipos` (**requerido**): La cantidad de teams de la tournament que deseas crear.
- `categoria` (**requerido**): La categoria de la tournament que deseas crear.
- `rama` (**requerido**): La rama de la tournament que deseas crear.

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
### ASIGNAR TEMPORADA A UNA LIGA
```bash
PUT /Temporadas/asignarLiga
```
```bash
{
    "ligaId": 1,
    "temporadaId": 1,
}
```

### Modificar Datos de una Temporada
```bash
PUT /Temporadas/modificarDatosTemporada
```
**Parámetros:**
- `temporadaId` (**requerido**): El identificador de la tournament que deseas modificar.
- `estado` (**requerido**): El estado de la tournament que deseas modificar.

```bash
{
  "temporadaId": 1,
  "estado": "FINALIZADA"
}
```

### Buscar una Temporada por nombre
```bash
GET /Temporadas/buscarTemporadasPorNombre?nombreTemporada=z
```
**Parámetros:**
- `nombreTemporada` (**requerido**): El nombre de la tournament que deseas buscar.

```bash
Resultado:
[
    {
        "claveTemporada": 37,
        "nombreTemporada": "Temporada 2023-2024"
    },
    {
        "claveTemporada": 38,
        "nombreTemporada": "Temporada 2023-2024"
    },
    {
        "claveTemporada": 39,
        "nombreTemporada": "Temporada 2023-2024"
    }
]
```




### Asignar Equipo a una Temporada
```bash
POST /EquipoTemporada/crearEquipoTemporada
```
**Parámetros:**
- `temporadaId` (**requerido**): El identificador de la tournament a la que deseas asignarle un team.
- `nombreEquipo` (**requerido**): El nombre del team que deseas asignarle a la tournament.

```bash
{
  "tournament": {
    "claveTemporada": 1
  },
  "team": {
    "nombre": "equipes"
  }
}
```

### Eliminar Equipos de una Temporada
```bash
DELETE /EquipoTemporada/eliminarEquipoTemporada
```

**Parámetros:**
- `temporadaId` (**requerido**): El identificador de la tournament a la que deseas modificarle/eliminarle un team.
- `nombreEquipo` (**requerido**): El nombre del team que deseas modificar/eliminar de la tournament.

```bash
{
  "tournament": {
    "claveTemporada": 1
  },
  "team": {
    "nombre": "equipes"
  }
}
```
### Generar matches de una tournament
```bash
POST Partido/generarPartidosTemporada
```
**Parámetros:**
- `temporadaId` (**requerido**): El identificador de la tournament a la que deseas generarle los matches.

**Resultado:**
```bash
{
    "idTemporada": 1
}
```

### Obtener Temporadas de una liga
```bash
GET /Temporadas/obtenerTemporadasDeLiga?idLiga=1
```
**Parámetros:**

- `idLiga` (**requerido**): El id de la liga a ver tournaments.

**Respuesta**
```bash
[
    {
        "idTemporada": 1,
        "nombreTemporada": "Temporada de Prueba"
    },
    {
        "idTemporada": 22,
        "nombreTemporada": "Temporada de Prueba"
    }
]
```
### Obtener teams de una tournament
```bash
GET EquipoTemporada/obtenerEquiposTemporada?temporadaId=1
```
**Parámetros:**
- `temporadaId` (**requerido**): El identificador de la tournament de la que deseas obtener los teams.

**Respuesta**
```bash
{
    "nombreEquipo": "equipe"
}
```

### Obtener Equipos no en tournament
```bash
GET /EquipoTemporada/obtenerEquiposNoEnTemporada?temporadaId=1
```
**Parámetros:**
- `temporadaId` (**requerido**): El identificador de la tournament de la que deseas obtener los teams.

**Respuesta**
```bash
[
    {
        "nombre": "equipe"
    }
]
```

### Eliminar un arbitro de una tournament
```bash
http://localhost:8080/Temporadas/eliminarArbitro?temporadaId=27&arbitroId=nombre_de_usuario101
```
**Parámetros:**
- `temporadaId` (**requerido**): El identificador de la tournament de la que deseas eliminar el arbitro.

**Respuesta**
```bash
{
    "message": "Arbitro eliminado exitosamente."
}
```







### Obtener Estado de una tournament
```bash
GET /Temporadas/obtenerEstadoTemporada?idTemporada=1
```
**Parámetros:**
- `idTemporada` (**requerido**): El identificador de la tournament de la que deseas obtener el estado.

**Respuesta**
```bash
{
    "estado": "En Curso"
}
```



## Usuarios

### Log in Usuario
```bash
POST /auth/login
```
**Parámetros:**

- `usuario` (**requerido**): El nombre de usuario del usuario que deseas loguear.
- `password` (**requerido**): La contraseña del usuario que deseas loguear.

```bash
{
    "usuario": "nombre_de_usuario",
    "password": "contrasena_segura"
}
```
**Respuesta**
```bash
{
    "token": "token"
}
```


### Registrar Usuarios
```bash
POST /auth/register
}
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
**Respuesta**
```bash
{
    "token": "token"
}
```

### Buscar Usuario por Nombre de Usuario y Rol
```bash
GET /usuarios/obtenerJugador?usuario=A&rol=JUGADOR
```
**Parámetros:**

- `usuario` (**requerido**): El nombre de usuario del usuario que deseas buscar.
- `rol` (**requerido**): El rol del usuario que deseas buscar.

```bash
Resultado:
[
    {
        "usuario": "nombre_de_usuario10",
        "email": "correo_electronico10@example.com",
        "nombre": "Nombre del Usuario",
        "fechaNacimiento": "1990-01-01",
        "apellido": "Apellido del Usuario",
        "genero": "MASCULINO",
        "rol": "JUGADOR",
        "edad": 33
    },
    {
        "usuario": "nombre_de_usuario101",
        "email": "correo_electronico101@example.com",
        "nombre": "Nombre del Usuario",
        "fechaNacimiento": "1990-01-01",
        "apellido": "Apellido del Usuario",
        "genero": "MASCULINO",
        "rol": "JUGADOR",
        "edad": 33
    }
]
```
### Obtener Tipo de jugador

```bash
GET http://localhost:8080/usuarios/obtenerTipoUser?usuario=jarcor022
```
**Parámetros:**

- `usuario` (**requerido**): El nombre de usuario del usuario que deseas buscar.

```bash
Resultado:
{
    "rol": "JUGADOR"
}
```


### Modificar Datos de un Usuario

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

## Arbitros
### Agregar arbitro a una tournament
```bash
POST /Temporadas/agregarArbitro
```
**Parámetros:**

- `temporadaId` (**requerido**): El identificador de la tournament a la que deseas agregar un arbitro.
- `arbitroId` (**requerido**): El identificador del arbitro que deseas agregar a la tournament.

```bash
{
    "temporadaId": 1,
    "arbitroId": "usuario1"
}
```
### Obtener arbitros de una tournament
```bash
GET /Temporadas/obtenerArbitros?idTemporada=1
```
**resultado**
```bash

  [
      {
        "usuario": "usuario1",
        "email": "usuario1@example.com",
        "password": null,
        "nombre": "Nombre1",
        "fechaNacimiento": "1990-05-15",
        "apellido": "Apellido1",
        "genero": "MASCULINO",
        "rol": "ADMIN_EQUIPO",
        "edad": 33
      }
  ]
```
### obtener matches de un arbitro (calendario)
```bash
Get Partido/obtenerPartidosArbitro?idArbitro=Manuel321
```

**parámetros**
- `idArbitro` (**requerido**): El identificador del arbitro del que deseas obtener los matches.

**resultado**
```bash
[
    {
        "temporadaId": 1,
        "arbitro": "Manuel321",
        "fechaInicio": "2023-09-22T02:17:07.000+00:00",
        "team2": "el real madrid",
        "team1": "Chivas",
        "idPartido": 1
    }
]
```

### Arbitro registra datos de un match
```bash
PUT /JugadorPartido/agregarJugadorPartido
```
**Parámetros:**
- `idPartido` (**requerido**): El identificador del match al que deseas agregarle un jugador.
- `idJugador` (**requerido**): El identificador del jugador que deseas agregar al match.
- `team` (**requerido**): El nombre del team al que pertenece el jugador.

```bash
{
  "team": "equipe",
  "jugador": {
    "usuario": "nombre_de_usuario"
  },
  "match": {
    "clavePartido": 1
  }
}
```


### obtener matches de un jugador (calendario)
```bash
Get Partido/obtenerPartidosJugador?idJugador=usuario1
```

**parámetros**
- `idJugador` (**requerido**): El identificador del jugador del que deseas obtener los matches.

**resultado**
```bash
[
    {
        "temporadaId": 1,
        "arbitro": "Manuel321",
        "fechaInicio": "2023-09-22T02:17:07.000+00:00",
        "team2": "el real madrid",
        "team1": "Chivas",
        "idPartido": 1
    }
]
```
### Obtener matches de una tournament
```bash
GET Partido/obtenerPartidosTemporada?idTemporada=1
```
**parámetros**
- `idTemporada` (**requerido**): El identificador de la tournament de la que deseas obtener los matches.
```bash
    [
        {
        "temporadaId": 1,
        "arbitro": "Manuel321",
        "fechaInicio": "2023-09-22T02:17:07.000+00:00",
        "team2": "el real madrid",
        "team1": "Chivas",
        "idPartido": 1
        }
  ]
```
### Agendar match
```bash
PUT /Partido/agendar
```
**Parámetros:**

- `clavePartido` (**requerido**): El identificador del match
- `fechaInicio` (**requerido**): La fecha de inicio del match
```bash
{
    "clavePartido":1,
    "fechaInicio": "2023-09-21 20:17:45"
}
```

### Asignar arbitro a un match
```bash
PUT /Partido/asignarArbitro
```
**Parámetros:**

- `clavePartido` (**requerido**): El identificador del match
- `arbitro` (**requerido**): El identificador del arbitro
```bash
{
  "clavePartido": 1, // Reemplaza con el ID del match que deseas asignar
  "arbitro": {
    "usuario": "Jesus123" // Reemplaza con el nombre de usuario del árbitro que deseas asignar
  }
}
```

### Arbitro modifica datos de un match
**Arbitro modifica datos de un match (WebSocket)
Actualización en tiempo real de estadísticas de un match
Para permitir al árbitro modificar datos de un match en tiempo real a través de WebSocket, se utiliza un mensaje con el siguiente formato:**

```bash
/app/agregarPunto
```
**Parametros**


- `clavePartido` (**requerido**): El identificador del match.

- `jugador` (**requerido**): El identificador del jugador.

- `descripcion` (**requerido**): La descripción de la estadística que se va a actualizar.


```bash

{
    "clavePartido": 9,
    "jugador": "daniel",
    "descripcion": "tirosDe3Puntos"
}
```



### Obtener Arbitros que no estan en una tournament
```bash
GET /Temporadas/obtenerArbitrosNoEnTemporada?idTemporada=1
```
**Parámetros:**

- `idTemporada` (**requerido**): El identificador de la tournament de la que deseas obtener los arbitros.

**Respuesta**
```bash
[
    {
        "usuario": "usuario1"
    }
]
```

## Partidos

### Obtener todos los jugadores de un match y un team
```bash
  GET /JugadorPartido/obtenerJugadoresDePartidoyEquipo?clavePartido=9&nombreEquipo=chivas&enBanca=0
```
**Parámetros:**
- `clavePartido` (**requerido**): El identificador del match
- `nombreEquipo` (**requerido**): El nombre del team
- `enBanca` (**opcional**): este parametro toma 2 valores 1 o 0  si es 1 retorna todos los jugadores que estan en banca si es 0 retorna los que no estan en banca si no se especifica el campo retorna ambos

```bash
[
  { 
    "jugador":"daniel",
    "asistencias":3,
    "faltas":6,
    "tirosDe2Puntos":2,
    "tirosDe3Puntos":10,
    "tirosLibres":2
  },
  { 
    "jugador":"daniel2",
    "asistencias":13,
    "faltas":2,
    "tirosDe2Puntos":1,
    "tirosDe3Puntos":7,
    "tirosLibres":3
  }
]
```

### Obtener match por id
```bash
  GET /Partido/obtenerPartidoPorId?clavePartido=9
```

**Parámetros:**

- `idPartido` (**requerido**): El identificador del match

```bash
{
    "clavePartido": 9,
    "fechaInicio": "2023-10-26T03:43:08Z",
    "team1": "Miami Heat",
    "team2": "Denver Nuggets",
    "arbitro": "prueba",
    "resultado": "",
    "claveTemporada": 1,
    "fase": "CUARTOS_DE_FINAL"
}
```

### Obtener los matches de un team
```bash
  GET Partido/obtenerPartidosEquipo?idEquipo=equipo12&estatusPartido=proximos
```

**Parámetros:**
- `idEquipo` (**requerido**): El identificador del team
- `estatusPartido` (**requerido**): El estatus del match puede ser proximo, en curso o finalizado

```bash
[
    {
        "temporadaId": 27,
        "fechaInicio": "2023-11-24T15:30:45Z",
        "team2": "equipo13",
        "team1": "equipo12",
        "idPartido": 9
    }
]
```



- [x] Registrar Administradores de Ligas
- [x] Modificar datos de Administradores de Ligas
- [x] Registrar Liga
- [x] Modificar datos de una liga
- [x] Registrar Temporada
- [x] Modificar datos de una Temporada
- [x] Registrar Administrador de team
- [x] Modificar datos de Administrador de Equipo
- [x] Crear Equipo
- [x] Modificar datos de un team
- [x] Registrar Jugadores
- [x] Modificar datos de un Jugador
- [x] Registrar Árbitros
- [x] Modificar datos de un Arbitro
- [x] Asignar una tournament a una liga
- [x] Iniciar Temporada
- [x] Concluir una tournament
- [x] Asignar jugadores a teams
- [x] Modificar jugadores de teams
- [x] Asignar Equipos a tournament
- [x] Modificar Equipos de una tournament
- [x] Agendar matches dentro de una tournament
- [x] Reagendar match dentro de una tournament
- [x] Asignar arbitro a un match
- [ ] Arbitro da por iniciado un match
- [x] Arbitro registra datos de un match
- [x] Arbitro modifica datos de un match
- [ ] Arbitro da por terminado algunos de los tiempos del match
- [ ] Arbitro inicia algunos de los tiempos del match
- [ ] Arbitro finaliza un match
- [x] Obtener Arbitro de una tournament
- [x] Asginar Arbitro a una tournament
- [x] Ver calendario de matches
- [x] Ver jugadores
- [x] Ver estadísticas de puntos de un jugador por tournament
- [x] Ver estadísticas de asistencias de un jugador por tournament
- [x] Ver estadísticas de tiros de 3 puntos de un jugador por tournament
- [x] Ver estadísticas de tiros de 2 puntos de un jugador por tournament
- [x] Ver estadísticas de tiros libres de un jugador por tournament
- [x] Ver estadísticas de faltas de un jugador por tournament
- [x] Ver estadísticas de asistencias de un jugador por tournament
- [ ] Ver estadísticas de team por tournament
- [x] Ver perfil de un jugador (cualquier usuario)
- [x] Ver perfil de un team
- [x] Ver perfil de un arbitro (cualquier usuario)
- [ ] Ver ranking de los teams
- [x] Buscar Equipo por nombre
- [x] Buscar Jugador por nombre (cualquier usuario)
- [x] Buscar liga por nombre
- [x] Buscar Temporada por nombre
- [x] obtener matches de una tournament
- [x] generar matches de una tournament








