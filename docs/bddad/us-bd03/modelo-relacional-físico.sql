CREATE TABLE TipoPermanencia (
  designacaoTipoPermanencia varchar2(15) NOT NULL, 
  PRIMARY KEY (designacaoTipoPermanencia));
CREATE TABLE Edificio (
  idEdificio             number(5) NOT NULL, 
  designacaoTipoEdificio varchar2(50) NOT NULL, 
  designacaoUnidade      varchar2(5), 
  nomeEdificio           varchar2(50) NOT NULL UNIQUE, 
  area                   number(10), 
  PRIMARY KEY (idEdificio));
CREATE TABLE FichaTecnica (
  idFichaTecnica number(5) NOT NULL, 
  PRIMARY KEY (idFichaTecnica));
CREATE TABLE FatorProducao (
  nomeComercial   varchar2(50) NOT NULL, 
  idFichaTecnica  number(5) NOT NULL, 
  idStock         number(5) NOT NULL, 
  classificacao   varchar2(50) NOT NULL, 
  estadoMateria   varchar2(50) NOT NULL, 
  metodoAplicacao varchar2(50) NOT NULL, 
  fabricante      varchar2(50) NOT NULL, 
  PRIMARY KEY (nomeComercial));
CREATE TABLE Armazem (
  idEdificio            number(5) NOT NULL, 
  designacaoTipoArmazem varchar2(50) NOT NULL, 
  PRIMARY KEY (idEdificio));
CREATE TABLE Cultura (
  idCultura number(5) NOT NULL, 
  variedade varchar2(50) NOT NULL, 
  nomeComum varchar2(50) NOT NULL, 
  PRIMARY KEY (idCultura));
CREATE TABLE Parcela (
  idParcela         number(5) NOT NULL, 
  designacaoUnidade varchar2(5) NOT NULL, 
  designacaoSensor  varchar2(50) NOT NULL, 
  idEdificio        number(5) NOT NULL, 
  nomeParcela       varchar2(50) NOT NULL UNIQUE, 
  area              number(10) NOT NULL, 
  PRIMARY KEY (idParcela));
CREATE TABLE EstacaoMeteorologica (
  idEstacaoMeteorologica number(5) NOT NULL, 
  PRIMARY KEY (idEstacaoMeteorologica));
CREATE TABLE Sensor (
  designacaoSensor       varchar2(50) NOT NULL, 
  idEstacaoMeteorologica number(5) NOT NULL, 
  PRIMARY KEY (designacaoSensor));
CREATE TABLE TipoSensor (
  designacaoSensor varchar2(50) NOT NULL, 
  PRIMARY KEY (designacaoSensor));
CREATE TABLE Produto (
  idProduto number(5) NOT NULL, 
  idStock   number(5) NOT NULL, 
  nome      varchar2(50) NOT NULL UNIQUE, 
  PRIMARY KEY (idProduto));
CREATE TABLE CulturaInstalada (
  idParcela         number(5) NOT NULL, 
  idCultura         number(5) NOT NULL, 
  designacaoUnidade varchar2(5) NOT NULL, 
  dataInicial       date NOT NULL, 
  dataFinal         date, 
  quantidade        number(11), 
  PRIMARY KEY (idParcela, 
  idCultura));
CREATE TABLE Producao (
  idProduto number(5) NOT NULL, 
  idCultura number(5) NOT NULL, 
  PRIMARY KEY (idProduto, 
  idCultura));
CREATE TABLE SistemaDeRega (
  idEdificio number(5) NOT NULL, 
  PRIMARY KEY (idEdificio));
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
CREATE TABLE TipoArmazem (
  designacaoTipoArmazem varchar2(50) NOT NULL, 
  PRIMARY KEY (designacaoTipoArmazem));
CREATE TABLE Planta (
  variedade                 varchar2(50) NOT NULL, 
  nomeComum                 varchar2(50) NOT NULL, 
  idCalendarioFenologico    number(5) NOT NULL, 
  designacaoTipoPermanencia varchar2(15) NOT NULL, 
  especie                   varchar2(50) NOT NULL, 
  PRIMARY KEY (variedade, 
  nomeComum));
CREATE TABLE OperacaoAgricola (
  idOperacaoAgricola         number(5) NOT NULL, 
  idParcela                  number(5) NOT NULL, 
  idCultura                  number(5) NOT NULL, 
  designacaoOperacaoAgricola varchar2(50) NOT NULL, 
  designacaoUnidade          varchar2(5), 
  quantidade                 number(11), 
  data                       date NOT NULL, 
  PRIMARY KEY (idOperacaoAgricola));
