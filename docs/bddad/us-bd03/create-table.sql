CREATE TABLE Colheita (
    idColheita int(5) NOT NULL AUTO_INCREMENT,
    idParcela  int(5) NOT NULL UNIQUE,
    idCultura  int(5) NOT NULL UNIQUE,
    semana     int(11) NOT NULL,
    quantidade int(11) NOT NULL,
    PRIMARY KEY (idColheita));

CREATE TABLE Armazem (
    idEdificio    int(5) NOT NULL,
    idTipoArmazem int(5) NOT NULL,
    PRIMARY KEY (idEdificio));

CREATE TABLE CadernoCampo (
    idCadernoCampo int(5) NOT NULL AUTO_INCREMENT,
    PRIMARY KEY (idCadernoCampo));

CREATE TABLE Classificacao (
    idClassificacao int(5) NOT NULL AUTO_INCREMENT,
    designacao      varchar(100) UNIQUE,
    PRIMARY KEY (idClassificacao));

CREATE TABLE Componente (
    FormulaQuimica varchar(50) NOT NULL,
    quantidade     int(11) NOT NULL);

CREATE TABLE Cultura (
    idCultura     int(5) NOT NULL AUTO_INCREMENT,
    variedade     varchar(50) NOT NULL,
    nomeComum     varchar(50) NOT NULL,
    idTipoCultura int(5) NOT NULL,
    idParcela     int(5) NOT NULL,
    PRIMARY KEY (idCultura));

CREATE TABLE CulturaInstalada (
    idParcela   int(5) NOT NULL UNIQUE,
    idCultura   int(5) NOT NULL UNIQUE,
    idEdificio  int(5) NOT NULL,
    dataInicial date NOT NULL,
    dataFinal   date,
    PRIMARY KEY (idParcela, idCultura));

CREATE TABLE Edificio (
    idEdificio     int(5) NOT NULL AUTO_INCREMENT,
    idTipoEdificio int(5) NOT NULL,
    designacao     varchar(50),
    area           double NOT NULL,
    PRIMARY KEY (idEdificio));

CREATE TABLE EstacaoMeteorologica (
    idEstacaoMeteorologica int(5) NOT NULL AUTO_INCREMENT,
    PRIMARY KEY (idEstacaoMeteorologica));

CREATE TABLE FatorProducao (
    nomeComercial   varchar(50) NOT NULL,
    idClassificacao int(5) NOT NULL,
    idTipoAplicacao int(5) NOT NULL,
    idFormulacao    int(5) NOT NULL,
    idStock         int(5) NOT NULL,
    fabricante      varchar(50),
    PRIMARY KEY (nomeComercial));

CREATE TABLE Fertilizacao (
    idFertilizacao int(5) NOT NULL AUTO_INCREMENT,
    nomeComercial  varchar(50) NOT NULL,
    quantidade     int(11),
    PRIMARY KEY (idFertilizacao));

CREATE TABLE Formulacao (
    idFormulacao  int(5) NOT NULL AUTO_INCREMENT,
    estadoMateria varchar(100) UNIQUE,
    PRIMARY KEY (idFormulacao));

CREATE TABLE OperacaoAgricola (
    idParcela      int(5) NOT NULL,
    idCultura      int(5) NOT NULL,
    data           date,
    idColheita     int(5) NOT NULL,
    idFertilizacao int(5) NOT NULL,
    idCadernoCampo int(5) NOT NULL);

CREATE TABLE Parcela (
    idParcela              int(5) NOT NULL AUTO_INCREMENT,
    idTipoSensor           int(5) NOT NULL,
    idEstacaoMeteorologica int(5) NOT NULL,
    designacao             varchar(100) NOT NULL,
    area                   double NOT NULL,
    PRIMARY KEY (idParcela));

CREATE TABLE Planta (
    variedade  varchar(50) NOT NULL UNIQUE,
    nomeComum  varchar(50) NOT NULL UNIQUE,
    especie    varchar(50) NOT NULL,
    sementeira date,
    poda       date,
    colheita   date,
    floracao   date,
    PRIMARY KEY (variedade, nomeComum));

