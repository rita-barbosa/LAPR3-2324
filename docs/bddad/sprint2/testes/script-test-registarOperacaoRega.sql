-----------------------------------------------
--FUNÇÃO---------------------------------------
SET SERVEROUTPUT ON;

DECLARE
    v_desigOp tipoOperacaoAgricola.DESIGNACAOOPERACAOAGRICOLA%TYPE := 'Rega';
    v_desigUnidade tipounidade.DESIGNACAOUNIDADE%TYPE := 'min';
    v_qtd NUMBER := 26;
    v_dataOp DATE := TO_DATE('29/11/2023 - 07:30', 'DD/MM/YYYY - HH24:MI');
    v_idSetor setor.IDSETOR%TYPE := 30;
    v_success NUMBER;
BEGIN

    v_success := registarOperacaoRega(v_desigOp, v_desigUnidade, v_qtd, v_dataOp, v_idSetor);

    IF v_success = 1 THEN
        DBMS_OUTPUT.PUT_LINE('success');
    ELSE
        DBMS_OUTPUT.PUT_LINE('failure');
    END IF;

END;
/
--------------------------------------------------------------------------
--TESTE-------------------------------------------------------------------