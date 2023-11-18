package project.domain.dataAccess;

public class Repositories {

    private static final Repositories instance = new Repositories();
    private OperacaoRepository operacaoRepository;

    private UnitsRepository unitsRepository;
    private FieldsRepository fieldsRepository;
    private CultureRepository cultureRepository;

    public UnitsRepository getUnitsRepository() {
        return unitsRepository;
    }

    public FieldsRepository getFieldsRepository() {
        return fieldsRepository;
    }

    public CultureRepository getCultureRepository() {
        return cultureRepository;
    }

    private Repositories() {
        operacaoRepository = new OperacaoRepository();
        unitsRepository = new UnitsRepository();
        fieldsRepository = new FieldsRepository();
        cultureRepository = new CultureRepository();
    }

    public static Repositories getInstance() {
        return instance;
    }

    public OperacaoRepository getOperacaoRepository() {
        return operacaoRepository;
    }

}
