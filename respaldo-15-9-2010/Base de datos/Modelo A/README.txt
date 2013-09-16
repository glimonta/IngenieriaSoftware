Cosas a considerar:
    - Si el plan es pregpago, este no tiene tarifas adicionales.
    - Las fechas en consume y factura solo tienen mes y año (no día), sus respectivos 
        "contadores" se deberían manejar de manera acumulativa.
    - La relación PLAN-PAQUETE (TIENE) posee fechas y costos, esto es para poder 
        a medida que pasa el tiempo cambiar el costo del plan sin cambiar los 
        servicios que tiene ni el plan en sí, además de quedar el costo viejo
        registrado en el sistema. (Esto lo recomendó Carina)
    - Hay que estar muy pendiente cuando se quiere buscar el costo del plan de un
        producto: Se debe verificar que la fecha esté comprendida entre las 
        fechas de la relación TIENE

Triggers a modificar:

Consume_posee
    - Hay problemas con las fechas, si se coloca POSEE con fechas mensuales se resuelve.
    - Una vez agregado el punto anterior, se debe agregar la verificacion de que 
        ese servicio que se consume lo tenga el producto.
    
VerificarServicio
    - Sería cambiarlo para que no se pueda contratar dos veces en un mismo mes
        en vez de en un mismo período siempre y cuando se haya arreglado el punto 
        anterior.

VerificarDisponibilidadServ, PrepagoConsume:
    - Cuando se cambie POSEE adicional a que sea mensual en vez de por períodos
        Hay que cambiar un poco el trigger

TODO's:

    - Algunas claves son Strings, esto se puede quedar así o pasarlas a códigos
        pero habría que cambiar muchas cosas.
    - Agregar MODELO
    - Agregar la entidad para facilitar la búsqueda 
    - Agregar los tipos en servicio (Prepago, postpago, todos)
    - Eliminar también las funciones en el delete.sql

Lo que puede no lograr:

    - Los casos bordes de la factura.


