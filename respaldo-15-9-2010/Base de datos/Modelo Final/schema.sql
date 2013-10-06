-- TABLAS DE LA BASE DE DATOS ------------------------------------------------

CREATE TABLE CLIENTE(
   CI                INTEGER      NOT NULL,
   NOMBRE            VARCHAR(50)  NOT NULL,
   DIRECCION         VARCHAR(100) NOT NULL,
   CONSTRAINT PK_CLIENTE PRIMARY KEY(CI),
   CONSTRAINT DOM_CI CHECK(CI > 0)
);

CREATE TABLE FORMA_PAGO(
   POSTIZA_PAGO      NUMERIC     NOT NULL,
   CONSTRAINT PK_FORMA_PAGO PRIMARY KEY(POSTIZA_PAGO)
);

CREATE TABLE PAQUETE(
   NOMBRE_PAQUETE    VARCHAR(50)  NOT NULL,
   DESCRIPCION       VARCHAR(100) NOT NULL,
   CONSTRAINT PK_PAQUETE PRIMARY KEY(NOMBRE_PAQUETE)
);

CREATE TABLE MODELO(
   NOMBRE_MODELO     VARCHAR(20) NOT NULL,
   CONSTRAINT PK_MODELO PRIMARY KEY(NOMBRE_MODELO)
);

CREATE TABLE TIPO_SERVICIO(
   NOMBRE_TIPO_SERVICIO VARCHAR(20) NOT NULL,
   CONSTRAINT PK_TIPO_SERVICIO PRIMARY KEY(NOMBRE_TIPO_SERVICIO)
);

CREATE TABLE PLAN(
   NOMBRE_PLAN       VARCHAR(50)  NOT NULL,
   DESCRIPCION       VARCHAR(100) NOT NULL,
   TIPO_PLAN         VARCHAR(50)  NOT NULL,
   CONSTRAINT PK_PLAN PRIMARY KEY(NOMBRE_PLAN, TIPO_PLAN),
   CONSTRAINT DOM_TIPO CHECK (TIPO_PLAN IN ('PREPAGO', 'POSTPAGO'))
);

CREATE TABLE PRODUCTO(
   ID                INTEGER     NOT NULL,
   NOMBRE_MODELO     VARCHAR(20) NOT NULL,
   CONSTRAINT PK_PRODUCTO PRIMARY KEY(ID),
   CONSTRAINT FK_PRODUCTO_MODELO FOREIGN KEY(NOMBRE_MODELO)
      REFERENCES MODELO(NOMBRE_MODELO)
);

CREATE TABLE SERVICIO(
   NOMBRE_SERVICIO      VARCHAR(50)  NOT NULL,
   DESCRIPCION          VARCHAR(100) NOT NULL,
   NOMBRE_TIPO_SERVICIO VARCHAR(20)  NOT NULL,
   CONSTRAINT PK_SERVICIO PRIMARY KEY(NOMBRE_SERVICIO),
   CONSTRAINT FK_SERVICIO_TIPO_SERVICIO FOREIGN KEY(NOMBRE_TIPO_SERVICIO)
      REFERENCES TIPO_SERVICIO(NOMBRE_TIPO_SERVICIO)
);

CREATE TABLE ADICIONAL(
   NOMBRE_SERVICIO    VARCHAR(50)   NOT NULL,
   TARIFA             NUMERIC       NOT NULL,
   CANTIDAD_ADICIONAL INTEGER       NOT NULL,
   TIPO_PLAN          VARCHAR(20)   NOT NULL, 
   CONSTRAINT PK_ADICIONAL PRIMARY KEY(NOMBRE_SERVICIO),
   CONSTRAINT FK_ADICIONAL_SERVICIO FOREIGN KEY(NOMBRE_SERVICIO)
      REFERENCES SERVICIO(NOMBRE_SERVICIO),
   CONSTRAINT DOM_TARIFA CHECK (TARIFA >= 0),
   CONSTRAINT DOM_CANTIDAD_ADICIONAL CHECK (CANTIDAD_ADICIONAL > 0),
   CONSTRAINT DOM_TIPO_PLAN CHECK (TIPO_PLAN IN 
      ('PREPAGO', 'POSTPAGO', 'AMBAS'))
);

CREATE TABLE EFECTIVO(
   NRO_PAGO          INTEGER     NOT NULL,
   POSTIZA_PAGO      INTEGER     NOT NULL,
   CONSTRAINT PK_EFECTIVO PRIMARY KEY(NRO_PAGO),
   CONSTRAINT FK_EFECTIVO_FORMA_PAGO FOREIGN KEY(POSTIZA_PAGO)
      REFERENCES FORMA_PAGO(POSTIZA_PAGO)
);

