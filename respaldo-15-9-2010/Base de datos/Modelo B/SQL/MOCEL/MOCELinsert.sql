
---------------------- DATOS SUMINISTRADOS EN LA TAREA -------------------------

--Insertando modelos de celular MOCEL --
INSERT INTO MODELO VALUES('BB310');
INSERT INTO MODELO VALUES('Zenzei');
INSERT INTO MODELO VALUES('Letus');

-- Agregando los Planes MOCEL previstos en el PDF --
INSERT INTO PLAN VALUES(1,'PLAN MOCEL 2000',49,'PREPAGO','El plan MOCEL 2000 aplica para móviles prepago');          
INSERT INTO PLAN VALUES(2,'PLAN MIXTO PLUS',211,'POSTPAGO','El plan Mixto Plus aplica para móviles postpago');

--Agregamos los tipos de servicios correspondientes a MOCEL--
INSERT INTO TIPO_SERVICIO VALUES('MENSAJE');
INSERT INTO TIPO_SERVICIO VALUES('LLAMADA');
INSERT INTO TIPO_SERVICIO VALUES('BUZON');

--Agregamos los servicios que TVCABLE ofrece--


INSERT INTO SERVICIO VALUES (0,'LLAMADA','Segundos a móviles MOCEL',10.75,15.89,'No details yet');
INSERT INTO SERVICIO VALUES (1,'LLAMADA','Segundos a otras operadoras móviles',11.05,16.45,'No details yet');
INSERT INTO SERVICIO VALUES (2,'LLAMADA','Segundos a fijos MOCEL',5.47,4.15,'No details yet');
INSERT INTO SERVICIO VALUES (3,'LLAMADA','Segundo adicional MOCEL-MOCEL',0.01150,8.999,'No details yet');
INSERT INTO SERVICIO VALUES (4,'LLAMADA','Segundos a cualquier operador fijo',61.3,7.65,'No details yet');
INSERT INTO SERVICIO VALUES (5,'LLAMADA','Segundos a cualquier operadora',8.04,8.65,'No details yet');
INSERT INTO SERVICIO VALUES (6,'LLAMADA','Minutos a cualquier operadora',6.04,5.55,'No details yet');

INSERT INTO SERVICIO VALUES (7,'MENSAJE','Mensajes de texto',8.01,9.099,'No details yet');
INSERT INTO SERVICIO VALUES (8,'BUZON','Buzón de mensajes',10.087,15.065,'No details yet');
INSERT INTO SERVICIO VALUES (9,'LLAMADA','Segundo adicional a cualquier operador fijo',0.01150,7.65,'No details yet');
INSERT INTO SERVICIO VALUES (10,'LLAMADA','Segundo adicional a cualquier operadora',0.01250,7.65,'No details yet');


--Incluimos el servicio adicional Pegadito con otros 1500 --
INSERT INTO SERV_ADICIONAL VALUES(5,1500,'POSTPAGO',16);
--Incluimos el servicio adicional Pegadito con otros 30 --
INSERT INTO SERV_ADICIONAL VALUES(6,30,'PREPAGO',19);
--Incluimos el servicio adicional Mensajes 800 --
INSERT INTO SERV_ADICIONAL VALUES(7,800,'TODOS',38);


-- Agregamos informacion de los paquetes disponibles en MOCEL -- 
INSERT INTO PAQUETE VALUES(1,'AHORRADOR');
INSERT INTO PAQUETE VALUES(2,'ESPECTACULAR');


-- Agregamos los servicios que va a incluir cada paquete --

-- Paquete que va a ofrecer el Plan MOCEL 2000 (AHORRADOR)
INSERT INTO INCLUYE_SERVICIO VALUES(1,0,1000);
INSERT INTO INCLUYE_SERVICIO VALUES(1,1,1000);
INSERT INTO INCLUYE_SERVICIO VALUES(1,7,200);
INSERT INTO INCLUYE_SERVICIO VALUES(1,8,1);

