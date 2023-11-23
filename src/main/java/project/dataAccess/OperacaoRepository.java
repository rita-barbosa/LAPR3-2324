package project.dataAccess;

import oracle.jdbc.OracleTypes;
import project.domain.Operacao;
import project.domain.OperacaoCultura;
import project.domain.Rega;
import project.ui.console.utils.Utils;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
                    resultSet.getString("designacaoUnidade"),
                    resultSet.getDouble("quantidade"),
                    resultSet.getDate("dataOperacao"),
                    resultSet.getString("nomeParcela"),
                    resultSet.getString("nomeComum"),
                    resultSet.getString("variedade"),
                    resultSet.getDate("dataInicial"),
                    resultSet.getDate("dataFinal")
            );
            operacoes.add(operacao);
        }
        return operacoes;
    }

    public boolean registerSemeaduraOperation(String nomeParcela, String desigEstadoFenologico, String designacaoOperacaoAgricola, String nomeComum, String variedade, Date dataOperacao, String tipoUnidade, Double quantidade) throws SQLException {
        CallableStatement callStmt = null;
        boolean success = false;
        try {
            Connection connection = DatabaseConnection.getInstance().getConnection();
            callStmt = connection.prepareCall("{ ? = call registarOperacaoSemeadura(?,?,?,?,?,?,?,?) }");

            callStmt.registerOutParameter(1, OracleTypes.NUMBER);

            callStmt.setString(2, designacaoOperacaoAgricola);
            callStmt.setString(3, tipoUnidade);
            callStmt.setDouble(4, quantidade);

            java.sql.Date sqlDataOp = new java.sql.Date(dataOperacao.getTime());
            callStmt.setDate(5, sqlDataOp);

            callStmt.setString(6, nomeParcela);
            callStmt.setString(7, desigEstadoFenologico);
            callStmt.setString(8, nomeComum);
            callStmt.setString(9, variedade);

            callStmt.execute();

            int opStatus = callStmt.getInt(1);

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

    //VER COM O GRUPO
    public boolean registerRegaOperation(Rega rega) throws SQLException {
        CallableStatement callStmt = null;
        boolean success = false;
        try {
            Connection connection = DatabaseConnection.getInstance().getConnection();
            callStmt = connection.prepareCall("{ ? = call registarOperacaoRega(?,?,?,?) }");

            callStmt.registerOutParameter(1, OracleTypes.NUMBER);

            callStmt.setString(2, rega.getIdSetor());
            callStmt.setString(3, rega.getHoraInicio().toString());
            callStmt.setString(4, rega.getHoraFim().toString());

            Date dataRega = Date.from(rega.getData().atStartOfDay(ZoneId.systemDefault()).toInstant());
            java.sql.Date sqlDataOp = new java.sql.Date(dataRega.getTime());
            callStmt.setDate(5, sqlDataOp);

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

    public boolean verifyIfCulturaOperationExists(String nomeParcela, String designacaoOperacaoAgricola, String nomeComum, String variedade, Date dataOperacao, String tipoUnidade, Double quantidade) throws SQLException {
        CallableStatement callStmt = null;
        boolean exists = false;
        try {
            Connection connection = DatabaseConnection.getInstance().getConnection();
            callStmt = connection.prepareCall("{ ? = call verificarSeOperacaoExiste(?,?,?,?,?,?,?) }");

            callStmt.registerOutParameter(1, OracleTypes.NUMBER);

            callStmt.setString(2, designacaoOperacaoAgricola);
            callStmt.setString(3, tipoUnidade);
            callStmt.setDouble(4, quantidade);

            java.sql.Date sqlDataOp = new java.sql.Date(dataOperacao.getTime());
            callStmt.setDate(5, sqlDataOp);

            callStmt.setString(6, nomeParcela);
            callStmt.setString(7, nomeComum);
            callStmt.setString(8, variedade);

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


    //mudar para estar de acordo com o novo modelo relacional
    public void registerColheitaOperation(Integer idParcela, Integer idCultura, Date dataInicio, Date dataFim, Date dataOperacao, String tipoUnidade, Double quantidade) throws SQLException {
        CallableStatement callStmt = null;
        try {
            Connection connection = DatabaseConnection.getInstance().getConnection();
            callStmt = connection.prepareCall("{ call registarOperacaoColheita(?,?,?,?,?,?,?,?,?) }");

            int idOperacao = Utils.getNewIdOperation();

            callStmt.setInt(1, idOperacao);
            callStmt.setString(2, "Colheita");
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


    public boolean registarOperacaoMonda(String nomeParcela, String nomeComum, String variedade, Date dataOperacao, String tipoUnidade, Double quantidade) throws SQLException {
        CallableStatement callStmt = null;
        boolean success = false;
        try {
            Connection connection = DatabaseConnection.getInstance().getConnection();
            callStmt = connection.prepareCall("{ ? = call registarOperacaoMonda(?,?,?,?,?,?,?) }");

            callStmt.registerOutParameter(1, OracleTypes.NUMBER);

            callStmt.setString(2, "Monda");
            callStmt.setString(3, tipoUnidade);
            callStmt.setDouble(4, quantidade);

            java.sql.Date sqlDataOp = new java.sql.Date(dataOperacao.getTime());
            callStmt.setDate(5, sqlDataOp);

            callStmt.setString(6, nomeParcela);
            callStmt.setString(7, nomeComum);
            callStmt.setString(8, variedade);

            callStmt.execute();

            BigDecimal bigDecimalValue = (BigDecimal) callStmt.getObject(1);
            int opStatus = bigDecimalValue.intValue();

            if (opStatus == 0) {
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
}