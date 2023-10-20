CREATE TABLE Armazem (
    idEdificio            int(5) NOT NULL,
    designacaoTipoArmazem varchar(50) NOT NULL,
    PRIMARY KEY (idEdificio));

CREATE TABLE CalendarioOperacao (
    idCalendarioOperacao int(5) NOT NULL AUTO_INCREMENT,
    sementeira           date,
    poda                 date,
    colheita             date,
    floracao             date,
    PRIMARY KEY (idCalendarioOperacao));

CREATE TABLE Classificacao (
    classificacao varchar(50) NOT NULL,
    PRIMARY KEY (classificacao));

CREATE TABLE ComponenteQuimico (
    formulaQuimica varchar(50) NOT NULL,
    PRIMARY KEY (formulaQuimica));

CREATE TABLE ConstituicaoQuimica (
    formulaQuimica    varchar(50) NOT NULL,
    idFichaTecnica    int(5) NOT NULL,
    designacaoUnidade varchar(5) NOT NULL,
    quantidade        int(11) NOT NULL,
    PRIMARY KEY (formulaQuimica,idFichaTecnica));

CREATE TABLE Cultura (
    idCultura             int(5) NOT NULL AUTO_INCREMENT,
    variedade             varchar(50) NOT NULL,
    nomeComum             varchar(50) NOT NULL,
    designacaoTipoCultura varchar(50) NOT NULL,
    PRIMARY KEY (idCultura));

CREATE TABLE CulturaInstalada (
    idParcela   int(5) NOT NULL,
    idCultura   int(5) NOT NULL,
    idEdificio  int(5) NOT NULL,
    dataInicial date NOT NULL,
    dataFinal   date,
    PRIMARY KEY (idParcela,idCultura));

CREATE TABLE Edificio (
    idEdificio             int(5) NOT NULL,
    designacaoTipoEdificio varchar(50) NOT NULL,
    designacaoUnidade      varchar(5) NOT NULL,
    nomeEdificio           varchar(50) NOT NULL UNIQUE,
    area                   double,
    PRIMARY KEY (idEdificio));

CREATE TABLE EstacaoMeteorologica (
    idEstacaoMeteorologica int(5) NOT NULL AUTO_INCREMENT,
    PRIMARY KEY (idEstacaoMeteorologica));

CREATE TABLE FatorProducao (
    nomeComercial   varchar(50) NOT NULL,
    idFichaTecnica  int(5) NOT NULL,
    idStock         int(5) NOT NULL,
    classificacao   varchar(50) NOT NULL,
    estadoMateria   varchar(50) NOT NULL,
    metodoAplicacao varchar(50) NOT NULL,
    PRIMARY KEY (nomeComercial));

CREATE TABLE Fertilizacao (
    nomeComercial      varchar(50) NOT NULL,
    idOperacaoAgricola int(5) NOT NULL,
    PRIMARY KEY (nomeComercial,idOperacaoAgricola));

CREATE TABLE FichaTecnica (
    idFichaTecnica int(5) NOT NULL AUTO_INCREMENT,
    PRIMARY KEY (idFichaTecnica));

CREATE TABLE Formulacao (
    estadoMateria varchar(50) NOT NULL,
    PRIMARY KEY (estadoMateria));

CREATE TABLE OperacaoAgricola (
    idOperacaoAgricola         int(5) NOT NULL AUTO_INCREMENT,
    idParcela                  int(5) NOT NULL,
    idCultura                  int(5) NOT NULL,
    designacaoOperacaoAgricola varchar(50) NOT NULL,
    designacaoUnidade          varchar(5) NOT NULL,
    quantidade                 int(11),
    data                       date NOT NULL,
    PRIMARY KEY (idOperacaoAgricola));

CREATE TABLE Parcela (
    idParcela         int(5) NOT NULL,
    designacaoUnidade varchar(5) NOT NULL,
    designacaoSensor  varchar(50) NOT NULL,
    nomeParcela       varchar(50) NOT NULL UNIQUE,
    area              double NOT NULL,
    PRIMARY KEY (idParcela));

CREATE TABLE Planta (
    variedade            varchar(50) NOT NULL,
    nomeComum            varchar(50) NOT NULL,
    idCalendarioOperacao int(5) NOT NULL,
    especie              varchar(50) NOT NULL,
    PRIMARY KEY (variedade,nomeComum));

