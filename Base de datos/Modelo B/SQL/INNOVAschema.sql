CREATE TABLE PRODUCTO(
    COD_PRODUCTO  CHAR(10)    NOT NULL,  --Clave primaria
    COD_CLIENTE   CHAR(10)    NOT NULL,  --Codigo del cliente dueno del producto
    COD_PLAN      CHAR(10)    NOT NULL,  --Codigo del plan asociado al producto
    NOMBRE_MODELO CHAR(20)    NOT NULL,  --Nombre del modelo asociado al producto
    FECHA_AFILIA  DATE        NOT NULL,  --Fecha en la que se afilio al plan
    MODO_PAGO     VARCHAR(20) NOT NULL,  --Indica el modo de pago
    ALQUILER      REAL        NOT NULL   --Util para prepago. Saldo del producto
);

CREATE TABLE CLIENTE(
    COD_CLIENTE CHAR(10)     NOT NULL,   --Clave primaria
    NOMBRE      VARCHAR(80)  NOT NULL,   --Nombre del cliente
    DIRECCION   VARCHAR(300) NOT NULL,   --Direccion del cliente
    NUMERO_T    CHAR(12)             ,   --Numero de tarjeta del cliente
    BANCO_T     VARCHAR(20)          ,   --Banco emisor de la tarjeta
    TIPO_T      VARCHAR(7)           ,   --Indica el tipo de la tarjeta(Deb/Cred)
    FECHA_V_T   DATE                     --Fecha de vencimiento de la tarjeta
);

CREATE TABLE TELEFONO(
    COD_CLIENTE CHAR(10)    NOT NULL,   --Codigo del cliente
    NUMERO      VARCHAR(10) NOT NULL    --Numero de telefono del cliente
);

CREATE TABLE MODELO(
    NOMBRE_MODELO VARCHAR(20)    NOT NULL   --Nombre del Modelo
);

CREATE TABLE PLAN(
    COD_PLAN    CHAR(10)     NOT NULL,  --Codigo del plan
    NOMBRE      VARCHAR(20)  NOT NULL,  --Nombre del plan
    RENTA       REAL         NOT NULL,  --Renta del plan
    TIPO_P      VARCHAR(8)   NOT NULL,  --Tipo del plan (prepago/postpago)
    DESCRIPCION VARCHAR(300) NOT NULL   --Descripcion breve del plan
);

CREATE TABLE FACTURA(
    COD_PRODUCTO CHAR(10)     NOT NULL,  --Codigo del producto
    PERIODO_I    DATE         NOT NULL,  --Inicio del periodo de facturacion
    MONTO        REAL         NOT NULL,  --Monto total a pagar
    PERIODO_F    DATE         NOT NULL,  --Fin del periodo de facturacion
    OBSERVACION  VARCHAR(300) NOT NULL   --Observaciones por la empresa
);

CREATE TABLE CONSUMO(
    COD_PRODUCTO    CHAR(10)  NOT NULL, --Codigo del producto
    FECHA           DATE      NOT NULL, --Fecha en la que se realizo el consumo
    COD_SERVICIO    CHAR(10)  NOT NULL, --Codigo del servicio consumido
    CANTIDAD        INTEGER   NOT NULL --Cantidad que se consume del servicio
);

CREATE TABLE SERVICIO(
    COD_SERVICIO    CHAR(10)     NOT NULL, --Codigo del servicio
    NOMBRE_TIPO_S   CHAR(20)     NOT NULL, --Es el Tipo de servicio
    NOMBRE          VARCHAR(50)  NOT NULL, --Nombre del servicio
    TARIFA_NORM     REAL         NOT NULL, --Tarifa normal asociada al servicio
    TARIFA_EXCE     REAL         NOT NULL, --Tarifa excedida del servicio
    DESCRIPCION     VARCHAR(300) NOT NULL  --Descripcion breve del servicio
);

CREATE TABLE TIPO_SERVICIO(
    NOMBRE_TIPO_S   CHAR(20)  NOT NULL  --Nombre del Tipo de servicio
);

CREATE TABLE SERV_ADICIONAL(
    COD_SERVICIO    CHAR(10)   NOT NULL, -- Codigo del servicio
    CANTIDAD        INTEGER    NOT NULL, -- Cantidad que se ofrece por adicional
    TIPO_P          VARCHAR(8) NOT NULL, -- Tipo de plan para el cual aplica
    MONTO           REAL       NOT NULL  -- Costo de contratar el serv. Adicional
);