CREATE TABLE TARJETA(
   NUMERO            BIGINT      NOT NULL,
   MARCA             VARCHAR(50) NOT NULL,
   BANCO             VARCHAR(50) NOT NULL,
   COD_SEGURIDAD     INTEGER     NOT NULL,
   FECHA_VENC        DATE        NOT NULL,
   TIPO_TARJETA      VARCHAR(50) NOT NULL,
   POSTIZA_PAGO      INTEGER     NOT NULL,
   CI_TITULAR        INTEGER     NOT NULL,
   CONSTRAINT PK_TARJETA PRIMARY KEY(NUMERO),
   CONSTRAINT FK_TARJETA_FORMA_PAGO FOREIGN KEY(POSTIZA_PAGO)
      REFERENCES FORMA_PAGO(POSTIZA_PAGO),
   CONSTRAINT DOM_TIPO_TARJETA CHECK (TIPO_TARJETA IN ('CREDITO', 'DEBITO')),
   CONSTRAINT DOM_CI_TITULAR CHECK (CI_TITULAR > 0), 
   CONSTRAINT DOM_NUMERO CHECK (NUMERO > 0),
   CONSTRAINT DOM_COD_SEGURIDAD CHECK (COD_SEGURIDAD > 0)
);

CREATE TABLE TELEFONO(
   CI                INTEGER     NOT NULL,
   NUMERO            BIGINT     NOT NULL,
   CONSTRAINT PK_TELEFONO PRIMARY KEY(CI,NUMERO),
   CONSTRAINT FK_TELEFONO_CLIENTE FOREIGN KEY(CI)
      REFERENCES CLIENTE(CI),
   CONSTRAINT DOM_NUMERO CHECK (NUMERO > 0)
);

CREATE TABLE FACTURA(
   ID                INTEGER     NOT NULL,
   FECHA             DATE        NOT NULL,
   MONTO_TOTAL       NUMERIC     NOT NULL,
   CONSTRAINT PK_FACTURA PRIMARY KEY(ID, FECHA),
   CONSTRAINT FK_FACTURA_PRODUCTO FOREIGN KEY(ID)
      REFERENCES PRODUCTO(ID),
   CONSTRAINT DOM_MONTO_TOTAL CHECK (MONTO_TOTAL >= 0)
);

CREATE TABLE COMENTARIO(
   ID                INTEGER     NOT NULL,
   FECHA             DATE        NOT NULL,
   VALOR             VARCHAR(100) NOT NULL,
   CONSTRAINT PK_COMENTARIO PRIMARY KEY(ID, FECHA, VALOR),
   CONSTRAINT FK_COMENTARIO_FACTURA FOREIGN KEY(ID,FECHA)
      REFERENCES FACTURA(ID,FECHA)
);

CREATE TABLE CONTIENE(
   NOMBRE_PAQUETE     VARCHAR(50) NOT NULL,
   NOMBRE_SERVICIO    VARCHAR(50) NOT NULL,
   CANTIDAD           INTEGER     NOT NULL,
   COSTO_ADICIONAL    NUMERIC     NOT NULL,
   CONSTRAINT PK_CONTIENE PRIMARY KEY(NOMBRE_PAQUETE,NOMBRE_SERVICIO),
   CONSTRAINT FK_CONTIENE_PAQUETE FOREIGN KEY(NOMBRE_PAQUETE)
      REFERENCES PAQUETE(NOMBRE_PAQUETE),
   CONSTRAINT FK_CONTIENE_SERVICIO FOREIGN KEY(NOMBRE_SERVICIO)
      REFERENCES SERVICIO(NOMBRE_SERVICIO),
   CONSTRAINT DOM_CANTIDAD CHECK (CANTIDAD > 0),
   CONSTRAINT DOM_COSTO_ADICIONAL CHECK (COSTO_ADICIONAL >= 0)
);

CREATE TABLE CONSUME(
   ID                INTEGER     NOT NULL,
   NOMBRE_SERVICIO   VARCHAR(50) NOT NULL,
   FECHA             DATE        NOT NULL,
   CANTIDAD          INTEGER     NOT NULL,
   CONSTRAINT PK_CONSUME PRIMARY KEY (ID, NOMBRE_SERVICIO, FECHA),
   CONSTRAINT FK_CONSUME_PRODUCTO FOREIGN KEY (ID)
      REFERENCES PRODUCTO(ID),
   CONSTRAINT FK_CONSUME_SERVICIO FOREIGN KEY (NOMBRE_SERVICIO)
      REFERENCES SERVICIO(NOMBRE_SERVICIO),
   CONSTRAINT DOM_CANTIDAD CHECK (CANTIDAD > 0)
);

