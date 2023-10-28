WITH OperacoesTotais AS (
    SELECT p.idParcela, o.idOperacao, toa.designacaoOperacaoAgricola
    FROM Parcela p
             INNER JOIN OperacaoParcela op ON p.idParcela = op.idParcela
             INNER JOIN Operacao o ON op.idOperacao = o.idOperacao
             INNER JOIN TipoOperacaoAgricola toa ON o.designacaoOperacaoAgricola = toa.designacaoOperacaoAgricola
    WHERE o.dataOperacao BETWEEN TO_DATE('01/01/2016', 'DD/MM/YYYY') AND TO_DATE('31/12/2024', 'DD/MM/YYYY')
    UNION ALL
    SELECT oc.idParcela, o.idOperacao, toa.designacaoOperacaoAgricola
    FROM OperacaoCultura oc
             INNER JOIN Operacao o ON oc.idOperacao = o.idOperacao
             INNER JOIN TipoOperacaoAgricola toa ON o.designacaoOperacaoAgricola = toa.designacaoOperacaoAgricola
    WHERE o.dataOperacao BETWEEN TO_DATE('01/01/2016', 'DD/MM/YYYY') AND TO_DATE('31/12/2024', 'DD/MM/YYYY')
)
SELECT idParcela AS ID_PARCELA,
       designacaoOperacaoAgricola AS TIPO_OPERACAO,
       COUNT(distinct idOperacao) AS NUMERO_OPERACOES
FROM OperacoesTotais
GROUP BY idParcela, designacaoOperacaoAgricola
ORDER BY idParcela, designacaoOperacaoAgricola;