CREATE TABLE CONTRATA_ADICIONAL(
    COD_PRODUCTO CHAR(10)  NOT NULL,    -- Codigo del producto
    COD_SERV_AD  CHAR(10)  NOT NULL,    -- Codigo del servicio adicional
    FECHA        DATE      NOT NULL     -- Fecha en la que se contrata el serv.
);

CREATE TABLE PAQUETE(
    COD_PAQUETE CHAR(10)    NOT NULL,   -- Codigo del paquete
    NOMBRE      VARCHAR(20) NOT NULL    -- Nombre del paquete
);

CREATE TABLE OFRECE_PAQUETE(
    COD_PLAN    CHAR(10)  NOT NULL,     -- Codigo del plan
    COD_PAQUETE CHAR(10)  NOT NULL      -- Codigo del paquete
);

CREATE TABLE INCLUYE_SERVICIO(
    COD_PAQUETE  CHAR(10)  NOT NULL,     -- Codigo del paquete
    COD_SERVICIO CHAR(10)  NOT NULL,     -- Codigo del servicio
    CANTIDAD     INTEGER   NOT NULL      -- Cantidad que el paquete ofrece
);

-- Se agregan los CONSTRAINT de claves primarias, foraneas y dominio.

ALTER TABLE CLIENTE
    ADD CONSTRAINT PK_CLIENTE PRIMARY KEY (COD_CLIENTE),
    ADD CONSTRAINT DOM_CHECK_TIPO_T CHECK (TIPO_T IN ('DEBITO', 'CREDITO'));
    
ALTER TABLE MODELO
    ADD CONSTRAINT PK_MODELO PRIMARY KEY(NOMBRE_MODELO);

ALTER TABLE TELEFONO
    ADD CONSTRAINT PK_TELEFONO PRIMARY KEY(COD_CLIENTE, NUMERO),
    ADD CONSTRAINT FK_TELEFONO_CLIENTE FOREIGN KEY(COD_CLIENTE) REFERENCES CLIENTE(COD_CLIENTE);

ALTER TABLE PLAN
    ADD CONSTRAINT PK_PLAN PRIMARY KEY (COD_PLAN),
    ADD CONSTRAINT DOM_CHECK_PLAN_TIPO_P CHECK (TIPO_P IN ('PREPAGO', 'POSTPAGO')),
    ADD CONSTRAINT DOM_CHECK_RENTA_PLAN CHECK (RENTA>0);

ALTER TABLE PRODUCTO
    ADD CONSTRAINT PK_PRODUCTO PRIMARY KEY(COD_PRODUCTO),
    ADD CONSTRAINT FK_PRODUCTO_PLAN FOREIGN KEY(COD_PLAN) REFERENCES PLAN(COD_PLAN),
    ADD CONSTRAINT FK_PRODUCTO_CLIENTE FOREIGN KEY(COD_CLIENTE) REFERENCES CLIENTE(COD_CLIENTE),
    ADD CONSTRAINT DOM_CHECK_PRODUCTO_MODO_PAGO CHECK (MODO_PAGO IN ('EFECTIVO', 'DEBITO', 'CREDITO')),
    ADD CONSTRAINT FK_PRODUCTO_MODELO FOREIGN KEY(NOMBRE_MODELO) REFERENCES MODELO(NOMBRE_MODELO),
    ADD CONSTRAINT DOM_CHECK_ALQUILER CHECK (ALQUILER >= 0);

ALTER TABLE FACTURA
    ADD CONSTRAINT PK_FACTURA PRIMARY KEY(COD_PRODUCTO, PERIODO_I),
    ADD CONSTRAINT FK_FACTURA_PRODUCTO FOREIGN KEY(COD_PRODUCTO) REFERENCES PRODUCTO(COD_PRODUCTO),
    ADD CONSTRAINT DOM_CHECK_MONTO_FACTURA CHECK (MONTO>=0);

ALTER TABLE TIPO_SERVICIO
    ADD CONSTRAINT PK_TIPO_SERVICIO PRIMARY KEY(NOMBRE_TIPO_S);

