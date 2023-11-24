-----------------------------------------------
--FUNÇÃO---------------------------------------
CREATE OR REPLACE FUNCTION listaOperacoes(p_nomeParcela Parcela.nomeParcela%type,
                                          p_data_inicial Operacao.dataOperacao%type,
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
        WHERE OT.nomeParcela = p_nomeParcela
          AND O.dataOperacao BETWEEN p_data_inicial AND p_data_final
        ORDER BY OT.nomeParcela;

    RETURN listaOperacoesParcela;
END;
/
-----------------------------------------------
-----------------------------------------------
SET SERVEROUTPUT ON
-----------------------------------------------
--BLOCO ANONIMO--------------------------------
DECLARE
    listaOperacoesParcela      SYS_REFCURSOR;

    nomeParcela                Parcela.nomeParcela%type;
    designacaoOperacaoAgricola TipoOperacaoAgricola.designacaoOperacaoAgricola%type;
    quantidade                 Operacao.quantidade%type;
    dataOperacao               Operacao.dataOperacao%type;
    operacoesInexistente EXCEPTION;
BEGIN
    listaOperacoesParcela := listaOperacoes('Lameiro da ponte', TO_DATE('2016-10-05', 'YYYY-MM-DD'),
                                            TO_DATE('2020-10-11', 'YYYY-MM-DD'));

    FETCH listaOperacoesParcela INTO nomeParcela, designacaoOperacaoAgricola, quantidade, dataOperacao;

    IF listaOperacoesParcela%ROWCOUNT = 0 THEN
        RAISE operacoesInexistente;
    ELSE
        DBMS_OUTPUT.PUT_LINE('PARCELA: ' || nomeParcela);
        DBMS_OUTPUT.PUT_LINE( RPAD('TIPO OPERAÇÃO',20, ' ') || '|  ' || RPAD('QUANTIDADE',20, ' ') ||  '|  ' || RPAD('DATA OPERAÇÃO',20, ' '));
        LOOP
            EXIT WHEN listaOperacoesParcela%notfound;
            DBMS_OUTPUT.PUT_LINE(RPAD(designacaoOperacaoAgricola,20, ' ') || '|  ' || RPAD(quantidade,20, ' ') ||  '|  ' || RPAD(dataOperacao,20, ' '));
            FETCH listaOperacoesParcela INTO nomeParcela, designacaoOperacaoAgricola, quantidade, dataOperacao;
        END LOOP;
    END IF;

    CLOSE listaOperacoesParcela;
EXCEPTION
    WHEN operacoesInexistente THEN
        RAISE_APPLICATION_ERROR(-20001, 'Não existem operações no período indicado.');
END;
/
-----------------------------------------------
--TESTE----------------------------------------
-- Verifica se a função retorna as operações apenas da parcela que pretendemos e se as datas das operações estão dentro do intervalo definido.
DECLARE
    listaOperacoesParcela      SYS_REFCURSOR;
    nomeParcela                Parcela.nomeParcela%type;
    designacaoOperacaoAgricola TipoOperacaoAgricola.designacaoOperacaoAgricola%type;
    quantidade                 Operacao.quantidade%type;
    dataOperacao               Operacao.dataOperacao%type;
    test_nomeParcela   Parcela.nomeParcela%type;
    test_dataInicial   Operacao.dataOperacao%type;
    test_dataFinal     Operacao.dataOperacao%type;
    failedTest EXCEPTION;
BEGIN
    nomeParcela := 'Lameiro da ponte';
    test_nomeParcela := 'Lameiro da ponte';
    test_dataInicial := TO_DATE('2016-10-05', 'YYYY-MM-DD');
    test_dataFinal   := TO_DATE('2020-10-11', 'YYYY-MM-DD');

    listaOperacoesParcela := listaOperacoes('Lameiro da ponte', TO_DATE('2016-10-05', 'YYYY-MM-DD'),
                                            TO_DATE('2020-10-11', 'YYYY-MM-DD'));

    FETCH listaOperacoesParcela INTO nomeParcela, designacaoOperacaoAgricola, quantidade, dataOperacao;

    IF listaOperacoesParcela%ROWCOUNT = 0 THEN
        RAISE failedTest;
    ELSE
        LOOP
            EXIT WHEN listaOperacoesParcela%notfound;
            IF(nomeParcela != test_nomeParcela) THEN
                RAISE failedTest;
            END IF;
            IF( NOT dataOperacao  BETWEEN test_dataInicial AND  test_dataFinal) THEN
                RAISE failedTest;
            END IF;
            FETCH listaOperacoesParcela INTO nomeParcela, designacaoOperacaoAgricola, quantidade, dataOperacao;
        END LOOP;
        DBMS_OUTPUT.PUT_LINE('TESTE PASSOU');
    END IF;

    CLOSE listaOperacoesParcela;
EXCEPTION
    WHEN failedTest THEN
        DBMS_OUTPUT.PUT_LINE('TESTE FALHOU');
END;
/

-----------------------------------------------
--EXEMPLO OUTPUT-------------------------------

-- PARCELA: Lameiro da ponte
-- TIPO OPERAÇÃO       |  QUANTIDADE          |  DATA OPERAÇÃO
-- Plantação           |  90                  |  17.01.07 - 00:00
-- Plantação           |  60                  |  17.01.08 - 00:00
-- Plantação           |  40                  |  17.01.08 - 00:00
-- Rega                |  3                   |  17.07.10 - 00:00
-- Rega                |  3                   |  17.07.10 - 00:00
-- Rega                |  3                   |  17.07.10 - 00:00
-- Rega                |  3,5                 |  17.08.10 - 00:00
-- Rega                |  3,5                 |  17.08.10 - 00:00
-- Rega                |  3,5                 |  17.08.10 - 00:00
-- Rega                |  3                   |  17.09.10 - 00:00
-- Rega                |  3                   |  17.09.10 - 00:00
-- Rega                |  3                   |  17.09.10 - 00:00
-- Poda                |  90                  |  18.01.07 - 00:00
-- Poda                |  60                  |  18.01.08 - 00:00