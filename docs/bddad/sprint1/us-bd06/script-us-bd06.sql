SELECT p.idParcela as ID_PARCELA,
       p.nomeParcela as NOME_PARCELA,
	   f.classificacao as TIPO_FATOR_PRODUCAO,
COUNT(afp.nomeComercial) as NUMERO_FATORES_APLICADOS
FROM Parcela p
INNER JOIN OperacaoAgricola oa ON p.idParcela = oa.idParcela
INNER JOIN AplicacaoFatorProducao afp ON oa.idOperacaoAgricola = afp.idOperacaoAgricola
INNER JOIN FatorProducao f ON afp.nomeComercial = f.nomeComercial
WHERE oa.data >= TO_DATE('01/01/2016', 'DD/MM/YYYY')
AND oa.data <= TO_DATE('31/12/2024', 'DD/MM/YYYY')
GROUP BY p.idParcela, p.nomeParcela, f.classificacao
ORDER BY p.idParcela;


--Script sem o nome da parcela e com o nome comercial do fator producao COM AS NOVAS COISAS DE OPERACAO
--TESTAR ISTO DEPOIS
SELECT oc.idParcela as ID_PARCELA,
	   f.classificacao as TIPO_FATOR_PRODUCAO,
       afp.nomeComercial as NOME_COMERCIAL,
COUNT(afp.nomeComercial) as NUMERO_FATORES_APLICADOS
FROM OperacaoCultura oc
INNER JOIN Operacao op ON oc.idOperacao = op.idOperacao
INNER JOIN AplicacaoFatorProducao afp ON op.idOperacao = afp.idOperacao
INNER JOIN FatorProducao f ON afp.nomeComercial = f.nomeComercial
WHERE oc.dataInicial >= TO_DATE('01/01/2016', 'DD/MM/YYYY')
AND oc.dataInicial <= TO_DATE('31/12/2024', 'DD/MM/YYYY')
GROUP BY oc.idParcela, f.classificacao, afp.nomeComercial
ORDER BY oc.idParcela;

--Script sem o nome comercaila, ou aquilo separa mesmo sendo do mesmo tipo
SELECT oc.idParcela as ID_PARCELA,
	   f.classificacao as TIPO_FATOR_PRODUCAO,
       --afp.nomeComercial as NOME_COMERCIAL,
COUNT(f.classificacao) as NUMERO_FATORES_APLICADOS
FROM OperacaoCultura oc
INNER JOIN Operacao op ON oc.idOperacao = op.idOperacao
INNER JOIN AplicacaoFatorProducao afp ON op.idOperacao = afp.idOperacao
INNER JOIN FatorProducao f ON afp.nomeComercial = f.nomeComercial
WHERE oc.dataInicial >= TO_DATE('01/01/2016', 'DD/MM/YYYY')
AND oc.dataInicial <= TO_DATE('31/12/2024', 'DD/MM/YYYY')
GROUP BY oc.idParcela, f.classificacao
ORDER BY oc.idParcela;


--Modo para aparecer a parcela 103 também, visto que não existe em operacaoCultura
SELECT oc.idParcela as ID_PARCELA,
       f.classificacao as TIPO_FATOR_PRODUCAO,
       COUNT(f.classificacao) as NUMERO_FATORES_APLICADOS
FROM OperacaoCultura oc
INNER JOIN Operacao op ON oc.idOperacao = op.idOperacao
INNER JOIN AplicacaoFatorProducao afp ON op.idOperacao = afp.idOperacao
INNER JOIN FatorProducao f ON afp.nomeComercial = f.nomeComercial
WHERE oc.dataInicial >= TO_DATE('01/01/2016', 'DD/MM/YYYY')
AND oc.dataInicial <= TO_DATE('31/12/2024', 'DD/MM/YYYY')
GROUP BY oc.idParcela, f.classificacao

UNION ALL