CREATE TABLE Producao (
    idProduto int(11) NOT NULL UNIQUE,
    idCultura int(5) NOT NULL UNIQUE,
    PRIMARY KEY (idProduto, idCultura));

CREATE TABLE Produto (
    idProduto int(11) NOT NULL AUTO_INCREMENT,
    idStock   int(5) NOT NULL,
    nome      varchar(50),
    PRIMARY KEY (idProduto));

CREATE TABLE Sensor (
    idTipoSensor           int(5) NOT NULL,
    idEstacaoMeteorologica int(5) NOT NULL UNIQUE,
    idCadernoCampo         int(5) NOT NULL,
    PRIMARY KEY (idTipoSensor));

CREATE TABLE SistemaDeRega (
    idEdificio int(5) NOT NULL,
    PRIMARY KEY (idEdificio));

CREATE TABLE Stock (
    idStock    int(5) NOT NULL AUTO_INCREMENT,
    idEdificio int(5) NOT NULL,
    quantidade int(11),
    PRIMARY KEY (idStock));

CREATE TABLE TipoAplicacao (
    idTipoAplicacao int(5) NOT NULL AUTO_INCREMENT,
    metodoAplicacao varchar(100) UNIQUE,
    PRIMARY KEY (idTipoAplicacao));

CREATE TABLE TipoArmazem (
    idTipoArmazem int(5) NOT NULL AUTO_INCREMENT,
    designacao    varchar(50) UNIQUE,
    PRIMARY KEY (idTipoArmazem));

CREATE TABLE TipoCultura (
    idTipoCultura int(5) NOT NULL AUTO_INCREMENT,
    designacao    varchar(50),
    PRIMARY KEY (idTipoCultura));

CREATE TABLE TipoEdificio (
    idTipoEdificio int(5) NOT NULL AUTO_INCREMENT,
    designacao     varchar(100) NOT NULL,
    PRIMARY KEY (idTipoEdificio));

CREATE TABLE TipoSensor (
    idTipoSensor int(5) NOT NULL AUTO_INCREMENT,
    designacao   varchar(50) UNIQUE,
    PRIMARY KEY (idTipoSensor));


