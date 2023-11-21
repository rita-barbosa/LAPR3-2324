package project.controller;

import project.dataAccess.*;

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

    public  List<String> getFieldsNames() throws SQLException {
        return fieldsRepository.getFieldsNames();
    }

    public List<String> getCultures() throws SQLException {
        return cultureRepository.getCultures();
    }

    public boolean registerOperation(String nomeParcela, String desigEstadoFenologico, String designacaoOperacaoAgricola, String nomeComum, String variedade, Date dataOperacao, String tipoUnidade, Double quantidade) throws SQLException {
        return operacaoRepository.registerSemeaduraOperation(nomeParcela, desigEstadoFenologico, designacaoOperacaoAgricola, nomeComum, variedade, dataOperacao, tipoUnidade, quantidade);
    }

    public boolean verifyIfOperationExists(String nomeParcela, String designacaoOperacaoAgricola, String nomeComum, String variedade, Date dataOperacao, String tipoUnidade, Double quantidade) throws SQLException {
        return operacaoRepository.verifyIfCulturaOperationExists(nomeParcela, designacaoOperacaoAgricola, nomeComum, variedade, dataOperacao, tipoUnidade, quantidade);

    }

    public List<String> showPlantGrowthStage() {
        return cultureRepository.getPlantGrowthStage();
    }
}