SELECT opp.idParcela as ID_PARCELA,
       f.classificacao as TIPO_FATOR_PRODUCAO,
       COUNT(f.classificacao) as NUMERO_FATORES_APLICADOS
FROM OperacaoParcela opp
INNER JOIN Operacao op2 ON opp.idOperacao = op2.idOperacao
INNER JOIN AplicacaoFatorProducao afp ON op2.idOperacao = afp.idOperacao
INNER JOIN FatorProducao f ON afp.nomeComercial = f.nomeComercial
GROUP BY opp.idParcela, f.classificacao
ORDER BY ID_PARCELA;

--tentativa com duas tabelas no from NAO RESULTTOU
SELECT oc.idParcela as ID_PARCELA,
	   f.classificacao as TIPO_FATOR_PRODUCAO,
COUNT(f.classificacao) as NUMERO_FATORES_APLICADOS
FROM OperacaoParcela opp, OperacaoCultura oc
INNER JOIN Operacao op ON oc.idOperacao = op.idOperacao
INNER JOIN Operacao op ON opp.idOperacao = op.idOperacao
INNER JOIN AplicacaoFatorProducao afp ON op.idOperacao = afp.idOperacao
INNER JOIN FatorProducao f ON afp.nomeComercial = f.nomeComercial
WHERE oc.dataInicial >= TO_DATE('01/01/2016', 'DD/MM/YYYY')
AND oc.dataInicial <= TO_DATE('31/12/2024', 'DD/MM/YYYY')
GROUP BY oc.idParcela, f.classificacao
ORDER BY oc.idParcela;


--tentativa com o with
WITH Operacoes AS (
    SELECT DISTINCT idParcela, idOperacao
    FROM (
        SELECT idParcela, idOperacao
        FROM OperacaoCultura
        WHERE dataInicial >= TO_DATE('01/01/2016', 'DD/MM/YYYY')
        AND dataInicial <= TO_DATE('31/12/2024', 'DD/MM/YYYY')
        UNION ALL
        SELECT idParcela, idOperacao
        FROM OperacaoParcela
    )
), Fatores AS (
    SELECT op.idParcela as ID_PARCELA,
           f.classificacao as TIPO_FATOR_PRODUCAO
    FROM Operacoes op
    INNER JOIN Operacao o ON op.idOperacao = o.idOperacao
    INNER JOIN AplicacaoFatorProducao afp ON o.idOperacao = afp.idOperacao
    INNER JOIN FatorProducao f ON afp.nomeComercial = f.nomeComercial
)
SELECT f.ID_PARCELA,
       f.TIPO_FATOR_PRODUCAO,
       COUNT(f.TIPO_FATOR_PRODUCAO) as NUMERO_FATORES_APLICADOS
FROM Fatores f
GROUP BY f.ID_PARCELA, f.TIPO_FATOR_PRODUCAO
ORDER BY f.ID_PARCELA;


--Usando o COALESCE
SELECT COALESCE(oc.idParcela, opp.idParcela) as ID_PARCELA,
	   f.classificacao as TIPO_FATOR_PRODUCAO,
COUNT(f.classificacao) as NUMERO_FATORES_APLICADOS
FROM Operacao op
FULL OUTER JOIN OperacaoCultura oc ON op.idOperacao = oc.idOperacao
FULL OUTER JOIN OperacaoParcela opp ON op.idOperacao = opp.idOperacao
INNER JOIN AplicacaoFatorProducao afp ON op.idOperacao = afp.idOperacao
INNER JOIN FatorProducao f ON afp.nomeComercial = f.nomeComercial
WHERE op.dataOperacao >= TO_DATE('01/01/2016', 'DD/MM/YYYY')
AND op.dataOperacao <= TO_DATE('31/12/2024', 'DD/MM/YYYY')
GROUP BY COALESCE(oc.idParcela, opp.idParcela), f.classificacao
ORDER BY ID_PARCELA;
