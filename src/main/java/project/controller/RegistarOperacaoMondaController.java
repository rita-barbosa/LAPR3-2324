package project.controller;

import project.dataAccess.*;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class RegistarOperacaoMondaController {

    private OperacaoRepository operacaoRepository;
    private UnitsRepository unitsRepository;
    private FieldsRepository fieldsRepository;
    private CultureRepository cultureRepository;

    public RegistarOperacaoMondaController() {
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

    public List<String> getFieldsNames() throws SQLException {
        return fieldsRepository.getFieldsNames();
    }

//    public Map<String, String> getCulturesByField(String nomeParcela) throws SQLException { // key -> nomeComum; value -> variedade
//        return cultureRepository.getCulturesByField(nomeParcela);
//    }

    public boolean registerMondaOperation(String nomeParcela, String nomeComum, String variedade,Date dataOperacao, String tipoUnidade, Double quantidade) throws SQLException {
        return operacaoRepository.registarOperacaoMonda(nomeParcela, nomeComum, variedade, dataOperacao, tipoUnidade, quantidade);
    }

    public boolean verifyIfOperationExists(String nomeParcela, String nomeComum, String variedade, Date dataOperacao, String tipoUnidade, Double quantidade) throws SQLException {
        operacaoRepository.verifyIfCulturaOperationExists(nomeParcela,"Monda",nomeComum,variedade,dataOperacao,tipoUnidade,quantidade);
        return false;
    }
}