CREATE TABLE Stock (
  idStock           number(5) NOT NULL, 
  idEdificio        number(5) NOT NULL, 
  designacaoUnidade varchar2(5) NOT NULL, 
  quantidade        number(11) NOT NULL, 
  PRIMARY KEY (idStock));
CREATE TABLE AplicacaoFatorProducao (
  nomeComercial      varchar2(50) NOT NULL, 
  idOperacaoAgricola number(5) NOT NULL, 
  PRIMARY KEY (nomeComercial, 
  idOperacaoAgricola));
CREATE TABLE TipoEdificio (
  designacaoTipoEdificio varchar2(50) NOT NULL, 
  PRIMARY KEY (designacaoTipoEdificio));
CREATE TABLE TipoOperacaoAgricola (
  designacaoOperacaoAgricola varchar2(50) NOT NULL, 
  PRIMARY KEY (designacaoOperacaoAgricola));
CREATE TABLE CalendarioFenologico (
  idCalendarioFenologico number(5) NOT NULL, 
  sementeira             varchar2(50), 
  poda                   varchar2(50), 
  colheita               varchar2(50), 
  floracao               varchar2(50), 
  PRIMARY KEY (idCalendarioFenologico));
CREATE TABLE TipoUnidade (
  designacaoUnidade varchar2(5) NOT NULL, 
  PRIMARY KEY (designacaoUnidade));
CREATE TABLE ConstituicaoQuimica (
  formulaQuimica    varchar2(50) NOT NULL, 
  idFichaTecnica    number(5) NOT NULL, 
  designacaoUnidade varchar2(5) NOT NULL, 
  quantidade        number(11) NOT NULL, 
  PRIMARY KEY (formulaQuimica, 
  idFichaTecnica));
CREATE TABLE ColheitaPrevista (
  idCultura  number(5) NOT NULL, 
  idParcela  number(5) NOT NULL, 
  semana     varchar2(10) NOT NULL, 
  quantidade number(11) NOT NULL, 
  PRIMARY KEY (idCultura, 
  idParcela));
