--------------------------------------------------------------------------
--TRIGGERS----------------------------------------------------------------
CREATE OR REPLACE TRIGGER registerLog
    AFTER INSERT OR UPDATE ON Operacao
    FOR EACH ROW
BEGIN
    -- Inserir registro de log
    INSERT INTO Log (idRegistoLog, idOperacao, designacaoOperacaoAgricola, designacaoUnidade, idEstadoOperacao, quantidade, dataOperacao, dadosAdicionais)
    VALUES (SYSTIMESTAMP, :NEW.idOperacao, :NEW.designacaoOperacaoAgricola, :NEW.designacaoUnidade, :NEW.idEstadoOperacao, :NEW.quantidade, :NEW.dataOperacao, null);

EXCEPTION
    WHEN OTHERS THEN
        RAISE_APPLICATION_ERROR(-20001, 'ERRO: Não foi possível registar a operação.');
END;
/
--------------------------------------------------------------------------
CREATE OR REPLACE TRIGGER updateLogOnAplicacao
    AFTER INSERT OR UPDATE ON AplicacaoFatorProducao
    FOR EACH ROW
DECLARE
    invalidRegister EXCEPTION;
BEGIN
    -- If idOperacao not found, raise an exception
    IF fncExistsRegisterInLog(:NEW.idOperacao) = 0 THEN
        RAISE invalidRegister;
    END IF;

    -- Update dadosAdicionais in Log table
    UPDATE Log
    SET dadosAdicionais = COALESCE(dadosAdicionais, 'Nome Comercial: ') || :NEW.nomeComercial || '; '
    WHERE idOperacao = :NEW.idOperacao;


EXCEPTION
    WHEN invalidRegister THEN
        RAISE_APPLICATION_ERROR(-20001, 'ERRO: Registo inválido em AplicacaoFatorProducao.');
END;
/
--------------------------------------------------------------------------
CREATE OR REPLACE TRIGGER updateLogOnPlantacaoPermanente
    AFTER INSERT OR UPDATE ON PlantacaoPermanente
    FOR EACH ROW
DECLARE
    invalidRegister EXCEPTION;
BEGIN
    -- If idOperacao not found, raise an exception
    IF fncExistsRegisterInLog(:NEW.idOperacao) = 0 THEN
        RAISE invalidRegister;
    END IF;

    -- Update dadosAdicionais in Log table
    UPDATE Log
    SET dadosAdicionais = 'Distância entre filas: ' || :NEW.distanciaEntreFilas || ' | Compasso: ' || :NEW.compasso
    WHERE idOperacao = :NEW.idOperacao;

EXCEPTION
    WHEN invalidRegister THEN
        RAISE_APPLICATION_ERROR(-20001, 'ERRO: Registo inválido em AplicacaoFatorProducao.');
END;
/
--------------------------------------------------------------------------
CREATE OR REPLACE TRIGGER updateLogOnProdutoColhido
    AFTER INSERT OR UPDATE ON ProdutoColhido
    FOR EACH ROW
DECLARE
    invalidRegister EXCEPTION;
BEGIN
    -- If idOperacao not found, raise an exception
    IF fncExistsRegisterInLog(:NEW.idOperacao) = 0 THEN
        RAISE invalidRegister;
    END IF;

    -- Update dadosAdicionais in Log table
    UPDATE Log
    SET dadosAdicionais = dadosAdicionais || 'Produto colhido: ' || :NEW.nomeProduto || ' - ' || :NEW.quantidade || ' ' || :NEW.designacaoUnidade || ' | '
    WHERE idOperacao = :NEW.idOperacao;

EXCEPTION
    WHEN invalidRegister THEN
        RAISE_APPLICATION_ERROR(-20001, 'ERRO: Registo inválido em AplicacaoFatorProducao.');
END;
/
--------------------------------------------------------------------------
CREATE OR REPLACE TRIGGER updateLogOnRega
    AFTER INSERT OR UPDATE ON Rega
    FOR EACH ROW
DECLARE
    invalidRegister EXCEPTION;
BEGIN
    -- If idOperacao not found, raise an exception
    IF fncExistsRegisterInLog(:NEW.idOperacao) = 0 THEN
        RAISE invalidRegister;
    END IF;

    -- Update dadosAdicionais in Log table
    UPDATE Log
    SET dadosAdicionais = 'Setor: ' || :NEW.designacaoSetor || ' - Duração: ' || :NEW.duracao
    WHERE idOperacao = :NEW.idOperacao;

EXCEPTION
    WHEN invalidRegister THEN
        RAISE_APPLICATION_ERROR(-20001, 'ERRO: Registo inválido em AplicacaoFatorProducao.');
END;
/
--------------------------------------------------------------------------
--FUNÇÃO------------------------------------------------------------------
create or replace NONEDITIONABLE FUNCTION fncExistsRegisterInLog(p_idOperacao in Operacao.idOperacao%TYPE)
    RETURN NUMBER
    IS v_log_count NUMBER;
BEGIN
    BEGIN
        SELECT COUNT(*)
        INTO v_log_count
        FROM Log
        WHERE idOperacao = p_idOperacao;

        RETURN v_log_count;

    EXCEPTION
        WHEN OTHERS THEN
            RAISE_APPLICATION_ERROR(-20001, 'ERRO: Não foi possível registar a operação.');
    END ;
END;
/
--------------------------------------------------------------------------
--TESTE-------------------------------------------------------------------
--Verifica se é efetuado o registo na tabela log.
SET SERVEROUTPUT ON;

DECLARE
    v_idOperacao Operacao.idOperacao%TYPE := novoIdOperacao();
    v_desigOp tipoOperacaoAgricola.DESIGNACAOOPERACAOAGRICOLA%TYPE := 'Rega';
    v_desigUnidade tipounidade.DESIGNACAOUNIDADE%TYPE := 'min';
    v_qtd NUMBER := 26;
    v_dataOp DATE := TO_DATE('29/11/2023 - 07:30', 'DD/MM/YYYY - HH24:MI');
    v_idSetor setor.IDSETOR%TYPE := 21;
    failedTest EXCEPTION;
BEGIN

    INSERT INTO Operacao(idOperacao, designacaoOperacaoAgricola, designacaoUnidade, idEstadoOperacao, quantidade, dataOperacao, instanteRegistoOperacao) VALUES (v_idOperacao, v_desigOp, v_desigUnidade, 1 , v_qtd, v_dataOp,CURRENT_TIMESTAMP);
    INSERT INTO Rega (idOperacao, designacaoSetor, duracao) VALUES (v_idOperacao, v_idSetor, v_qtd);

    IF fncExistsRegisterInLog(v_idOperacao) = 1  THEN
        DBMS_OUTPUT.PUT_LINE('TESTE PASSOU');
    ELSE
        RAISE failedTest;
    END IF;

EXCEPTION
    WHEN failedTest THEN
        DBMS_OUTPUT.PUT_LINE('TESTE FALHOU');
END;
/