ALTER TABLE SERVICIO
    ADD CONSTRAINT PK_SERVICIO PRIMARY KEY(COD_SERVICIO),
    ADD CONSTRAINT FK_SERVICIO_TIPO_SERVICIO FOREIGN KEY (NOMBRE_TIPO_S) REFERENCES TIPO_SERVICIO(NOMBRE_TIPO_S),
    ADD CONSTRAINT DOM_CHECK_TARIFA_N CHECK (TARIFA_NORM >= 0),
    ADD CONSTRAINT DOM_CHECK_TARIFA_E CHECK (TARIFA_EXCE >= 0);

ALTER TABLE SERV_ADICIONAL
    ADD CONSTRAINT PK_SERV_ADICIONAL PRIMARY KEY(COD_SERVICIO),
    ADD CONSTRAINT FK_SERV_ADI_SERVICIO FOREIGN KEY(COD_SERVICIO) REFERENCES SERVICIO(COD_SERVICIO),
    ADD CONSTRAINT DOM_CHECK_TIPO_P_SERV_AD CHECK (TIPO_P IN ('PREPAGO', 'POSTPAGO', 'TODOS')),
    ADD CONSTRAINT DOM_CHECK_MONTO_SERV_AD CHECK (MONTO > 0),
    ADD CONSTRAINT DOM_CHECK_CANTIDAD_SERV_AD CHECK (CANTIDAD > 0);

ALTER TABLE CONTRATA_ADICIONAL  
    ADD CONSTRAINT PK_CONTRATA_ADICIONAL PRIMARY KEY(COD_PRODUCTO, COD_SERV_AD, FECHA),
    ADD CONSTRAINT FK_CONTRATA_ADI_PRODUCTO FOREIGN KEY(COD_PRODUCTO) REFERENCES PRODUCTO(COD_PRODUCTO),
    ADD CONSTRAINT FK_CONTRATA_ADI_SERV_AD FOREIGN KEY(COD_SERV_AD) REFERENCES SERV_ADICIONAL(COD_SERVICIO);

ALTER TABLE PAQUETE
    ADD CONSTRAINT PK_PAQUETE PRIMARY KEY(COD_PAQUETE);

ALTER TABLE OFRECE_PAQUETE
    ADD CONSTRAINT PK_OFRECE_PAQUETE PRIMARY KEY(COD_PLAN,COD_PAQUETE),
    ADD CONSTRAINT FK_OFRECE_PAQUETE_PLAN FOREIGN KEY(COD_PLAN) REFERENCES PLAN(COD_PLAN),
    ADD CONSTRAINT FK_OFRECE_PAQUETE_PAQ FOREIGN KEY(COD_PAQUETE) REFERENCES PAQUETE(COD_PAQUETE);

ALTER TABLE INCLUYE_SERVICIO
    ADD CONSTRAINT PK_INCLUYE_SERVICIO PRIMARY KEY(COD_PAQUETE,COD_SERVICIO),
    ADD CONSTRAINT FK_INCLUYE_SERVICIO_PAQ FOREIGN KEY(COD_PAQUETE) REFERENCES PAQUETE(COD_PAQUETE),
    ADD CONSTRAINT FK_INCLUYE_SERVICIO_SERV FOREIGN KEY(COD_SERVICIO) REFERENCES SERVICIO(COD_SERVICIO),
    ADD CONSTRAINT DOM_CHECK_CANTIDAD_INC_SERV CHECK (CANTIDAD > 0);

ALTER TABLE CONSUMO
    ADD CONSTRAINT PK_CONSUMO PRIMARY KEY(COD_PRODUCTO, FECHA),
    ADD CONSTRAINT FK_CONSUMO_PRODUCTO FOREIGN KEY(COD_PRODUCTO) REFERENCES PRODUCTO(COD_PRODUCTO),
    ADD CONSTRAINT FK_CONSUMO_SERVICIO FOREIGN KEY(COD_SERVICIO) REFERENCES SERVICIO(COD_SERVICIO),
    ADD CONSTRAINT DOM_CHECK_CANTIDAD_CONSUMO CHECK (CANTIDAD > 0);

-- TRIGGERS

-- TRIGGER 1 --

-- Si un cliente no tiene tarjeta asociada, todos los atributos relacionados
-- a la tarjeta de credito deben ser null. Si si tiene una tarjeta asociada
-- todos los atributos deben ser distintos de null.