ALTER TABLE Armazem ADD CONSTRAINT FKArmazem34014 FOREIGN KEY (idEdificio) REFERENCES Edificio (idEdificio);
ALTER TABLE FatorProducao ADD CONSTRAINT FKFatorProdu46093 FOREIGN KEY (idFichaTecnica) REFERENCES FichaTecnica (idFichaTecnica);
ALTER TABLE Sensor ADD CONSTRAINT FKSensor724240 FOREIGN KEY (idEstacaoMeteorologica) REFERENCES EstacaoMeteorologica (idEstacaoMeteorologica);
ALTER TABLE CulturaInstalada ADD CONSTRAINT FKCulturaIns8416 FOREIGN KEY (idParcela) REFERENCES Parcela (idParcela);
ALTER TABLE OperacaoAgricola ADD CONSTRAINT FKOperacaoAg418117 FOREIGN KEY (idParcela, idCultura) REFERENCES CulturaInstalada (idParcela, idCultura);
ALTER TABLE Producao ADD CONSTRAINT FKProducao271906 FOREIGN KEY (idCultura) REFERENCES Cultura (idCultura);
ALTER TABLE Producao ADD CONSTRAINT FKProducao147123 FOREIGN KEY (idProduto) REFERENCES Produto (idProduto);
ALTER TABLE Planta ADD CONSTRAINT FKPlanta661690 FOREIGN KEY (designacaoTipoPermanencia) REFERENCES TipoPermanencia (designacaoTipoPermanencia);
ALTER TABLE SistemaDeRega ADD CONSTRAINT FKSistemaDeR592919 FOREIGN KEY (idEdificio) REFERENCES Edificio (idEdificio);
ALTER TABLE Stock ADD CONSTRAINT FKStock783580 FOREIGN KEY (idEdificio) REFERENCES Armazem (idEdificio);
ALTER TABLE FatorProducao ADD CONSTRAINT FKFatorProdu30790 FOREIGN KEY (metodoAplicacao) REFERENCES MetodoAplicacao (metodoAplicacao);
ALTER TABLE Armazem ADD CONSTRAINT FKArmazem920049 FOREIGN KEY (designacaoTipoArmazem) REFERENCES TipoArmazem (designacaoTipoArmazem);
ALTER TABLE FatorProducao ADD CONSTRAINT FKFatorProdu949597 FOREIGN KEY (classificacao) REFERENCES Classificacao (classificacao);
ALTER TABLE FatorProducao ADD CONSTRAINT FKFatorProdu998558 FOREIGN KEY (estadoMateria) REFERENCES Formulacao (estadoMateria);
ALTER TABLE Cultura ADD CONSTRAINT FKCultura587347 FOREIGN KEY (variedade, nomeComum) REFERENCES Planta (variedade, nomeComum);
ALTER TABLE CulturaInstalada ADD CONSTRAINT FKCulturaIns779551 FOREIGN KEY (idCultura) REFERENCES Cultura (idCultura);
ALTER TABLE Sensor ADD CONSTRAINT FKSensor785525 FOREIGN KEY (designacaoSensor) REFERENCES TipoSensor (designacaoSensor);
ALTER TABLE Parcela ADD CONSTRAINT FKParcela38859 FOREIGN KEY (designacaoSensor) REFERENCES Sensor (designacaoSensor);
ALTER TABLE AplicacaoFatorProducao ADD CONSTRAINT FKAplicacaoF208158 FOREIGN KEY (nomeComercial) REFERENCES FatorProducao (nomeComercial);
ALTER TABLE Edificio ADD CONSTRAINT FKEdificio200555 FOREIGN KEY (designacaoTipoEdificio) REFERENCES TipoEdificio (designacaoTipoEdificio);
ALTER TABLE FatorProducao ADD CONSTRAINT FKFatorProdu857690 FOREIGN KEY (idStock) REFERENCES Stock (idStock);
ALTER TABLE Parcela ADD CONSTRAINT FKParcela754666 FOREIGN KEY (idEdificio) REFERENCES SistemaDeRega (idEdificio);
ALTER TABLE AplicacaoFatorProducao ADD CONSTRAINT FKAplicacaoF986291 FOREIGN KEY (idOperacaoAgricola) REFERENCES OperacaoAgricola (idOperacaoAgricola);
ALTER TABLE OperacaoAgricola ADD CONSTRAINT FKOperacaoAg771766 FOREIGN KEY (designacaoOperacaoAgricola) REFERENCES TipoOperacaoAgricola (designacaoOperacaoAgricola);
ALTER TABLE Planta ADD CONSTRAINT FKPlanta960851 FOREIGN KEY (idCalendarioFenologico) REFERENCES CalendarioFenologico (idCalendarioFenologico);
ALTER TABLE Parcela ADD CONSTRAINT FKParcela450334 FOREIGN KEY (designacaoUnidade) REFERENCES TipoUnidade (designacaoUnidade);
ALTER TABLE OperacaoAgricola ADD CONSTRAINT FKOperacaoAg548404 FOREIGN KEY (designacaoUnidade) REFERENCES TipoUnidade (designacaoUnidade);
ALTER TABLE Stock ADD CONSTRAINT FKStock442583 FOREIGN KEY (designacaoUnidade) REFERENCES TipoUnidade (designacaoUnidade);
ALTER TABLE Edificio ADD CONSTRAINT FKEdificio711412 FOREIGN KEY (designacaoUnidade) REFERENCES TipoUnidade (designacaoUnidade);
ALTER TABLE ConstituicaoQuimica ADD CONSTRAINT FKConstituic589638 FOREIGN KEY (formulaQuimica) REFERENCES ComponenteQuimico (formulaQuimica);
ALTER TABLE ConstituicaoQuimica ADD CONSTRAINT FKConstituic724074 FOREIGN KEY (idFichaTecnica) REFERENCES FichaTecnica (idFichaTecnica);
ALTER TABLE ConstituicaoQuimica ADD CONSTRAINT FKConstituic208297 FOREIGN KEY (designacaoUnidade) REFERENCES TipoUnidade (designacaoUnidade);
ALTER TABLE CulturaInstalada ADD CONSTRAINT FKCulturaIns272236 FOREIGN KEY (designacaoUnidade) REFERENCES TipoUnidade (designacaoUnidade);
ALTER TABLE ColheitaPrevista ADD CONSTRAINT FKColheitaPr857028 FOREIGN KEY (idParcela, idCultura) REFERENCES CulturaInstalada (idParcela, idCultura);
ALTER TABLE Produto ADD CONSTRAINT FKProduto908588 FOREIGN KEY (idStock) REFERENCES Stock (idStock);
