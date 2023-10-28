SELECT cul.idParcela,
       prod.nomeProduto,
       SUM(DISTINCT op.quantidade) AS totalQuantidade,
       op.designacaoUnidade
FROM Operacao op
    INNER JOIN OperacaoCultura cul ON op.idOperacao = cul.idOperacao
    INNER JOIN Producao prod ON cul.idCultura = prod.idCultura
WHERE UPPER(op.DESIGNACAOOPERACAOAGRICOLA) LIKE 'COLHEITA'
  AND op.dataOperacao BETWEEN TO_DATE('12/10/2005', 'DD/MM/YYYY') AND TO_DATE('26/05/2024', 'DD/MM/YYYY')
GROUP BY cul.idParcela, prod.nomeProduto, op.designacaoUnidade
ORDER BY cul.idParcela, prod.nomeProduto;