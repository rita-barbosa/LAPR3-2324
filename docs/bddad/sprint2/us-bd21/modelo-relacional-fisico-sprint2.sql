CREATE TABLE TipoPermanencia (
  designacaoTipoPermanencia varchar2(15) NOT NULL, 
  PRIMARY KEY (designacaoTipoPermanencia));
CREATE TABLE Edificio (
  nomeEdificio           varchar2(50) NOT NULL, 
  designacaoTipoEdificio varchar2(50) NOT NULL, 
  designacaoUnidade      varchar2(5) NOT NULL, 
  dimensao               number(10) NOT NULL, 
  PRIMARY KEY (nomeEdificio));
CREATE TABLE FatorProducao (
  nomeComercial   varchar2(50) NOT NULL, 
  idStock         number(5) NOT NULL, 
  classificacao   varchar2(50) NOT NULL, 
  estadoMateria   varchar2(50) NOT NULL, 
  metodoAplicacao varchar2(50) NOT NULL, 
  idFichaTecnica  number(5) NOT NULL, 
  fabricante      varchar2(50) NOT NULL, 
  PRIMARY KEY (nomeComercial));
CREATE TABLE Cultura (
  idCultura number(5) NOT NULL, 
  variedade varchar2(50) NOT NULL, 
  nomeComum varchar2(50) NOT NULL, 
  PRIMARY KEY (idCultura));
CREATE TABLE Parcela (
  nomeParcela          varchar2(50) NOT NULL, 
  designacaoUnidade    varchar2(5) NOT NULL, 
  EdificionomeEdificio varchar2(50) NOT NULL, 
  idEdificio           number(5) NOT NULL, 
  area                 number(10) NOT NULL, 
  PRIMARY KEY (nomeParcela));
CREATE TABLE Produto (
  nomeProduto varchar2(50) NOT NULL, 
  idStock     number(5) NOT NULL, 
  PRIMARY KEY (nomeProduto));
CREATE TABLE CulturaInstalada (
  dataInicial       date NOT NULL, 
  nomeParcela       varchar2(50) NOT NULL, 
  idCultura         number(5) NOT NULL, 
  designacaoUnidade varchar2(5) NOT NULL, 
  dataFinal         date DEFAULT '01/01/1900' NOT NULL CHECK(dataVerification), 
  quantidade        number(11) NOT NULL, 
  PRIMARY KEY (dataInicial, 
  nomeParcela, 
  idCultura), 
  CONSTRAINT dataVerification 
    CHECK (dataFinal >= dataInicial));
CREATE TABLE Producao (
  idCultura   number(5) NOT NULL, 
  nomeProduto varchar2(50) NOT NULL, 
  PRIMARY KEY (idCultura, 
  nomeProduto));
CREATE TABLE Classificacao (
  classificacao varchar2(50) NOT NULL, 
  PRIMARY KEY (classificacao));
CREATE TABLE MetodoAplicacao (
  metodoAplicacao varchar2(50) NOT NULL, 
  PRIMARY KEY (metodoAplicacao));
CREATE TABLE ComponenteQuimico (
  formulaQuimica varchar2(50) NOT NULL, 
  PRIMARY KEY (formulaQuimica));
CREATE TABLE Formulacao (
  estadoMateria varchar2(50) NOT NULL, 
  PRIMARY KEY (estadoMateria));
CREATE TABLE Planta (
  variedade                 varchar2(50) NOT NULL, 
  nomeComum                 varchar2(50) NOT NULL, 
  idCalendarioFenologico    number(5) NOT NULL, 
  designacaoTipoPermanencia varchar2(15) NOT NULL, 
  especie                   varchar2(50) NOT NULL, 
  PRIMARY KEY (variedade, 
  nomeComum));
CREATE TABLE OperacaoCultura (
  idOperacao  number(10) NOT NULL, 
  nomeParcela varchar2(50) NOT NULL, 
  idCultura   number(5) NOT NULL, 
  dataInicial date NOT NULL, 
  PRIMARY KEY (idOperacao));
CREATE TABLE Stock (
  idStock           number(5) NOT NULL, 
  nomeEdificio      varchar2(50) NOT NULL, 
  designacaoUnidade varchar2(5) NOT NULL, 
  quantidade        number(11) NOT NULL, 
  PRIMARY KEY (idStock));
CREATE TABLE AplicacaoFatorProducao (
  nomeComercial varchar2(50) NOT NULL, 
  idOperacao    number(10) NOT NULL, 
  PRIMARY KEY (nomeComercial, 
  idOperacao));
CREATE TABLE TipoEdificio (
  designacaoTipoEdificio varchar2(50) NOT NULL, 
  PRIMARY KEY (designacaoTipoEdificio));
