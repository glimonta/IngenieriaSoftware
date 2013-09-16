Cosas a considerar:
    - Si el plan es pregpago, este no tiene tarifas adicionales (Consume hasta 
        que se le acaba el plan, no más de eso).
    - Las fechas en CONSUME y FACTURA sólo tienen mes y año (no día), sus respectivos 
        "contadores" se deberían manejar de manera acumulativa.
    - La relación PLAN-PAQUETE (TIENE) posee fechas y costos, esto es para poder 
        a medida que pasa el tiempo cambiar el costo del plan sin cambiar los 
        servicios que tiene ni el plan en sí, además de quedar el costo viejo
        registrado en el sistema. (Esto lo recomendó Carina)
    - Hay que estar muy pendiente cuando se quiere buscar el costo del plan de un
        producto: Se debe verificar que la fecha esté comprendida entre las 
        fechas de la relación TIENE
    - Las afiliaciones de los servicios adicionales son mensuales: las fechas de
        la relación POSEE tienen sólo mes

Lo que puede no lograr:

    - Los casos bordes de la factura.