ALTER TABLE FatorProducao ADD CONSTRAINT FKFatorProdu857281 FOREIGN KEY () REFERENCES FichaTecnica ();
ALTER TABLE Armazem ADD CONSTRAINT FKArmazem34014 FOREIGN KEY (idEdificio) REFERENCES Edificio (idEdificio);
ALTER TABLE Sensor ADD CONSTRAINT FKSensor724240 FOREIGN KEY (idEstacaoMeteorologica) REFERENCES EstacaoMeteorologica (idEstacaoMeteorologica);
ALTER TABLE CulturaInstalada ADD CONSTRAINT FKCulturaIns8416 FOREIGN KEY (idParcela) REFERENCES Parcela (idParcela);
ALTER TABLE `	Colheita` ADD CONSTRAINT `FK	Colheita773942` FOREIGN KEY (idParcela, idCultura) REFERENCES CulturaInstalada (idParcela, idCultura);
ALTER TABLE Producao ADD CONSTRAINT FKProducao271906 FOREIGN KEY (idCultura) REFERENCES Cultura (idCultura);
ALTER TABLE Producao ADD CONSTRAINT FKProducao147123 FOREIGN KEY (idProduto) REFERENCES Produto (idProduto);
ALTER TABLE Cultura ADD CONSTRAINT FKCultura750413 FOREIGN KEY (idTipoCultura) REFERENCES TipoCultura (idTipoCultura);
ALTER TABLE SistemaDeRega ADD CONSTRAINT FKSistemaDeR592919 FOREIGN KEY (idEdificio) REFERENCES Edificio (idEdificio);
ALTER TABLE Stock ADD CONSTRAINT FKStock783580 FOREIGN KEY (idEdificio) REFERENCES Armazem (idEdificio);
ALTER TABLE Componente ADD CONSTRAINT FKComponente967329 FOREIGN KEY () REFERENCES FichaTecnica ();
ALTER TABLE FatorProducao ADD CONSTRAINT FKFatorProdu734245 FOREIGN KEY (idTipoAplicacao) REFERENCES TipoAplicacao (idTipoAplicacao);
ALTER TABLE Armazem ADD CONSTRAINT FKArmazem554517 FOREIGN KEY (idTipoArmazem) REFERENCES TipoArmazem (idTipoArmazem);
ALTER TABLE FatorProducao ADD CONSTRAINT FKFatorProdu306315 FOREIGN KEY (idClassificacao) REFERENCES Classificacao (idClassificacao);
ALTER TABLE FatorProducao ADD CONSTRAINT FKFatorProdu678014 FOREIGN KEY (idFormulacao) REFERENCES Formulacao (idFormulacao);
ALTER TABLE Cultura ADD CONSTRAINT FKCultura587347 FOREIGN KEY (variedade, nomeComum) REFERENCES Planta (variedade, nomeComum);
ALTER TABLE CulturaInstalada ADD CONSTRAINT FKCulturaIns779551 FOREIGN KEY (idCultura) REFERENCES Cultura (idCultura);
ALTER TABLE OperacaoAgricola ADD CONSTRAINT FKOperacaoAg107762 FOREIGN KEY (idCadernoCampo) REFERENCES CadernoCampo (idCadernoCampo);
ALTER TABLE Produto ADD CONSTRAINT FKProduto908588 FOREIGN KEY (idStock) REFERENCES Stock (idStock);
ALTER TABLE Sensor ADD CONSTRAINT FKSensor374510 FOREIGN KEY (idTipoSensor) REFERENCES TipoSensor (idTipoSensor);
ALTER TABLE Parcela ADD CONSTRAINT FKParcela449874 FOREIGN KEY (idTipoSensor) REFERENCES Sensor (idTipoSensor);
ALTER TABLE OperacaoAgricola ADD CONSTRAINT FKOperacaoAg19227 FOREIGN KEY (idColheita) REFERENCES `	Colheita` (idColheita);
ALTER TABLE OperacaoAgricola ADD CONSTRAINT FKOperacaoAg84969 FOREIGN KEY (idFertilizacao) REFERENCES Fertilizacao (idFertilizacao);
ALTER TABLE Fertilizacao ADD CONSTRAINT FKFertilizac46637 FOREIGN KEY (nomeComercial) REFERENCES FatorProducao (nomeComercial);
ALTER TABLE SistemaDeRega ADD CONSTRAINT FKSistemaDeR148458 FOREIGN KEY () REFERENCES OperacaoAgricola ();
ALTER TABLE Edificio ADD CONSTRAINT FKEdificio496350 FOREIGN KEY (idTipoEdificio) REFERENCES TipoEdificio (idTipoEdificio);
ALTER TABLE FatorProducao ADD CONSTRAINT FKFatorProdu857690 FOREIGN KEY (idStock) REFERENCES Stock (idStock);
ALTER TABLE CulturaInstalada ADD CONSTRAINT FKCulturaIns32096 FOREIGN KEY (idEdificio) REFERENCES SistemaDeRega (idEdificio);
ALTER TABLE Sensor ADD CONSTRAINT FKSensor668839 FOREIGN KEY (idCadernoCampo) REFERENCES CadernoCampo (idCadernoCampo);


