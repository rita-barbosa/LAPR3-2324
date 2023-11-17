package project.controller;

import project.domain.OperacaoCultura;
import project.domain.dataAccess.OperacaoRepository;
import project.domain.dataAccess.Repositories;

import java.sql.SQLException;

import java.util.List;
import java.util.Objects;

public class SelectStatementTestController {

    private OperacaoRepository operacaoRepository;

    public SelectStatementTestController(){
        getOperacaoRepository();
    }

    private OperacaoRepository getOperacaoRepository() {
        if (Objects.isNull(operacaoRepository)) {
            Repositories repositories = Repositories.getInstance();
            operacaoRepository = repositories.getOperacaoRepository();
        }
        return operacaoRepository;
    }

    public List<OperacaoCultura> getSemeaduraOperationList() throws SQLException {
        return operacaoRepository.getSemeaduraOperationsList();
    }

}
