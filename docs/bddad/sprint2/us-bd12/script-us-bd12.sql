-----------------
-----FUNÇÕES-----
-----------------
CREATE OR REPLACE FUNCTION registarOperacaoMonda(
    desigOp IN tipoOperacaoAgricola.designacaoOperacaoAgricola%TYPE,
    desigUnidade IN tipoUnidade.designacaoUnidade%TYPE,
    qtd IN NUMBER,
    dataOp IN DATE,
    nomePar IN parcela.nomeParcela%TYPE,
    nomeCom IN cultura.nomeComum%TYPE,
    vard IN cultura.variedade%TYPE)
RETURN NUMBER IS
    success NUMBER := 0;
    datas   SYS_REFCURSOR;
    dataIni DATE;
    idOp    Number;
BEGIN
    BEGIN
        datas := getdatainicialcultura(nomePar, nomeCom,vard);
        LOOP
            FETCH
                datas
            INTO
                dataIni;
            EXIT WHEN datas%notfound;
            idOp := novoIdOperacao();

            INSERT INTO operacao (idOperacao, designacaoOperacaoAgricola, designacaoUnidade, quantidade, dataOperacao)
            VALUES (idOp, desigOp, desigUnidade, qtd, dataOp);

            INSERT INTO operacaocultura (idOperacao, nomeParcela, dataInicial, nomeComum,variedade)
            VALUES (idOp, nomePar, dataIni,nomeCom,vard);
            success := 0;
        END LOOP;
    EXCEPTION
        WHEN OTHERS THEN
            success := 1;
    END;
    RETURN success;
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
CREATE OR REPLACE FUNCTION getDataInicialCultura(nomePar IN parcela.nomeParcela%TYPE,
                                                 nomeCom IN cultura.idCultura%TYPE,
                                                 vard IN cultura.variedade%TYPE)
RETURN SYS_REFCURSOR IS
    datas SYS_REFCURSOR;
BEGIN
    BEGIN
        open datas for
            select datainicial
            from culturaInstalada
            where nomeParcela = nomePar
              and nomeComum = nomeCom
              and variedade = vard;
        return datas;
    EXCEPTION
        WHEN no_data_found THEN
            return null;
    END;
END;
/