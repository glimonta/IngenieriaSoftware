INSERT INTO CLIENTE VALUES (1,'cliente1','direccion1');
INSERT INTO CLIENTE VALUES (2,'cliente2','direccion2');
INSERT INTO CLIENTE VALUES (3,'cliente3','direccion3');
INSERT INTO CLIENTE VALUES (4,'cliente4','direccion4');
INSERT INTO CLIENTE VALUES (5,'cliente5','direccion5');
INSERT INTO CLIENTE VALUES (6,'cliente6','direccion6');
INSERT INTO CLIENTE VALUES (7,'cliente7','direccion7');
INSERT INTO CLIENTE VALUES (8,'cliente8','direccion8');
INSERT INTO CLIENTE VALUES (9,'cliente9','direccion9');
INSERT INTO CLIENTE VALUES (10,'cliente10','direccion10');
INSERT INTO CLIENTE VALUES (11,'cliente11','direccion11');
INSERT INTO CLIENTE VALUES (12,'cliente12','direccion12');
INSERT INTO CLIENTE VALUES (13,'cliente13','direccion13');
INSERT INTO CLIENTE VALUES (14,'cliente14','direccion14');
INSERT INTO CLIENTE VALUES (15,'cliente15','direccion15');
INSERT INTO CLIENTE VALUES (16,'cliente16','direccion16');
INSERT INTO CLIENTE VALUES (17,'cliente17','direccion17');
INSERT INTO CLIENTE VALUES (18,'cliente18','direccion18');
INSERT INTO CLIENTE VALUES (19,'cliente19','direccion19');
INSERT INTO CLIENTE VALUES (20,'cliente20','direccion20');

INSERT INTO PRODUCTO VALUES (000);
INSERT INTO PRODUCTO VALUES (001);
INSERT INTO PRODUCTO VALUES (002);
INSERT INTO PRODUCTO VALUES (003);
INSERT INTO PRODUCTO VALUES (004);
INSERT INTO PRODUCTO VALUES (005);
INSERT INTO PRODUCTO VALUES (006);
INSERT INTO PRODUCTO VALUES (007);
INSERT INTO PRODUCTO VALUES (008);
INSERT INTO PRODUCTO VALUES (009);
INSERT INTO PRODUCTO VALUES (010);
INSERT INTO PRODUCTO VALUES (011);
INSERT INTO PRODUCTO VALUES (012);
INSERT INTO PRODUCTO VALUES (013);
INSERT INTO PRODUCTO VALUES (014);
INSERT INTO PRODUCTO VALUES (015);
INSERT INTO PRODUCTO VALUES (016);
INSERT INTO PRODUCTO VALUES (017);
INSERT INTO PRODUCTO VALUES (018);
INSERT INTO PRODUCTO VALUES (019);
INSERT INTO PRODUCTO VALUES (020);



INSERT INTO ES_DUENIO VALUES (001,1);
INSERT INTO ES_DUENIO VALUES (002,2);
INSERT INTO ES_DUENIO VALUES (003,3);
INSERT INTO ES_DUENIO VALUES (004,4);
INSERT INTO ES_DUENIO VALUES (005,5);
INSERT INTO ES_DUENIO VALUES (006,6);
INSERT INTO ES_DUENIO VALUES (007,7);
INSERT INTO ES_DUENIO VALUES (008,8);
INSERT INTO ES_DUENIO VALUES (009,9);
INSERT INTO ES_DUENIO VALUES (010,10);
INSERT INTO ES_DUENIO VALUES (011,11);
INSERT INTO ES_DUENIO VALUES (012,12);
INSERT INTO ES_DUENIO VALUES (013,13);
INSERT INTO ES_DUENIO VALUES (014,14);
INSERT INTO ES_DUENIO VALUES (015,15);
INSERT INTO ES_DUENIO VALUES (016,16);
INSERT INTO ES_DUENIO VALUES (017,17);
INSERT INTO ES_DUENIO VALUES (018,18);
INSERT INTO ES_DUENIO VALUES (019,19);
INSERT INTO ES_DUENIO VALUES (020,20);
INSERT INTO ES_DUENIO VALUES (000,20);

                                  
INSERT INTO PLAN VALUES ('Plan Mocel 2000', 'Plan 1', 'PREPAGO');
INSERT INTO PLAN VALUES ('Plan Mixto Plus', 'Plan 2', 'POSTPAGO');

        
                  
                                  



INSERT INTO SERVICIO VALUES ('Segundos a moviles MOCEL', 'Servicio 1');
INSERT INTO SERVICIO VALUES ('Segundos a otros operadores moviles', 
                             'Servicio2');
INSERT INTO SERVICIO VALUES ('Mensajes de texto', 'Servicio 3');
INSERT INTO SERVICIO VALUES ('Buzon de mensajes', 'Servicio 4');
INSERT INTO SERVICIO VALUES ('Segundos fijos MOCEL y otros operadores',
                             'Servicio 5');



INSERT INTO SERVICIO VALUES ('Repique personalizado', 'Servicio 8');
INSERT INTO SERVICIO VALUES ('Llamadas internacionales', 'Servicio 9');

INSERT INTO SERVICIO VALUES ('Pegadito con otros 1500', 'Servicio 6');
INSERT INTO SERVICIO VALUES ('Pegadito con otros 30', 'Servicio 7');