CREATE OR REPLACE FUNCTION ver_cliente_tarjeta() RETURNS TRIGGER AS $ver_cliente_tarjeta$
    BEGIN
        IF (((NEW.NUMERO_T IS NULL)AND((NEW.BANCO_T IS NOT NULL) OR (NEW.FECHA_V_T IS NOT NULL) OR (NEW.TIPO_T IS NOT NULL)))OR
            ((NEW.NUMERO_T IS NOT NULL)AND((NEW.BANCO_T IS NULL) OR (NEW.FECHA_V_T IS NULL) OR (NEW.TIPO_T IS NULL)))) THEN
            RAISE EXCEPTION 'El cliente debe tener todos o ninguno de los atributos de la tarjeta de credito.';
        END IF;
        RETURN NEW;
    END;
$ver_cliente_tarjeta$ LANGUAGE plpgsql;

CREATE TRIGGER ver_cliente_tarjeta BEFORE INSERT OR UPDATE ON CLIENTE
    FOR EACH ROW EXECUTE PROCEDURE ver_cliente_tarjeta();

-- FIN DEL TRIGGER 1 --



-- TRIGGER 2 --

-- Si un producto esta afiliado a un plan postpago, el cliente debe tener
-- una tarjeta asociada.

CREATE OR REPLACE FUNCTION ver_modopago_producto() RETURNS TRIGGER AS $ver_modopago_producto$
    DECLARE
        tipoplan VARCHAR(8);
        tipotar  VARCHAR(7);
        tieneT   CHAR(12);
    BEGIN
       SELECT TIPO_P INTO tipoplan FROM PLAN WHERE (COD_PLAN=NEW.COD_PLAN);
    
       IF (tipoplan = 'POSTPAGO') THEN 
           IF ((NEW.MODO_PAGO = 'EFECTIVO')) THEN
               RAISE EXCEPTION 'Un plan postpago no puede estar asociado a un modo de pago "EFECTIVO" o "DEBITO"';
           END IF;
    
           SELECT NUMERO_T, TIPO_T INTO tieneT, tipotar FROM CLIENTE WHERE (COD_CLIENTE=NEW.COD_CLIENTE);
        
            --Caso en el que el modo de pago es tarjeta de debito o de credito
            --Se debe chequear que el cliente en efecto posea una tarjeta      
            IF ((tieneT IS NULL) OR (tipotar != NEW.MODO_PAGO)) THEN
                RAISE EXCEPTION 'El cliente no posee una tarjeta de %', NEW.MODO_PAGO;
            END IF;
            
        ELSE -- Si el plan es prepago
        
           IF ((NEW.MODO_PAGO != 'EFECTIVO')) THEN
               RAISE EXCEPTION 'Un plan prepago solo puede afiliarse a un modo de pago "EFECTIVO"';
           END IF;
           
        END IF;
        
        RETURN NEW;
    END;
$ver_modopago_producto$ LANGUAGE plpgsql;

CREATE TRIGGER ver_modopago_producto BEFORE INSERT OR UPDATE ON PRODUCTO
    FOR EACH ROW EXECUTE PROCEDURE ver_modopago_producto();

-- FIN DEL TRIGGER 2 --



-- TRIGGER 3 --

-- Para todo afilia, se genera una factura para dicho cliente cuyo periodo
-- inicial sera la misma fecha de la afiliacion. Su periodo final sera el mes
-- siguiente al periodo inicial. Asimismo, se le coloca en la factura el monto
-- de la renta mas el monto de alquiler de su producto.

-- Funcion que determina el ultimo dia del mes.

CREATE OR REPLACE FUNCTION last_day(date)
RETURNS date AS
$$
  SELECT (date_trunc('MONTH', $1) + INTERVAL '1 MONTH - 1 day')::date;
$$ LANGUAGE 'sql' IMMUTABLE STRICT;