-- Paquete que va a ofrecer el PLan MIXTO PLUS (ESPECTACULAR)
INSERT INTO INCLUYE_SERVICIO VALUES(2,0,39000);
INSERT INTO INCLUYE_SERVICIO VALUES(2,1,2600);
INSERT INTO INCLUYE_SERVICIO VALUES(2,2,5000);
INSERT INTO INCLUYE_SERVICIO VALUES(2,3,1);
INSERT INTO INCLUYE_SERVICIO VALUES(2,5,5000);
INSERT INTO INCLUYE_SERVICIO VALUES(2,7,200);
INSERT INTO INCLUYE_SERVICIO VALUES(2,8,1);
INSERT INTO INCLUYE_SERVICIO VALUES(2,9,1);
INSERT INTO INCLUYE_SERVICIO VALUES(2,10,1);


-- Agregamos informacion sobre los paquetes que ofrece cada plan --
INSERT INTO OFRECE_PAQUETE VALUES(1,1);
INSERT INTO OFRECE_PAQUETE VALUES(2,2);



-- REQUERIMIENTOS DESCRITOS EN LA TAREA 2 --


-- ******* i) 15 clientes registrados y afiliados a planes de servicio *********

-- Agregamos clientes a la base de datos MOCEL

-- 9 clientes sin tarjeta

CREATE SEQUENCE CODIGO_CLIENTE  START WITH 20000000 INCREMENT BY 1;
CREATE SEQUENCE CODIGO_PRODUCTO START WITH 1        INCREMENT BY 1;
INSERT INTO CLIENTE  VALUES(nextval('CODIGO_CLIENTE'),'Maria Ines','La concepcion',NULL,NULL,NULL,NULL);
INSERT INTO PRODUCTO VALUES(nextval('CODIGO_PRODUCTO'),currval('CODIGO_CLIENTE'),1,'BB310',CURRENT_DATE,'EFECTIVO',0); 
INSERT INTO CLIENTE  VALUES(nextval('CODIGO_CLIENTE'),'Petra Ines','La concepcion',NULL,NULL,NULL,NULL);
INSERT INTO PRODUCTO VALUES(nextval('CODIGO_PRODUCTO'),currval('CODIGO_CLIENTE'),1,'Zenzei',CURRENT_DATE,'EFECTIVO',0); 
INSERT INTO CLIENTE  VALUES(nextval('CODIGO_CLIENTE'),'Oscar Valero','Carabobo - Valencia',NULL,NULL,NULL,NULL);
INSERT INTO PRODUCTO VALUES(nextval('CODIGO_PRODUCTO'),currval('CODIGO_CLIENTE'),1,'Zenzei',CURRENT_DATE,'EFECTIVO',0); 
INSERT INTO CLIENTE  VALUES(nextval('CODIGO_CLIENTE'),'Hector Padron','Miranda - Los Teques',NULL,NULL,NULL,NULL);
INSERT INTO PRODUCTO VALUES(nextval('CODIGO_PRODUCTO'),currval('CODIGO_CLIENTE'),1,'Letus',CURRENT_DATE,'EFECTIVO',0); 
INSERT INTO CLIENTE  VALUES(nextval('CODIGO_CLIENTE'),'Kyra Goncalves','Caracas',NULL,NULL,NULL,NULL);
INSERT INTO PRODUCTO VALUES(nextval('CODIGO_PRODUCTO'),currval('CODIGO_CLIENTE'),1,'BB310',CURRENT_DATE,'EFECTIVO',0); 
INSERT INTO CLIENTE  VALUES(nextval('CODIGO_CLIENTE'),'Martina Valenzuela','Miranda - Las Minas',NULL,NULL,NULL,NULL);
INSERT INTO PRODUCTO VALUES(nextval('CODIGO_PRODUCTO'),currval('CODIGO_CLIENTE'),1,'Zenzei',CURRENT_DATE,'EFECTIVO',0); 
INSERT INTO CLIENTE  VALUES(nextval('CODIGO_CLIENTE'),'Octavio Perez','Baruta',NULL,NULL,NULL,NULL);
INSERT INTO PRODUCTO VALUES(nextval('CODIGO_PRODUCTO'),currval('CODIGO_CLIENTE'),1,'Letus',CURRENT_DATE,'EFECTIVO',0); 
INSERT INTO CLIENTE  VALUES(nextval('CODIGO_CLIENTE'),'Iris Varela','Miraflores',NULL,NULL,NULL,NULL);
INSERT INTO PRODUCTO VALUES(nextval('CODIGO_PRODUCTO'),currval('CODIGO_CLIENTE'),1,'Zenzei',CURRENT_DATE,'EFECTIVO',0); 
INSERT INTO CLIENTE  VALUES(nextval('CODIGO_CLIENTE'),'Augusto Villanueva','Holanda - La Guaira',NULL,NULL,NULL,NULL);
INSERT INTO PRODUCTO VALUES(nextval('CODIGO_PRODUCTO'),currval('CODIGO_CLIENTE'),1,'BB310',CURRENT_DATE,'EFECTIVO',0); 

