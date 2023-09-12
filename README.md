

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