CREATE TABLE Producao (
    idProduto int(5) NOT NULL,
    idCultura int(5) NOT NULL,
    PRIMARY KEY (idProduto,idCultura));

CREATE TABLE Produto (
    idProduto int(5) NOT NULL AUTO_INCREMENT,
    nome      varchar(50) NOT NULL UNIQUE,
    PRIMARY KEY (idProduto));

CREATE TABLE Sensor (
    designacaoSensor       varchar(50) NOT NULL,
    idEstacaoMeteorologica int(5) NOT NULL,
    PRIMARY KEY (designacaoSensor));

CREATE TABLE SistemaDeRega (
    idEdificio int(5) NOT NULL,
    PRIMARY KEY (idEdificio));

CREATE TABLE Stock (
    idStock           int(5) NOT NULL AUTO_INCREMENT,
    idEdificio        int(5) NOT NULL,
    idProduto         int(5) NOT NULL,
    designacaoUnidade varchar(5) NOT NULL,
    quantidade        int(11) NOT NULL,
    PRIMARY KEY (idStock));

CREATE TABLE TipoAplicacao (
    metodoAplicacao varchar(50) NOT NULL,
    PRIMARY KEY (metodoAplicacao));

CREATE TABLE TipoArmazem (
    designacaoTipoArmazem varchar(50) NOT NULL,
    PRIMARY KEY (designacaoTipoArmazem));

CREATE TABLE TipoCultura (
    designacaoTipoCultura varchar(50) NOT NULL,
    PRIMARY KEY (designacaoTipoCultura));

CREATE TABLE TipoEdificio (
    designacaoTipoEdificio varchar(50) NOT NULL,
    PRIMARY KEY (designacaoTipoEdificio));

CREATE TABLE TipoOperacaoAgricola (
    designacaoOperacaoAgricola varchar(50) NOT NULL,
    PRIMARY KEY (designacaoOperacaoAgricola));

CREATE TABLE TipoSensor (
    designacaoSensor varchar(50) NOT NULL,
    PRIMARY KEY (designacaoSensor));

CREATE TABLE TipoUnidade (
    designacaoUnidade varchar(5) NOT NULL,
    PRIMARY KEY (designacaoUnidade));