-- 7 clientes con tarjeta
CREATE SEQUENCE NUMERO_TARJETA START WITH 1 INCREMENT BY 1;
-- DEBITO
INSERT INTO CLIENTE  VALUES(nextval('CODIGO_CLIENTE'),'Gisela Martino','Guacara',
nextval('NUMERO_TARJETA'),'VENEZUELA','DEBITO', to_date('08-09-2023', 'DD-MM-YYYY'));
INSERT INTO PRODUCTO VALUES(nextval('CODIGO_PRODUCTO'),currval('CODIGO_CLIENTE'),2,'Letus',CURRENT_DATE,'DEBITO',0); 
INSERT INTO CLIENTE  VALUES(nextval('CODIGO_CLIENTE'),'Esperanza Quintero','Venezuela City',
nextval('NUMERO_TARJETA'),'BANCARIBE','DEBITO', to_date('08-09-2023', 'DD-MM-YYYY'));
INSERT INTO PRODUCTO VALUES(nextval('CODIGO_PRODUCTO'),currval('CODIGO_CLIENTE'),2,'BB310',CURRENT_DATE,'DEBITO',0); 
INSERT INTO CLIENTE  VALUES(nextval('CODIGO_CLIENTE'),'Giselo Martino','Texas de Caracas',
nextval('NUMERO_TARJETA'),'MERCANTIL','DEBITO', to_date('11-05-2023', 'DD-MM-YYYY'));
INSERT INTO PRODUCTO VALUES(nextval('CODIGO_PRODUCTO'),currval('CODIGO_CLIENTE'),2,'Letus',CURRENT_DATE,'DEBITO',0); 
INSERT INTO CLIENTE  VALUES(nextval('CODIGO_CLIENTE'),'Lesther Diaz','Magallanes Plaza',
nextval('NUMERO_TARJETA'),'BANCARIBE','DEBITO', to_date('08-09-2023', 'DD-MM-YYYY'));
INSERT INTO PRODUCTO VALUES(nextval('CODIGO_PRODUCTO'),currval('CODIGO_CLIENTE'),2,'Zenzei',CURRENT_DATE,'DEBITO',0); 

-- CREDITO
INSERT INTO CLIENTE  VALUES(nextval('CODIGO_CLIENTE'),'Ovidio Lozada','Guacara',
nextval('NUMERO_TARJETA'),'BANCARIBE','CREDITO', to_date('05-07-2018', 'DD-MM-YYYY'));
INSERT INTO PRODUCTO VALUES(nextval('CODIGO_PRODUCTO'),currval('CODIGO_CLIENTE'),2,'Zenzei',CURRENT_DATE,'CREDITO',0); 
INSERT INTO CLIENTE  VALUES(nextval('CODIGO_CLIENTE'),'Gustavo Arocha','Miranda - Los Teques',
nextval('NUMERO_TARJETA'),'MERCANTIL','CREDITO', to_date('08-09-2019', 'DD-MM-YYYY'));
INSERT INTO PRODUCTO VALUES(nextval('CODIGO_PRODUCTO'),currval('CODIGO_CLIENTE'),2,'BB310',CURRENT_DATE,'CREDITO',0); 
INSERT INTO CLIENTE  VALUES(nextval('CODIGO_CLIENTE'),'Pedro Monascal','Caracas - Sartenejas',
nextval('NUMERO_TARJETA'),'VENEZUELA','CREDITO', to_date('21-06-2021', 'DD-MM-YYYY'));
INSERT INTO PRODUCTO VALUES(nextval('CODIGO_PRODUCTO'),currval('CODIGO_CLIENTE'),2,'Letus',CURRENT_DATE,'CREDITO',0); 

