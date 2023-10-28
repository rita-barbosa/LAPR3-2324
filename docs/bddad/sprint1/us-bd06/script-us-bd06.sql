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