CREATE TABLE ES_DUENIO(
   ID                INTEGER     NOT NULL,
   CI                INTEGER     NOT NULL,
   CONSTRAINT PK_ES_DUENIO PRIMARY KEY(ID),
   CONSTRAINT FK_ES_DUENIO_CLIENTE FOREIGN KEY(CI)
      REFERENCES CLIENTE(CI),
   CONSTRAINT FK_ES_DUENIO_PRODUCTO FOREIGN KEY(ID)
      REFERENCES PRODUCTO(ID)
);

CREATE TABLE ESTA_AFILIADO(
   ID                INTEGER     NOT NULL,
   NOMBRE_PLAN       VARCHAR(50) NOT NULL,
   TIPO_PLAN         VARCHAR(50) NOT NULL,
   FECHA_INIC        DATE        NOT NULL,
   FECHA_FIN         DATE,
   CONSTRAINT PK_ESTA_AFILIADO PRIMARY KEY(ID, NOMBRE_PLAN, TIPO_PLAN,
      FECHA_INIC),
   CONSTRAINT FK_ESTA_AFILIADO_PRODUCTO FOREIGN KEY(ID)
      REFERENCES PRODUCTO(ID),
   CONSTRAINT FK_ESTA_AFILIADO_PLAN FOREIGN KEY(NOMBRE_PLAN, TIPO_PLAN)
      REFERENCES PLAN(NOMBRE_PLAN, TIPO_PLAN),
   CONSTRAINT VERIFICAR_FECHAS_ESTA_AFILIADO CHECK (FECHA_FIN IS NULL OR
      FECHA_INIC < FECHA_FIN)
);

CREATE TABLE PAGA(
   ID                INTEGER     NOT NULL,
   FECHA             DATE        NOT NULL,
   POSTIZA_PAGO      INTEGER     NOT NULL,
   CONSTRAINT PK_PAGA PRIMARY KEY(ID, FECHA),
   CONSTRAINT FK_PAGA_FACTURA FOREIGN KEY(ID,FECHA)
      REFERENCES FACTURA(ID,FECHA),
   CONSTRAINT FK_PAGA_FORMA_PAGO FOREIGN KEY(POSTIZA_PAGO)
      REFERENCES FORMA_PAGO(POSTIZA_PAGO)
);
   
CREATE TABLE POSEE(
   ID                INTEGER     NOT NULL,
   NOMBRE_SERVICIO   VARCHAR(50) NOT NULL,
   FECHA_INIC        DATE        NOT NULL,
   CONSTRAINT PK_POSEE PRIMARY KEY(ID,NOMBRE_SERVICIO,FECHA_INIC),
   CONSTRAINT FK_POSEE_PRODUCTO FOREIGN KEY(ID)
      REFERENCES PRODUCTO(ID),
   CONSTRAINT FK_POSEE_ADICIONAL FOREIGN KEY(NOMBRE_SERVICIO)
      REFERENCES ADICIONAL(NOMBRE_SERVICIO)
);

CREATE TABLE TIENE(
   NOMBRE_PLAN       VARCHAR(50) NOT NULL,
   TIPO_PLAN         VARCHAR(59) NOT NULL,
   NOMBRE_PAQUETE    VARCHAR(50) NOT NULL,
   COSTO             NUMERIC     NOT NULL,
   FECHA_INIC        DATE        NOT NULL,
   FECHA_FIN         DATE,
   CONSTRAINT PK_TIENE PRIMARY KEY(NOMBRE_PLAN, TIPO_PLAN, FECHA_INIC),
   CONSTRAINT FK_TIENE_PLAN FOREIGN KEY(NOMBRE_PLAN, TIPO_PLAN)
      REFERENCES PLAN(NOMBRE_PLAN, TIPO_PLAN),
   CONSTRAINT FK_TIENE_PAQUETE FOREIGN KEY(NOMBRE_PAQUETE)
      REFERENCES PAQUETE(NOMBRE_PAQUETE),
   CONSTRAINT VERIFICAR_FECHAS_TIENE CHECK (FECHA_FIN IS NULL OR
      FECHA_INIC < FECHA_FIN),
   CONSTRAINT DOM_COSTO CHECK (COSTO >= 0)
);


-- TRIGGERS DE LAS RESTRICCIONES EXPLICITAS ----------------------------------


