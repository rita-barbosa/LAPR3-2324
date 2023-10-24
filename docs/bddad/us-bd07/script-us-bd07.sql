SELECT p.idParcela                   ID_PARCELA,
       p.nomeParcela                 NOME_PARCELA,
       oa.designacaoOperacaoAgricola TIPO_OPERACAO,
       COUNT(*)                      NUM_OPERACOES
FROM Parcela p
         INNER JOIN OperacaoAgricola oa ON p.idParcela = oa.idParcela
WHERE oa.data >= TO_DATE('01/01/2016', 'DD/MM/YYYY')
  AND oa.data <= TO_DATE('31/12/2022', 'DD/MM/YYYY')
GROUP BY p.idParcela, p.nomeParcela, oa.designacaoOperacaoAgricola
ORDER BY p.idParcela;