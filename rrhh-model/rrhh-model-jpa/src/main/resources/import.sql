--
-- JBoss, Home of Professional Open Source
-- Copyright 2013, Red Hat, Inc. and/or its affiliates, and individual
-- contributors by the @authors tag. See the copyright.txt in the
-- distribution for a full listing of individual contributors.
--
-- Licensed under the Apache License, Version 2.0 (the 'License');
-- you may not use this file except in compliance with the License.
-- You may obtain a copy of the License at
-- http://www.apache.org/licenses/LICENSE-2.0
-- Unless required by applicable law or agreed to in writing, software
-- distributed under the License is distributed on an 'AS IS' BASIS,
-- WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
-- See the License for the specific language governing permissions and
-- limitations under the License.
--

-- You can use this file to load seed data into the database using SQL statements
INSERT INTO SUCURSAL (ID,DENOMINACION,optlk) VALUES ('bf1754a8-46b8-11e5-a151-feff819cdc9f','Ayacucho','1/01/2015');
INSERT INTO SUCURSAL (ID,DENOMINACION,optlk) VALUES ('bf1757d2-46b8-11e5-a151-feff819cdc9f','Lima','2/01/2015');
INSERT INTO SUCURSAL (ID,DENOMINACION,optlk) VALUES ('bf1759ee-46b8-11e5-a151-feff819cdc9f','Ica','3/01/2015');

INSERT INTO AGENCIA (ID,DENOMINACION,DIRECCION,UBIGEO,SUCURSAL_ID,optlk) VALUES ('af8bedfe-46b9-11e5-a151-feff819cdc9f','Portales','Jr. los portales 985','050101','bf1754a8-46b8-11e5-a151-feff819cdc9f','1/01/2015');
INSERT INTO AGENCIA (ID,DENOMINACION,DIRECCION,UBIGEO,SUCURSAL_ID,optlk) VALUES ('af8bf1a0-46b9-11e5-a151-feff819cdc9f','Jr. Lima','Jr. Lima 456','050102','bf1754a8-46b8-11e5-a151-feff819cdc9f','1/01/2015');
INSERT INTO AGENCIA (ID,DENOMINACION,DIRECCION,UBIGEO,SUCURSAL_ID,optlk) VALUES ('af8bf344-46b9-11e5-a151-feff819cdc9f','Neri Garcia','Jr. Proceres 132','050103','bf1754a8-46b8-11e5-a151-feff819cdc9f','1/01/2015');
INSERT INTO AGENCIA (ID,DENOMINACION,DIRECCION,UBIGEO,SUCURSAL_ID,optlk) VALUES ('af8bf790-46b9-11e5-a151-feff819cdc9f','Centro de Lima','Jr. Luna pizarro 12541','010101','bf1757d2-46b8-11e5-a151-feff819cdc9f','1/01/2015');
INSERT INTO AGENCIA (ID,DENOMINACION,DIRECCION,UBIGEO,SUCURSAL_ID,optlk) VALUES ('af8bf916-46b9-11e5-a151-feff819cdc9f','La molina','Jr. Casuarina 54','010101','bf1757d2-46b8-11e5-a151-feff819cdc9f','1/01/2015');
INSERT INTO AGENCIA (ID,DENOMINACION,DIRECCION,UBIGEO,SUCURSAL_ID,optlk) VALUES ('af8bfa74-46b9-11e5-a151-feff819cdc9f','Centro de Ica','Jr. Nazca 5877','060101','bf1759ee-46b8-11e5-a151-feff819cdc9f','1/01/2015');

INSERT INTO TRABAJADOR (ID,TIPO_DOCUMENTO,NUMERO_DOCUMENTO,AGENCIA_ID,optlk) VALUES ('88d07bd4-46ba-11e5-a151-feff819cdc9f','DNI','46779354','af8bedfe-46b9-11e5-a151-feff819cdc9f','1/01/2015');
INSERT INTO TRABAJADOR (ID,TIPO_DOCUMENTO,NUMERO_DOCUMENTO,AGENCIA_ID,optlk) VALUES ('88d0832c-46ba-11e5-a151-feff819cdc9f','DNI','42646768','af8bf1a0-46b9-11e5-a151-feff819cdc9f','1/01/2015');
INSERT INTO TRABAJADOR (ID,TIPO_DOCUMENTO,NUMERO_DOCUMENTO,AGENCIA_ID,optlk) VALUES ('88d08494-46ba-11e5-a151-feff819cdc9f','DNI','21467122','af8bf344-46b9-11e5-a151-feff819cdc9f','1/01/2015');
INSERT INTO TRABAJADOR (ID,TIPO_DOCUMENTO,NUMERO_DOCUMENTO,AGENCIA_ID,optlk) VALUES ('88d0858e-46ba-11e5-a151-feff819cdc9f','DNI','28219421','af8bf790-46b9-11e5-a151-feff819cdc9f','1/01/2015');
INSERT INTO TRABAJADOR (ID,TIPO_DOCUMENTO,NUMERO_DOCUMENTO,AGENCIA_ID,optlk) VALUES ('88d0866a-46ba-11e5-a151-feff819cdc9f','DNI','28268159','af8bf916-46b9-11e5-a151-feff819cdc9f','1/01/2015');
INSERT INTO TRABAJADOR (ID,TIPO_DOCUMENTO,NUMERO_DOCUMENTO,AGENCIA_ID,optlk) VALUES ('88d08732-46ba-11e5-a151-feff819cdc9f','DNI','28216533','af8bfa74-46b9-11e5-a151-feff819cdc9f','1/01/2015');

INSERT INTO TRABAJADOR_USUARIO (ID,USUARIO,TRABAJADOR_ID,optlk) VALUES ('cb8cc8c9-ad9f-4a5c-b91c-1968411a9cd1','admin','88d07bd4-46ba-11e5-a151-feff819cdc9f','1/01/2015');
INSERT INTO TRABAJADOR_USUARIO (ID,USUARIO,TRABAJADOR_ID,optlk) VALUES ('4b19d0df-e970-4637-abf0-cd260125aa84','cajero','88d0832c-46ba-11e5-a151-feff819cdc9f','1/01/2015');
INSERT INTO TRABAJADOR_USUARIO (ID,USUARIO,TRABAJADOR_ID,optlk) VALUES ('fb426803-553c-480d-94cf-5bcc23e2fba1','jefeCaja','88d08494-46ba-11e5-a151-feff819cdc9f','1/01/2015');
INSERT INTO TRABAJADOR_USUARIO (ID,USUARIO,TRABAJADOR_ID,optlk) VALUES ('e0c94a33-014c-4da4-b7f2-2fb8a377fd89','administrador','88d0858e-46ba-11e5-a151-feff819cdc9f','1/01/2015');
INSERT INTO TRABAJADOR_USUARIO (ID,USUARIO,TRABAJADOR_ID,optlk) VALUES ('9c18c0cb-f814-4276-9b2e-7f6d4a6997a5','plataforma','88d0866a-46ba-11e5-a151-feff819cdc9f','1/01/2015');
INSERT INTO TRABAJADOR_USUARIO (ID,USUARIO,TRABAJADOR_ID,optlk) VALUES ('a8922744-8fd8-47bb-aacd-7bcfac520ad2','admin','88d08732-46ba-11e5-a151-feff819cdc9f','1/01/2015');