-- Verifica que la postiza de la Tarjeta a agregar no existe en Efectivo.

CREATE OR REPLACE FUNCTION func_PostTarjeta() RETURNS TRIGGER
AS $func_PostTarjeta$
DECLARE

   C1 CURSOR FOR SELECT POSTIZA_PAGO FROM EFECTIVO;
   POSTIZA NUMERIC;
   
BEGIN

   OPEN C1;
   LOOP

      FETCH C1 INTO POSTIZA;
      EXIT WHEN NOT FOUND;
      
      IF NEW.POSTIZA_PAGO = POSTIZA THEN
      
         RAISE EXCEPTION 'Esta postiza ya existe en Efectivo'; 
         CLOSE C1;
         RETURN NULL;
      
      END IF;
      
   END LOOP;
   
   CLOSE C1;
   RETURN NEW;
   
END;
$func_PostTarjeta$ LANGUAGE plpgsql;

CREATE TRIGGER trig_PostTarjeta
BEFORE INSERT ON TARJETA
FOR EACH ROW
EXECUTE PROCEDURE func_PostTarjeta();

-- Verifica que la postiza del Efectivo a agregar no existe en Tarjeta.

CREATE OR REPLACE FUNCTION func_PostEfectivo() RETURNS TRIGGER
AS $func_PostEfectivo$
DECLARE

   C1 CURSOR FOR SELECT POSTIZA_PAGO FROM TARJETA;
   POSTIZA NUMERIC;
   
BEGIN

   OPEN C1;
   LOOP
   
      FETCH C1 INTO POSTIZA;
      EXIT WHEN NOT FOUND;
      
      IF NEW.POSTIZA_PAGO = POSTIZA THEN
      
         RAISE EXCEPTION 'Esta postiza ya existe en Tarjeta';
         CLOSE C1;
         RETURN NULL;
      
      END IF;
      
   END LOOP;
   
   CLOSE C1;
   RETURN NEW;
   
END;
$func_PostEfectivo$ LANGUAGE plpgsql;

CREATE TRIGGER trig_PostEfectivo
BEFORE INSERT ON EFECTIVO
FOR EACH ROW
EXECUTE PROCEDURE func_PostEfectivo();


-- Un producto no debe consumir mas de la cantidad dada para un servicio 
-- adicional

CREATE OR REPLACE FUNCTION func_ConsumePosee() RETURNS TRIGGER
AS $func_ConsumePosee$
DECLARE

   C1 CURSOR FOR SELECT CANTIDAD_ADICIONAL FROM ADICIONAL A
      WHERE NEW.NOMBRE_SERVICIO = A.NOMBRE_SERVICIO;

   CANTIDAD INTEGER;
      
BEGIN

   OPEN C1;
   FETCH C1 INTO CANTIDAD;
   
   IF FOUND AND NEW.CANTIDAD > CANTIDAD THEN
      
      RAISE EXCEPTION 'El producto no puede consumir mas de lo que ofrece 
                       el servicio adicional.';
      CLOSE C1;
   
      RETURN NULL;


   END IF;
   
   
   CLOSE C1;
   RETURN NEW;


END;
$func_ConsumePosee$ LANGUAGE plpgsql;


CREATE TRIGGER trig_ConsumePosee
BEFORE INSERT OR UPDATE ON CONSUME
FOR EACH ROW
EXECUTE PROCEDURE func_ConsumePosee();


-- Verifica que un producto no esta afiliado a dos planes con fecha fin null

CREATE OR REPLACE FUNCTION func_FechaFinAfiliado() RETURNS TRIGGER
AS $func_FechaFinAfiliado$
DECLARE

   C1 CURSOR FOR SELECT FECHA_FIN FROM ESTA_AFILIADO WHERE ID = NEW.ID
                 AND FECHA_FIN IS NULL;
   FECHA DATE;

BEGIN

   IF NEW.FECHA_FIN IS NULL THEN
   
      OPEN C1;      
      FETCH C1 INTO FECHA;
      
      IF NOT FOUND THEN
      
         CLOSE C1;
         RETURN NEW;
         
      END IF;
      
      RAISE EXCEPTION 'El producto esta afiliado a un plan actualmente.';
      CLOSE C1;
      RETURN NULL;
      
   ELSE
       
      RETURN NEW;
      
   END IF;
   
END;
$func_FechaFinAfiliado$ LANGUAGE plpgsql;

CREATE TRIGGER trig_FechaFinAfiliado
BEFORE INSERT ON ESTA_AFILIADO
FOR EACH ROW
EXECUTE PROCEDURE func_FechaFinAfiliado();


