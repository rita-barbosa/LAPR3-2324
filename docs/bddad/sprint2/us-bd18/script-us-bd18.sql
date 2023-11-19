--FUNCTION:
CREATE OR REPLACE FUNCTION listaOperacoes(p_nome_parcela Parcela.nomeParcela%type,

                                              p_data_inicial Operacao.dataOperacao%type,
                                              p_data_final Operacao.dataOperacao%type)
    RETURN SYS_REFCURSOR IS
    listaOperacoesParcela SYS_REFCURSOR;

BEGIN
    OPEN listaOperacoesParcela FOR
        WITH Operacoes AS (
            SELECT  nomeParcela,
                    idOperacao

            FROM (
                     SELECT nomeParcela, idOperacao
                     FROM OperacaoCultura
                     UNION ALL
                     SELECT nomeParcela, idOperacao
                     FROM OperacaoParcela
                 )
        )
        SELECT O.idOperacao, O.designacaoOperacaoAgricola, O.quantidade, O.dataOperacao
        FROM Operacoes OT
                 INNER JOIN  Operacao O
                    ON OT.idOperacao = O.idOperacao
                 INNER JOIN  TipoOperacaoAgricola TOA
                    ON O.designacaoOperacaoAgricola = TOA.designacaoOperacaoAgricola
        WHERE OT.idParcela = p_id_parcela
        AND O.dataOperacao BETWEEN p_data_inicial AND p_data_final;

    RETURN listaOperacoesParcela;
END;

--DECLARE:
DECLARE
    listaOperacoesParcela SYS_REFCURSOR;
    idOperacao Operacao.idOperacao%type;
    designacaoOperacaoAgricola TipoOperacaoAgricola.designacaoOperacaoAgricola%type;
    quantidade Operacao.quantidade%type;
    dataOperacao Operacao.dataOperacao%type;
BEGIN
    listaOperacoesParcela := listaOperacoes('Lameiro da Ponte',TO_DATE('2016-10-05', 'YYYY-MM-DD'),TO_DATE('2020-10-11', 'YYYY-MM-DD'));
    LOOP
        FETCH listaOperacoesParcela
            INTO idOperacao, designacaoOperacaoAgricola, quantidade, dataOperacao;
        EXIT WHEN  listaOperacoesParcela%notfound;

        dbms_output.put_line(idOperacao || ' - ' || designacaoOperacaoAgricola || ' - ' || quantidade || ' - ' || dataOperacao);
    END LOOP;
    CLOSE listaOperacoesParcela;
END;