ALTER TABLE FatorProducao ADD CONSTRAINT FKFatorProdu46093 FOREIGN KEY (idFichaTecnica) REFERENCES FichaTecnica (idFichaTecnica);
ALTER TABLE Armazem ADD CONSTRAINT FKArmazem34014 FOREIGN KEY (idEdificio) REFERENCES Edificio (idEdificio);
ALTER TABLE Sensor ADD CONSTRAINT FKSensor724240 FOREIGN KEY (idEstacaoMeteorologica) REFERENCES EstacaoMeteorologica (idEstacaoMeteorologica);
ALTER TABLE CulturaInstalada ADD CONSTRAINT FKCulturaIns8416 FOREIGN KEY (idParcela) REFERENCES Parcela (idParcela);
ALTER TABLE OperacaoAgricola ADD CONSTRAINT FKOperacaoAg418117 FOREIGN KEY (idParcela, idCultura) REFERENCES CulturaInstalada (idParcela, idCultura);
ALTER TABLE Producao ADD CONSTRAINT FKProducao271906 FOREIGN KEY (idCultura) REFERENCES Cultura (idCultura);
ALTER TABLE Producao ADD CONSTRAINT FKProducao147123 FOREIGN KEY (idProduto) REFERENCES Produto (idProduto);
ALTER TABLE Cultura ADD CONSTRAINT FKCultura115946 FOREIGN KEY (designacaoTipoCultura) REFERENCES TipoCultura (designacaoTipoCultura);
ALTER TABLE SistemaDeRega ADD CONSTRAINT FKSistemaDeR592919 FOREIGN KEY (idEdificio) REFERENCES Edificio (idEdificio);
ALTER TABLE Stock ADD CONSTRAINT FKStock783580 FOREIGN KEY (idEdificio) REFERENCES Armazem (idEdificio);
ALTER TABLE FatorProducao ADD CONSTRAINT FKFatorProdu643107 FOREIGN KEY (metodoAplicacao) REFERENCES TipoAplicacao (metodoAplicacao);
ALTER TABLE Armazem ADD CONSTRAINT FKArmazem920049 FOREIGN KEY (designacaoTipoArmazem) REFERENCES TipoArmazem (designacaoTipoArmazem);
ALTER TABLE FatorProducao ADD CONSTRAINT FKFatorProdu949597 FOREIGN KEY (classificacao) REFERENCES Classificacao (classificacao);
ALTER TABLE FatorProducao ADD CONSTRAINT FKFatorProdu998558 FOREIGN KEY (estadoMateria) REFERENCES Formulacao (estadoMateria);
ALTER TABLE Cultura ADD CONSTRAINT FKCultura587347 FOREIGN KEY (variedade, nomeComum) REFERENCES Planta (variedade, nomeComum);
ALTER TABLE CulturaInstalada ADD CONSTRAINT FKCulturaIns779551 FOREIGN KEY (idCultura) REFERENCES Cultura (idCultura);
ALTER TABLE Sensor ADD CONSTRAINT FKSensor785525 FOREIGN KEY (designacaoSensor) REFERENCES TipoSensor (designacaoSensor);
ALTER TABLE Parcela ADD CONSTRAINT FKParcela38859 FOREIGN KEY (designacaoSensor) REFERENCES Sensor (designacaoSensor);
ALTER TABLE Fertilizacao ADD CONSTRAINT FKFertilizac46637 FOREIGN KEY (nomeComercial) REFERENCES FatorProducao (nomeComercial);
ALTER TABLE Edificio ADD CONSTRAINT FKEdificio200555 FOREIGN KEY (designacaoTipoEdificio) REFERENCES TipoEdificio (designacaoTipoEdificio);
ALTER TABLE FatorProducao ADD CONSTRAINT FKFatorProdu857690 FOREIGN KEY (idStock) REFERENCES Stock (idStock);
ALTER TABLE CulturaInstalada ADD CONSTRAINT FKCulturaIns32096 FOREIGN KEY (idEdificio) REFERENCES SistemaDeRega (idEdificio);
ALTER TABLE Stock ADD CONSTRAINT FKStock810484 FOREIGN KEY (idProduto) REFERENCES Produto (idProduto);
ALTER TABLE Fertilizacao ADD CONSTRAINT FKFertilizac730503 FOREIGN KEY (idOperacaoAgricola) REFERENCES OperacaoAgricola (idOperacaoAgricola);
ALTER TABLE OperacaoAgricola ADD CONSTRAINT FKOperacaoAg771766 FOREIGN KEY (designacaoOperacaoAgricola) REFERENCES TipoOperacaoAgricola (designacaoOperacaoAgricola);
ALTER TABLE Planta ADD CONSTRAINT FKPlanta713876 FOREIGN KEY (idCalendarioOperacao) REFERENCES CalendarioOperacao (idCalendarioOperacao);
ALTER TABLE Parcela ADD CONSTRAINT FKParcela450334 FOREIGN KEY (designacaoUnidade) REFERENCES TipoUnidade (designacaoUnidade);
ALTER TABLE OperacaoAgricola ADD CONSTRAINT FKOperacaoAg548404 FOREIGN KEY (designacaoUnidade) REFERENCES TipoUnidade (designacaoUnidade);
ALTER TABLE Stock ADD CONSTRAINT FKStock442583 FOREIGN KEY (designacaoUnidade) REFERENCES TipoUnidade (designacaoUnidade);
ALTER TABLE Edificio ADD CONSTRAINT FKEdificio711412 FOREIGN KEY (designacaoUnidade) REFERENCES TipoUnidade (designacaoUnidade);
ALTER TABLE ConstituicaoQuimica ADD CONSTRAINT FKConstituic589638 FOREIGN KEY (formulaQuimica) REFERENCES ComponenteQuimico (formulaQuimica);
ALTER TABLE ConstituicaoQuimica ADD CONSTRAINT FKConstituic724074 FOREIGN KEY (idFichaTecnica) REFERENCES FichaTecnica (idFichaTecnica);
ALTER TABLE ConstituicaoQuimica ADD CONSTRAINT FKConstituic208297 FOREIGN KEY (designacaoUnidade) REFERENCES TipoUnidade (designacaoUnidade);