DROP SEQUENCE CODIGO_CLIENTE;
DROP SEQUENCE CODIGO_PRODUCTO;
DROP SEQUENCE NUMERO_TARJETA;


-- ******* ii) Exista al menos un cliente que posea dos productos, uno afiliado
-- a un plan prepago y el otro a un plan postpago *********

-- Insercion del cliente que cumplira con el requerimiento ii)
INSERT INTO CLIENTE VALUES (28500000,'Cliente Requerimiento ii)','USB - Sartenejas',
999999999999,'VENEZUELA','CREDITO', to_date('17-08-2029', 'DD-MM-YYYY'));

-- Cliente Requerimiento ii afilia el codificador p1 al Plan Prepago MOCEL 2000
INSERT INTO PRODUCTO VALUES ('p1',28500000,1,'Letus',CURRENT_DATE,'EFECTIVO',0);
-- Cliente Requerimiento ii afilia el codificador p2 al Plan Postpago MIXTO PLUS                          
INSERT INTO PRODUCTO VALUES ('p2',28500000,2,'Zenzei',CURRENT_DATE,'CREDITO',0);
                                 
   
-- ******* iii) Exista un paquete de servicios sin suscriptores *********   

-- Insercion del paquete Requerimiento iii. No tendra ningun suscriptor asociado.                                 
INSERT INTO PAQUETE VALUES(3,'Requerimiento iii)');                                
-- Insercion de algunos servicios dentro del paquete
INSERT INTO INCLUYE_SERVICIO VALUES(3,0,5); -- SERVICIO #0
INSERT INTO INCLUYE_SERVICIO VALUES(3,1,8); -- SERVICIO #1 
INSERT INTO INCLUYE_SERVICIO VALUES(3,2,7); -- SERVICIO #2 
INSERT INTO INCLUYE_SERVICIO VALUES(3,3,10);-- SERVICIO #3
INSERT INTO INCLUYE_SERVICIO VALUES(3,4,15);-- SERVICIO #4 
INSERT INTO INCLUYE_SERVICIO VALUES(3,5,8); -- SERVICIO #5 


-- ******* iv) Exista al menos un cliente afiliado solo a un plan *********                                   

-- Insercion del cliente que cumplira con el requerimiento iv)
INSERT INTO CLIENTE VALUES (28500001,'Cliente Requerimiento iv)','USB - Sartenejas',
NULL,NULL,NULL,NULL);

-- Cliente Requerimiento iv) afilia sus codificadores c1, c2, c3 y c4 a un mismo 
-- plan (PLAN TVBRONCE PREPAGO)
INSERT INTO PRODUCTO VALUES ('c1',28500001,1,'Letus',CURRENT_DATE,'EFECTIVO',0);  
INSERT INTO PRODUCTO VALUES ('c2',28500001,1,'BB310',CURRENT_DATE,'EFECTIVO',0);  
INSERT INTO PRODUCTO VALUES ('c3',28500001,1,'Zenzei',CURRENT_DATE,'EFECTIVO',0);  
INSERT INTO PRODUCTO VALUES ('c4',28500001,1,'BB310',CURRENT_DATE,'EFECTIVO',0);  


-- ******* v) Exista al menos un cliente afiliado a un plan y servicios adicionales

-- Insercion del cliente que cumplira con el requerimiento v)
INSERT INTO CLIENTE VALUES (28500002,'Cliente Requerimiento v)','USB - Sartenejas',
NULL,NULL,NULL,NULL);                 
-- Cliente Requerimiento v) afilia el codificador p3 al Plan Prepago #1 TVBRONCE                              
INSERT INTO PRODUCTO VALUES ('p3',28500002,1,'Letus',CURRENT_DATE,'EFECTIVO',0);                                 
-- Cliente Requerimiento v) contrata los servicios adicionales Pegadito con otros 30 
-- Mensajes 800 respectivamente
INSERT INTO CONTRATA_ADICIONAL VALUES('p3',6,CURRENT_DATE);
INSERT INTO CONTRATA_ADICIONAL VALUES('p3',7,CURRENT_DATE);                 

