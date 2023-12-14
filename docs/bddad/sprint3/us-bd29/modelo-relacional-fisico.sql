CREATE TABLE TipoPermanencia (
  designacaoTipoPermanencia varchar2(15) CONSTRAINT nnTipoPermanenciadesignacao NOT NULL,
  CONSTRAINT pkTipoPermanenciadesignacao PRIMARY KEY (designacaoTipoPermanencia));

CREATE TABLE Edificio (
  nomeEdificio           varchar2(50) CONSTRAINT nnEdificioNomeEdificio NOT NULL,
  designacaoTipoEdificio varchar2(50) CONSTRAINT nnEdificioDesignacaoTipoEdificio NOT NULL,
  designacaoUnidade      varchar2(5), 
  dimensao               number(10) CONSTRAINT nnEdificioDimensao NOT NULL,
  CONSTRAINT pkEdificioNomeEdificio PRIMARY KEY (nomeEdificio));

CREATE TABLE FatorProducao (
  nomeComercial   varchar2(50) CONSTRAINT nnFatorProducaoNomeComercial NOT NULL,
  idStock         number(5) CONSTRAINT nnFatorProducaoIdStock NOT NULL,
  classificacao   varchar2(50) CONSTRAINT nnFatorProducaoClassificacao NOT NULL,
  estadoMateria   varchar2(50) CONSTRAINT nnFatorProducaoEstadoMateria NOT NULL,
  metodoAplicacao varchar2(50) CONSTRAINT nnFatorProducaoMetodoAplicacao NOT NULL,
  fabricante      varchar2(50) CONSTRAINT nnFatorProducaoFabricante NOT NULL,
  pH              number(5, 1),
  CONSTRAINT pkFatorProducaoNomeComercial PRIMARY KEY (nomeComercial),
  CONSTRAINT ckFatorProducaopH CHECK (ph BETWEEN 0.0 AND 14.0));

CREATE TABLE Parcela (
  nomeParcela       varchar2(50) CONSTRAINT nnParcelaNomeParcela NOT NULL,
  designacaoUnidade varchar2(5) CONSTRAINT nnParcelaDesignacaoUnidade NOT NULL,
  area              number(10, 1) CONSTRAINT nnParcelaArea NOT NULL,
  CONSTRAINT pkParcelaNomeParcela PRIMARY KEY (nomeParcela));

CREATE TABLE Produto (
  nomeProduto varchar2(50) CONSTRAINT nnProdutoNomeProduto NOT NULL,
  idStock     number(5) CONSTRAINT nnProdutoIdStock NOT NULL,
  CONSTRAINT pkProdutoNomeProduto PRIMARY KEY (nomeProduto));


-- NAO TOCAR!!! --------------------------------------------------------------------------------------------------------
CREATE TABLE CulturaInstalada (
  dataInicial       date NOT NULL, 
  nomeParcela       varchar2(50) NOT NULL, 
  variedade         varchar2(50) NOT NULL, 
  nomeComum         varchar2(50) NOT NULL, 
  designacaoUnidade varchar2(5) NOT NULL, 
  quantidade        number(11, 2) NOT NULL, 
  dataFinal         date,
  PRIMARY KEY (dataInicial,
  nomeParcela, 
  variedade, 
  nomeComum), 
  CONSTRAINT dataVerification 
    CHECK (dataFinal >= dataInicial));

CREATE TABLE Producao (
  nomeProduto varchar2(50) CONSTRAINT nnProducaoNomeProduto NOT NULL,
  variedade   varchar2(50) CONSTRAINT nnProducaoVariedade NOT NULL,
  nomeComum   varchar2(50) CONSTRAINT nnProducaoNomeComum NOT NULL,
  CONSTRAINT pkProducaoNomeProduto PRIMARY KEY (nomeProduto,
  variedade, 
  nomeComum));

CREATE TABLE Classificacao (
  classificacao varchar2(50) CONSTRAINT nnClassificacaoClassificacao  NOT NULL,
  CONSTRAINT pkClassificacaoClassificacao  PRIMARY KEY (classificacao));

CREATE TABLE MetodoAplicacao (
  metodoAplicacao varchar2(50) CONSTRAINT nnMetodoAplicacaoMetodoAplicacao  NOT NULL,
  CONSTRAINT pkMetodoAplicacaoMetodoAplicacao PRIMARY KEY (metodoAplicacao));

CREATE TABLE ComponenteQuimico (
  formulaQuimica varchar2(50) CONSTRAINT nnComponenteQuimicoFormulaQuimica  NOT NULL,
  CONSTRAINT pkComponenteQuimicoFormulaQuimica PRIMARY KEY (formulaQuimica));

CREATE TABLE Formulacao (
  estadoMateria varchar2(50) CONSTRAINT nnFormulacaoEstadoMateria NOT NULL,
  CONSTRAINT pkFormulacaoEstadoMateria PRIMARY KEY (estadoMateria));

