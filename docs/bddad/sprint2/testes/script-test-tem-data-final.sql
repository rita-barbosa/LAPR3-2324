-----------------------------------------------
--FUNÇÃO---------------------------------------
CREATE OR REPLACE NONEDITIONABLE FUNCTION temDataFinal(nomePar IN parcela.nomeParcela%TYPE,
                                                       nomeCom IN culturaInstalada.nomeComum%TYPE,
                                                       vard IN culturaInstalada.variedade%TYPE,
                                                       dataIni IN culturaInstalada.dataInicial%TYPE)
    RETURN BOOLEAN IS
    dataF CulturaInstalada.dataFinal%TYPE;
BEGIN
    BEGIN
        SELECT dataFinal INTO dataF
        FROM culturaInstalada
        WHERE nomeParcela = nomePar
          AND nomeComum = nomeCom
          AND variedade = vard
          AND dataInicial = dataIni;

        IF dataF IS NULL THEN
            RETURN FALSE;
        ELSE
            RETURN TRUE;
        END IF;
    EXCEPTION
        WHEN OTHERS THEN
            RETURN FALSE;
    END;
END;
--------------------------------------------------------------------------
--TESTE-------------------------------------------------------------------
--VERIFICA QUE PARA CULTURA INSTALADA QUE NAO TEM DATA FINAL RETORNA FALSO
DECLARE
    test_dataInicial DATE := TO_DATE('12/10/2023 - 00:00', 'DD/MM/YYYY - HH24:MI');
    test_nomeParcela parcela.nomeParcela%TYPE := 'Campo Novo';
    test_nomeComum planta.nomeComum%TYPE := 'Tremoço';
    test_variedade planta.variedade%TYPE := 'AMARELO';
    success BOOLEAN;
    failedTest EXCEPTION;
BEGIN
    success := temDataFinal( test_nomeParcela, test_nomeComum, test_variedade,test_dataInicial);

    IF NOT success THEN
        DBMS_OUTPUT.PUT_LINE('TESTE PASSOU');
    ELSE
        RAISE failedTest;
    END IF;

EXCEPTION
    WHEN failedTest THEN
        DBMS_OUTPUT.PUT_LINE('TESTE FALHOU');
END;
/
--TESTE--------------------------------------------------------------------
--VERIFICA QUE PARA CULTURA INSTALADA QUE TEM DATA FINAL RETORNA VERDADEIRO
DECLARE
    test_dataInicial DATE := TO_DATE('10/10/2020 - 00:00', 'DD/MM/YYYY - HH24:MI');
    test_nomeParcela parcela.nomeParcela%TYPE := 'Campo da bouça';
    test_nomeComum planta.nomeComum%TYPE := 'Tremoço';
    test_variedade planta.variedade%TYPE := 'AMARELO';
    success BOOLEAN;
    failedTest EXCEPTION;
BEGIN
    success := temDataFinal(test_nomeParcela, test_nomeComum, test_variedade,test_dataInicial);

    IF success = TRUE THEN
        DBMS_OUTPUT.PUT_LINE('TESTE PASSOU');
    ELSE
        RAISE failedTest;
    END IF;

EXCEPTION
    WHEN failedTest THEN
        DBMS_OUTPUT.PUT_LINE('TESTE FALHOU');
END;
/