CREATE TABLE TipoOperacaoAgricola (
  designacaoOperacaoAgricola varchar2(50) NOT NULL, 
  PRIMARY KEY (designacaoOperacaoAgricola));
CREATE TABLE CalendarioFenologico (
  idCalendarioFenologico number(5) NOT NULL, 
  PRIMARY KEY (idCalendarioFenologico));
CREATE TABLE TipoUnidade (
  designacaoUnidade varchar2(5) NOT NULL, 
  PRIMARY KEY (designacaoUnidade));
CREATE TABLE ConstituicaoQuimica (
  formulaQuimica    varchar2(50) NOT NULL, 
  nomeComercial     varchar2(50) NOT NULL, 
  designacaoUnidade varchar2(5) NOT NULL, 
  quantidade        number(11) NOT NULL CHECK(${percentual}), 
  PRIMARY KEY (formulaQuimica, 
  nomeComercial), 
  CONSTRAINT percentual 
    CHECK (quantidade >= 0 AND quantidade <= 100));
CREATE TABLE ColheitaPrevista (
  idCultura   number(5) NOT NULL, 
  nomeParcela varchar2(50) NOT NULL, 
  dataInicial date NOT NULL, 
  semana      varchar2(10) NOT NULL, 
  quantidade  number(11) NOT NULL, 
  PRIMARY KEY (idCultura, 
  nomeParcela, 
  dataInicial));
CREATE TABLE OperacaoParcela (
  idOperacao  number(10) NOT NULL, 
  nomeParcela varchar2(50) NOT NULL, 
  PRIMARY KEY (idOperacao));
CREATE TABLE Operacao (
  idOperacao                 number(10) NOT NULL, 
  designacaoOperacaoAgricola varchar2(50) NOT NULL, 
  designacaoUnidade          varchar2(5) NOT NULL, 
  quantidade                 number(10) NOT NULL, 
  dataOperacao               date NOT NULL, 
  PRIMARY KEY (idOperacao));
CREATE TABLE TipoFenologia (
  designacaoTipoFenologia varchar2(20) NOT NULL, 
  PRIMARY KEY (designacaoTipoFenologia));
CREATE TABLE DataFenologia (
  idDataFenologia         number(5) GENERATED AS IDENTITY, 
  designacaoTipoFenologia varchar2(20) NOT NULL, 
  intervalo               varchar2(50) NOT NULL, 
  PRIMARY KEY (idDataFenologia));
CREATE TABLE DataFenologiaCalendarioFenologico (
  idDataFenologia        number(5) NOT NULL, 
  idCalendarioFenologico number(5) NOT NULL, 
  PRIMARY KEY (idDataFenologia, 
  idCalendarioFenologico));
