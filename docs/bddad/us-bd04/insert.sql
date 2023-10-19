-- tabela Colheita


-- tabela Armazem


-- tabela CadernoCampo


-- tabela Classificacao


-- tabela Componente


-- tabela Cultura
-- VERIFICAR O IDCULTURA, AUTO_INCREMENT!!
INSERT INTO Cultura(idCultura, variedade, nomeComum, idParcela) VALUES (1, 'Campo Grande', 'Oliveira Galega', 102);
INSERT INTO Cultura(idCultura, variedade, nomeComum, idParcela) VALUES (1, 'Campo Grande', 'Oliveira Picual', 102);
INSERT INTO Cultura(idCultura, variedade, nomeComum, idParcela) VALUES (1, 'Lameiro da ponte', 'Macieira Jonagored', 104);
INSERT INTO Cultura(idCultura, variedade, nomeComum, idParcela) VALUES (1, 'Lameiro da ponte', 'Macieira Fuji', 104);
INSERT INTO Cultura(idCultura, variedade, nomeComum, idParcela) VALUES (1, 'Lameiro da ponte', 'Macieira Royal Gala', 104);
INSERT INTO Cultura(idCultura, variedade, nomeComum, idParcela) VALUES (1, 'Lameiro da ponte', 'Macieira Royal Gala', 104);
INSERT INTO Cultura(idCultura, variedade, nomeComum, idParcela) VALUES (1, 'Campo da bouça', 'Tremoço Amarelo', 101);
INSERT INTO Cultura(idCultura, variedade, nomeComum, idParcela) VALUES (1, 'Campo da bouça', 'Milho Doce Golden Bantam', 101);
INSERT INTO Cultura(idCultura, variedade, nomeComum, idParcela) VALUES (1, 'Campo da bouça', 'Milho Doce Golden Bantam', 101);
INSERT INTO Cultura(idCultura, variedade, nomeComum, idParcela) VALUES (1, 'Campo da bouça', 'Tremoço Amarelo', 101);



-- tabela CulturaInstalada
-- VERIFICAR O IDPARCELA, AUTO_INCREMENT!!
INSERT INTO CulturaInstalada (idParcela, idCultura, dataInicial, dataFinal) VALUES (2, 102, '06/10/2016', null);
INSERT INTO CulturaInstalada (idParcela, idCultura, dataInicial, dataFinal) VALUES (2, 102, '10/10/2016', null);
INSERT INTO CulturaInstalada (idParcela, idCultura, dataInicial, dataFinal) VALUES (2, 104, '07/01/2017', null);
INSERT INTO CulturaInstalada (idParcela, idCultura, dataInicial, dataFinal) VALUES (2, 104, '08/01/2017', null);
INSERT INTO CulturaInstalada (idParcela, idCultura, dataInicial, dataFinal) VALUES (2, 104, '08/01/2017', null);
INSERT INTO CulturaInstalada (idParcela, idCultura, dataInicial, dataFinal) VALUES (2, 104, '10/12/2018', null);
INSERT INTO CulturaInstalada (idParcela, idCultura, dataInicial, dataFinal) VALUES (2, 101, '10/10/2020', TO_DATE('30/03/2021', 'DD/MM/YYYY'));
INSERT INTO CulturaInstalada (idParcela, idCultura, dataInicial, dataFinal) VALUES (2, 101, '10/04/2021', TO_DATE('12/08/2021', 'DD/MM/YYYY'));
INSERT INTO CulturaInstalada (idParcela, idCultura, dataInicial, dataFinal) VALUES (2, 101, '15/04/2021', TO_DATE('21/08/2021', 'DD/MM/YYYY'));
INSERT INTO CulturaInstalada (idParcela, idCultura, dataInicial, dataFinal) VALUES (2, 101, '03/10/2021', TO_DATE('05/04/2022', 'DD/MM/YYYY'));


-- tabela Edificio
-- VERIFICAR O IDTIPOEDIFICIO e IDEDIFICIO -> AUTO_INCREMENT!!
INSERT INTO Edificio(idEdificio, idTipoEdificio, designacao, area) VALUES (201, 1, 'Espigueiro', 600);
INSERT INTO Edificio(idEdificio, idTipoEdificio, designacao, area) VALUES (202, 1, 'Armazém novo', 800);
INSERT INTO Edificio(idEdificio, idTipoEdificio, designacao, area) VALUES (203, 1, 'Armazém grande', 900);
INSERT INTO Edificio(idEdificio, idTipoEdificio, designacao, area) VALUES (250, 1, 'Moinho', null);
INSERT INTO Edificio(idEdificio, idTipoEdificio, designacao, area) VALUES (301, 1, 'Tanque do campo grande', 15);


-- tabela EstacaoMeteorologica


-- tabela FatorProducao


-- tabela Fertilizacao


-- tabela Formulacao


-- tabela OperacaoAgricola


-- tabela Parcela


-- tabela Planta


-- tabela Producao


-- tabela Produto


-- tabela Sensor


-- tabela SistemaDeRega


-- tabela Stock


-- tabela TipoAplicacao


-- tabela TipoArmazem


-- tabela TipoSensor