CREATE OR REPLACE FUNCTION ver_factura_afiliacion() RETURNS TRIGGER AS $ver_factura_afiliacion$
    DECLARE
        tieneFactura INTEGER;
        rentaPlan    REAL;
        ultimoDia    DATE;
    BEGIN
    
        SELECT COUNT(*) INTO tieneFactura FROM FACTURA 
        WHERE (COD_PRODUCTO = NEW.COD_PRODUCTO) AND   
        (EXTRACT(month FROM PERIODO_I) = EXTRACT(month FROM NEW.FECHA_AFILIA)) AND
        (EXTRACT(year FROM PERIODO_I) = EXTRACT(year FROM NEW.FECHA_AFILIA));
        
        SELECT RENTA INTO rentaPlan FROM PLAN WHERE (COD_PLAN = NEW.COD_PLAN);
        
        IF (tieneFactura = 0) THEN
            SELECT last_day(NEW.FECHA_AFILIA) INTO ultimoDia;
            INSERT INTO FACTURA VALUES(NEW.COD_PRODUCTO, NEW.FECHA_AFILIA, NEW.ALQUILER+rentaPlan, ultimoDia, 'No details yet');
        END IF;
        
    
        RETURN NEW;
    END;
$ver_factura_afiliacion$ LANGUAGE plpgsql;

CREATE TRIGGER ver_factura_afiliacion AFTER INSERT OR UPDATE ON PRODUCTO
    FOR EACH ROW EXECUTE PROCEDURE ver_factura_afiliacion();
    
-- FIN DEL TRIGGER 3 --


-- TRIGGER 4 --

-- Para toda contratacion de un servicio adicional, el tipo de plan del
-- producto y el tipo de plan del servicio adicional deben coincidir.

CREATE OR REPLACE FUNCTION ver_tipo_plan_contrata_adicional() RETURNS TRIGGER AS $ver_tipo_plan_contrata_adicional$
    DECLARE
        tipoPlanServAd VARCHAR(8);
        tipoPlanProd   VARCHAR(8);
    BEGIN
    
        SELECT PL.TIPO_P INTO tipoPlanProd FROM PRODUCTO PR, PLAN PL 
        WHERE (PR.COD_PRODUCTO = NEW.COD_PRODUCTO) AND (PL.COD_PLAN = PR.COD_PLAN);
        SELECT TIPO_P INTO tipoPlanServAd FROM SERV_ADICIONAL WHERE (COD_SERVICIO = NEW.COD_SERV_AD);
        
        IF (tipoPlanServAd != 'TODOS')AND(tipoPlanServAd != tipoPlanProd) THEN
            RAISE EXCEPTION 'El servicio adicional contratado no aplica para el tipo de plan del producto.';
        END IF;
    
        RETURN NEW;
    END;
$ver_tipo_plan_contrata_adicional$ LANGUAGE plpgsql;

CREATE TRIGGER ver_tipo_plan_contrata_adicional BEFORE INSERT ON CONTRATA_ADICIONAL
    FOR EACH ROW EXECUTE PROCEDURE ver_tipo_plan_contrata_adicional();
    
-- FIN DEL TRIGGER 4 --


-- TRIGGER 5 --

-- Cuando se realiza un consumo, si la cantidad consumida de un servicio
-- excede a la cantidad ofrecida por el plan, este debe sumarse a la factura.

