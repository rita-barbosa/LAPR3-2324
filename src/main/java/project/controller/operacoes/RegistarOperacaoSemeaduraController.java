package project.controller.operacoes;

import project.domain.dataAccess.*;

import java.util.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Objects;

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

    public  Map<Integer, String>  getFieldsIDs() throws SQLException {
        return fieldsRepository.getFieldIds();
    }

    public Map<Integer, String> getCulturesIDs() throws SQLException {
        return cultureRepository.getCultures();
    }

    public boolean registerOperation(Integer idParcela, String designacaoOperacaoAgricola, Integer idCultura, Date dataOperacao, String tipoUnidade, Double quantidade) throws SQLException {
        return operacaoRepository.registerCultureOperation(idParcela, designacaoOperacaoAgricola, idCultura, dataOperacao, tipoUnidade, quantidade);
    }
}
