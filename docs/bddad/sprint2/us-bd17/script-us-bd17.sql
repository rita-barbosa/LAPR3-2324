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
    WHERE OPS.idParcela = p_id_Parcela AND
    op.dataOperacao BETWEEN p_data_inicial AND p_data_final
)
SELECT fs.ID_PARCELA,
    fs.COMPONENTE,
    SUM(fs.quantidade) as QUANTIDADES
FROM Fatores fs
GROUP BY fs.ID_PARCELA, fs.COMPONENTE
ORDER BY fs.ID_PARCELA;



--FUNCTION:
CREATE OR REPLACE FUNCTION listaComponentesFatores(p_id_parcela Parcela.idParcela%type,
                                              	   p_data_inicial Operacao.dataOperacao%type,
                                              	   p_data_final Operacao.dataOperacao%type)
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
            WHERE OPS.idParcela = p_id_Parcela AND
            op.dataOperacao BETWEEN p_data_inicial AND p_data_final
        )
        SELECT fs.ID_PARCELA,
               fs.COMPONENTE,
               SUM(fs.quantidade) as QUANTIDADES
        FROM Fatores fs
        GROUP BY fs.ID_PARCELA, fs.COMPONENTE
        ORDER BY fs.ID_PARCELA;

    RETURN listaComponentesParcela;
END;



--DECLARE:
DECLARE
    listaComponentesParcela SYS_REFCURSOR;
    ID_PARCELA OperacaoPArcela.idParcela%type;
    COMPONENTE ConstituicaoQuimica.formulaQuimica%type;
    QUANTIDADES ConstituicaoQuimica.quantidade%type;
BEGIN
    listaComponentesParcela := listaComponentesFatores(102,TO_DATE('2016-01-01', 'YYYY-MM-DD'),TO_DATE('2024-12-31', 'YYYY-MM-DD'));
    LOOP
        FETCH listaComponentesParcela
            INTO ID_PARCELA, COMPONENTE, QUANTIDADES;
        EXIT WHEN  listaComponentesParcela%notfound;

        dbms_output.put_line(ID_PARCELA || ' - ' || COMPONENTE || ' - ' || QUANTIDADES);
    END LOOP;
    CLOSE listaComponentesParcela;
END;