-- ALTER TABLE FatorProducao DROP FOREIGN KEY FKFatorProdu46093;
-- ALTER TABLE Armazem DROP FOREIGN KEY FKArmazem34014;
-- ALTER TABLE Sensor DROP FOREIGN KEY FKSensor724240;
-- ALTER TABLE CulturaInstalada DROP FOREIGN KEY FKCulturaIns8416;
-- ALTER TABLE OperacaoAgricola DROP FOREIGN KEY FKOperacaoAg418117;
-- ALTER TABLE Producao DROP FOREIGN KEY FKProducao271906;
-- ALTER TABLE Producao DROP FOREIGN KEY FKProducao147123;
-- ALTER TABLE Cultura DROP FOREIGN KEY FKCultura115946;
-- ALTER TABLE SistemaDeRega DROP FOREIGN KEY FKSistemaDeR592919;
-- ALTER TABLE Stock DROP FOREIGN KEY FKStock783580;
-- ALTER TABLE FatorProducao DROP FOREIGN KEY FKFatorProdu643107;
-- ALTER TABLE Armazem DROP FOREIGN KEY FKArmazem920049;
-- ALTER TABLE FatorProducao DROP FOREIGN KEY FKFatorProdu949597;
-- ALTER TABLE FatorProducao DROP FOREIGN KEY FKFatorProdu998558;
-- ALTER TABLE Cultura DROP FOREIGN KEY FKCultura587347;
-- ALTER TABLE CulturaInstalada DROP FOREIGN KEY FKCulturaIns779551;
-- ALTER TABLE Sensor DROP FOREIGN KEY FKSensor785525;
-- ALTER TABLE Parcela DROP FOREIGN KEY FKParcela38859;
-- ALTER TABLE Fertilizacao DROP FOREIGN KEY FKFertilizac46637;
-- ALTER TABLE Edificio DROP FOREIGN KEY FKEdificio200555;
-- ALTER TABLE FatorProducao DROP FOREIGN KEY FKFatorProdu857690;
-- ALTER TABLE CulturaInstalada DROP FOREIGN KEY FKCulturaIns32096;
-- ALTER TABLE Stock DROP FOREIGN KEY FKStock810484;
-- ALTER TABLE Fertilizacao DROP FOREIGN KEY FKFertilizac730503;
-- ALTER TABLE OperacaoAgricola DROP FOREIGN KEY FKOperacaoAg771766;
-- ALTER TABLE Planta DROP FOREIGN KEY FKPlanta713876;
-- ALTER TABLE Parcela DROP FOREIGN KEY FKParcela450334;
-- ALTER TABLE OperacaoAgricola DROP FOREIGN KEY FKOperacaoAg548404;
-- ALTER TABLE Stock DROP FOREIGN KEY FKStock442583;
-- ALTER TABLE Edificio DROP FOREIGN KEY FKEdificio711412;
-- ALTER TABLE ConstituicaoQuimica DROP FOREIGN KEY FKConstituic589638;
-- ALTER TABLE ConstituicaoQuimica DROP FOREIGN KEY FKConstituic724074;
-- ALTER TABLE ConstituicaoQuimica DROP FOREIGN KEY FKConstituic208297;
-- DROP TABLE IF EXISTS Armazem;
-- DROP TABLE IF EXISTS CalendarioOperacao;
-- DROP TABLE IF EXISTS Classificacao;
-- DROP TABLE IF EXISTS ComponenteQuimico;
-- DROP TABLE IF EXISTS ConstituicaoQuimica;
-- DROP TABLE IF EXISTS Cultura;
-- DROP TABLE IF EXISTS CulturaInstalada;
-- DROP TABLE IF EXISTS Edificio;
-- DROP TABLE IF EXISTS EstacaoMeteorologica;
-- DROP TABLE IF EXISTS FatorProducao;
-- DROP TABLE IF EXISTS Fertilizacao;
-- DROP TABLE IF EXISTS FichaTecnica;
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
-- DROP TABLE IF EXISTS TipoOperacaoAgricola;
-- DROP TABLE IF EXISTS TipoSensor;
-- DROP TABLE IF EXISTS TipoUnidade;