CREATE TABLE Planta (
  variedade                 varchar2(50) CONSTRAINT nnPlantaVariedade NOT NULL,
  nomeComum                 varchar2(50) CONSTRAINT nnPlantaNomeComum NOT NULL,
  designacaoTipoPermanencia varchar2(15) CONSTRAINT nnPlantaDesignacaoTipoPermanencia NOT NULL,
  especie                   varchar2(50) CONSTRAINT nnPlantaEspecie NOT NULL,
  CONSTRAINT pkPlantaVariedadeNomeComum PRIMARY KEY (variedade,
  nomeComum));

-- NAO TOCAR!!! --------------------------------------------------------------------------------------------------------
CREATE TABLE OperacaoCultura (
  idOperacao  number(10) NOT NULL,
  nomeParcela varchar2(50) NOT NULL,
  dataInicial date NOT NULL,
  nomeComum   varchar2(50) NOT NULL,
  variedade   varchar2(50) NOT NULL,
  PRIMARY KEY (idOperacao));


CREATE TABLE Stock (
  idStock           number(5) CONSTRAINT nnStockIdStock NOT NULL,
  nomeEdificio      varchar2(50) CONSTRAINT nnStockNomeEdificio NOT NULL,
  designacaoUnidade varchar2(5) CONSTRAINT nnStockDesignacaoUnidade NOT NULL,
  quantidade        number(11) CONSTRAINT nnStockQuantidade NOT NULL,
  CONSTRAINT pkStockIdStock PRIMARY KEY (idStock));

-- NAO TOCAR!!! --------------------------------------------------------------------------------------------------------
CREATE TABLE AplicacaoFatorProducao (
  nomeComercial varchar2(50) NOT NULL, 
  idOperacao    number(10) NOT NULL, 
  PRIMARY KEY (nomeComercial, 
  idOperacao));

CREATE TABLE TipoEdificio (
  designacaoTipoEdificio varchar2(50) CONSTRAINT nnTipoEdificioDesignacaoTipoEdificio NOT NULL,
  CONSTRAINT pkTipoEdificioDesignacaoTipoEdificio PRIMARY KEY (designacaoTipoEdificio));

CREATE TABLE TipoOperacaoAgricola (
  designacaoOperacaoAgricola varchar2(50) CONSTRAINT nnTipoOperacaoAgricolaDesignacaoOperacaoAgricola NOT NULL,
  CONSTRAINT pkTipoOperacaoAgricolaDesignacaoOperacaoAgricola PRIMARY KEY (designacaoOperacaoAgricola));

CREATE TABLE TipoUnidade (
  designacaoUnidade varchar2(5) CONSTRAINT nnTipoUnidadeDesignacaoUnidade NOT NULL,
  CONSTRAINT pkTipoUnidadeDesignacaoUnidade PRIMARY KEY (designacaoUnidade));

CREATE TABLE ConstituicaoQuimica (
  formulaQuimica    varchar2(50) CONSTRAINT nnConstituicaoQuimicaFormulaQuimica NOT NULL,
  nomeComercial     varchar2(50) CONSTRAINT nnConstituicaoQuimicaNomeComercial NOT NULL,
  designacaoUnidade varchar2(5) CONSTRAINT nnConstituicaoQuimicaDesignacaoUnidade NOT NULL,
  quantidade        number(11, 5) CONSTRAINT nnConstituicaoQuimicaQuantidade NOT NULL,
  CONSTRAINT pkConstituicaoQuimicaFormulaQuimicaNomeComercial PRIMARY KEY (formulaQuimica,
  nomeComercial), 
  CONSTRAINT ckConstituicaoQuimicaQuantidade CHECK (quantidade BETWEEN 0 AND 100));

-- NAO TOCAR!!! --------------------------------------------------------------------------------------------------------
CREATE TABLE OperacaoParcela (
  idOperacao  number(10) NOT NULL, 
  nomeParcela varchar2(50) NOT NULL, 
  PRIMARY KEY (idOperacao));

-- NAO TOCAR!!! --------------------------------------------------------------------------------------------------------
CREATE TABLE Operacao (
  idOperacao                 number(10) NOT NULL,
  designacaoOperacaoAgricola varchar2(50) NOT NULL,
  designacaoUnidade          varchar2(5) NOT NULL,
  quantidade                 number(10, 1) NOT NULL,
  dataOperacao               date NOT NULL, 
  PRIMARY KEY (idOperacao));

CREATE TABLE TipoAcaoAgricola (
  designacaoTipoAcaoAgricola varchar2(50) CONSTRAINT nnTipoAcaoAgricolaDesignacaoTipoAcaoAgricola NOT NULL,
  CONSTRAINT pkTipoAcaoAgricolaDesignacaoTipoAcaoAgricola PRIMARY KEY (designacaoTipoAcaoAgricola));

