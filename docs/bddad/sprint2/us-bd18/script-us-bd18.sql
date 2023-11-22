--FUNCTION:
CREATE OR REPLACE FUNCTION listaOperacoes(p_data_inicial Operacao.dataOperacao%type,
                                          p_data_final Operacao.dataOperacao%type)
    RETURN SYS_REFCURSOR IS
    listaOperacoesParcela SYS_REFCURSOR;

BEGIN
    OPEN listaOperacoesParcela FOR
        WITH Operacoes AS (SELECT nomeParcela,
                                  idOperacao
                           FROM (SELECT nomeParcela, idOperacao
                                 FROM OperacaoCultura
                                 UNION ALL
                                 SELECT nomeParcela, idOperacao
                                 FROM OperacaoParcela))

        SELECT OT.nomeParcela, O.designacaoOperacaoAgricola, O.quantidade, O.dataOperacao
        FROM Operacoes OT
                 INNER JOIN Operacao O
                            ON OT.idOperacao = O.idOperacao
                 INNER JOIN TipoOperacaoAgricola TOA
                            ON O.designacaoOperacaoAgricola = TOA.designacaoOperacaoAgricola
        WHERE O.dataOperacao BETWEEN p_data_inicial AND p_data_final
        ORDER BY OT.nomeParcela;

    RETURN listaOperacoesParcela;
END;
/
SET SERVEROUTPUT ON

--DECLARE:
DECLARE
    listaOperacoesParcela      SYS_REFCURSOR;
    nomeParcela                Parcela.nomeParcela%type;
    designacaoOperacaoAgricola TipoOperacaoAgricola.designacaoOperacaoAgricola%type;
    quantidade                 Operacao.quantidade%type;
    dataOperacao               Operacao.dataOperacao%type;
    operacoesInexistente EXCEPTION;
BEGIN
    listaOperacoesParcela := listaOperacoes(TO_DATE('2016-10-05', 'YYYY-MM-DD'),
                                            TO_DATE('2020-10-11', 'YYYY-MM-DD'));

    FETCH listaOperacoesParcela INTO nomeParcela, designacaoOperacaoAgricola, quantidade, dataOperacao;

    IF listaOperacoesParcela%ROWCOUNT = 0 THEN
        RAISE operacoesInexistente;
    ELSE
        LOOP
            EXIT WHEN listaOperacoesParcela%notfound;
            dbms_output.put_line('['||nomeParcela || '] | ' || designacaoOperacaoAgricola || ' - ' || quantidade || ' - ' ||
                                 dataOperacao);
            FETCH listaOperacoesParcela INTO nomeParcela, designacaoOperacaoAgricola, quantidade, dataOperacao;
        END LOOP;
    END IF;

    CLOSE listaOperacoesParcela;

EXCEPTION
    WHEN operacoesInexistente THEN
        RAISE_APPLICATION_ERROR(-20001, 'Não existem operações no período indicado.');
END;
/