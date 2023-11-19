package project.dataAccess;

import oracle.jdbc.OracleTypes;
import project.ui.console.utils.Utils;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class CultureRepository {
    public Map<BigDecimal, String> getCultures() throws SQLException {
        CallableStatement callStmt = null;
        ResultSet resultSet = null;
        Map<BigDecimal, String> map = new HashMap<>();

        try {
            Connection connection = DatabaseConnection.getInstance().getConnection();
            callStmt = connection.prepareCall("{ ? = call obterIntroCulturas() }");

            callStmt.registerOutParameter(1, OracleTypes.CURSOR);

            callStmt.execute();
            resultSet = (ResultSet) callStmt.getObject(1);

            Utils.resultSetMapToList(map, resultSet);
        } finally {
            if (!Objects.isNull(callStmt)) {
                callStmt.close();
            }
            if (!Objects.isNull(resultSet)) {
                resultSet.close();
            }
        }
        return map;
    }

    public Map<String, String> getCulturesByField(String nomeParcela) throws SQLException {
        CallableStatement callStmt = null;
        ResultSet resultSet = null;
        Map<String, String> map = new HashMap<>();

        try {
            Connection connection = DatabaseConnection.getInstance().getConnection();
            callStmt = connection.prepareCall("{ ? = call getCulturesByField(?) }");

            callStmt.registerOutParameter(1, OracleTypes.NUMBER);

            callStmt.setString(1, nomeParcela);

            callStmt.execute();

            resultSet = (ResultSet) callStmt.getObject(1);
            connection.commit();
        } finally {
            if (!Objects.isNull(callStmt)) {
                callStmt.close();
            }
            if (!Objects.isNull(resultSet)) {
                resultSet.close();
            }
        }
        return map;
    }
}
