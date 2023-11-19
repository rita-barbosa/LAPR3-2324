--FUNÇÃO
CREATE OR REPLACE FUNCTION registarOperacaoColheita(
    							--p_id_Operacao IN Operacao.idOperacao%TYPE,
    							p_des_Operacao IN TipoOperacaoAgricola.designacaoOperacaoAgricola%TYPE,
    							p_des_Unidade IN tipoUnidade.designacaoUnidade%TYPE,
    							p_quantidade IN NUMBER,
                            	p_data_Operacao IN DATE,
                            	p_id_Parcela IN parcela.idParcela%TYPE,
                            	p_id_Cultura IN cultura.idCultura%TYPE,
    							p_dataFinal IN DATE
) RETURN NUMBER IS
    l_success NUMBER := 0;
    datas SYS_REFCURSOR;
    dataInicio DATE;
    idOp NUMBER;
BEGIN
    BEGIN
        datas := getDataInicialCultura(p_id_Parcela, p_id_Cultura);
        LOOP
            FETCH
                datas
            INTO
                dataInicio;
            EXIT WHEN datas%NOTFOUND;
            idOp := novoIdOperacao();

            IF p_dataFinal IS NULL THEN
                INSERT INTO Operacao (idOperacao, designacaoOperacaoAgricola, designacaoUnidade, quantidade, dataOperacao)
                VALUES (idOp, p_des_Operacao, p_des_Unidade, p_quantidade, p_data_Operacao);
                INSERT INTO OperacaoCultura (idOperacao, idParcela, idCultura, dataInicial)
                VALUES (idOp, p_id_Parcela, p_id_Cultura, dataInicio);
            ELSE
                INSERT INTO CulturaInstalada (dataInicial, idParcela, idCultura, designacaoUnidade, dataFinal, quantidade)
                VALUES (dataInicio, p_id_Parcela, p_id_Cultura, p_des_Unidade, TO_DATE('10/01/2025', 'DD/MM/YYYY'), p_quantidade);
                INSERT INTO Operacao (idOperacao, designacaoOperacaoAgricola, designacaoUnidade, quantidade, dataOperacao)
                VALUES (idOp, p_des_Operacao, p_des_Unidade, p_quantidade, p_data_Operacao);
                INSERT INTO OperacaoCultura (idOperacao, idParcela, idCultura, dataInicial)
                VALUES (idOp, p_id_Parcela, p_id_Cultura, dataInicio);
            END IF;
        END LOOP;

        l_success := 0;
    EXCEPTION
        WHEN OTHERS THEN
            l_success := 1;
    END;

    RETURN l_success;
END;
/
----------------------------------------------------
CREATE OR REPLACE FUNCTION novoIdOperacao
RETURN NUMBER AS
    newIdOperation NUMBER;
BEGIN
    SELECT NVL(MAX(idOperacao), 0) + 1
    INTO newIdOperation
    FROM operacao;
    RETURN newIdOperation;
END;
/
----------------------------------------------------
CREATE OR REPLACE FUNCTION getDataInicialCultura(p_id_Parcela IN parcela.idParcela%TYPE,
                                                 p_id_Cultura IN cultura.idCultura%TYPE)
RETURN SYS_REFCURSOR IS
    datas SYS_REFCURSOR;
BEGIN
    BEGIN
        open datas for
            select datainicial
            from culturaInstalada
            where idParcela = p_id_Parcela
              and idCultura = p_id_Cultura;
        return datas;
    EXCEPTION
        WHEN no_data_found THEN
            return null;
    END;
END;
/



--BLOCO ANÓNIMO

SET SERVEROUTPUT ON;

DECLARE
    -- First operation
    --v_id_Operacao1 NUMBER := 481;
    v_des_Operacao1 tipoOperacaoAgricola.designacaoOperacaoAgricola%TYPE := 'Colheita';
    v_des_Unidade1 tipoUnidade.designacaoUnidade%TYPE := 'ha';
    v_quantidade1 NUMBER := 5678;
    v_data_Operacao1 DATE := TO_DATE('20/11/2024', 'DD/MM/YYYY');
    v_id_Parcela1 parcela.idParcela%TYPE := 107;
    v_id_Cultura1 cultura.idCultura%TYPE := 15;
    v_dataFinal1 DATE := TO_DATE('20/11/2024', 'DD/MM/YYYY');
    v_result1 NUMBER;
BEGIN
    --v_result1 := registarOperacaoColheita(v_id_Operacao1, v_des_Operacao1, v_des_Unidade1, v_quantidade1, v_data_Operacao1, v_id_Parcela1, v_id_Cultura1, v_dataInicio1);
    v_result1 := registarOperacaoColheita(v_des_Operacao1, v_des_Unidade1, v_quantidade1, v_data_Operacao1, v_id_Parcela1, v_id_Cultura1, v_dataFinal1);

    IF v_result1 = 0 THEN
        DBMS_OUTPUT.PUT_LINE('First operation registered successfully!');
    ELSE
        DBMS_OUTPUT.PUT_LINE('Failed to register first operation.');
    END IF;
END;


