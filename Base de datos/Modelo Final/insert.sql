


INSERT INTO CLIENTE (CI,NOMBRE,DIRECCION) VALUES
   (1, 'cliente1'  , 'direccion1' ),
   (2, 'cliente2'  , 'direccion2' ),
   (3, 'cliente3'  , 'direccion3' ),
   (4, 'cliente4'  , 'direccion4' ),
   (5, 'cliente5'  , 'direccion5' ),
   (6, 'cliente6'  , 'direccion6' ),
   (7, 'cliente7'  , 'direccion7' ),
   (8, 'cliente8'  , 'direccion8' ),
   (9, 'cliente9'  , 'direccion9' ),
   (10, 'cliente10', 'direccion10'),
   (11, 'cliente11', 'direccion11'),
   (12, 'cliente12', 'direccion12'),
   (13, 'cliente13', 'direccion13'),
   (14, 'cliente14', 'direccion14'),
   (15, 'cliente15', 'direccion15'),
   (16, 'cliente16', 'direccion16'),
   (17, 'cliente17', 'direccion17'),
   (18, 'cliente18', 'direccion18'),
   (19, 'cliente19', 'direccion19'),
   (20, 'cliente20', 'direccion20');

INSERT INTO MODELO (NOMBRE_MODELO) VALUES
   ('modelo1'),
   ('modelo2');   
   
INSERT INTO PRODUCTO (ID,NOMBRE_MODELO) VALUES
   (000, 'modelo1'),
   (001, 'modelo1'),
   (002, 'modelo1'),
   (003, 'modelo1'),
   (004, 'modelo1'),
   (005, 'modelo1'),
   (006, 'modelo1'),
   (007, 'modelo1'),
   (008, 'modelo1'),
   (009, 'modelo1'),
   (010, 'modelo1'),
   (011, 'modelo2'),
   (012, 'modelo2'),
   (013, 'modelo2'),
   (014, 'modelo2'),
   (015, 'modelo2'),
   (016, 'modelo2'),
   (017, 'modelo2'),
   (018, 'modelo2'),
   (019, 'modelo2'),
   (020, 'modelo2');
   
INSERT INTO ES_DUENIO (ID, CI) VALUES
   (001, 1 ),
   (002, 2 ),
   (003, 3 ),
   (004, 4 ),
   (005, 5 ),
   (006, 6 ),
   (007, 7 ),
   (008, 8 ),
   (009, 9 ),
   (010, 10),
   (011, 11),
   (012, 12),
   (013, 13),
   (014, 14),
   (015, 15),
   (016, 16),
   (017, 17),
   (018, 18),
   (019, 19),
   (020, 20),
   (000, 20);

   
INSERT INTO PLAN (NOMBRE_PLAN, DESCRIPCION,TIPO_PLAN) VALUES 
   ('Plan Mocel 2000', 'Plan 1', 'PREPAGO' ),
   ('Plan Mixto Plus', 'Plan 2', 'POSTPAGO');

INSERT INTO TIPO_SERVICIO (NOMBRE_TIPO_SERVICIO) VALUES
   ('TipoServicio1'),
   ('TipoServicio2'),
   ('TipoServicio3');
   
   
INSERT INTO SERVICIO (NOMBRE_SERVICIO, DESCRIPCION, NOMBRE_TIPO_SERVICIO) VALUES 
   ('Segundos a moviles MOCEL'               , 'Servicio 1' , 'TipoServicio1'),
   ('Segundos a otros operadores moviles'    , 'Servicio 2' , 'TipoServicio1'),
   ('Mensajes de texto'                      , 'Servicio 3' , 'TipoServicio1'),
   ('Buzon de mensajes'                      , 'Servicio 4' , 'TipoServicio2'),
   ('Segundos fijos MOCEL y otros operadores', 'Servicio 5' , 'TipoServicio2'),
   ('Pegadito con otros 1500'                , 'Servicio 6' , 'TipoServicio2'),
   ('Pegadito con otros 30'                  , 'Servicio 7' , 'TipoServicio3'),
   ('Repique personalizado'                  , 'Servicio 8' , 'TipoServicio3'),
   ('Llamadas internacionales'               , 'Servicio 9' , 'TipoServicio3'),
   ('Mensajes 800'                           , 'Servicio 10', 'TipoServicio3');

   
INSERT INTO ADICIONAL (NOMBRE_SERVICIO, TARIFA, CANTIDAD_ADICIONAL,TIPO_PLAN) VALUES
   ('Pegadito con otros 1500', 16, 1500, 'PREPAGO' ),
   ('Mensajes 800'           , 38, 800 , 'POSTPAGO'),
   ('Pegadito con otros 30'  , 19, 30  , 'AMBAS'   );
                                                    
                                                    
