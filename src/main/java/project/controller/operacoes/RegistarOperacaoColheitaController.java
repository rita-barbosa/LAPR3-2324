package project.controller.operacoes;

import project.domain.dataAccess.*;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class RegistarOperacaoColheitaController {
    private OperacaoRepository operacaoRepository;
    private UnitsRepository unitsRepository;
    private FieldsRepository fieldsRepository;
    private CultureRepository cultureRepository;
    private DatesRepository datesRepository;

    public RegistarOperacaoColheitaController() {
        getOperacaoRepository();
        getUnitsRepository();
        getCultureRepository();
        getFieldsRepository();
        getDatesRepository();
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

    private DatesRepository getDatesRepository() {
        if (Objects.isNull(datesRepository)) {
            Repositories repositories = Repositories.getInstance();
            datesRepository = repositories.getDatesRepository();
        }
        return datesRepository;
    }

    public List<String> getUnitTypes() throws SQLException {
        return unitsRepository.getUnitDesignations();
    }

    public Map<BigDecimal, String> getFieldsIDs() throws SQLException {
        return fieldsRepository.getFieldIds();
    }

    public Map<BigDecimal, String> getCulturesIDs() throws SQLException {
        return cultureRepository.getCultures();
    }

    public Date getEndDate() throws  SQLException {
        return datesRepository.getBeginDate();
    }

    public void registerOperation(Integer idParcela, Integer idCultura, Date dataInicio, Date dataFim, Date dataOperacao, String tipoUnidade, Double quantidade) throws SQLException {
        operacaoRepository.registerColheitaOperation(idParcela, idCultura, dataOperacao, dataInicio, dataFim, tipoUnidade, quantidade);
    }
}
