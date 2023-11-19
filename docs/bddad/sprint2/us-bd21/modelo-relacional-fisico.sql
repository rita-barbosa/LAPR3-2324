CREATE TABLE TipoPermanencia (
  designacaoTipoPermanencia varchar2(15) NOT NULL, 
  PRIMARY KEY (designacaoTipoPermanencia));
CREATE TABLE Edificio (
  nomeEdificio           varchar2(50) NOT NULL, 
  designacaoTipoEdificio varchar2(50) NOT NULL, 
  designacaoUnidade      varchar2(5), 
  dimensao               number(10) NOT NULL, 
  PRIMARY KEY (nomeEdificio));
CREATE TABLE FatorProducao (
  nomeComercial   varchar2(50) NOT NULL, 
  idStock         number(5) NOT NULL, 
  classificacao   varchar2(50) NOT NULL, 
  estadoMateria   varchar2(50) NOT NULL, 
  metodoAplicacao varchar2(50) NOT NULL, 
  fabricante      varchar2(50) NOT NULL, 
  pH              number(10), 
  PRIMARY KEY (nomeComercial));
CREATE TABLE Parcela (
  nomeParcela       varchar2(50) NOT NULL, 
  designacaoUnidade varchar2(5) NOT NULL, 
  nomeEdificio      varchar2(50) NOT NULL, 
  area              number(10) NOT NULL, 
  PRIMARY KEY (nomeParcela));
CREATE TABLE Produto (
  nomeProduto varchar2(50) NOT NULL, 
  idStock     number(5) NOT NULL, 
  PRIMARY KEY (nomeProduto));
CREATE TABLE CulturaInstalada (
  dataInicial                date NOT NULL, 
  nomeParcela                varchar2(50) NOT NULL, 
  variedade                  varchar2(50) NOT NULL, 
  nomeComum                  varchar2(50) NOT NULL, 
  designacaoUnidade          varchar2(5) NOT NULL, 
  designacaoEstadoFenologico number(10), 
  dataFinal                  date, 
  quantidade                 number(11) NOT NULL, 
  PRIMARY KEY (dataInicial, 
  nomeParcela, 
  variedade, 
  nomeComum), 
  CONSTRAINT dataVerification 
    CHECK (dataFinal >= dataInicial));
CREATE TABLE Producao (
  nomeProduto varchar2(50) NOT NULL, 
  variedade   varchar2(50) NOT NULL, 
  nomeComum   varchar2(50) NOT NULL, 
  PRIMARY KEY (nomeProduto, 
  variedade, 
  nomeComum));
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
  designacaoTipoPermanencia varchar2(15) NOT NULL, 
  especie                   varchar2(50) NOT NULL, 
  PRIMARY KEY (variedade, 
  nomeComum));
