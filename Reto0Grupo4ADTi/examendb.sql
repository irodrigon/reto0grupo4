#DROP DATABASE examendb;
CREATE DATABASE examendb;

USE examendb;


--
-- Table structure for table `enunciado`
--

DROP TABLE IF EXISTS enunciado;
CREATE TABLE enunciado (
  idE integer NOT NULL,
  descripcion varchar(50) NOT NULL,
  nivel enum('ALTA','MEDIA','BAJA') NOT NULL,
  disponible boolean DEFAULT true,
  ruta varchar(20) DEFAULT NULL,
  PRIMARY KEY (idE));

--
-- Table structure for table `convocatoriaexamen`
--

DROP TABLE IF EXISTS convocatoriaexamen;
CREATE TABLE convocatoriaexamen (
  convocatoria varchar(20) NOT NULL,
  descripcion varchar(50) NOT NULL,
  fecha date,
  curso varchar(20) NOT NULL,
  idE integer,
  PRIMARY KEY (convocatoria),
  FOREIGN KEY (idE) REFERENCES enunciado (idE));


--
-- Table structure for table `unidaddidactica`
--

DROP TABLE IF EXISTS unidaddidactica;
CREATE TABLE unidaddidactica (
  idUD integer NOT NULL,
  acronimo varchar(20) NOT NULL,
  titulo varchar(20) NOT NULL,
  evaluacion varchar(20) NOT NULL,
  descripcion varchar(50) NOT NULL,
  PRIMARY KEY(idUD));

--
-- Table structure for table `enunciado_unidaddidactica`
--

DROP TABLE IF EXISTS enunciado_unidaddidactica;
CREATE TABLE enunciado_unidaddidactica (
  idUD INTEGER NOT NULL,
  idE INTEGER NOT NULL,
  PRIMARY KEY (idE,idUD),
  FOREIGN KEY (idE) REFERENCES enunciado (idE),
  FOREIGN KEY (idUD) REFERENCES unidaddidactica (idUD)
  );
  
INSERT INTO UNIDADDIDACTICA VALUES (1,'INT','Introduccion','Primera','Facil');
INSERT INTO UNIDADDIDACTICA VALUES (2,'VAR','Variables','Primera','Facil');
INSERT INTO UNIDADDIDACTICA VALUES (3,'COND','Condicionales','Primera','Facil');

INSERT INTO CONVOCATORIAEXAMEN VALUES ('Primera','Facil','2024-09-25','2DAMi',NULL);
INSERT INTO CONVOCATORIAEXAMEN VALUES ('Segunda','Media','2024-09-26','2DAMi',NULL);
INSERT INTO CONVOCATORIAEXAMEN VALUES ('Tercera','Dificil','2024-09-27','2DAMi',NULL);

SELECT * FROM ENUNCIADO;

SELECT * FROM CONVOCATORIAEXAMEN;

SELECT * FROM UNIDADDIDACTICA;

SELECT * FROM ENUNCIADO_UNIDADDIDACTICA;