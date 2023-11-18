package project.domain.dataAccess;

import oracle.jdbc.OracleTypes;
import project.domain.Operacao;
import project.domain.OperacaoCultura;
import project.ui.console.utils.Utils;

import java.math.BigDecimal;
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

    public boolean registerCultureOperation(Integer idParcela, String designacaoOperacaoAgricola, Integer idCultura, Date dataOperacao, String tipoUnidade, Double quantidade) throws SQLException {
        CallableStatement callStmt = null;
        boolean success = false;
        try {
            Connection connection = DatabaseConnection.getInstance().getConnection();
            callStmt = connection.prepareCall("{ ? = call registarOperacaoSemeadura(?,?,?,?,?,?,?,?) }");

            int idOperacao = Utils.getNewIdOperation();

            callStmt.registerOutParameter(1, OracleTypes.NUMBER);

            callStmt.setInt(2, idOperacao);
            callStmt.setString(3, designacaoOperacaoAgricola);
            callStmt.setString(4, tipoUnidade);
            callStmt.setDouble(5, quantidade);

            java.sql.Date sqlDataOp = new java.sql.Date(dataOperacao.getTime());
            callStmt.setDate(6, sqlDataOp);

            callStmt.setInt(7, idParcela);
            callStmt.setInt(8, idCultura);

            callStmt.setDate(9, sqlDataOp);

            callStmt.execute();

            BigDecimal bigDecimalValue = (BigDecimal) callStmt.getObject(1);
            int opStatus = bigDecimalValue.intValue();

            if (opStatus == 1) {
                success = true;
            }

            connection.commit();

        } finally {
            if (!Objects.isNull(callStmt)) {
                callStmt.close();
            }
        }
        return success;
    }

    public boolean verifyIfOperationExists(Integer idParcela, String designacaoOperacaoAgricola, Integer idCultura, Date dataOperacao, String tipoUnidade, Double quantidade) throws SQLException {
        CallableStatement callStmt = null;
        boolean exists = false;
        try {
            Connection connection = DatabaseConnection.getInstance().getConnection();
            callStmt = connection.prepareCall("{ ? = call verificarSeOperacaoExiste(?,?,?,?,?,?) }");

            callStmt.registerOutParameter(1, OracleTypes.NUMBER);

            callStmt.setString(2, designacaoOperacaoAgricola);
            callStmt.setString(3, tipoUnidade);
            callStmt.setDouble(4, quantidade);

            java.sql.Date sqlDataOp = new java.sql.Date(dataOperacao.getTime());
            callStmt.setDate(5, sqlDataOp);

            callStmt.setInt(6, idParcela);
            callStmt.setInt(7, idCultura);

            callStmt.execute();

            BigDecimal bigDecimalValue = (BigDecimal) callStmt.getObject(1);
            int foundOp = bigDecimalValue.intValue();

            if (foundOp == 1) {
                exists = true;
            }

            connection.commit();

        } finally {
            if (!Objects.isNull(callStmt)) {
                callStmt.close();
            }
        }
        return exists;
    }
}