CREATE TABLE OperacaoCultura (
  idOperacao  number(10) NOT NULL, 
  nomeParcela varchar2(50) NOT NULL, 
  dataInicial date NOT NULL, 
  nomeComum   varchar2(50) NOT NULL, 
  variedade   varchar2(50) NOT NULL, 
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
CREATE TABLE TipoUnidade (
  designacaoUnidade varchar2(5) NOT NULL, 
  PRIMARY KEY (designacaoUnidade));
CREATE TABLE ConstituicaoQuimica (
  formulaQuimica    varchar2(50) NOT NULL, 
  nomeComercial     varchar2(50) NOT NULL, 
  designacaoUnidade varchar2(5) NOT NULL, 
  quantidade        number(11, 2) NOT NULL, 
  PRIMARY KEY (formulaQuimica, 
  nomeComercial), 
  CONSTRAINT percentual 
    CHECK (quantidade >= 0 AND quantidade <= 100));
CREATE TABLE ColheitaPrevista (
  idColheitaPrevista           number(10) NOT NULL,
  TipoUnidadedesignacaoUnidade varchar2(5) NOT NULL, 
  dataInicial                  date NOT NULL, 
  nomeParcela                  varchar2(50) NOT NULL, 
  nomeComum                    varchar2(50) NOT NULL, 
  variedade                    varchar2(50) NOT NULL, 
  semana                       varchar2(10) NOT NULL, 
  quantidade                   number(11) NOT NULL, 
  PRIMARY KEY (idColheitaPrevista));
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
CREATE TABLE TipoAcaoAgricola (
  designacaoTipoAçaoAgricola varchar2(20) NOT NULL, 
  PRIMARY KEY (designacaoTipoAçaoAgricola));
CREATE TABLE DataAcaoAgricola (
  designacaoTipoAçaoAgricola varchar2(20) NOT NULL, 
  intervaloTempo             varchar2(50), 
  PRIMARY KEY (designacaoTipoAçaoAgricola));
CREATE TABLE Setor (
  idSetor           number(10) NOT NULL,
  nomeEdificio      varchar2(50) NOT NULL, 
  designacaoUnidade varchar2(5) NOT NULL, 
  dataInicio        date NOT NULL, 
  dataFim           date, 
  caudalMaximo      number(10) NOT NULL, 
  PRIMARY KEY (idSetor));
CREATE TABLE SetorCulturaInstalada (
  designacao                  number(10) NOT NULL, 
  dataInicialCulturaInstalada date NOT NULL, 
  nomeParcela                 varchar2(50) NOT NULL, 
  nomeComum                   varchar2(50) NOT NULL, 
  variedade                   varchar2(50) NOT NULL, 
  dataFinalSetorCultura       date, 
  dataInicialSetorCultura     date NOT NULL, 
  PRIMARY KEY (designacao, 
  dataInicialCulturaInstalada, 
  nomeParcela, 
  nomeComum, 
  variedade));
CREATE TABLE CalendarioAcaoAgricola (
  variedade                  varchar2(50) NOT NULL, 
  nomeComum                  varchar2(50) NOT NULL, 
  designacaoTipoAçaoAgricola varchar2(20) NOT NULL, 
  intervalo                  varchar2(50), 
  PRIMARY KEY (variedade, 
  nomeComum, 
  designacaoTipoAçaoAgricola));
CREATE TABLE EstadoFenologico (
  designacao number(10) NOT NULL,
  PRIMARY KEY (designacao));
CREATE TABLE Rega (
  idOperacao      number(10) NOT NULL, 
  designacaoSetor number(10) NOT NULL, 
  horaInicial     varchar2(10) NOT NULL, 
  horaFinal       number(10) NOT NULL, 
  PRIMARY KEY (idOperacao));
CREATE TABLE Maquina (
  idMaquina         number(10) NOT NULL,
  nomeEdificio      varchar2(50) NOT NULL, 
  designacaoMaquina varchar2(50) NOT NULL, 
  PRIMARY KEY (idMaquina));
CREATE TABLE InstrumentoAgricola (
  designacao number(10) NOT NULL,
  PRIMARY KEY (designacao));
CREATE TABLE InstrumentoUtilizado (
  idOperacao number(10) NOT NULL, 
  designacao number(10) NOT NULL, 
  PRIMARY KEY (idOperacao, 
  designacao));
CREATE TABLE MaquinaUtilizada (
  idOperacao number(10) NOT NULL, 
  idMaquina  number(10) NOT NULL, 
  PRIMARY KEY (idOperacao, 
  idMaquina));
CREATE TABLE TipoMaquina (
  designacao varchar2(50) NOT NULL, 
  PRIMARY KEY (designacao));
CREATE TABLE Colheita (
  idOperacao  number(10) NOT NULL, 
  nomeProduto varchar2(50) NOT NULL, 
  PRIMARY KEY (idOperacao));
ALTER TABLE OperacaoCultura ADD CONSTRAINT FKOperacaoCu672704 FOREIGN KEY (dataInicial, nomeParcela, variedade, nomeComum) REFERENCES CulturaInstalada (dataInicial, nomeParcela, variedade, nomeComum);
ALTER TABLE Producao ADD CONSTRAINT FKProducao892124 FOREIGN KEY (nomeProduto) REFERENCES Produto (nomeProduto);
ALTER TABLE Planta ADD CONSTRAINT FKPlanta661690 FOREIGN KEY (designacaoTipoPermanencia) REFERENCES TipoPermanencia (designacaoTipoPermanencia);
ALTER TABLE Stock ADD CONSTRAINT FKStock307537 FOREIGN KEY (nomeEdificio) REFERENCES Edificio (nomeEdificio);
ALTER TABLE FatorProducao ADD CONSTRAINT FKFatorProdu30790 FOREIGN KEY (metodoAplicacao) REFERENCES MetodoAplicacao (metodoAplicacao);
ALTER TABLE FatorProducao ADD CONSTRAINT FKFatorProdu949597 FOREIGN KEY (classificacao) REFERENCES Classificacao (classificacao);
ALTER TABLE FatorProducao ADD CONSTRAINT FKFatorProdu998558 FOREIGN KEY (estadoMateria) REFERENCES Formulacao (estadoMateria);
ALTER TABLE AplicacaoFatorProducao ADD CONSTRAINT FKAplicacaoF208158 FOREIGN KEY (nomeComercial) REFERENCES FatorProducao (nomeComercial);
ALTER TABLE Edificio ADD CONSTRAINT FKEdificio200555 FOREIGN KEY (designacaoTipoEdificio) REFERENCES TipoEdificio (designacaoTipoEdificio);
ALTER TABLE FatorProducao ADD CONSTRAINT FKFatorProdu857690 FOREIGN KEY (idStock) REFERENCES Stock (idStock);
ALTER TABLE Parcela ADD CONSTRAINT FKParcela684711 FOREIGN KEY (nomeEdificio) REFERENCES Edificio (nomeEdificio);
ALTER TABLE AplicacaoFatorProducao ADD CONSTRAINT FKAplicacaoF136092 FOREIGN KEY (idOperacao) REFERENCES Operacao (idOperacao);
ALTER TABLE Operacao ADD CONSTRAINT FKOperacao266984 FOREIGN KEY (designacaoOperacaoAgricola) REFERENCES TipoOperacaoAgricola (designacaoOperacaoAgricola);
ALTER TABLE Parcela ADD CONSTRAINT FKParcela450334 FOREIGN KEY (designacaoUnidade) REFERENCES TipoUnidade (designacaoUnidade);
ALTER TABLE Operacao ADD CONSTRAINT FKOperacao587155 FOREIGN KEY (designacaoUnidade) REFERENCES TipoUnidade (designacaoUnidade);
ALTER TABLE Stock ADD CONSTRAINT FKStock442583 FOREIGN KEY (designacaoUnidade) REFERENCES TipoUnidade (designacaoUnidade);
ALTER TABLE Edificio ADD CONSTRAINT FKEdificio711412 FOREIGN KEY (designacaoUnidade) REFERENCES TipoUnidade (designacaoUnidade);
ALTER TABLE ConstituicaoQuimica ADD CONSTRAINT FKConstituic589638 FOREIGN KEY (formulaQuimica) REFERENCES ComponenteQuimico (formulaQuimica);
ALTER TABLE ConstituicaoQuimica ADD CONSTRAINT FKConstituic208297 FOREIGN KEY (designacaoUnidade) REFERENCES TipoUnidade (designacaoUnidade);
ALTER TABLE CulturaInstalada ADD CONSTRAINT FKCulturaIns272236 FOREIGN KEY (designacaoUnidade) REFERENCES TipoUnidade (designacaoUnidade);
ALTER TABLE Produto ADD CONSTRAINT FKProduto908588 FOREIGN KEY (idStock) REFERENCES Stock (idStock);
ALTER TABLE OperacaoParcela ADD CONSTRAINT FKOperacaoPa42292 FOREIGN KEY (nomeParcela) REFERENCES Parcela (nomeParcela);
ALTER TABLE OperacaoCultura ADD CONSTRAINT FKOperacaoCu905077 FOREIGN KEY (idOperacao) REFERENCES Operacao (idOperacao);
ALTER TABLE OperacaoParcela ADD CONSTRAINT FKOperacaoPa146660 FOREIGN KEY (idOperacao) REFERENCES Operacao (idOperacao);
ALTER TABLE ConstituicaoQuimica ADD CONSTRAINT FKConstituic201649 FOREIGN KEY (nomeComercial) REFERENCES FatorProducao (nomeComercial);
ALTER TABLE DataAcaoAgricola ADD CONSTRAINT FKDataAcaoAg534616 FOREIGN KEY (designacaoTipoAçaoAgricola) REFERENCES TipoAcaoAgricola (designacaoTipoAçaoAgricola);
ALTER TABLE CulturaInstalada ADD CONSTRAINT FKCulturaIns753417 FOREIGN KEY (nomeParcela) REFERENCES Parcela (nomeParcela);
ALTER TABLE SetorCulturaInstalada ADD CONSTRAINT FKSetorCultu259544 FOREIGN KEY (dataInicialCulturaInstalada, nomeParcela, variedade, nomeComum) REFERENCES CulturaInstalada (dataInicial, nomeParcela, variedade, nomeComum);
ALTER TABLE SetorCulturaInstalada ADD CONSTRAINT FKSetorCultu215803 FOREIGN KEY (designacao) REFERENCES Setor (idSetor);
ALTER TABLE ColheitaPrevista ADD CONSTRAINT FKColheitaPr933761 FOREIGN KEY (dataInicial, nomeParcela, variedade, nomeComum) REFERENCES CulturaInstalada (dataInicial, nomeParcela, variedade, nomeComum);
ALTER TABLE CalendarioAcaoAgricola ADD CONSTRAINT FKCalendario777208 FOREIGN KEY (variedade, nomeComum) REFERENCES Planta (variedade, nomeComum);
ALTER TABLE CalendarioAcaoAgricola ADD CONSTRAINT FKCalendario503235 FOREIGN KEY (designacaoTipoAçaoAgricola) REFERENCES DataAcaoAgricola (designacaoTipoAçaoAgricola);
ALTER TABLE CulturaInstalada ADD CONSTRAINT FKCulturaIns927340 FOREIGN KEY (designacaoEstadoFenologico) REFERENCES EstadoFenologico (designacao);
ALTER TABLE Setor ADD CONSTRAINT FKSetor749218 FOREIGN KEY (nomeEdificio) REFERENCES Edificio (nomeEdificio);
ALTER TABLE Rega ADD CONSTRAINT FKRega38580 FOREIGN KEY (idOperacao) REFERENCES Operacao (idOperacao);
ALTER TABLE Rega ADD CONSTRAINT FKRega444782 FOREIGN KEY (designacaoSetor) REFERENCES Setor (idSetor);
ALTER TABLE InstrumentoUtilizado ADD CONSTRAINT FKInstrument753640 FOREIGN KEY (idOperacao) REFERENCES Operacao (idOperacao);
ALTER TABLE InstrumentoUtilizado ADD CONSTRAINT FKInstrument565964 FOREIGN KEY (designacao) REFERENCES InstrumentoAgricola (designacao);
ALTER TABLE MaquinaUtilizada ADD CONSTRAINT FKMaquinaUti660321 FOREIGN KEY (idOperacao) REFERENCES Operacao (idOperacao);
ALTER TABLE MaquinaUtilizada ADD CONSTRAINT FKMaquinaUti767754 FOREIGN KEY (idMaquina) REFERENCES Maquina (idMaquina);
ALTER TABLE Maquina ADD CONSTRAINT FKMaquina759219 FOREIGN KEY (nomeEdificio) REFERENCES Edificio (nomeEdificio);
ALTER TABLE CulturaInstalada ADD CONSTRAINT FKCulturaIns609934 FOREIGN KEY (variedade, nomeComum) REFERENCES Planta (variedade, nomeComum);
ALTER TABLE Maquina ADD CONSTRAINT FKMaquina73966 FOREIGN KEY (designacaoMaquina) REFERENCES TipoMaquina (designacao);
ALTER TABLE Producao ADD CONSTRAINT FKProducao310198 FOREIGN KEY (variedade, nomeComum) REFERENCES Planta (variedade, nomeComum);
ALTER TABLE Setor ADD CONSTRAINT FKSetor884264 FOREIGN KEY (designacaoUnidade) REFERENCES TipoUnidade (designacaoUnidade);
ALTER TABLE Colheita ADD CONSTRAINT FKColheita605445 FOREIGN KEY (idOperacao) REFERENCES Operacao (idOperacao);
ALTER TABLE Colheita ADD CONSTRAINT FKColheita797062 FOREIGN KEY (nomeProduto) REFERENCES Produto (nomeProduto);
ALTER TABLE ColheitaPrevista ADD CONSTRAINT FKColheitaPr709527 FOREIGN KEY (TipoUnidadedesignacaoUnidade) REFERENCES TipoUnidade (designacaoUnidade);