-- Verifica que no se solapan las fechas de los planes de un producto.

CREATE OR REPLACE FUNCTION func_VerificarFechas() RETURNS TRIGGER
AS $func_VerificarFechas$
DECLARE

   C1 CURSOR FOR SELECT FECHA_INIC, FECHA_FIN FROM ESTA_AFILIADO
                 WHERE ID = NEW.ID;
   F_INIC DATE;
   F_FIN DATE;                 

BEGIN

   OPEN C1;
   LOOP
   
      FETCH C1 INTO F_INIC, F_FIN;
      EXIT WHEN NOT FOUND;
       
      IF (F_FIN IS NULL OR 
         NOT (F_INIC < NEW.FECHA_INIC AND F_FIN <= NEW.FECHA_INIC)) AND
         (NEW.FECHA_FIN IS NULL OR 
         NOT (NEW.FECHA_INIC < F_INIC AND NEW.FECHA_FIN <= F_INIC)) THEN
       
         RAISE EXCEPTION 'No se pueden solapar las fechas de los planes
                           asociados a un producto.';
         CLOSE C1;
         RETURN NULL;
          
      END IF;
     
   END LOOP;
   
   CLOSE C1;   
   RETURN NEW;  
  
END;
$func_VerificarFechas$ LANGUAGE plpgsql;

CREATE TRIGGER trig_VerificarFechas
BEFORE INSERT ON ESTA_AFILIADO
FOR EACH ROW
EXECUTE PROCEDURE func_VerificarFechas();


--Verifica que un producto no tiene el mismo servicio adicional dos veces 
--durante un mismo periodo.

CREATE OR REPLACE FUNCTION func_VerificarServicio() RETURNS TRIGGER
AS $func_VerificarServicio$
DECLARE

   C1 CURSOR FOR SELECT FECHA_INIC FROM POSEE P, ADICIONAL A 
                 WHERE ID = NEW.ID AND A.NOMBRE_SERVICIO = NEW.NOMBRE_SERVICIO
                 AND P.NOMBRE_SERVICIO = A.NOMBRE_SERVICIO;
   F_INIC DATE;
                 
BEGIN

   OPEN C1;
   
   LOOP
   
      FETCH C1 INTO F_INIC;
      EXIT WHEN NOT FOUND;
      
      IF (F_INIC = NEW.FECHA_INIC) THEN
        
         RAISE EXCEPTION 'Un producto no puede tener contratado el mismo
                          servicio adicional durante el mismo periodo.';
         CLOSE C1;                 
         RETURN NULL;
         
      END IF;
      
   END LOOP;
   
   CLOSE C1;
   RETURN NEW;
   
END;
$func_VerificarServicio$ LANGUAGE plpgsql;

CREATE TRIGGER trig_VerificarServicio
BEFORE INSERT ON POSEE
FOR EACH ROW
EXECUTE PROCEDURE func_VerificarServicio();   

-- Calcula la factura de un producto al agregar un servicio adicional a este.

CREATE OR REPLACE FUNCTION func_MontoFacturaPosee() RETURNS TRIGGER
AS $func_MontoFacturaPosee$
DECLARE

   C1 CURSOR FOR SELECT MONTO_TOTAL FROM FACTURA 
      WHERE date_part('month', FECHA) = date_part('month', NEW.FECHA_INIC)
      AND date_part('year', FECHA) = date_part('year', NEW.FECHA_INIC)
      AND ID = NEW.ID;
   C2 CURSOR FOR SELECT TARIFA FROM ADICIONAL A
      WHERE A.NOMBRE_SERVICIO = NEW.NOMBRE_SERVICIO;
   MONTO NUMERIC;
   TARIFA NUMERIC;

BEGIN

   OPEN C2;      
   FETCH C2 INTO TARIFA;
   
   OPEN C1;   
   FETCH C1 INTO MONTO;   
        
   IF NOT FOUND THEN
   
      INSERT INTO FACTURA 
      VALUES (NEW.ID, date_trunc('month', NEW.FECHA_INIC),TARIFA);                
                                          
   ELSE
     
      UPDATE FACTURA SET MONTO_TOTAL = MONTO_TOTAL + TARIFA 
      WHERE ID = NEW.ID AND FECHA = date_trunc('month', NEW.FECHA_INIC);
            
   END IF;
   
   CLOSE C1;
   CLOSE C2;
   
   RETURN NEW;

END;
$func_MontoFacturaPosee$ LANGUAGE plpgsql;

