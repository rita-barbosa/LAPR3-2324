SELECT opag.idParcela,
       opag.produto,
       SUM(opag.quantidade) AS "QUANTIDADE TOTAL",
       opag.designacaoUnidade AS UNIDADE
FROM OperacaoAgricola opag
         INNER JOIN Parcela p ON opag.idParcela = p.idParcela
WHERE UPPER(opag.designacaoOperacaoAgricola) LIKE UPPER('COLHEITA')
  AND opag.data BETWEEN TO_DATE('12/10/2005', 'DD/MM/YYYY') AND TO_DATE('26/05/2022', 'DD/MM/YYYY')
--AND opag.idParcela = 106
GROUP BY opag.idParcela, opag.produto, opag.designacaoUnidade
ORDER BY opag.idParcela;