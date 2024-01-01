SET SERVEROUTPUT ON;
-------Função em si--------
CREATE OR REPLACE FUNCTION registarFertirrega(tmp IN NUMBER,
                                                         dataOp IN VARCHAR2,
                                                         horaCm IN VARCHAR2,
                                                         idStr IN setor.designacaosetor%TYPE,
                                                         idMx IN receitafertirrega.idreceitafertirrega%TYPE
) RETURN NUMBER IS
    resultado NUMBER := 0;
    a NUMBER;
    idOp NUMBER;
    c_quantidade NUMBER;
    c_nomeComum planorega.nomecomum%TYPE;
    c_variedade planorega.variedade%TYPE;
    c_parcela planorega.nomeParcela%TYPE;
    c_area parcela.area%TYPE;
    c_nomeFator fatorproducao.nomecomercial%TYPE;
    invalidOperacao EXCEPTION;
    cursorDados SYS_REFCURSOR;
    fatoresProducao SYS_REFCURSOR;
BEGIN
    BEGIN
        IF verificarSeReceitaFertirregaExiste(idMx) != 0 THEN
            BEGIN
                    idOp := novoIdOperacao();
                    INSERT INTO Operacao (idOperacao, designacaoOperacaoAgricola, designacaoUnidade, idEstadoOperacao, quantidade, dataOperacao)
                    VALUES (idOp, 'Fertirrega', 'min', 0 , tmp, TO_DATE(dataOp||' - '||horaCm, 'DD/MM/YYYY - HH24:MI'));

                    cursorDados := obterFatoresProducao(idMx);

                    LOOP
                        FETCH cursorDados INTO c_nomeFator, c_quantidade;
                        EXIT WHEN cursorDados%notfound;
                        INSERT INTO aplicacaofatorproducao (nomecomercial, idoperacao)
                        VALUES (c_nomeFator, idOp);
                    END LOOP;

                    CLOSE cursorDados;

                    INSERT INTO Rega (idOperacao, designacaoSetor)
                    VALUES (idOp, idStr);

                    cursorDados := obterDados(idStr);
                    << outer_loop >>
                    LOOP
                        FETCH cursorDados INTO c_variedade, c_nomeComum, c_parcela, c_area;
                        EXIT WHEN cursorDados%notfound;

                        DBMS_OUTPUT.PUT_LINE('#Operação ' || idOp);
                        DBMS_OUTPUT.PUT_LINE('- Rega em cultura de ' || c_variedade || ' ' || c_nomeComum || ', localizada no ' || c_parcela || ', ' || tmp || ' min');
                        DBMS_OUTPUT.PUT_LINE('- Aplicação de fator de produção em cultura de ' || c_variedade || ' ' || c_nomeComum || ', localizada no ' || c_parcela || ' (' || c_area || ' ha de área)');

                        fatoresProducao := obterFatoresProducao(idMx);
                        << inner_loop >>
                        LOOP
                            FETCH fatoresProducao INTO c_nomeFator, c_quantidade;
                            EXIT WHEN fatoresProducao%notfound;

                            a := c_quantidade * c_area;
                            DBMS_OUTPUT.PUT_LINE('- ' || a || ' l ' || c_nomeFator);
                        END LOOP inner_loop;

                        CLOSE fatoresProducao;
                    END LOOP outer_loop;

                    CLOSE cursorDados;

            END;
        ELSE
            RAISE invalidOperacao;
        END IF;

    END;
RETURN resultado;
---------Exceções-----------------------------------
EXCEPTION
    WHEN invalidOperacao THEN
        resultado := 1;
        DBMS_OUTPUT.PUT_LINE('ERRO: O MIX ESCOLHIDO NÃO EXISTE NO SISTEMA.');
        RETURN resultado;
END;
/
---------Obtém os fatores de produção relacionados com a receita--------------------------------------------------
CREATE OR REPLACE FUNCTION obterFatoresProducao(idMx IN receitafertirrega.idreceitafertirrega%TYPE
)
RETURN SYS_REFCURSOR IS
    listaAplicacaoFatoresProducao SYS_REFCURSOR;
BEGIN
    OPEN listaAplicacaoFatoresProducao FOR
        SELECT catalogoreceitafertirrega.nomecomercial, catalogoreceitafertirrega.quantidade
        FROM catalogoreceitafertirrega
        WHERE catalogoreceitafertirrega.idreceitafertirrega = idMx;
    RETURN listaAplicacaoFatoresProducao;
END;
/
--------------------------------------------------------------------------------
CREATE OR REPLACE FUNCTION obterDados(
    idStr IN setor.designacaoSetor%TYPE
) RETURN SYS_REFCURSOR IS
    lista SYS_REFCURSOR;
BEGIN
    OPEN lista FOR
        SELECT pr.variedade, pr.nomecomum, pr.nomeParcela, p.area
        FROM Planorega pr
        INNER JOIN parcela p ON p.nomeParcela = pr.nomeParcela
        WHERE pr.designacaoSetor = idStr;
    RETURN lista;
END;
/
--------Verificação se a receita existe--------
CREATE OR REPLACE FUNCTION verificarSeReceitaFertirregaExiste(idMx IN receitafertirrega.idreceitafertirrega%TYPE
) RETURN NUMBER IS
    found NUMBER := 0;
BEGIN
    SELECT COUNT(*) INTO found
    FROM receitafertirrega
        WHERE receitafertirrega.idreceitafertirrega = idMx;
    RETURN found;
EXCEPTION
    WHEN OTHERS THEN
        RETURN 0;
END;
/
-----Criação de um novo id válido-----
CREATE OR REPLACE FUNCTION novoIdOperacao RETURN NUMBER AS
    newIdOperation NUMBER;
BEGIN
    SELECT NVL(MAX(idOperacao), 0) + 1 INTO newIdOperation FROM operacao;
    RETURN newIdOperation;
END;
/
-------------TESTE-VÁLIDO-------------------------------
DECLARE
     qtdTESTE NUMBER := 90;
     dataOpTESTE VARCHAR2(10) := '02/09/2023';
     tmTESTE VARCHAR2(5) := '05:00';
     idSetorTESTE NUMBER := 10;
     idMixTESTE receitafertirrega.idreceitafertirrega%TYPE := 10;
     resultado NUMBER;
 BEGIN
    resultado := RegistarFertirrega(qtdTESTE,dataOpTESTE,tmTESTE,idSetorTESTE,idMixTESTE);
     IF(resultado = 1)THEN
        DBMS_OUTPUT.PUT_LINE('TESTE NÃO PASSADO.');
     ELSE
        DBMS_OUTPUT.PUT_LINE('TESTE PASSADO.');
     END IF;
 END;
 /
 -------------TESTE-VÁLIDO-------------------------------
DECLARE
     qtdTESTE NUMBER := 90;
     dataOpTESTE VARCHAR2(10) := '02/09/2023';
     tmTESTE VARCHAR2(5) := '05:00';
     idSetorTESTE NUMBER := 10;
     idMixTESTE receitafertirrega.idreceitafertirrega%TYPE := 50;
     resultado NUMBER;
 BEGIN
    resultado := RegistarFertirrega(qtdTESTE,dataOpTESTE,tmTESTE,idSetorTESTE,idMixTESTE);
     IF(resultado = 0)THEN
        DBMS_OUTPUT.PUT_LINE('TESTE NÃO PASSADO.');
     ELSE
        DBMS_OUTPUT.PUT_LINE('TESTE PASSADO.');
     END IF;
 END;
 /