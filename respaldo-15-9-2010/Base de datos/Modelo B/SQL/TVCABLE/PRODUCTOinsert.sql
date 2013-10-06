-- Insertando los PRODUCTOS adquiridos por los Clientes

CREATE SEQUENCE CODIGO_CLIENTE_PR START WITH 20000000 INCREMENT BY 1;
CREATE SEQUENCE CODIGO_PRODUCTO START WITH 1 INCREMENT BY 1;

-- 4 clientes se afilian al plan prepago #1
INSERT INTO PRODUCTO VALUES (nextval('CODIGO_PRODUCTO'),nextval('CODIGO_CLIENTE_PR'),
                              1,'Modelo 1',CURRENT_DATE,'EFECTIVO',20); 
INSERT INTO PRODUCTO VALUES (nextval('CODIGO_PRODUCTO'),nextval('CODIGO_CLIENTE_PR'),
                              1,'Modelo 2',CURRENT_DATE,'EFECTIVO',20); 
INSERT INTO PRODUCTO VALUES (nextval('CODIGO_PRODUCTO'),nextval('CODIGO_CLIENTE_PR'),
                              1,'Modelo 1',CURRENT_DATE,'EFECTIVO',20);
                              
INSERT INTO PRODUCTO VALUES (nextval('CODIGO_PRODUCTO'),nextval('CODIGO_CLIENTE_PR'),
                              1,'Modelo 3',CURRENT_DATE,'EFECTIVO',20);
                                                            
-- 5 Clientes se afilian al plan prepago #3                              
INSERT INTO PRODUCTO VALUES (nextval('CODIGO_PRODUCTO'),nextval('CODIGO_CLIENTE_PR'),
                              3,'Modelo 3',CURRENT_DATE,'EFECTIVO',20);
INSERT INTO PRODUCTO VALUES (nextval('CODIGO_PRODUCTO'),nextval('CODIGO_CLIENTE_PR'),
                              3,'Modelo 1',CURRENT_DATE,'EFECTIVO',20);
INSERT INTO PRODUCTO VALUES (nextval('CODIGO_PRODUCTO'),nextval('CODIGO_CLIENTE_PR'),
                              3,'Modelo 2',CURRENT_DATE,'EFECTIVO',20);
INSERT INTO PRODUCTO VALUES (nextval('CODIGO_PRODUCTO'),nextval('CODIGO_CLIENTE_PR'),
                              3,'Modelo 1',CURRENT_DATE,'EFECTIVO',20);
INSERT INTO PRODUCTO VALUES (nextval('CODIGO_PRODUCTO'),nextval('CODIGO_CLIENTE_PR'),
                              3,'Modelo 3',CURRENT_DATE,'EFECTIVO',20);
                                                            
                                                            
-- 4 clientes se afilian al plan postpago # 2

INSERT INTO PRODUCTO VALUES (nextval('CODIGO_PRODUCTO'),nextval('CODIGO_CLIENTE_PR'),
                              2,'Modelo 1',CURRENT_DATE,'DEBITO',20);
INSERT INTO PRODUCTO VALUES (nextval('CODIGO_PRODUCTO'),nextval('CODIGO_CLIENTE_PR'),
                              2,'Modelo 3',CURRENT_DATE,'DEBITO',20);                              
INSERT INTO PRODUCTO VALUES (nextval('CODIGO_PRODUCTO'),nextval('CODIGO_CLIENTE_PR'),
                              2,'Modelo 2',CURRENT_DATE,'DEBITO',20);      
INSERT INTO PRODUCTO VALUES (nextval('CODIGO_PRODUCTO'),nextval('CODIGO_CLIENTE_PR'),
                              2,'Modelo 2',CURRENT_DATE,'DEBITO',20);                        

-- 3 Clientes se afilian al plan postpago # 3

INSERT INTO PRODUCTO VALUES (nextval('CODIGO_PRODUCTO'),nextval('CODIGO_CLIENTE_PR'),
                              4,'Modelo 3',CURRENT_DATE,'CREDITO',20);                              
INSERT INTO PRODUCTO VALUES (nextval('CODIGO_PRODUCTO'),nextval('CODIGO_CLIENTE_PR'),
                              4,'Modelo 1',CURRENT_DATE,'CREDITO',20);                              
INSERT INTO PRODUCTO VALUES (nextval('CODIGO_PRODUCTO'),nextval('CODIGO_CLIENTE_PR'),
                              4,'Modelo 2',CURRENT_DATE,'CREDITO',20);                              
                              
DROP SEQUENCE CODIGO_CLIENTE_PR;
DROP SEQUENCE CODIGO_PRODUCTO;                             
                              
                              
                              
                              
                                                            
