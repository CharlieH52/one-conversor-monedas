# Conversor de divisas
## Introducción
Este challenge pretende reforzar los conocimientos obtenidos a lo largo de la especialización backend, en la cual se vieron los temás principales de la programación orientada a objetos con Java, además del consumo de APIs como enfoque principal para esta especialidad.

## Punto de partida...
Para esta primera entrega desde Develop a Main, solo se ha realizado la base del proyecto sin encapsular con clases independientes el código.

Para la funcionalidades principales se obtiene el catalogo completo de las divisas disponibles en la API ExchangeRate y la conversión básica de divisas, así como una validación rapida de las opciones.

> [!NOTE]  
> El programa cumple con lo necesario para funcionar y cubrir lo esencial del challenge a excepción de la encapsulación por clases y separación de responsabilidades.

## Segunda entrega...
Para este punto se presende encapsular en modulos independientes la lógica, separando la aplicación en un modelo MVC (Modelo Vista Controlador) creando la siguiente estructura:
```
|---java
    |---src
        |---api
        |   |---Api.java >> Clase especifica para la conexión.
        |---model
        |   |---Currency.java >> Clase que representa una divisa.
        |---service
        |   |---CurrencyService.java >> Clase encargada de trabajar con la API y el modelo.
        Main.java >> Aquí se orquesta todo mediante un programa CLI.
```
