SELECT *
FROM FATORPRODUCAO
WHERE FATORPRODUCAO.NOMECOMERCIAL = (SELECT NOMECOMERCIAL
                                     FROM (SELECT APLICACAOFATORPRODUCAO.NOMECOMERCIAL
	                                       FROM APLICACAOFATORPRODUCAO INNER JOIN OPERACAO
	                                       ON APLICACAOFATORPRODUCAO.IDOPERACAO = OPERACAO.IDOPERACAO
                                           WHERE OPERACAO.DATAOPERACAO >= TO_DATE('01/01/2016', 'DD/MM/YYYY')
	                                       AND OPERACAO.DATAOPERACAO <= TO_DATE('31/12/2023', 'DD/MM/YYYY'))
                                     HAVING COUNT(NOMECOMERCIAL) = (SELECT MAX(COUNT(APLICACAOFATORPRODUCAO.NOMECOMERCIAL))
				                                                   FROM APLICACAOFATORPRODUCAO
				 			                                       GROUP BY APLICACAOFATORPRODUCAO.NOMECOMERCIAL)
                                     GROUP BY NOMECOMERCIAL);