-- ALTER TABLE FatorProducao DROP FOREIGN KEY FKFatorProdu857281;
-- ALTER TABLE Armazem DROP FOREIGN KEY FKArmazem34014;
-- ALTER TABLE Sensor DROP FOREIGN KEY FKSensor724240;
-- ALTER TABLE CulturaInstalada DROP FOREIGN KEY FKCulturaIns8416;
-- ALTER TABLE `	Colheita` DROP FOREIGN KEY `FK	Colheita773942`;
-- ALTER TABLE Producao DROP FOREIGN KEY FKProducao271906;
-- ALTER TABLE Producao DROP FOREIGN KEY FKProducao147123;
-- ALTER TABLE Cultura DROP FOREIGN KEY FKCultura750413;
-- ALTER TABLE SistemaDeRega DROP FOREIGN KEY FKSistemaDeR592919;
-- ALTER TABLE Stock DROP FOREIGN KEY FKStock783580;
-- ALTER TABLE Componente DROP FOREIGN KEY FKComponente967329;
-- ALTER TABLE FatorProducao DROP FOREIGN KEY FKFatorProdu734245;
-- ALTER TABLE Armazem DROP FOREIGN KEY FKArmazem554517;
-- ALTER TABLE FatorProducao DROP FOREIGN KEY FKFatorProdu306315;
-- ALTER TABLE FatorProducao DROP FOREIGN KEY FKFatorProdu678014;
-- ALTER TABLE Cultura DROP FOREIGN KEY FKCultura587347;
-- ALTER TABLE CulturaInstalada DROP FOREIGN KEY FKCulturaIns779551;
-- ALTER TABLE OperacaoAgricola DROP FOREIGN KEY FKOperacaoAg107762;
-- ALTER TABLE Produto DROP FOREIGN KEY FKProduto908588;
-- ALTER TABLE Sensor DROP FOREIGN KEY FKSensor374510;
-- ALTER TABLE Parcela DROP FOREIGN KEY FKParcela449874;
-- ALTER TABLE OperacaoAgricola DROP FOREIGN KEY FKOperacaoAg19227;
-- ALTER TABLE OperacaoAgricola DROP FOREIGN KEY FKOperacaoAg84969;
-- ALTER TABLE Fertilizacao DROP FOREIGN KEY FKFertilizac46637;
-- ALTER TABLE SistemaDeRega DROP FOREIGN KEY FKSistemaDeR148458;
-- ALTER TABLE Edificio DROP FOREIGN KEY FKEdificio496350;
-- ALTER TABLE FatorProducao DROP FOREIGN KEY FKFatorProdu857690;
-- ALTER TABLE CulturaInstalada DROP FOREIGN KEY FKCulturaIns32096;
-- ALTER TABLE Sensor DROP FOREIGN KEY FKSensor668839;
-- DROP TABLE IF EXISTS Colheita;
-- DROP TABLE IF EXISTS Armazem;
-- DROP TABLE IF EXISTS CadernoCampo;
-- DROP TABLE IF EXISTS Classificacao;
-- DROP TABLE IF EXISTS Componente;
-- DROP TABLE IF EXISTS Cultura;
-- DROP TABLE IF EXISTS CulturaInstalada;
-- DROP TABLE IF EXISTS Edificio;
-- DROP TABLE IF EXISTS EstacaoMeteorologica;
-- DROP TABLE IF EXISTS FatorProducao;
-- DROP TABLE IF EXISTS Fertilizacao;
-- DROP TABLE IF EXISTS Formulacao;
-- DROP TABLE IF EXISTS OperacaoAgricola;
-- DROP TABLE IF EXISTS Parcela;
-- DROP TABLE IF EXISTS Planta;
-- DROP TABLE IF EXISTS Producao;
-- DROP TABLE IF EXISTS Produto;
-- DROP TABLE IF EXISTS Sensor;
-- DROP TABLE IF EXISTS SistemaDeRega;
-- DROP TABLE IF EXISTS Stock;
-- DROP TABLE IF EXISTS TipoAplicacao;
-- DROP TABLE IF EXISTS TipoArmazem;
-- DROP TABLE IF EXISTS TipoCultura;
-- DROP TABLE IF EXISTS TipoEdificio;
-- DROP TABLE IF EXISTS TipoSensor;