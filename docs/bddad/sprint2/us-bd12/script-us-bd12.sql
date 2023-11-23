---------------------------------------------------
----------------------FUNÇÕES----------------------
---------------------------------------------------
create or replace NONEDITIONABLE FUNCTION registarOperacaoMonda(desigOp IN tipoOperacaoAgricola.designacaoOperacaoAgricola%TYPE,
                                                                desigUnidade IN tipoUnidade.designacaoUnidade%TYPE,
                                                                qtd IN NUMBER,
                                                                dataOp IN DATE,
                                                                nomePar IN parcela.nomeParcela%TYPE,
                                                                nomeCom IN planta.nomeComum%TYPE,
                                                                vard IN planta.variedade%TYPE)
    RETURN NUMBER IS
    success NUMBER := 1;
    datas   SYS_REFCURSOR;
    dataIni DATE;
    idOp    Number;
    invalidOperation exception;
    invalidArea exception;
    invalidCulture exception;
BEGIN
    BEGIN
        SAVEPOINT antesRegisto;
        IF( verificarSeOperacaoExiste('Monda', desigUnidade, qtd,dataOp,nomePar,nomeCom,vard) = 0) THEN
            datas := getdatainicialcultura(nomePar, nomeCom,vard);
            LOOP
                FETCH
                    datas
                    INTO
                    dataIni;
                EXIT WHEN datas%notfound;

                IF((obterAreaPlantada(nomePar,nomeCom,vard,dataIni) > qtd)) THEN
                    IF (temDataFinal(nomePar,nomeCom,vard,dataIni)) THEN
                        RAISE invalidCulture;
                    ELSE
                        idOp := novoIdOperacao();

                        INSERT INTO operacao (idOperacao, designacaoOperacaoAgricola, designacaoUnidade, quantidade, dataOperacao)
                        VALUES (idOp, desigOp, desigUnidade, qtd, dataOp);

                        INSERT INTO operacaocultura (idOperacao, nomeParcela, dataInicial, nomeComum,variedade)
                        VALUES (idOp, nomePar, dataIni,nomeCom,vard);

                        success := 0;
                    END IF;
                ELSE
                    RAISE invalidArea;
                END IF;
            END LOOP;
        ELSE
            RAISE invalidOperation;
        END IF;

        IF( success = 1) THEN
            ROLLBACK TO SAVEPOINT antesRegisto;
        ELSE
            COMMIT;
        END IF;

    EXCEPTION
        WHEN invalidCulture THEN
            ROLLBACK TO SAVEPOINT antesRegisto;
            RAISE_APPLICATION_ERROR(-20001, 'ERRO: A cultura selecionada não se encontra na parcela.');
        WHEN invalidArea THEN
            ROLLBACK TO SAVEPOINT antesRegisto;
            RAISE_APPLICATION_ERROR(-20001, 'ERRO: Não é possível mondar a quantidade pretendida.');
        WHEN invalidOperation THEN
            ROLLBACK TO SAVEPOINT antesRegisto;
            RAISE_APPLICATION_ERROR(-20001, 'ERRO: Os dados introduzidos já existem no sistema.');
        WHEN OTHERS THEN
            ROLLBACK TO SAVEPOINT antesRegisto;
            success := 1;

    END;
    RETURN success;
END;
/
----------------------------------------------------
create or replace FUNCTION verificarSeOperacaoExiste(
    desigOp IN tipoOperacaoAgricola.designacaoOperacaoAgricola%TYPE,
    desigUnidade IN tipoUnidade.designacaoUnidade%TYPE,
    qtd IN NUMBER,
    dataOp IN DATE,
    nomeParcela IN parcela.nomeParcela%TYPE,
    nomeComum IN culturainstalada.nomeComum%TYPE,
    variedade IN culturainstalada.variedade%TYPE
) RETURN NUMBER IS
    found NUMBER := 0;
BEGIN
    BEGIN
        SELECT NVL(COUNT(*), 0) INTO found
        FROM OPERACAO op
                 INNER JOIN operacaoCultura cul ON op.idOperacao = cul.idOperacao
        WHERE op.designacaooperacaoagricola = desigOp
          AND op.dataoperacao = dataOp
          AND op.designacaounidade = desigUnidade
          AND op.quantidade = qtd
          AND cul.nomeComum = nomeComum
          AND cul.variedade = variedade
          AND cul.nomeParcela = nomeParcela;
    EXCEPTION
        WHEN OTHERS THEN
            found := 0;
    END;
    RETURN found;
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
                                                 nomeCom IN planta.nomeComum%TYPE,
                                                 vard IN planta.variedade%TYPE)
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
----------------------------------------------------
create or replace NONEDITIONABLE FUNCTION obterAreaPlantada(nomePar IN parcela.nomeParcela%TYPE,
                                                            nomeCom IN culturaInstalada.nomeComum%TYPE,
                                                            vard IN culturaInstalada.variedade%TYPE,
                                                            dataIni IN culturaInstalada.dataInicial%TYPE)
    RETURN NUMBER IS
    areaPlantada culturaInstalada.quantidade%TYPE;

BEGIN

    BEGIN
        SELECT quantidade INTO areaPlantada
        FROM culturaInstalada
        WHERE nomeParcela LIKE nomePar
          AND nomeComum LIKE nomeCom
          AND variedade LIKE vard
          AND dataInicial LIKE dataIni;

        RETURN areaPlantada;
    EXCEPTION
        WHEN NO_DATA_FOUND THEN
            RETURN -1;
        WHEN OTHERS THEN
            RETURN null;
    END;
END;
/
----------------------------------------------------
CREATE OR REPLACE FUNCTION
    (nomePar IN parcela.nomeParcela%TYPE,
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

        if dataF is null then
            return false;
        else
            RETURN TRUE;
        end if;
    EXCEPTION
        WHEN OTHERS THEN
            RETURN FALSE;
    END;
END;
/
------------------------------------------------
------------------BLOCO ANÓNIMO-----------------
------------------------------------------------
SET SERVEROUTPUT ON;

DECLARE
    v_desigOp tipoOperacaoAgricola.designacaoOperacaoAgricola%TYPE := 'Monda';
    v_desigUnidade tipoUnidade.DESIGNACAOUNIDADE%TYPE := 'ha';
    v_qtd NUMBER := 0.7;
    v_dataOp DATE := TO_DATE('22/11/2023 - 23:20', 'DD/MM/YYYY - HH24:MI');
    v_nomeParcela parcela.nomeParcela%TYPE := 'Campo Novo';
    v_nomeComum planta.nomeComum%TYPE := 'Tremoço';
    v_variedade planta.variedade%TYPE := 'AMARELO';


    v_success NUMBER;
BEGIN
    v_success := registarOperacaoMonda(v_desigOp, v_desigUnidade, v_qtd, v_dataOp, v_nomeParcela, v_nomeComum, v_variedade);

    IF v_success = 0 THEN
        DBMS_OUTPUT.PUT_LINE('Operation registered successfully!');
    ELSE
        DBMS_OUTPUT.PUT_LINE('Failed to register operation.');
    END IF;
END;
/

------------------------------------------------
-------------------CONFIRMAÇÃO------------------
------------------------------------------------
select *
from  operacaoCultura oc
          inner join Operacao o
                     on oc.idoperacao = o.idoperacao
order by dataoperacao;
--||----||----||----||----||----||----||----||--
select * from culturaInstalada;
--||----||----||----||----||----||----||----||--