CREATE TRIGGER trig_MontoFacturaPosee
BEFORE INSERT ON POSEE
FOR EACH ROW
EXECUTE PROCEDURE func_MontoFacturaPosee();


-- Calcula la factura de un producto con un plan postpago al consumir 
-- mas de la cantidad otorgada de un servicio.

CREATE OR REPLACE FUNCTION func_MontoFacturaConsume() RETURNS TRIGGER
AS $func_MontoFacturaConsume$
DECLARE

   C1 CURSOR FOR SELECT MONTO_TOTAL FROM FACTURA 
      WHERE date_part('month', FECHA) = date_part('month', NEW.FECHA)
      AND date_part('year', FECHA) = date_part('year', NEW.FECHA)
      AND ID = NEW.ID;
      
   C2 CURSOR FOR SELECT C.CANTIDAD, C.COSTO_ADICIONAL, E.TIPO_PLAN 
      FROM ESTA_AFILIADO E, TIENE T, CONTIENE C
      WHERE NEW.ID = E.ID AND T.NOMBRE_PLAN = E.NOMBRE_PLAN AND
      T.TIPO_PLAN = E.TIPO_PLAN AND C.NOMBRE_PAQUETE = T.NOMBRE_PAQUETE AND
      C.NOMBRE_SERVICIO = NEW.NOMBRE_SERVICIO AND E.FECHA_INIC <= NEW.FECHA AND
      (E.FECHA_FIN IS NULL OR NEW.FECHA < E.FECHA_FIN) AND 
      T.FECHA_INIC <= NEW.FECHA AND (T.FECHA_FIN IS NULL OR 
      NEW.FECHA < T.FECHA_FIN);
      
   COSTO NUMERIC;   
   CANTIDAD NUMERIC;   
   TIPO_P VARCHAR(50);
   MONTO NUMERIC;
   NUEVO_MONTO NUMERIC;

BEGIN

   OPEN C2;
   FETCH C2 INTO CANTIDAD, COSTO, TIPO_P;
   
   IF FOUND AND (TIPO_P = 'POSTPAGO') AND (CANTIDAD - NEW.CANTIDAD < 0) THEN
              
      NUEVO_MONTO = (NEW.CANTIDAD - CANTIDAD)*COSTO;
      
      OPEN C1;
      FETCH C1 INTO MONTO; 
      
      -- Si no existe una factura para el producto, se crea una con la
      -- fecha asociada al consumo
      
      IF NOT FOUND THEN 
            
         INSERT INTO FACTURA
         VALUES (NEW.ID, date_trunc('month', NEW.FECHA), NUEVO_MONTO);
                    
      ELSE
         
         -- Si el consumo se esta agregando, entonces se suma el nuevo
         -- monto a la factura.
         
         IF (TG_OP = 'INSERT') THEN
         
            UPDATE FACTURA SET MONTO_TOTAL = MONTO_TOTAL + NUEVO_MONTO
            WHERE ID = NEW.ID AND FECHA = date_trunc('month', NEW.FECHA);
                  
         -- Si el consumo se esta modificando, entonces se calcula el monto
         -- nuevo de la factura en base el que ya estaba         
         
         ELSE
         
            UPDATE FACTURA SET MONTO_TOTAL = MONTO_TOTAL +
            (NEW.CANTIDAD - OLD.CANTIDAD)*COSTO
            WHERE ID = NEW.ID AND FECHA = date_trunc('month', NEW.FECHA);
               
         END IF;
         
      END IF;  
   
      CLOSE C1;
   
   END IF;   
   
   CLOSE C2;
   RETURN NEW;

END;
$func_MontoFacturaConsume$ LANGUAGE plpgsql; 

CREATE TRIGGER trig_MontoFacturaConsume
BEFORE INSERT OR UPDATE ON CONSUME
FOR EACH ROW
EXECUTE PROCEDURE func_MontoFacturaConsume();

-- Verifica que un producto con un plan prepago se no se sobrepase de la
-- cantidad dada de un servicio

