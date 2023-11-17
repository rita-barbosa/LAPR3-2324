package project.domain.dataAccess;

import oracle.jdbc.OracleTypes;
import project.domain.Operacao;
import project.domain.OperacaoCultura;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.util.Objects;

public class OperacaoRepository {

    public OperacaoRepository() {
    }

    public List<Operacao> getOperationsList() throws SQLException {
        CallableStatement callStmt = null;
        ResultSet resultSet = null;
        List<Operacao> operacoes;
        try {
            Connection connection = DatabaseConnection.getInstance().getConnection();
            callStmt = connection.prepareCall("{ ? = call mostrarListaOperacao() }");

            callStmt.registerOutParameter(1, OracleTypes.CURSOR);

            callStmt.execute();

            resultSet = (ResultSet) callStmt.getObject(1);

            operacoes = resultSetToOperationList(resultSet);
        } finally {
            if (!Objects.isNull(callStmt)) {
                callStmt.close();
            }
            if (!Objects.isNull(resultSet)) {
                resultSet.close();
            }
        }
        return operacoes;
    }

    public List<OperacaoCultura> getSemeaduraOperationsList() throws SQLException {
        CallableStatement callStmt = null;
        ResultSet resultSet = null;
        List<OperacaoCultura> operacoes;
        try {
            Connection connection = DatabaseConnection.getInstance().getConnection();
            callStmt = connection.prepareCall("{ ? = call mostrarOperacaoSemeadura() }");

            callStmt.registerOutParameter(1, OracleTypes.CURSOR);

            callStmt.execute();

            resultSet = (ResultSet) callStmt.getObject(1);

            operacoes = resultSetToCultureOperationList(resultSet);
        } finally {
            if (!Objects.isNull(callStmt)) {
                callStmt.close();
            }
            if (!Objects.isNull(resultSet)) {
                resultSet.close();
            }
        }
        return operacoes;
    }

    private List<Operacao> resultSetToOperationList(ResultSet resultSet) throws SQLException {
        List<Operacao> operacoes = new ArrayList<>();
                        while (true) {
                    if (!resultSet.next()) break;
                    Operacao operacao = new Operacao(
                            resultSet.getInt("idOperacao"),
                            resultSet.getString("designacaoOperacaoAgricola"),
                            resultSet.getString("designacaoUnidade"),
                            resultSet.getDouble("quantidade"),
                            resultSet.getDate("dataOperacao")
                    );
                    operacoes.add(operacao);
                }
        return operacoes;
    }


    private List<OperacaoCultura> resultSetToCultureOperationList(ResultSet resultSet) throws SQLException {
        List<OperacaoCultura> operacoes = new ArrayList<>();
        while (true) {
            if (!resultSet.next()) break;
            OperacaoCultura operacao = new OperacaoCultura(
                    resultSet.getInt("idOperacao"),
                    resultSet.getString("designacaoOperacaoAgricola"),
                    resultSet.getDate("dataOperacao"),
                    resultSet.getInt("idParcela"),
                    resultSet.getInt("idCultura"),
                    resultSet.getDate("dataInicial"),
                    resultSet.getDate("dataFinal"),
                    resultSet.getString("designacaoUnidade"),
                    resultSet.getDouble("quantidade")
            );
            operacoes.add(operacao);
        }
        return operacoes;
    }

    public void registerSemeaduraOperation(Integer idParcela, Integer idCultura, Date dataInicio, Date dataFim, Date dataOperacao, String tipoUnidade, Double quantidade) throws SQLException {
        CallableStatement callStmt = null;
        try {
            Connection connection = DatabaseConnection.getInstance().getConnection();
            callStmt = connection.prepareCall("{ call registarOperacaoSemeadura(?,?,?,?,?,?,?,?,?) }");

            int idOperacao = getNewIdOperation();

            callStmt.setInt(1, idOperacao);
            callStmt.setString(2, "Semeadura");
            callStmt.setString(3, tipoUnidade);
            callStmt.setDouble(4, quantidade);

            java.sql.Date sqlDataOp = new java.sql.Date(dataOperacao.getTime());
            callStmt.setDate(5, sqlDataOp);

            callStmt.setInt(6, idParcela);
            callStmt.setInt(7, idCultura);

            java.sql.Date sqlDataInicio = new java.sql.Date(dataInicio.getTime());
            callStmt.setDate(8, sqlDataInicio);

            java.sql.Date sqlDataFim = new java.sql.Date(dataFim.getTime());
            callStmt.setDate(9, sqlDataFim);

            callStmt.execute();
            connection.commit();
        } finally {
            if (!Objects.isNull(callStmt)) {
                callStmt.close();
            }
        }
    }

    private int getNewIdOperation() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}