ALTER TABLE OperacaoCultura ADD CONSTRAINT FKOperacaoCu339375 FOREIGN KEY (dataInicial, nomeParcela, idCultura) REFERENCES CulturaInstalada (dataInicial, nomeParcela, idCultura);
ALTER TABLE Producao ADD CONSTRAINT FKProducao271906 FOREIGN KEY (idCultura) REFERENCES Cultura (idCultura);
ALTER TABLE Producao ADD CONSTRAINT FKProducao892124 FOREIGN KEY (nomeProduto) REFERENCES Produto (nomeProduto);
ALTER TABLE Planta ADD CONSTRAINT FKPlanta661690 FOREIGN KEY (designacaoTipoPermanencia) REFERENCES TipoPermanencia (designacaoTipoPermanencia);
ALTER TABLE Stock ADD CONSTRAINT FKStock307537 FOREIGN KEY (nomeEdificio) REFERENCES Edificio (nomeEdificio);
ALTER TABLE FatorProducao ADD CONSTRAINT FKFatorProdu30790 FOREIGN KEY (metodoAplicacao) REFERENCES MetodoAplicacao (metodoAplicacao);
ALTER TABLE FatorProducao ADD CONSTRAINT FKFatorProdu949597 FOREIGN KEY (classificacao) REFERENCES Classificacao (classificacao);
ALTER TABLE FatorProducao ADD CONSTRAINT FKFatorProdu998558 FOREIGN KEY (estadoMateria) REFERENCES Formulacao (estadoMateria);
ALTER TABLE Cultura ADD CONSTRAINT FKCultura587347 FOREIGN KEY (variedade, nomeComum) REFERENCES Planta (variedade, nomeComum);
ALTER TABLE CulturaInstalada ADD CONSTRAINT FKCulturaIns779551 FOREIGN KEY (idCultura) REFERENCES Cultura (idCultura);
ALTER TABLE AplicacaoFatorProducao ADD CONSTRAINT FKAplicacaoF208158 FOREIGN KEY (nomeComercial) REFERENCES FatorProducao (nomeComercial);
ALTER TABLE Edificio ADD CONSTRAINT FKEdificio200555 FOREIGN KEY (designacaoTipoEdificio) REFERENCES TipoEdificio (designacaoTipoEdificio);
ALTER TABLE FatorProducao ADD CONSTRAINT FKFatorProdu857690 FOREIGN KEY (idStock) REFERENCES Stock (idStock);
ALTER TABLE Parcela ADD CONSTRAINT FKParcela754645 FOREIGN KEY (EdificionomeEdificio) REFERENCES Edificio (nomeEdificio);
ALTER TABLE AplicacaoFatorProducao ADD CONSTRAINT FKAplicacaoF136092 FOREIGN KEY (idOperacao) REFERENCES Operacao (idOperacao);
ALTER TABLE Operacao ADD CONSTRAINT FKOperacao266984 FOREIGN KEY (designacaoOperacaoAgricola) REFERENCES TipoOperacaoAgricola (designacaoOperacaoAgricola);
ALTER TABLE Planta ADD CONSTRAINT FKPlanta960851 FOREIGN KEY (idCalendarioFenologico) REFERENCES CalendarioFenologico (idCalendarioFenologico);
ALTER TABLE Parcela ADD CONSTRAINT FKParcela450334 FOREIGN KEY (designacaoUnidade) REFERENCES TipoUnidade (designacaoUnidade);
ALTER TABLE Operacao ADD CONSTRAINT FKOperacao587155 FOREIGN KEY (designacaoUnidade) REFERENCES TipoUnidade (designacaoUnidade);
ALTER TABLE Stock ADD CONSTRAINT FKStock442583 FOREIGN KEY (designacaoUnidade) REFERENCES TipoUnidade (designacaoUnidade);
ALTER TABLE Edificio ADD CONSTRAINT FKEdificio711412 FOREIGN KEY (designacaoUnidade) REFERENCES TipoUnidade (designacaoUnidade);
ALTER TABLE ConstituicaoQuimica ADD CONSTRAINT FKConstituic589638 FOREIGN KEY (formulaQuimica) REFERENCES ComponenteQuimico (formulaQuimica);
ALTER TABLE ConstituicaoQuimica ADD CONSTRAINT FKConstituic208297 FOREIGN KEY (designacaoUnidade) REFERENCES TipoUnidade (designacaoUnidade);
ALTER TABLE CulturaInstalada ADD CONSTRAINT FKCulturaIns272236 FOREIGN KEY (designacaoUnidade) REFERENCES TipoUnidade (designacaoUnidade);
ALTER TABLE ColheitaPrevista ADD CONSTRAINT FKColheitaPr600432 FOREIGN KEY (dataInicial, nomeParcela, idCultura) REFERENCES CulturaInstalada (dataInicial, nomeParcela, idCultura);
ALTER TABLE Produto ADD CONSTRAINT FKProduto908588 FOREIGN KEY (idStock) REFERENCES Stock (idStock);
ALTER TABLE OperacaoParcela ADD CONSTRAINT FKOperacaoPa42292 FOREIGN KEY (nomeParcela) REFERENCES Parcela (nomeParcela);
ALTER TABLE OperacaoCultura ADD CONSTRAINT FKOperacaoCu905077 FOREIGN KEY (idOperacao) REFERENCES Operacao (idOperacao);
ALTER TABLE OperacaoParcela ADD CONSTRAINT FKOperacaoPa146660 FOREIGN KEY (idOperacao) REFERENCES Operacao (idOperacao);
ALTER TABLE ConstituicaoQuimica ADD CONSTRAINT FKConstituic201649 FOREIGN KEY (nomeComercial) REFERENCES FatorProducao (nomeComercial);
ALTER TABLE DataFenologia ADD CONSTRAINT FKDataFenolo878973 FOREIGN KEY (designacaoTipoFenologia) REFERENCES TipoFenologia (designacaoTipoFenologia);
ALTER TABLE DataFenologiaCalendarioFenologico ADD CONSTRAINT FKDataFenolo251415 FOREIGN KEY (idDataFenologia) REFERENCES DataFenologia (idDataFenologia);
ALTER TABLE DataFenologiaCalendarioFenologico ADD CONSTRAINT FKDataFenolo126650 FOREIGN KEY (idCalendarioFenologico) REFERENCES CalendarioFenologico (idCalendarioFenologico);
ALTER TABLE CulturaInstalada ADD CONSTRAINT FKCulturaIns753417 FOREIGN KEY (nomeParcela) REFERENCES Parcela (nomeParcela);