CREATE OR REPLACE FUNCTION func_PrepagoConsume() RETURNS TRIGGER
AS $func_PrepagoConsume$
DECLARE

   C1 CURSOR FOR SELECT C.CANTIDAD, E.TIPO_PLAN 
      FROM ESTA_AFILIADO E, TIENE T, CONTIENE C
      WHERE NEW.ID = E.ID AND T.NOMBRE_PLAN = E.NOMBRE_PLAN AND
      T.TIPO_PLAN = E.TIPO_PLAN AND C.NOMBRE_PAQUETE = T.NOMBRE_PAQUETE AND
      C.NOMBRE_SERVICIO = NEW.NOMBRE_SERVICIO AND E.FECHA_INIC <= NEW.FECHA AND
      (E.FECHA_FIN IS NULL OR NEW.FECHA < E.FECHA_FIN) AND 
      T.FECHA_INIC <= NEW.FECHA AND (T.FECHA_FIN IS NULL OR 
      NEW.FECHA < T.FECHA_FIN);
      
   C2 CURSOR FOR SELECT CANTIDAD FROM POSEE P WHERE NEW.ID = P.ID AND
      P.NOMBRE_SERVICIO = NEW.NOMBRE_SERVICIO AND 
      P.FECHA_INIC = NEW.FECHA;
      
   CANTIDAD NUMERIC;
   TIPO_P VARCHAR(50);
   CANTIDAD_POSEE NUMERIC;
   
BEGIN

   OPEN C1;
   FETCH C1 INTO CANTIDAD, TIPO_P;
   
   IF FOUND AND (TIPO_P = 'PREPAGO') AND (CANTIDAD - NEW.CANTIDAD < 0) THEN
   
      OPEN C2;
      FETCH C2 INTO CANTIDAD_POSEE;
      
      IF (NOT FOUND OR (CANTIDAD + CANTIDAD_POSEE - NEW.CANTIDAD < 0)) THEN  
   
         RAISE EXCEPTION 'Un producto con plan prepago no puede consumir
                          mas de lo que su plan permite.';
      
         CLOSE C1;
         CLOSE C2;
         RETURN NULL;
      
      END IF;

      CLOSE C2;
      
   END IF;
   
   CLOSE C1;
   RETURN NEW;

END;
$func_PrepagoConsume$ LANGUAGE plpgsql;

CREATE TRIGGER trig_PrepagoConsume
BEFORE INSERT OR UPDATE ON CONSUME
FOR EACH ROW
EXECUTE PROCEDURE func_PrepagoConsume();


-- Un producto no puede consumir servicios si no esta afiliado a un plan.

CREATE OR REPLACE FUNCTION func_VerificarConsume() RETURNS TRIGGER
AS $func_VerificarConsume$
DECLARE

   C1 CURSOR FOR SELECT ID, E.FECHA_INIC, E.FECHA_FIN FROM ESTA_AFILIADO E
                 WHERE NEW.ID = E.ID;
   IDENTIFICADOR  NUMERIC;
   F_INIC DATE;
   F_FIN DATE;      
      
BEGIN

   OPEN C1;
   
   LOOP
   
      FETCH C1 INTO IDENTIFICADOR, F_INIC, F_FIN;
      EXIT WHEN NOT FOUND;
   
   
      IF ((F_FIN IS NULL AND NEW.FECHA >= date_trunc('month', F_INIC)) OR
         (F_FIN IS NOT NULL AND NEW.FECHA >= date_trunc('month', F_INIC) 
         AND F_FIN > NEW.FECHA)) THEN

         CLOSE C1;
         RETURN NEW;
      
      END IF;
      
   END LOOP;
   
   RAISE EXCEPTION 'Un producto no puede consumir servicios si
                        no esta afiliado a un plan.';      
   CLOSE C1;
   RETURN NULL;
   
END;
$func_VerificarConsume$ LANGUAGE plpgsql;

CREATE TRIGGER trig_VerificarConsume
BEFORE INSERT ON CONSUME
FOR EACH ROW
EXECUTE PROCEDURE func_VerificarConsume();

-- Un producto no puede consumir un servicio si el plan no lo tiene o si
-- no lo posee como servicio adicional

CREATE OR REPLACE FUNCTION func_VerificarDisponibilidadServ() RETURNS TRIGGER
AS $func_VerificarDisponibilidadServ$
DECLARE

   C1 CURSOR FOR SELECT NOMBRE_SERVICIO 
      FROM ESTA_AFILIADO E, TIENE T, CONTIENE C
      WHERE NEW.ID = E.ID AND T.NOMBRE_PLAN = E.NOMBRE_PLAN AND
      T.TIPO_PLAN = E.TIPO_PLAN AND T.NOMBRE_PAQUETE = C.NOMBRE_PAQUETE AND
      NEW.FECHA >= E.FECHA_INIC AND (E.FECHA_FIN IS NULL OR 
      NEW.FECHA < E.FECHA_FIN);
      
   C2 CURSOR FOR SELECT NOMBRE_SERVICIO
      FROM POSEE P WHERE NEW.ID = P.ID AND NEW.FECHA = P.FECHA_INIC AND
      P.NOMBRE_SERVICIO = NEW.NOMBRE_SERVICIO;
   
   nomServicio VARCHAR(50);