CREATE OR REPLACE FUNCTION ver_consumo_factura() RETURNS TRIGGER AS $ver_consumo_factura$
    DECLARE
        totConsumida INTEGER;
        totOfrecida  INTEGER;
        totContratada INTEGER;
        fechaAfiliacion DATE;
        tarifaServicio  REAL;
        tipoPlan      VARCHAR(8);
    BEGIN
    
        SELECT SUM(I.CANTIDAD) INTO totOfrecida FROM PRODUCTO PR, PLAN PL, OFRECE_PAQUETE OP, INCLUYE_SERVICIO I
        WHERE (PR.COD_PRODUCTO = NEW.COD_PRODUCTO) AND (PR.COD_PLAN = PL.COD_PLAN) AND (OP.COD_PLAN = PL.COD_PLAN) AND
        (OP.COD_PAQUETE = I.COD_PAQUETE) AND (I.COD_SERVICIO = NEW.COD_SERVICIO);
        
        SELECT FECHA_AFILIA INTO fechaAfiliacion FROM PRODUCTO WHERE (COD_PRODUCTO = NEW.COD_PRODUCTO);
        
        SELECT SUM(CANTIDAD) INTO totConsumida FROM CONSUMO
        WHERE (COD_PRODUCTO = NEW.COD_PRODUCTO) AND (COD_SERVICIO = NEW.COD_SERVICIO) 
        AND (fechaAfiliacion <= FECHA) AND (FECHA <= last_day(fechaAfiliacion));
        
        SELECT SUM(SA.CANTIDAD) INTO totContratada FROM CONTRATA_ADICIONAL CA, SERV_ADICIONAL SA
        WHERE (CA.COD_SERV_AD = NEW.COD_SERVICIO) AND (CA.COD_PRODUCTO = NEW.COD_PRODUCTO) AND (CA.COD_SERV_AD = SA.COD_SERVICIO)
        AND (fechaAfiliacion <= CA.FECHA) AND (CA.FECHA <= last_day(fechaAfiliacion));
        
        IF (totOfrecida is NULL) THEN
            totOfrecida = 0;
        END IF;
        
        IF (totConsumida is NULL) THEN
            totConsumida = 0;
        END IF;

        IF (totContratada is NULL) THEN
            totContratada = 0;
        END IF;
        
        SELECT TARIFA_NORM INTO tarifaServicio FROM SERVICIO WHERE (COD_SERVICIO = NEW.COD_SERVICIO);
        
        SELECT PL.TIPO_P INTO tipoPlan FROM PRODUCTO PR, PLAN PL
                WHERE (PR.COD_PLAN = PL.COD_PLAN) AND (PR.COD_PRODUCTO = NEW.COD_PRODUCTO);
        
        IF ( totConsumida >= totOfrecida+totContratada ) THEN
        
            IF (tipoPlan = 'PREPAGO') THEN
                    RAISE EXCEPTION 'Este producto no puede realizar este consumo pues excede de lo ofrecido por su plan.';
            END IF;
        
            UPDATE FACTURA SET MONTO = MONTO + NEW.CANTIDAD*tarifaServicio
            WHERE (COD_PRODUCTO = NEW.COD_PRODUCTO) AND   
            (EXTRACT(month FROM PERIODO_I) = EXTRACT(month FROM NEW.FECHA)) AND
            (EXTRACT(year FROM PERIODO_I) = EXTRACT(year FROM NEW.FECHA));
            
        ELSE
        
            IF (totConsumida + NEW.CANTIDAD > totOfrecida+totContratada ) THEN
            
                IF (tipoPlan = 'PREPAGO') THEN
                    RAISE EXCEPTION 'Este producto no puede realizar este consumo pues excede de lo ofrecido por su plan.';
                END IF;
            
                UPDATE FACTURA SET MONTO = MONTO + (totConsumida + NEW.CANTIDAD - totOfrecida -totContratada )*tarifaServicio
                WHERE (COD_PRODUCTO = NEW.COD_PRODUCTO) AND   
                (EXTRACT(month FROM PERIODO_I) = EXTRACT(month FROM NEW.FECHA)) AND
                (EXTRACT(year FROM PERIODO_I) = EXTRACT(year FROM NEW.FECHA));
            
            END IF;
        
        END IF;
        
        RETURN NEW;
    END;
$ver_consumo_factura$ LANGUAGE plpgsql;

CREATE TRIGGER ver_consumo_factura BEFORE INSERT ON CONSUMO
    FOR EACH ROW EXECUTE PROCEDURE ver_consumo_factura();

-- FIN DEL TRIGGER 5 --

-- TRIGGER 6 --

-- Cada vez que un producto contrata un servicio adicional, se le suma el
-- monto a su factura

CREATE OR REPLACE FUNCTION contrata_adicional_actualizar_factura() 
RETURNS TRIGGER AS $contrata_adicional_actualizar_factura$
    DECLARE
        Monto_Adicional REAL;
    BEGIN
        SELECT SVA.MONTO INTO Monto_Adicional FROM SERV_ADICIONAL SVA WHERE (SVA.COD_SERVICIO = NEW.COD_SERV_AD);

        UPDATE FACTURA SET MONTO = MONTO + Monto_Adicional
        WHERE (COD_PRODUCTO = NEW.COD_PRODUCTO) AND (Periodo_I <= NEW.FECHA) AND (NEW.FECHA <= Periodo_F);
        
        RETURN NEW;
    END;

$contrata_adicional_actualizar_factura$ LANGUAGE plpgsql;

CREATE TRIGGER contrata_adicional_actualizar_factura AFTER INSERT ON CONTRATA_ADICIONAL   
FOR EACH ROW EXECUTE PROCEDURE contrata_adicional_actualizar_factura();

-- FIN DEL TRIGGER 6 --

