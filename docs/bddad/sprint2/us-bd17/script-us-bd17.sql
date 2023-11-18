--COMANDOS:
WITH Operacoes AS (
SELECT DISTINCT idParcela, idOperacao
    FROM (
        SELECT oc.idParcela, oc.idOperacao
        FROM OperacaoCultura oc
        UNION ALL
        SELECT opp.idParcela, opp.idOperacao
        FROM OperacaoParcela opp
    )
), Fatores AS (
    SELECT ops.idParcela as ID_PARCELA,
           cq.formulaQuimica as COMPONENTE,
           cq.quantidade as QUANTIDADE
    FROM Operacoes ops
    INNER JOIN Operacao op ON ops.idOperacao = op.idOperacao
    INNER JOIN AplicacaoFatorProducao afp ON op.idOperacao = afp.idOperacao
    INNER JOIN FatorProducao f ON afp.nomeComercial = f.nomeComercial
    INNER JOIN ConstituicaoQuimica cq ON f.idFichaTecnica = cq.idFichaTecnica
    WHERE op.dataOperacao BETWEEN TO_DATE('12/10/2010', 'DD/MM/YYYY') AND TO_DATE('26/05/2024', 'DD/MM/YYYY')
)
SELECT fs.ID_PARCELA,
    fs.COMPONENTE,
    SUM(fs.quantidade) as QUANTIDADES
FROM Fatores fs
GROUP BY fs.ID_PARCELA, fs.COMPONENTE
ORDER BY fs.ID_PARCELA;



--FUNCTION:
CREATE OR REPLACE FUNCTION fncListaComponentesFatores(listaParametros OUT SYS_REFCURSOR)
    RETURN SYS_REFCURSOR IS
    listaComponentesParcela SYS_REFCURSOR;

BEGIN
    OPEN listaComponentesParcela FOR
        WITH Operacoes AS (
            SELECT DISTINCT idParcela, idOperacao
            FROM (
                SELECT oc.idParcela, oc.idOperacao
                FROM OperacaoCultura oc
                UNION ALL
                SELECT opp.idParcela, opp.idOperacao
                FROM OperacaoParcela opp
            )
        ), Fatores AS (
            SELECT ops.idParcela as ID_PARCELA,
                   cq.formulaQuimica as COMPONENTE,
                   cq.quantidade as QUANTIDADE
            FROM Operacoes ops
            INNER JOIN Operacao op ON ops.idOperacao = op.idOperacao
            INNER JOIN AplicacaoFatorProducao afp ON op.idOperacao = afp.idOperacao
            INNER JOIN FatorProducao f ON afp.nomeComercial = f.nomeComercial
            INNER JOIN ConstituicaoQuimica cq ON f.idFichaTecnica = cq.idFichaTecnica
            WHERE op.dataOperacao BETWEEN TO_DATE('12/10/2010', 'DD/MM/YYYY') AND TO_DATE('26/05/2024', 'DD/MM/YYYY')
        )
        SELECT fs.ID_PARCELA,
               fs.COMPONENTE,
               SUM(fs.quantidade) as QUANTIDADES
        FROM Fatores fs
        GROUP BY fs.ID_PARCELA, fs.COMPONENTE
        ORDER BY fs.ID_PARCELA;

    RETURN listaComponentesParcela;
END;


SET SERVEROUTPUT  ON;

--DECLARE:
DECLARE
    aplicacaoFatorProducaoInexistente EXCEPTION;
    listaComponentesParcela SYS_REFCURSOR;
    v_id_Parcela OperacaoParcela.idParcela%type;
    v_componente ConstituicaoQuimica.formulaQuimica%type;
    v_quantidade ConstituicaoQuimica.quantidade%type;
BEGIN
    listaComponentesParcela := fncListaComponentesFatores(listaComponentesParcela);

    FETCH listaComponentesParcela INTO v_id_Parcela, v_componente, v_quantidade;

    IF listaComponentesParcela%ROWCOUNT = 0 THEN
        RAISE  aplicacaoFatorProducaoInexistente;
    ELSE
        LOOP
            DBMS_OUTPUT.PUT_LINE(v_id_Parcela || ' - ' || v_componente || ' - ' || v_quantidade);
            FETCH listaComponentesParcela
                INTO v_id_Parcela, v_componente, v_quantidade;
            EXIT WHEN  listaComponentesParcela%NOTFOUND;
        END LOOP;
    END IF;
    CLOSE listaComponentesParcela;

EXCEPTION
    WHEN aplicacaoFatorProducaoInexistente THEN
        RAISE_APPLICATION_ERROR(-20001, 'Não existem aplicações de fator produção no período indicado.');
END;
