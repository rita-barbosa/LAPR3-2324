SELECT p.idParcela as ID_PARCELA,
       p.nomeParcela as NOME_PARCELA,
	   f.classificacao as FATOR_PRODUCAO,
COUNT(afp.nomeComercial) as NUMERO_FATORES_APLICADOS
FROM Parcela p
INNER JOIN OperacaoAgricola oa ON p.idParcela = oa.idParcela
INNER JOIN AplicacaoFatorProducao afp ON oa.idOperacaoAgricola = afp.idOperacaoAgricola
INNER JOIN FatorProducao f ON afp.nomeComercial = f.nomeComercial
WHERE oa.data >= TO_DATE('01/01/2016', 'DD/MM/YYYY')
AND oa.data <= TO_DATE('31/12/2022', 'DD/MM/YYYY')
GROUP BY p.idParcela, p.nomeParcela, f.classificacao
ORDER BY p.idParcela;