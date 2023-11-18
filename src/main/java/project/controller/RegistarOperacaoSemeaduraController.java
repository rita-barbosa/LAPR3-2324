package project.controller;

import project.dataAccess.*;
import java.math.BigDecimal;
import java.util.*;
import java.sql.SQLException;

public class RegistarOperacaoSemeaduraController {

    private OperacaoRepository operacaoRepository;
    private UnitsRepository unitsRepository;
    private FieldsRepository fieldsRepository;
    private CultureRepository cultureRepository;

    public RegistarOperacaoSemeaduraController() {
        getOperacaoRepository();
        getUnitsRepository();
        getCultureRepository();
        getFieldsRepository();
    }

    private OperacaoRepository getOperacaoRepository() {
        if (Objects.isNull(operacaoRepository)) {
            Repositories repositories = Repositories.getInstance();
            operacaoRepository = repositories.getOperacaoRepository();
        }
        return operacaoRepository;
    }

    private UnitsRepository getUnitsRepository() {
        if (Objects.isNull(unitsRepository)) {
            Repositories repositories = Repositories.getInstance();
            unitsRepository = repositories.getUnitsRepository();
        }
        return unitsRepository;
    }

    private CultureRepository getCultureRepository() {
        if (Objects.isNull(cultureRepository)) {
            Repositories repositories = Repositories.getInstance();
            cultureRepository = repositories.getCultureRepository();
        }
        return cultureRepository;
    }

    private FieldsRepository getFieldsRepository() {
        if (Objects.isNull(fieldsRepository)) {
            Repositories repositories = Repositories.getInstance();
            fieldsRepository = repositories.getFieldsRepository();
        }
        return fieldsRepository;
    }


    public List<String> getUnitTypes() throws SQLException {
        return unitsRepository.getUnitDesignations();
    }

    public  Map<BigDecimal, String>  getFieldsIDs() throws SQLException {
        return fieldsRepository.getFieldIds();
    }

    public Map<BigDecimal, String> getCulturesIDs() throws SQLException {
        return cultureRepository.getCultures();
    }

    public boolean registerOperation(Integer idParcela, String designacaoOperacaoAgricola, Integer idCultura, Date dataOperacao, String tipoUnidade, Double quantidade) throws SQLException {
        return operacaoRepository.registerCultureOperation(idParcela, designacaoOperacaoAgricola, idCultura, dataOperacao, tipoUnidade, quantidade);
    }

    public boolean verifyIfOperationExists(Integer idParcela, String designacaoOperacaoAgricola, Integer idCultura, Date dataOperacao, String tipoUnidade, Double quantidade) throws SQLException {
        return operacaoRepository.verifyIfOperationExists(idParcela, designacaoOperacaoAgricola, idCultura, dataOperacao, tipoUnidade, quantidade);

    }
}