CREATE TABLE DataAcaoAgricola (
  designacaoTipoAcaoAgricola varchar2(50) CONSTRAINT nnDataAcaoAgricolaDesignacaoTipoAcaoAgricola NOT NULL,
  intervaloTempo             varchar2(70) CONSTRAINT nnDataAcaoAgricolaIntervaloTempo NOT NULL,
  CONSTRAINT pkDataAcaoAgricolaDesignacaoTipoAcaoAgricolaIntervaloTempo PRIMARY KEY (designacaoTipoAcaoAgricola,
  intervaloTempo));

-- NAO TOCAR!!! --------------------------------------------------------------------------------------------------------
CREATE TABLE Setor (
  idSetor           varchar2(20) NOT NULL,
  nomeEdificio      varchar2(50) NOT NULL, 
  designacaoUnidade varchar2(5) NOT NULL, 
  caudalMaximo      number(10) NOT NULL, 
  dataInicio        date NOT NULL, 
  dataFim           date, 
  PRIMARY KEY (idSetor));

CREATE TABLE CalendarioAcaoAgricola (
  variedade                  varchar2(50) CONSTRAINT nnCalendarioAcaoAgricolaVariedade NOT NULL,
  nomeComum                  varchar2(50) CONSTRAINT nnCalendarioAcaoAgricolaNomeComum NOT NULL,
  designacaoTipoAcaoAgricola varchar2(50) CONSTRAINT nnCalendarioAcaoAgricolaDesignacaoTipoAcaoAgricola NOT NULL,
  intervaloTempo             varchar2(70) CONSTRAINT nnCalendarioAcaoAgricolaIntervaloTempo NOT NULL,
  CONSTRAINT pkCalendarioAcaoAgricolaPKComponent PRIMARY KEY (variedade,
  nomeComum, 
  designacaoTipoAcaoAgricola, 
  intervaloTempo));

CREATE TABLE Rega (
  idOperacao      number(10) NOT NULL, 
  designacaoSetor varchar2(20) NOT NULL, 
  PRIMARY KEY (idOperacao));

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
ALTER TABLE DataAcaoAgricola ADD CONSTRAINT FKDataAcaoAg678809 FOREIGN KEY (designacaoTipoAcaoAgricola) REFERENCES TipoAcaoAgricola (designacaoTipoAcaoAgricola);
ALTER TABLE CulturaInstalada ADD CONSTRAINT FKCulturaIns753417 FOREIGN KEY (nomeParcela) REFERENCES Parcela (nomeParcela);
ALTER TABLE SetorCulturaInstalada ADD CONSTRAINT FKSetorCultu259544 FOREIGN KEY (dataInicialCulturaInstalada, nomeParcela, variedade, nomeComum) REFERENCES CulturaInstalada (dataInicial, nomeParcela, variedade, nomeComum);
ALTER TABLE SetorCulturaInstalada ADD CONSTRAINT FKSetorCultu215803 FOREIGN KEY (designacao) REFERENCES Setor (idSetor);
ALTER TABLE CalendarioAcaoAgricola ADD CONSTRAINT FKCalendario777208 FOREIGN KEY (variedade, nomeComum) REFERENCES Planta (variedade, nomeComum);
ALTER TABLE CalendarioAcaoAgricola ADD CONSTRAINT FKCalendario687087 FOREIGN KEY (designacaoTipoAcaoAgricola, intervaloTempo) REFERENCES DataAcaoAgricola (designacaoTipoAcaoAgricola, intervaloTempo);
ALTER TABLE Setor ADD CONSTRAINT FKSetor749218 FOREIGN KEY (nomeEdificio) REFERENCES Edificio (nomeEdificio);
ALTER TABLE Rega ADD CONSTRAINT FKRega38580 FOREIGN KEY (idOperacao) REFERENCES Operacao (idOperacao);
ALTER TABLE Rega ADD CONSTRAINT FKRega444782 FOREIGN KEY (designacaoSetor) REFERENCES Setor (idSetor);
ALTER TABLE CulturaInstalada ADD CONSTRAINT FKCulturaIns609934 FOREIGN KEY (variedade, nomeComum) REFERENCES Planta (variedade, nomeComum);
ALTER TABLE Producao ADD CONSTRAINT FKProducao310198 FOREIGN KEY (variedade, nomeComum) REFERENCES Planta (variedade, nomeComum);
ALTER TABLE Setor ADD CONSTRAINT FKSetor884264 FOREIGN KEY (designacaoUnidade) REFERENCES TipoUnidade (designacaoUnidade);
ALTER TABLE Colheita ADD CONSTRAINT FKColheita605445 FOREIGN KEY (idOperacao) REFERENCES Operacao (idOperacao);
ALTER TABLE Colheita ADD CONSTRAINT FKColheita797062 FOREIGN KEY (nomeProduto) REFERENCES Produto (nomeProduto);
