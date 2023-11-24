SET SERVEROUTPUT ON;
-------Função em si--------
CREATE OR REPLACE PROCEDURE RegistoAplicacaoFatorProducao(
    desigOp IN tipoOperacaoAgricola.designacaoOperacaoAgricola%TYPE,
    desigUnidade IN tipoUnidade.designacaoUnidade%TYPE,
    qtd IN NUMBER,
    dataOp IN DATE,
    nmFator IN aplicacaofatorproducao.nomecomercial%TYPE
) IS
    invalidOperacao EXCEPTION;
    invalidInputs EXCEPTION;
    idOp NUMBER;
BEGIN
    SAVEPOINT antesDoRegisto;
    idOp := novoIdOperacao();

    IF NOT verificarDadosDeInsercao(desigOp, desigUnidade, qtd, dataOp, nmFator) THEN
        RAISE invalidInputs;
    ELSE
        IF verificarSeOperacaoExiste(desigOp, desigUnidade, qtd, dataOp, nmFator) = 0 THEN
            BEGIN
                INSERT INTO operacao (idOperacao, designacaoOperacaoAgricola, designacaoUnidade, quantidade, dataOperacao)
                VALUES (idOp, desigOp, desigUnidade, qtd, dataOp);

                IF SQL%ROWCOUNT > 0 THEN
                    DBMS_OUTPUT.PUT_LINE('Operação de aplicação do fator de produção inserida com sucesso.(1/2)');
                ELSE
                    RAISE_APPLICATION_ERROR(-20001, 'Erro durante a inserção.');
                END IF;

                INSERT INTO aplicacaofatorproducao (nomecomercial, idoperacao)
                VALUES (nmFator, idOp);

                IF SQL%ROWCOUNT > 0 THEN
                    DBMS_OUTPUT.PUT_LINE('Operação de aplicação do fator de produção inserida com sucesso.(2/2)');
                ELSE
                    RAISE_APPLICATION_ERROR(-20001, 'Erro durante a inserção.');
                END IF;
            EXCEPTION
                WHEN OTHERS THEN
                    RAISE_APPLICATION_ERROR(-20001, 'Erro durante a inserção.');
            END;
        ELSE
            RAISE invalidOperacao;
        END IF;
    END IF;
--------Exceções---------
EXCEPTION
    WHEN invalidOperacao THEN
        RAISE_APPLICATION_ERROR(-20001, 'ERRO: A operação já existe na base de dados.');
        ROLLBACK TO SAVEPOINT antesDoRegisto;
    WHEN invalidInputs THEN
        RAISE_APPLICATION_ERROR(-20001, 'ERRO: Os inputs estão incorretos.');
        ROLLBACK TO SAVEPOINT antesDoRegisto;
END;
/
--------Verificação se a Operação já existia--------
CREATE OR REPLACE FUNCTION verificarSeOperacaoExiste(
    desigOp IN tipoOperacaoAgricola.designacaoOperacaoAgricola%TYPE,
    desigUnidade IN tipoUnidade.designacaoUnidade%TYPE,
    qtd IN NUMBER,
    dataOp IN DATE,
    nmFator IN aplicacaofatorproducao.nomecomercial%TYPE
) RETURN NUMBER IS
    found NUMBER := 0;
BEGIN
    SELECT COUNT(*)
    INTO found
    FROM operacao op
         INNER JOIN aplicacaofatorproducao afp ON op.idOperacao = afp.idOperacao
    WHERE op.designacaooperacaoagricola = desigOp
      AND op.dataoperacao = dataOp
      AND op.designacaounidade = desigUnidade
      AND op.quantidade = qtd
      AND afp.nomeComercial = nmFator;

    RETURN found;
EXCEPTION
    WHEN OTHERS THEN
        RETURN 0;
END;
/
--------Verificação dos Inputs---------
CREATE OR REPLACE FUNCTION verificarDadosDeInsercao(
    desigOp IN tipoOperacaoAgricola.designacaoOperacaoAgricola%TYPE,
    desigUnidade IN tipoUnidade.designacaoUnidade%TYPE,
    qtd IN NUMBER,
    dataOp IN DATE,
    nmFator IN aplicacaofatorproducao.nomecomercial%TYPE
) RETURN BOOLEAN IS
    valid BOOLEAN;
BEGIN
    IF desigOp IN ('Fertilização', 'Aplicação fitofármaco', 'Aplicação de fator de produção') THEN
        IF desigUnidade = 'kg' THEN
            IF qtd < 100 THEN
                valid := TRUE;
            END IF;
        END IF;
    ELSE
        valid := FALSE;
    END IF;
    RETURN valid;
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
--------Bloco Anónimo de Teste(Válido)--------
 DECLARE
     desigOp tipoOperacaoAgricola.designacaoOperacaoAgricola%TYPE := 'Aplicação fitofármaco';
     desigUnidade tipoUnidade.designacaoUnidade%TYPE := 'kg';
     qtd NUMBER := 1;
     dataOp DATE := TO_DATE('23/11/2024', 'DD/MM/YYYY');
     nmFator aplicacaofatorproducao.nomecomercial%TYPE := 'BIOFERTIL N6';
 BEGIN
     RegistoAplicacaoFatorProducao(desigOp, desigUnidade, qtd, dataOp, nmFator);
 END;
/
--------Bloco Anónimo de Teste(Inválido)--------
 DECLARE
     desigOp tipoOperacaoAgricola.designacaoOperacaoAgricola%TYPE := 'Aplicação fitoco';
     desigUnidade tipoUnidade.designacaoUnidade%TYPE := 'kg';
     qtd NUMBER := 1;
     dataOp DATE := TO_DATE('23/11/2024', 'DD/MM/YYYY');
     nmFator aplicacaofatorproducao.nomecomercial%TYPE := 'BIOFERTIL N6';
 BEGIN
     RegistoAplicacaoFatorProducao(desigOp, desigUnidade, qtd, dataOp, nmFator);
 END;
/
--------Bloco Anónimo de Teste(Inválido)--------
 DECLARE
     desigOp tipoOperacaoAgricola.designacaoOperacaoAgricola%TYPE := 'Aplicação fitofármaco';
     desigUnidade tipoUnidade.designacaoUnidade%TYPE := 'ha';
     qtd NUMBER := 1;
     dataOp DATE := TO_DATE('23/11/2024', 'DD/MM/YYYY');
     nmFator aplicacaofatorproducao.nomecomercial%TYPE := 'BIOFERTIL N6';
 BEGIN
     RegistoAplicacaoFatorProducao(desigOp, desigUnidade, qtd, dataOp, nmFator);
 END;
/
--------Bloco Anónimo de Teste(Inválido)--------
 DECLARE
     desigOp tipoOperacaoAgricola.designacaoOperacaoAgricola%TYPE := 'Aplicação fitofármaco';
     desigUnidade tipoUnidade.designacaoUnidade%TYPE := 'kg';
     qtd NUMBER := 13444;
     dataOp DATE := TO_DATE('23/11/2024', 'DD/MM/YYYY');
     nmFator aplicacaofatorproducao.nomecomercial%TYPE := 'BIOFERTIL N6';
 BEGIN
     RegistoAplicacaoFatorProducao(desigOp, desigUnidade, qtd, dataOp, nmFator);
 END;
/