INSERT INTO PAQUETE (NOMBRE_PAQUETE, DESCRIPCION) VALUES 
   ('Paquete1', 'descripcion1'),
   ('Paquete2', 'descripcion2'),
   ('Paquete3', 'descripcion3'),
   ('Paquete4', 'descripcion4');

   
INSERT INTO TIENE (NOMBRE_PLAN, TIPO_PLAN, NOMBRE_PAQUETE, COSTO, FECHA_INIC, FECHA_FIN) VALUES
   ('Plan Mocel 2000', 'PREPAGO' , 'Paquete1', 49.00 , DATE '1999-11-1', DATE '2500-12-1'),
   ('Plan Mixto Plus', 'POSTPAGO', 'Paquete2', 211.00, DATE '1999-11-1', DATE '2500-12-1');

INSERT INTO CONTIENE (NOMBRE_PAQUETE, NOMBRE_SERVICIO, CANTIDAD, COSTO_ADICIONAL) VALUES 
   ('Paquete1', 'Segundos a moviles MOCEL'               , 1000 , 0.1   ),
   ('Paquete1', 'Segundos a otros operadores moviles'    , 1000 , 0.4   ),
   ('Paquete1', 'Mensajes de texto'                      , 200  , 0.5   ),
   ('Paquete1', 'Buzon de mensajes'                      , 1    , 0     ),
   ('Paquete2', 'Segundos a moviles MOCEL'               , 39000, 0.0115),
   ('Paquete2', 'Segundos a otros operadores moviles'    , 2600 , 0.0125),
   ('Paquete2', 'Segundos fijos MOCEL y otros operadores', 5000 , 0.0115),
   ('Paquete2', 'Mensajes de texto'                      , 200  , 0.5   ),
   ('Paquete2', 'Buzon de mensajes'                      , 1    , 0     ),
   ('Paquete3', 'Mensajes de texto'                      , 400  , 0.5   ),
   ('Paquete3', 'Buzon de mensajes'                      , 1    , 0     );



INSERT INTO ESTA_AFILIADO (ID, NOMBRE_PLAN, TIPO_PLAN, FECHA_INIC, FECHA_FIN) VALUES 
   (000,'Plan Mocel 2000', 'PREPAGO' , DATE '2001-8-1', DATE '2010-8-1' ),
   (001,'Plan Mixto Plus', 'POSTPAGO', DATE '2000-8-1', NULL            ),
   (002,'Plan Mocel 2000', 'PREPAGO' , DATE '2010-8-1', DATE '2014-1-1' ),
   (003,'Plan Mixto Plus', 'POSTPAGO', DATE '2000-8-1', DATE '2009-4-1' ),
   (004,'Plan Mocel 2000', 'PREPAGO' , DATE '2005-8-1', DATE '2010-12-1'),
   (005,'Plan Mixto Plus', 'POSTPAGO', DATE '2006-8-1', NULL            ),
   (006,'Plan Mocel 2000', 'PREPAGO' , DATE '2007-8-1', DATE '2010-10-1'),
   (007,'Plan Mixto Plus', 'POSTPAGO', DATE '2008-8-1', NULL            ),
   (008,'Plan Mocel 2000', 'PREPAGO' , DATE '2010-2-1', DATE '2010-7-1' ),
   (009,'Plan Mixto Plus', 'POSTPAGO', DATE '2013-8-1', DATE '2019-2-1' ),    
   (010,'Plan Mocel 2000', 'PREPAGO' , DATE '2010-1-1', DATE '2010-3-1' ),
   (011,'Plan Mixto Plus', 'POSTPAGO', DATE '2000-8-1', NULL            ),                       
   (012,'Plan Mocel 2000', 'PREPAGO' , DATE '2002-8-1', DATE '2016-8-1' ),
   (013,'Plan Mixto Plus', 'POSTPAGO', DATE '2002-8-1', DATE '2004-2-1' ), 
   (014,'Plan Mocel 2000', 'PREPAGO' , DATE '2009-8-1', DATE '2013-8-1' ),   
   (020,'Plan Mixto Plus', 'POSTPAGO', DATE '2002-8-1', DATE '2012-2-1' );  

INSERT INTO POSEE (ID, NOMBRE_SERVICIO, FECHA_INIC) VALUES
   (001, 'Mensajes 800', DATE '2002-5-1');

INSERT INTO CONSUME (ID, NOMBRE_SERVICIO, FECHA, CANTIDAD) VALUES 
   (001, 'Segundos a moviles MOCEL'           , '2013-5-1', 500 ),
   (011, 'Segundos a otros operadores moviles', '2013-5-1', 1000),                            
   (002, 'Segundos a moviles MOCEL'           , '2013-5-1', 100 );
                   

INSERT INTO TELEFONO (CI, NUMERO) VALUES
   (1, 04244658129),
   (2, 02124657871),
   (3, 04124831597),
   (4, 04269136584);