INSERT INTO SERVICIO VALUES ('Mensajes 800', 'Servicio 10');
------
INSERT INTO ADICIONAL VALUES ('Pegadito con otros 1500', 16,1500);
INSERT INTO ADICIONAL VALUES ('Pegadito con otros 30', 19,30);
INSERT INTO ADICIONAL VALUES ('Mensajes 800', 38, 800);
-------                             

                             
INSERT INTO PAQUETE VALUES ('Paquete1', 'descripcion1');
INSERT INTO PAQUETE VALUES ('Paquete2', 'descripcion2');
INSERT INTO PAQUETE VALUES ('Paquete3', 'descripcion3');
INSERT INTO PAQUETE VALUES ('Paquete4', 'descripcion4');

INSERT INTO TIENE VALUES ('Plan Mocel 2000', 'PREPAGO', 'Paquete1', 49.00,
                           DATE '1999-11-1', DATE '2500-12-1');
INSERT INTO TIENE VALUES ('Plan Mixto Plus', 'POSTPAGO', 'Paquete2', 211.00,
                          DATE '1999-11-1', DATE '2500-12-1');

-- COSTO ADICIONALES INVENTADOSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSS
INSERT INTO CONTIENE VALUES ('Paquete1', 'Segundos a moviles MOCEL', 1000, 0.1);
INSERT INTO CONTIENE VALUES ('Paquete1', 'Segundos a otros operadores moviles',
                            1000, 0.4);
INSERT INTO CONTIENE VALUES ('Paquete1', 'Mensajes de texto', 200, 0.5);
INSERT INTO CONTIENE VALUES ('Paquete1', 'Buzon de mensajes', 1, 0);


INSERT INTO CONTIENE VALUES ('Paquete2', 'Segundos a moviles MOCEL', 39000,
                             0.0115);
INSERT INTO CONTIENE VALUES ('Paquete2', 'Segundos a otros operadores moviles',
                             2600, 0.0125);
INSERT INTO CONTIENE VALUES ('Paquete2',
                             'Segundos fijos MOCEL y otros operadores', 
                             5000, 0.0115);
INSERT INTO CONTIENE VALUES ('Paquete2', 'Mensajes de texto', 200, 0.5);
INSERT INTO CONTIENE VALUES ('Paquete2', 'Buzon de mensajes', 1, 0);

INSERT INTO CONTIENE VALUES ('Paquete3', 'Mensajes de texto', 400, 0.5);
INSERT INTO CONTIENE VALUES ('Paquete3', 'Buzon de mensajes', 1, 0);


INSERT INTO ESTA_AFILIADO VALUES (000,'Plan Mocel 2000','PREPAGO', 
                                  DATE '2001-8-1', DATE '2010-8-1');
INSERT INTO ESTA_AFILIADO VALUES (001,'Plan Mixto Plus','POSTPAGO',
                                  DATE '2000-8-1', NULL);
INSERT INTO ESTA_AFILIADO VALUES (002,'Plan Mocel 2000','PREPAGO', 
                                  DATE '2010-8-1', DATE '2014-1-1');
INSERT INTO ESTA_AFILIADO VALUES (003,'Plan Mixto Plus','POSTPAGO',
                                  DATE '2000-8-1', DATE '2009-4-1');
INSERT INTO ESTA_AFILIADO VALUES (004,'Plan Mocel 2000','PREPAGO', 
                                  DATE '2005-8-1', DATE '2010-12-1');
INSERT INTO ESTA_AFILIADO VALUES (005,'Plan Mixto Plus','POSTPAGO',
                                  DATE '2006-8-1', NULL);
INSERT INTO ESTA_AFILIADO VALUES (006,'Plan Mocel 2000','PREPAGO', 
                                  DATE '2007-8-1', DATE '2010-10-1');
INSERT INTO ESTA_AFILIADO VALUES (007,'Plan Mixto Plus','POSTPAGO',
                                  DATE '2008-8-1', NULL);         
INSERT INTO ESTA_AFILIADO VALUES (008,'Plan Mocel 2000','PREPAGO', 
                                  DATE '2010-2-1', DATE '2010-7-1');
INSERT INTO ESTA_AFILIADO VALUES (009,'Plan Mixto Plus','POSTPAGO',
                                  DATE '2013-8-1', DATE '2019-2-1');    
INSERT INTO ESTA_AFILIADO VALUES (010,'Plan Mocel 2000','PREPAGO', 
                                  DATE '2010-1-1', DATE '2010-3-1');
INSERT INTO ESTA_AFILIADO VALUES (011,'Plan Mixto Plus','POSTPAGO',
                                  DATE '2000-8-1', NULL);                       
INSERT INTO ESTA_AFILIADO VALUES (012,'Plan Mocel 2000','PREPAGO', 
                                  DATE '2002-8-1', DATE '2016-8-1');
INSERT INTO ESTA_AFILIADO VALUES (013,'Plan Mixto Plus','POSTPAGO',
                                  DATE '2002-8-1', DATE '2004-2-1'); 
INSERT INTO ESTA_AFILIADO VALUES (014,'Plan Mocel 2000','PREPAGO', 
                                  DATE '2009-8-1', DATE '2013-8-1');   
INSERT INTO ESTA_AFILIADO VALUES (020,'Plan Mixto Plus','POSTPAGO',
                                  DATE '2002-8-1', DATE '2012-2-1');  

INSERT INTO POSEE VALUES (001, 'Pegadito con otros 1500', DATE '2002-5-1');

INSERT INTO CONSUME VALUES (001, 'Segundos a moviles MOCEL', '2013-5-1', 500);
INSERT INTO CONSUME VALUES (011, 'Segundos a otros operadores moviles', 
                            '2013-5-1', 1000);
                            
INSERT INTO CONSUME VALUES (002, 'Segundos a moviles MOCEL', '2013-5-1', 100);
                            