BEGIN

   OPEN C1;
   FETCH C1 INTO nomServicio;
   
   IF NOT FOUND THEN
   
      OPEN C2;
      FETCH C2 INTO nomServicio;
      
      IF NOT FOUND THEN
      
         RAISE EXCEPTION 'El producto no esta afiliado a este servicio.';
         CLOSE C2;
         CLOSE C1;
         RETURN NULL;
         
      END IF;

      CLOSE C2;   
      
   END IF;
    
   CLOSE C1;
   RETURN NEW;
 

END;
$func_VerificarDisponibilidadServ$ LANGUAGE plpgsql;

CREATE TRIGGER trig_VerificarDisponibilidadServ
BEFORE INSERT ON CONSUME
FOR EACH ROW
EXECUTE PROCEDURE func_VerificarDisponibilidadServ();

-- Verifica que las fechas de los paquetes (con el mismo nombre)
-- que tiene un plan no se solapan
 
CREATE OR REPLACE FUNCTION func_VerificarFechasTiene() RETURNS TRIGGER
AS $func_VerificarFechasTiene$
DECLARE

   C1 CURSOR FOR SELECT FECHA_INIC, FECHA_FIN FROM TIENE
      WHERE NOMBRE_PLAN = NEW.NOMBRE_PLAN AND TIPO_PLAN = NEW.TIPO_PLAN AND
      NOMBRE_PAQUETE = NEW.NOMBRE_PAQUETE;
   F_INIC DATE;
   F_FIN DATE;                 

BEGIN

   OPEN C1;
   LOOP
   
      FETCH C1 INTO F_INIC, F_FIN;
      EXIT WHEN NOT FOUND;
       
      IF (F_FIN IS NULL OR 
         NOT (F_INIC < NEW.FECHA_INIC AND F_FIN <= NEW.FECHA_INIC)) AND
         (NEW.FECHA_FIN IS NULL OR 
         NOT (NEW.FECHA_INIC < F_INIC AND NEW.FECHA_FIN <= F_INIC)) THEN
       
         RAISE EXCEPTION 'No se pueden solapar las fechas de los paquetes
                           asociados a un plan.';
         CLOSE C1;
         RETURN NULL;
          
      END IF;
     
   END LOOP;
   
   CLOSE C1;   
   RETURN NEW;  
  
END;
$func_VerificarFechasTiene$ LANGUAGE plpgsql;

CREATE TRIGGER trig_VerificarFechasTiene
BEFORE INSERT ON TIENE
FOR EACH ROW
EXECUTE PROCEDURE func_VerificarFechasTiene();

-- Verifica el tipo de plan asociado a un servicio adicional de un producto
-- concuerde con el plan al cual esta afiliado.

CREATE OR REPLACE FUNCTION func_VerificarTipoPlanAdicional() RETURNS TRIGGER
AS $func_VerificarTipoPlanAdicional$

DECLARE

   C1 CURSOR FOR SELECT TIPO_PLAN FROM ESTA_AFILIADO E
                 WHERE E.ID = NEW.ID AND E.FECHA_INIC <= NEW.FECHA_INIC
                 AND (E.FECHA_FIN IS NULL OR NEW.FECHA_INIC < E.FECHA_FIN);
   C2 CURSOR FOR SELECT TIPO_PLAN FROM ADICIONAL A
                 WHERE A.NOMBRE_SERVICIO = NEW.NOMBRE_SERVICIO;
   TIPO  VARCHAR(20);
   TIPOSERV VARCHAR(20);

BEGIN

   OPEN C2;
   FETCH C2 INTO TIPOSERV;

   OPEN C1;
   FETCH C1 INTO TIPO;

   IF (FOUND AND ((TIPOSERV != 'AMBAS') AND (TIPOSERV != TIPO))) THEN

      RAISE EXCEPTION 'El producto no puede poseer este servicio porque
                       difieren en el tipo de plan.';
      CLOSE C1;
      CLOSE C2;
      RETURN NULL;

   END IF;

   CLOSE C1;
   CLOSE C2;
   
   RETURN NEW;

END;
$func_VerificarTipoPlanAdicional$ LANGUAGE plpgsql;

CREATE TRIGGER trig_VerificarTipoPlanAdicional 
BEFORE INSERT ON POSEE
FOR EACH ROW
EXECUTE PROCEDURE func_VerificarTipoPlanAdicional();
